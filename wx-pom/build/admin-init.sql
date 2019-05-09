/*
Navicat MySQL Data Transfer

Source Server         : 172.19.137.246-L430
Source Server Version : 50610
Source Host           : 172.19.137.246:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-12-26 15:30:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(32) NOT NULL COMMENT '节点名称',
  `PARENT_ID` bigint(20) NOT NULL COMMENT '父节点ID',
  `URL` varchar(256) DEFAULT NULL COMMENT 'url链接',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '节点描述',
  `STATUS` smallint(4) NOT NULL DEFAULT '1' COMMENT '状态，0未启用，1启用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='系统资源配置';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '系统配置', '-1', '', '系统配置', '1');
INSERT INTO `sys_resource` VALUES ('2', '菜单配置', '1', '/sysResource/index.htm', '菜单配置', '1');
INSERT INTO `sys_resource` VALUES ('3', '后台用户管理', '1', '/sysUser/index.htm', '后台用户管理', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_NAME` varchar(20) NOT NULL COMMENT '用户名',
  `ACCOUNT_NAME` varchar(32) NOT NULL COMMENT '账户',
  `PASSWORD` varchar(32) NOT NULL COMMENT '密码',
  `SALT` varchar(64) NOT NULL COMMENT '加盐值',
  `DESCRIPTION` varchar(128) DEFAULT NULL,
  `STATUS` int(2) NOT NULL DEFAULT '1' COMMENT '状态，1启动，0禁用',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INDEX_ACCOUNT_NAME` (`ACCOUNT_NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6000000003 DEFAULT CHARSET=utf8 COMMENT='系统管理用户记录';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('6000000001', 'admin', 'admin', '0bbac2380ca9af9156c4af5e1114b875', 'b6d97d8b2ff7d54a1b9819b4e9c78205', '请问', '1', '2016-06-22 02:29:53', '2016-06-22 02:29:55');

-- ----------------------------
-- Table structure for weixin_menu_config
-- ----------------------------
DROP TABLE IF EXISTS `weixin_menu_config`;
CREATE TABLE `weixin_menu_config` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MENU_NAME` varchar(32) NOT NULL COMMENT '菜单名称',
  `MENU_TYPE` varchar(32) NOT NULL DEFAULT 'view' COMMENT '菜单类型',
  `PARENT_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父层ID',
  `REPLY_ID` bigint(20) NOT NULL DEFAULT '-1' COMMENT '回复ID',
  `SORT_NO` tinyint(4) NOT NULL COMMENT '排序码',
  `ENABLE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否生效，1是，0否',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `INDEX_REPLY_ID` (`REPLY_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='微信菜单配置';

-- ----------------------------
-- Records of weixin_menu_config
-- ----------------------------
INSERT INTO `weixin_menu_config` VALUES ('1', 'test', 'view', '-1', '1', '1', '1', '2016-06-19 16:40:09', '2016-06-19 16:40:11');
INSERT INTO `weixin_menu_config` VALUES ('2', 'test2', 'view', '-1', '2', '2', '1', '2016-06-19 16:44:50', '2016-06-19 16:44:52');

-- ----------------------------
-- Table structure for weixin_reply_config
-- ----------------------------
DROP TABLE IF EXISTS `weixin_reply_config`;
CREATE TABLE `weixin_reply_config` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `KEYWORDS` varchar(60) NOT NULL COMMENT '关键字',
  `TITLE` varchar(64) NOT NULL COMMENT '标题',
  `DESCRIPTION` varchar(256) DEFAULT NULL COMMENT '描述',
  `REPLY_TEXT` varchar(512) DEFAULT NULL COMMENT '回复文本',
  `REPLY_LINK` varchar(256) DEFAULT NULL COMMENT '回复链接',
  `REPLY_IMG` varchar(256) DEFAULT NULL COMMENT '回复图片',
  `REPLY_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '回复类型,0:默认回复,1:文本,2:链接,3:关注回复,4:图文',
  `ENABLE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否生效，1是，0否',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='微信回复配置';

-- ----------------------------
-- Records of weixin_reply_config
-- ----------------------------
INSERT INTO `weixin_reply_config` VALUES ('1', 'test', '测试', '测试', 'test', 'http://www.baidu.com', null, '2', '1', '2016-06-19 16:41:01', '2016-06-19 16:41:03');
INSERT INTO `weixin_reply_config` VALUES ('2', 'test2', 'asd', 'asd', 'asdasd', 'http://www.youku.com', null, '2', '1', '2016-06-19 16:45:20', '2016-06-19 16:45:23');

-- ----------------------------
-- Table structure for weixin_reply_log
-- ----------------------------
DROP TABLE IF EXISTS `weixin_reply_log`;
CREATE TABLE `weixin_reply_log` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPEN_ID` varchar(32) NOT NULL COMMENT '微信用户id',
  `TYPE` varchar(24) NOT NULL DEFAULT 'text' COMMENT '类型，包括普通消息、事件消息',
  `CREATE_TIME` datetime NOT NULL COMMENT '操作时间',
  `CONTENT` varchar(512) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='微信回复日志';

-- ----------------------------
-- Records of weixin_reply_log
-- ----------------------------

-- ----------------------------
-- Table structure for weixin_user_record
-- ----------------------------
DROP TABLE IF EXISTS `weixin_user_record`;
CREATE TABLE `weixin_user_record` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPEN_ID` varchar(32) NOT NULL COMMENT '微信用户id',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INDEX_OPEN_ID` (`OPEN_ID`) USING BTREE,
  KEY `INDEX_UPDATE_TIME` (`UPDATE_TIME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='微信用户记录';

-- ----------------------------
-- Records of weixin_user_record
-- ----------------------------
