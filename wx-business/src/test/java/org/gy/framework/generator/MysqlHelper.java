package org.gy.framework.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: 连接数据库
 * 
 */
public class MysqlHelper {

    private static final Logger logger         = LoggerFactory.getLogger(MysqlHelper.class);

    private static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";

    private MysqlHelper() {

    }

    public static List<Entity> wrapEntities(String url,
                                            String user,
                                            String password,
                                            String schema,
                                            String... tableNames) {
        long start = System.currentTimeMillis();
        List<Entity> list = new LinkedList<Entity>();
        Connection conn = null;
        try {
            conn = getConnection(url, user, password);
            list = wrapEntities(conn, schema, tableNames);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(conn);
        }
        logger.debug("方法执行耗时：{}ms", System.currentTimeMillis() - start);
        return list;

    }

    public static List<Entity> wrapEntities(Connection conn,
                                            String schema,
                                            String... tableNames) {
        List<Entity> list = new LinkedList<Entity>();
        wrapTableInfo(conn, list, tableNames);// 获取table信息
        for (Entity entity : list) {
            wrapEntityInfo(conn, schema, entity);// 获取实体信息
        }
        logger.debug("满足条件表数量：schema={}，tableNum={}", schema, list.size());
        return list;
    }

    public static Connection getConnection(String url,
                                           String user,
                                           String password) {
        Connection conn = null;
        try {
            Class.forName(DEFAULT_DRIVER);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 功能描述: 获取属性信息
     * 
     * @param conn
     * @param schema
     * @param entity
     * @version 2.0.0
     * @author guanyang
     */
    private static void wrapEntityInfo(Connection conn,
                                       String schema,
                                       Entity entity) {
        PreparedStatement columnStmt = null;
        PreparedStatement tableStmt = null;
        ResultSet rsc = null;
        ResultSet rs = null;
        try {
            // 获取表注释
            logger.debug("获取表注释,schema={},tableName={}", schema, entity.getTableName());
            tableStmt = conn.prepareStatement("select TABLE_COMMENT from TABLES where TABLE_NAME=?");
            tableStmt.setString(1, entity.getTableName());
            tableStmt.execute("use information_schema");
            rsc = tableStmt.executeQuery();
            while (rsc.next()) {
                String tableComment = rsc.getString("TABLE_COMMENT");
                entity.setTableComment(tableComment);
            }

            logger.debug("获取列属性,schema={},tableName={}", schema, entity.getTableName());
            columnStmt = conn.prepareStatement("SELECT COLUMN_NAME,ORDINAL_POSITION,DATA_TYPE,COLUMN_KEY,EXTRA,COLUMN_COMMENT FROM COLUMNS WHERE table_name=? AND TABLE_SCHEMA=?");
            columnStmt.setString(1, entity.getTableName());
            columnStmt.setString(2, schema);
            columnStmt.execute("use information_schema");
            rs = columnStmt.executeQuery();
            List<Property> properties = new ArrayList<Property>();
            HashSet<String> packages = new HashSet<String>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                int ordinalPosition = rs.getInt("ORDINAL_POSITION");
                String dataType = rs.getString("DATA_TYPE");
                String columnKey = rs.getString("COLUMN_KEY");
                String extra = rs.getString("EXTRA");
                String columnComment = rs.getString("COLUMN_COMMENT");
                Property p = new Property();
                columnComment = ColumnToPropertyUtil.defaultIfBlank(columnComment).replaceAll("\n|\r", " ");
                p.setAnnotation(columnComment);
                p.setColName(columnName);
                p.setOrdinalPosition(ordinalPosition);
                p.setExtra(extra);
                p.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
                p.setPropertyName(ColumnToPropertyUtil.columnToProperty(columnName));
                String javaType = ColumnToPropertyUtil.getJavaType(dataType.toLowerCase());
                p.setJavaType(javaType.substring("java.lang.".length()));
                p.setPk("PRI".equalsIgnoreCase(columnKey));
                properties.add(p);
                if (!javaType.startsWith("java.lang.")) {
                    packages.add(javaType);
                }
            }
            entity.setCreateDate(new Date());
            entity.setProperties(properties);
            entity.setPackages(Arrays.asList(packages.toArray(new String[] {})));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeResultSet(rsc);
            closeStatement(tableStmt);
            closeResultSet(rs);
            closeStatement(columnStmt);
        }
    }

    /**
     * 功能描述: 获取table信息
     * 
     * @param conn
     * @param list
     * @param tableNames
     * @version 2.0.0
     * @author guanyang
     */
    private static void wrapTableInfo(Connection conn,
                                      List<Entity> list,
                                      String... tableNames) {
        logger.debug("获取表基本信息,tableNames={}", StringUtils.join(tableNames, ","));
        if (tableNames == null || tableNames.length == 0) {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.prepareStatement("SHOW TABLES");
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    Entity e = new Entity();
                    e.setTableName(tableName);
                    String property = ColumnToPropertyUtil.columnToProperty(tableName);
                    e.setClassName(StringUtils.capitalize(property));// 类名首字母大写
                    e.setLowerClassName(property);
                    list.add(e);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                closeResultSet(rs);
                closeStatement(stmt);
            }
        } else {
            for (String tableName : tableNames) {
                Entity e = new Entity();
                e.setTableName(tableName);
                String property = ColumnToPropertyUtil.columnToProperty(tableName);
                e.setClassName(StringUtils.capitalize(property));// 类名首字母大写
                e.setLowerClassName(property);
                list.add(e);
            }
        }
    }

    /**
     * 功能描述:
     * 
     * @param rsc
     * @version 2.0.0
     * @author guanyang
     */
    private static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 功能描述:
     * 
     * @param rsc
     * @version 2.0.0
     * @author guanyang
     */
    private static void closeResultSet(ResultSet rsc) {
        if (rsc != null) {
            try {
                rsc.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
