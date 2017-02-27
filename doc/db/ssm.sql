/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-02-27 10:04:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `sys_user_id` bigint(20) NOT NULL,
  `sys_user_login_name` varchar(50) NOT NULL,
  `sys_user_login_password` varchar(50) NOT NULL,
  `sys_user_status` varchar(1) NOT NULL,
  `sys_user_is_delete` varchar(1) NOT NULL,
  `sys_user_register_datetime` datetime NOT NULL,
  `sys_user_register_source` varchar(1) NOT NULL,
  `sys_user_type` varchar(1) NOT NULL,
  `sys_user_sex` varchar(1) NOT NULL,
  `sys_user_is_email_active` varchar(1) NOT NULL,
  `sys_user_is_mobile_active` varchar(1) NOT NULL,
  `sys_user_register_type` varchar(1) NOT NULL,
  `sys_user_pay_passwrod` varchar(50) DEFAULT NULL,
  `sys_user_icon` varchar(100) DEFAULT NULL,
  `sys_user_real_name` varchar(20) DEFAULT NULL,
  `sys_user_email` varchar(50) DEFAULT NULL,
  `sys_user_mobile` varchar(20) DEFAULT NULL,
  `sys_user_weibo_id` varchar(36) DEFAULT NULL,
  `sys_user_qq_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'YouMeek1', 'e10adc3949ba59abbe56e057f20f883e', '0', 'N', '2016-02-24 00:12:23', '0', '0', '0', 'Y', 'Y', '0', 'e10adc3949ba59abbe56e057f20f883e', '', '张觉恩1', '363379441@qq.com', '13800000001', '', '');
INSERT INTO `sys_user` VALUES ('2', 'YouMeek2', 'e10adc3949ba59abbe56e057f20f883e', '0', 'N', '2016-02-24 00:12:23', '0', '0', '0', 'Y', 'Y', '0', 'e10adc3949ba59abbe56e057f20f883e', '', '张觉恩2', '363379442@qq.com', '13800000002', '', '');
INSERT INTO `sys_user` VALUES ('3', 'YouMeek3', 'e10adc3949ba59abbe56e057f20f883e', '0', 'N', '2016-02-24 00:12:23', '0', '0', '0', 'Y', 'Y', '0', 'e10adc3949ba59abbe56e057f20f883e', '', '张觉恩3', '363379443@qq.com', '13800000003', '', '');
INSERT INTO `sys_user` VALUES ('4', 'YouMeek4', 'e10adc3949ba59abbe56e057f20f883e', '0', 'N', '2016-02-24 00:12:23', '0', '0', '0', 'Y', 'Y', '0', 'e10adc3949ba59abbe56e057f20f883e', '', '张觉恩4', '363379444@qq.com', '13800000004', '', '');
INSERT INTO `sys_user` VALUES ('5', 'YouMeek5', 'e10adc3949ba59abbe56e057f20f883e', '0', 'N', '2016-02-24 00:12:23', '0', '0', '0', 'Y', 'Y', '0', 'e10adc3949ba59abbe56e057f20f883e', '', '张觉恩5', '363379445@qq.com', '13800000005', '', '');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` char(32) NOT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `pid` char(32) NOT NULL,
  `res_id` char(32) NOT NULL,
  `menu_url` varchar(200) NOT NULL,
  `image_url` varchar(200) NOT NULL,
  `order` int(2) NOT NULL DEFAULT '1' COMMENT '排序 顺序',
  `menu_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`pid`,`res_id`,`menu_url`,`image_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` char(32) NOT NULL,
  `res_desc` varchar(255) DEFAULT NULL COMMENT '资源描述',
  `res_type` int(2) NOT NULL DEFAULT '1' COMMENT '资源类型 1、链接 2 、菜单',
  `res_url` varchar(255) NOT NULL COMMENT '资源地址',
  `res_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagD', '', '1', 'GET@/sysUserController/showUserToJspById/*', '查询用户');
INSERT INTO `t_resource` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagE', '', '1', 'GET@/sysUserController/showUserToJSONById/*', '查询用户Json');
INSERT INTO `t_resource` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3Hagm', '', '1', 'GET@/show.jsp', '查看当前用户信息');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` char(32) NOT NULL,
  `role_name` varchar(32) DEFAULT NULL COMMENT '资源描述',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '资源描述',
  `role_type` int(2) NOT NULL DEFAULT '1' COMMENT '1=C端，2=B端，缺省为2',
  `role_status` int(2) NOT NULL DEFAULT '1' COMMENT '1=有效，2=已禁用，9=已删除',
  `role_weight` int(2) DEFAULT '1' COMMENT '角色权重',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagF', '管理员', null, '1', '1', '1');
INSERT INTO `t_role` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagG', '普通用户', null, '1', '1', '1');

-- ----------------------------
-- Table structure for t_role_res
-- ----------------------------
DROP TABLE IF EXISTS `t_role_res`;
CREATE TABLE `t_role_res` (
  `id` char(32) NOT NULL,
  `link_type` int(2) NOT NULL DEFAULT '1' COMMENT '主体类型1=U,2=R',
  `res_id` char(32) NOT NULL,
  `role_id` char(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_res
-- ----------------------------
INSERT INTO `t_role_res` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagH', '1', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagD', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagF');
INSERT INTO `t_role_res` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagI', '1', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagE', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagF');
INSERT INTO `t_role_res` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagJ', '1', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagD', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagG');
INSERT INTO `t_role_res` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3Hagn', '1', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3Hagm', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagG');
INSERT INTO `t_role_res` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3Hago', '1', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3Hagm', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagF');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `u_id` varchar(32) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `register_type` int(1) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `email` varchar(120) DEFAULT '',
  `real_name` varchar(120) DEFAULT NULL,
  `id_type` int(2) DEFAULT NULL,
  `id_number` varchar(22) DEFAULT NULL,
  `pic_group_name` varchar(8) DEFAULT NULL,
  `pic_file_name` varchar(128) DEFAULT NULL,
  `regist_resource` int(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `active_flag` int(1) DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
  `last_login_time` timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
  `channel_type` int(1) DEFAULT '1',
  `recommend_code` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagA', 'IN15041001307961', 'user', '123456', 'user', null, null, null, null, null, '', null, null, null, null, null, null, null, null, null, '2016-08-12 10:28:45.551566', null, '2016-08-12 10:28:31.000000', '2016-08-14 10:28:38.000000', '1', null);
INSERT INTO `t_user` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagC', 'IN15041001307960', 'admin', '123456', 'admin', '0', null, '1', '13111111111', null, '', null, null, '', null, null, null, null, null, null, '2016-08-12 10:28:42.883413', null, '2016-08-12 10:28:34.000000', '2016-08-02 10:28:41.000000', '1', null);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` char(32) NOT NULL,
  `role_id` char(32) NOT NULL,
  `user_id` char(32) NOT NULL,
  `link_type` int(2) NOT NULL DEFAULT '1' COMMENT '主体类型1=U,2=R',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagH', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagG', 'IN15041001307961', '1');
INSERT INTO `t_user_role` VALUES ('MhRFj1GHyrNpFuCw4moCqoOl5xH3HagK', 'MhRFj1GHyrNpFuCw4moCqoOl5xH3HagF', 'IN15041001307960', '1');
