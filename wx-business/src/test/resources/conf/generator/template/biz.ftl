package ${entity.javaPackage};

import java.util.List;
import org.springframework.stereotype.Service;
import ${entity.rootPackage}.bo.${entity.className}Bo;
import ${entity.rootPackage}.dao.${entity.className}Mapper;
import ${entity.rootPackage}.model.${entity.className};
import ${entity.rootPackage}.model.${entity.className}Example;

/**
 * 功能描述：${entity.tableComment}Biz
 * 
 * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Service
public class ${entity.className}Biz extends BaseBiz {

    <#list entity.properties as property>
    <#if property.pk>
    <#assign primary=property >
    <#break>
    </#if>
    </#list>

    /**
     * 功能描述: 添加非空字段，返回主键
     * 
     * @param entity 实体
     * @return 主键
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    public ${primary.javaType} insertSelective(${entity.className} entity) {
        sqlSessionMaster.getMapper(${entity.className}Mapper.class).insertSelective(entity);
        return entity.get${primary.propertyName?cap_first}();
    }
    

    /**
     * 功能描述: 根据主键更新非空字段
     * 
     * @param entity 实体
     * @return 成功的条数
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    public int updateByPrimaryKeySelective(${entity.className} entity) {
        return sqlSessionMaster.getMapper(${entity.className}Mapper.class).updateByPrimaryKeySelective(entity);
    }
    
    /**
     * 功能描述: 根据主键查询
     *
     * @param id 主键
     * @return 实体
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    public ${entity.className} selectByPrimaryKey(${primary.javaType} id) {
        return sqlSessionSlave.getMapper(${entity.className}Mapper.class).selectByPrimaryKey(id);
    }
    
    /**
     * 功能描述: 根据主键批量删除
     * 
     * @param ids 主键集合
     * @return 成功的条数
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    public int deleteByPrimaryKey(List<Long> ids) {
        ${entity.className}Example example = new ${entity.className}Example();
        example.createCriteria().and${primary.propertyName?cap_first}In(ids);
        return sqlSessionMaster.getMapper(${entity.className}Mapper.class).deleteByExample(example);
    }
    
    /**
     * 功能描述: 根据主键删除
     * 
     * @param id 主键
     * @return 成功的条数
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
	public int deleteByPrimaryKey(${primary.javaType} id){
        return sqlSessionMaster.getMapper(${entity.className}Mapper.class).deleteByPrimaryKey(id);
    }        
    
    /**
     * 功能描述: 获取满足条件的记录列表，带分页
     * 
     * @param query
     * @return
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    public List<${entity.className}> queryForList(${entity.className}Bo query) {
        return sqlSessionSlave.selectList("${entity.tableName?upper_case}.QUERY_FOR_LIST", query);
    }
    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    public int queryForCount(${entity.className}Bo query) {
        return sqlSessionSlave.selectOne("${entity.tableName?upper_case}.QUERY_FOR_COUNT", query);
    }
    
    
    
}
