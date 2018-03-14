-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.21 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5261
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 csap_db 的数据库结构
DROP DATABASE IF EXISTS `csap_db`;
CREATE DATABASE IF NOT EXISTS `csap_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `csap_db`;

-- 导出  表 csap_db.carousel_setting 结构
DROP TABLE IF EXISTS `carousel_setting`;
CREATE TABLE IF NOT EXISTS `carousel_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL DEFAULT '0' COMMENT '名字',
  `summary` text COMMENT '简介',
  `linkTo` text COMMENT '点击内容链接',
  `pic` text COMMENT '图片链接',
  `seq` int(11) DEFAULT '0' COMMENT '序号',
  `cAt` datetime DEFAULT NULL,
  `mAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  `area` varchar(200) DEFAULT NULL COMMENT '所属区域',
  `operId` int(11) DEFAULT '0',
  `status` varchar(50) DEFAULT '0' COMMENT '状态 0:正常，1:禁用',
  PRIMARY KEY (`id`),
  KEY `name_seq_area_status` (`name`,`seq`,`area`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮播区域设置';

-- 正在导出表  csap_db.carousel_setting 的数据：~0 rows (大约)
DELETE FROM `carousel_setting`;
/*!40000 ALTER TABLE `carousel_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `carousel_setting` ENABLE KEYS */;

-- 导出  表 csap_db.doctor_info 结构
DROP TABLE IF EXISTS `doctor_info`;
CREATE TABLE IF NOT EXISTS `doctor_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `summary` text COMMENT '简介',
  `sex` int(11) DEFAULT NULL COMMENT '1:男 2:女',
  `tel1` varchar(50) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `hospital` varchar(200) DEFAULT NULL,
  `licenseNo` varchar(50) DEFAULT NULL COMMENT '执照号',
  `weixin` varchar(200) DEFAULT NULL COMMENT '微信号',
  `tel2` varchar(50) DEFAULT NULL,
  `tel3` varchar(50) DEFAULT NULL,
  `introduction` text COMMENT '详细介绍',
  `cAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  `mAt` datetime DEFAULT NULL,
  `operId` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name_sex_tel1_email_licenseNo_weixin_tel2_tel3_dAt` (`name`,`sex`,`tel1`,`email`,`licenseNo`,`weixin`,`tel2`,`tel3`,`dAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生资料表\r\n';

-- 正在导出表  csap_db.doctor_info 的数据：~0 rows (大约)
DELETE FROM `doctor_info`;
/*!40000 ALTER TABLE `doctor_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `doctor_info` ENABLE KEYS */;

-- 导出  表 csap_db.doctor_tax 结构
DROP TABLE IF EXISTS `doctor_tax`;
CREATE TABLE IF NOT EXISTS `doctor_tax` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dId` int(11) DEFAULT NULL COMMENT '医生ID',
  `taxId` int(11) DEFAULT NULL COMMENT '分类ID  ',
  `type` int(11) DEFAULT NULL COMMENT '1:擅长疾病，2:手术方式，3:职称',
  PRIMARY KEY (`id`),
  KEY `dId_taxId_type` (`dId`,`taxId`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生分类关系表\r\n';

-- 正在导出表  csap_db.doctor_tax 的数据：~0 rows (大约)
DELETE FROM `doctor_tax`;
/*!40000 ALTER TABLE `doctor_tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `doctor_tax` ENABLE KEYS */;

-- 导出  表 csap_db.doctor_visit 结构
DROP TABLE IF EXISTS `doctor_visit`;
CREATE TABLE IF NOT EXISTS `doctor_visit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dId` int(11) DEFAULT NULL COMMENT '医生ID',
  `num` int(11) DEFAULT NULL COMMENT '看诊数量',
  `address` text COMMENT '出诊地址',
  `visitBAt` datetime DEFAULT NULL COMMENT '出诊开始时间',
  `visitEAt` datetime DEFAULT NULL COMMENT '出诊结束时间',
  `visitDate` date DEFAULT NULL COMMENT '出诊日期',
  `status` int(11) DEFAULT NULL COMMENT '0:正常1:停诊',
  `dAt` datetime DEFAULT NULL,
  `cAt` datetime DEFAULT NULL,
  `mAt` datetime DEFAULT NULL,
  `operId` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dId_num_visitBAt_visitEAt_visitDate_status_dAt` (`dId`,`num`,`visitBAt`,`visitEAt`,`visitDate`,`status`,`dAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生出诊计划';

-- 正在导出表  csap_db.doctor_visit 的数据：~0 rows (大约)
DELETE FROM `doctor_visit`;
/*!40000 ALTER TABLE `doctor_visit` DISABLE KEYS */;
/*!40000 ALTER TABLE `doctor_visit` ENABLE KEYS */;

-- 导出  表 csap_db.doctor_visit_api 结构
DROP TABLE IF EXISTS `doctor_visit_api`;
CREATE TABLE IF NOT EXISTS `doctor_visit_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` text,
  `name` varchar(200) DEFAULT '0' COMMENT '外部API名称',
  `dvId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生出诊外部API';

-- 正在导出表  csap_db.doctor_visit_api 的数据：~0 rows (大约)
DELETE FROM `doctor_visit_api`;
/*!40000 ALTER TABLE `doctor_visit_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `doctor_visit_api` ENABLE KEYS */;

-- 导出  表 csap_db.like_records 结构
DROP TABLE IF EXISTS `like_records`;
CREATE TABLE IF NOT EXISTS `like_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `targetId` int(11) DEFAULT NULL COMMENT '点赞内容ID',
  `targetObj` varchar(50) DEFAULT NULL COMMENT '点赞目标表名',
  `cAt` datetime DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  KEY `userId_objId_targetTable` (`userId`,`targetId`,`targetObj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞记录\r\n';

-- 正在导出表  csap_db.like_records 的数据：~0 rows (大约)
DELETE FROM `like_records`;
/*!40000 ALTER TABLE `like_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_records` ENABLE KEYS */;

-- 导出  表 csap_db.post_info 结构
DROP TABLE IF EXISTS `post_info`;
CREATE TABLE IF NOT EXISTS `post_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `cAt` datetime DEFAULT NULL,
  `mAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  `checkStatus` varchar(2) DEFAULT NULL COMMENT '00:正常，01:待审，02:未通过',
  `status` varchar(2) DEFAULT NULL COMMENT '0:正常，1:禁用',
  `origin` varchar(2) DEFAULT NULL COMMENT '来源0:PC,1:ios:2android',
  `checkOperId` int(11) DEFAULT NULL COMMENT '审核人员ID',
  `lastReply` datetime DEFAULT NULL COMMENT '最后回复时间',
  `replyCount` int(11) NOT NULL DEFAULT '0' COMMENT '回复数量',
  `ifTop` int(11) NOT NULL DEFAULT '0' COMMENT '置顶0是1否',
  `operId` int(11) unsigned DEFAULT NULL COMMENT '作者ID',
  `viewCount` int(11) unsigned DEFAULT NULL COMMENT '查看次数',
  `likeCount` int(11) unsigned DEFAULT NULL COMMENT '点赞数量',
  `taxId` int(11) unsigned DEFAULT NULL COMMENT '板块 plate ID',
  PRIMARY KEY (`id`),
  KEY `title_cAt_dAt_checkStatus_status_checkOperId_operId_taxId` (`title`,`cAt`,`dAt`,`checkStatus`,`status`,`checkOperId`,`operId`,`taxId`,`lastReply`,`replyCount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='帖子信息';

-- 正在导出表  csap_db.post_info 的数据：~0 rows (大约)
DELETE FROM `post_info`;
/*!40000 ALTER TABLE `post_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_info` ENABLE KEYS */;

-- 导出  表 csap_db.replys 结构
DROP TABLE IF EXISTS `replys`;
CREATE TABLE IF NOT EXISTS `replys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `targetId` int(11) NOT NULL COMMENT '回复目标id',
  `replyId` int(11) NOT NULL COMMENT '回复Id',
  `likeCount` int(11) NOT NULL COMMENT '点赞数量',
  `bestReply` int(11) NOT NULL COMMENT '0是1否 最佳回复',
  `cAt` datetime NOT NULL,
  `dAt` datetime NOT NULL,
  `mAt` datetime NOT NULL,
  `content` text NOT NULL COMMENT '回复内容',
  `userId` int(11) NOT NULL COMMENT 'user ID',
  `targetObj` varchar(200) DEFAULT NULL COMMENT '回复目标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回复';

-- 正在导出表  csap_db.replys 的数据：~0 rows (大约)
DELETE FROM `replys`;
/*!40000 ALTER TABLE `replys` DISABLE KEYS */;
/*!40000 ALTER TABLE `replys` ENABLE KEYS */;

-- 导出  表 csap_db.reservation_records 结构
DROP TABLE IF EXISTS `reservation_records`;
CREATE TABLE IF NOT EXISTS `reservation_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '序号',
  `userId` int(11) DEFAULT NULL,
  `dvId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId_dvId` (`userId`,`dvId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约记录\r\n';

-- 正在导出表  csap_db.reservation_records 的数据：~0 rows (大约)
DELETE FROM `reservation_records`;
/*!40000 ALTER TABLE `reservation_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation_records` ENABLE KEYS */;

-- 导出  表 csap_db.s_attachment 结构
DROP TABLE IF EXISTS `s_attachment`;
CREATE TABLE IF NOT EXISTS `s_attachment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `path` varchar(200) DEFAULT NULL COMMENT '存储路径',
  `module` varchar(50) DEFAULT NULL COMMENT '所属模块',
  `objId` int(11) DEFAULT NULL COMMENT '关联对象id',
  `cAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fname` (`name`),
  KEY `module` (`module`),
  KEY `objId` (`objId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  csap_db.s_attachment 的数据：~0 rows (大约)
DELETE FROM `s_attachment`;
/*!40000 ALTER TABLE `s_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_attachment` ENABLE KEYS */;

-- 导出  表 csap_db.s_content 结构
DROP TABLE IF EXISTS `s_content`;
CREATE TABLE IF NOT EXISTS `s_content` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` text COMMENT '标题',
  `text` longtext COMMENT '内容',
  `summary` text COMMENT '摘要',
  `linkTo` varchar(256) DEFAULT NULL COMMENT '引用的连接地址，用于区分是原创还是引用',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `module` varchar(32) DEFAULT NULL COMMENT '模型',
  `userid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `userip` varchar(128) DEFAULT NULL COMMENT 'IP地址',
  `userAgent` text COMMENT '发布浏览agent',
  `parentId` int(20) unsigned DEFAULT NULL COMMENT '父级内容ID',
  `status` varchar(32) DEFAULT NULL COMMENT '状态,00:正常，01，待审核，02，未通过审核',
  `voteUp` int(11) unsigned DEFAULT '0' COMMENT '支持人数',
  `voteDown` int(11) unsigned DEFAULT '0' COMMENT '反对人数',
  `rate` int(11) DEFAULT NULL COMMENT '评分分数',
  `ratecount` int(10) unsigned DEFAULT '0' COMMENT '评分次数',
  `commentStatus` varchar(2) DEFAULT NULL COMMENT '评论状态',
  `commentCount` int(11) unsigned DEFAULT '0' COMMENT '评论总数',
  `commentTime` datetime DEFAULT NULL COMMENT '最后评论时间',
  `viewCount` int(11) unsigned DEFAULT '0' COMMENT '访问量',
  `cAt` datetime DEFAULT NULL COMMENT '创建日期',
  `dAt` datetime DEFAULT NULL COMMENT '删除时间',
  `mAt` datetime DEFAULT NULL COMMENT '修改时间',
  `metaKeywords` varchar(256) DEFAULT NULL COMMENT 'SEO关键字',
  `metaDesc` varchar(256) DEFAULT NULL COMMENT 'SEO描述信息',
  `remarks` text COMMENT '备注信息',
  `good` varchar(1) DEFAULT NULL COMMENT '精华0：是，1：否',
  `pAt` datetime DEFAULT NULL COMMENT '发布时间',
  `top` varchar(1) DEFAULT NULL COMMENT '置顶0：是，1：否',
  `flag` varchar(2) DEFAULT '' COMMENT '00,正常，01，草稿',
  `collectCount` int(11) DEFAULT '0' COMMENT '收藏次数',
  `laudCount` int(11) DEFAULT '0' COMMENT '点赞次数',
  PRIMARY KEY (`id`),
  KEY `user_id` (`userid`),
  KEY `parent_id` (`parentId`),
  KEY `content_module` (`module`),
  KEY `created` (`cAt`),
  KEY `vote_down` (`voteDown`),
  KEY `vote_up` (`voteUp`),
  KEY `view_count` (`viewCount`),
  KEY `good` (`good`),
  KEY `top` (`top`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容表，用于存放比如文章、帖子、商品、问答等用户自定义模型内容。';

-- 正在导出表  csap_db.s_content 的数据：~0 rows (大约)
DELETE FROM `s_content`;
/*!40000 ALTER TABLE `s_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_content` ENABLE KEYS */;

-- 导出  表 csap_db.s_mapping 结构
DROP TABLE IF EXISTS `s_mapping`;
CREATE TABLE IF NOT EXISTS `s_mapping` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cid` int(20) unsigned NOT NULL COMMENT '内容ID',
  `tid` int(20) unsigned NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`),
  KEY `taxonomy_id` (`tid`),
  KEY `content_id` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容和分类的多对多映射关系。';

-- 正在导出表  csap_db.s_mapping 的数据：~0 rows (大约)
DELETE FROM `s_mapping`;
/*!40000 ALTER TABLE `s_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_mapping` ENABLE KEYS */;

-- 导出  表 csap_db.s_param 结构
DROP TABLE IF EXISTS `s_param`;
CREATE TABLE IF NOT EXISTS `s_param` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `k` varchar(100) DEFAULT NULL COMMENT '键',
  `val` text COMMENT '值',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表，设置系统参数信息';

-- 正在导出表  csap_db.s_param 的数据：~0 rows (大约)
DELETE FROM `s_param`;
/*!40000 ALTER TABLE `s_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_param` ENABLE KEYS */;

-- 导出  表 csap_db.s_res 结构
DROP TABLE IF EXISTS `s_res`;
CREATE TABLE IF NOT EXISTS `s_res` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '资源名',
  `url` varchar(255) DEFAULT NULL COMMENT '资源url',
  `description` varchar(100) DEFAULT NULL COMMENT '说明',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `logged` char(1) DEFAULT NULL COMMENT '是否需要记录日志1：是，0：否',
  `type` varchar(2) DEFAULT NULL COMMENT '资源类型,0:菜单，1：服务',
  `enabled` varchar(2) DEFAULT 'y' COMMENT '是否可用 y:是，n:否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='资源表，保存可访问资源数据';

-- 正在导出表  csap_db.s_res 的数据：~33 rows (大约)
DELETE FROM `s_res`;
/*!40000 ALTER TABLE `s_res` DISABLE KEYS */;
INSERT INTO `s_res` (`id`, `name`, `url`, `description`, `pid`, `seq`, `logged`, `type`, `enabled`) VALUES
	(1, '系统管理', '/admin', NULL, 0, 1, NULL, '0', 'y'),
	(2, '系统参数设置', '/admin/param', NULL, 1, 1, NULL, '0', 'y'),
	(3, '用户管理', '/admin/user', NULL, 1, 2, NULL, '0', 'y'),
	(4, '角色管理', '/admin/role', NULL, 1, 3, NULL, '0', 'y'),
	(5, '新闻管理', '/admin/art', NULL, 1, 4, NULL, '0', 'y'),
	(7, '分类管理', '/admin/tax', NULL, 1, 5, NULL, '0', 'y'),
	(19, '系统管理服务', '/ad00', NULL, 0, 5, NULL, '1', 'y'),
	(20, '系统参数设置服务', '/ad00/getSettingJSON', NULL, 19, 1, NULL, '1', 'y'),
	(21, '系统参数设置保存服务', '/ad00/save', NULL, 19, 2, NULL, '1', 'y'),
	(22, '用户管理服务', '/ad01', NULL, 0, 6, NULL, '1', 'y'),
	(23, '用户管理查询服务', '/ad01/list', NULL, 22, 1, NULL, '1', 'y'),
	(24, '用户管理新增服务', '/ad01/save', NULL, 22, 2, NULL, '1', 'y'),
	(25, '用户管理更新服务', '/ad01/update', NULL, 22, 3, NULL, '1', 'y'),
	(26, '用户管理删除服务', '/ad01/del', NULL, 22, 4, NULL, '1', 'y'),
	(27, '用户管理禁用服务', '/ad01/forbidden', NULL, 22, 5, NULL, '1', 'y'),
	(28, '用户管理恢复服务', '/ad01/resumed', NULL, 22, 6, NULL, '1', 'y'),
	(29, '用户重置密码服务', '/ad01/resetPwd', NULL, 22, 7, NULL, '1', 'y'),
	(30, '角色管理服务', '/ad02', NULL, 0, 7, NULL, '1', 'y'),
	(31, '角色管理查询服务', '/ad02/list', NULL, 30, 1, '', '1', 'y'),
	(32, '角色管理保存服务', '/ad02/save', NULL, 30, 2, NULL, '1', 'y'),
	(33, '角色管理更新服务', '/ad02/update', NULL, 30, 3, NULL, '1', 'y'),
	(34, '角色管理删除服务', '/ad02/del', NULL, 30, 4, NULL, '1', 'y'),
	(35, '角色管理资源设置服务', '/ad02/setRes', NULL, 30, 5, '', '1', 'y'),
	(36, '分类管理服务', '/ad05', NULL, 0, 8, NULL, '1', 'y'),
	(37, '分类管理JSON数据查询服务', '/ad05/treeJsonArray', NULL, 36, 1, NULL, '1', 'y'),
	(38, '分类管理新增服务', '/ad05/save', NULL, 36, 2, NULL, '1', 'y'),
	(39, '分类管理更新服务', '/ad05/update', NULL, 36, 3, NULL, '1', 'y'),
	(40, '分类管理删除服务', '/ad05/del', NULL, 36, 4, NULL, '1', 'y'),
	(41, '文章管理服务', '/ad04', NULL, 0, 9, NULL, '1', 'y'),
	(42, '文章管理查询服务', '/ad04/list', NULL, 41, 1, NULL, '1', 'y'),
	(43, '文章管理新增服务', '/ad04/save', NULL, 41, 2, NULL, '1', 'y'),
	(44, '文章管理删除服务', '/ad04/del', NULL, 41, 3, NULL, '1', 'y'),
	(45, '文章发布服务', '/ad04/publish', NULL, 41, 4, '', '1', 'y');
/*!40000 ALTER TABLE `s_res` ENABLE KEYS */;

-- 导出  表 csap_db.s_role 结构
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE IF NOT EXISTS `s_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  csap_db.s_role 的数据：~3 rows (大约)
DELETE FROM `s_role`;
/*!40000 ALTER TABLE `s_role` DISABLE KEYS */;
INSERT INTO `s_role` (`id`, `name`, `description`) VALUES
	(1, 'admin', '管理员'),
	(2, 'oper', '操作员'),
	(5, 'user', '普通用户');
/*!40000 ALTER TABLE `s_role` ENABLE KEYS */;

-- 导出  表 csap_db.s_role_res 结构
DROP TABLE IF EXISTS `s_role_res`;
CREATE TABLE IF NOT EXISTS `s_role_res` (
  `roleId` int(11) DEFAULT NULL COMMENT '角色id',
  `resId` int(11) DEFAULT NULL COMMENT '资源id',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=489 DEFAULT CHARSET=utf8 COMMENT='角色，资源多对多关系表';

-- 正在导出表  csap_db.s_role_res 的数据：~34 rows (大约)
DELETE FROM `s_role_res`;
/*!40000 ALTER TABLE `s_role_res` DISABLE KEYS */;
INSERT INTO `s_role_res` (`roleId`, `resId`, `id`) VALUES
	(1, 1, 132),
	(1, 2, 133),
	(1, 3, 134),
	(1, 4, 135),
	(1, 5, 136),
	(1, 7, 137),
	(1, 19, 138),
	(1, 20, 139),
	(1, 21, 140),
	(1, 22, 141),
	(1, 23, 142),
	(1, 24, 143),
	(1, 25, 144),
	(1, 26, 145),
	(1, 27, 146),
	(1, 28, 147),
	(1, 29, 148),
	(1, 30, 149),
	(1, 31, 150),
	(1, 32, 151),
	(1, 33, 152),
	(1, 34, 153),
	(1, 35, 154),
	(1, 36, 155),
	(1, 37, 156),
	(1, 38, 157),
	(1, 39, 158),
	(1, 40, 159),
	(1, 41, 160),
	(1, 42, 161),
	(1, 43, 162),
	(1, 44, 163),
	(1, 45, 164),
	(2, 5, 488);
/*!40000 ALTER TABLE `s_role_res` ENABLE KEYS */;

-- 导出  表 csap_db.s_taxonomy 结构
DROP TABLE IF EXISTS `s_taxonomy`;
CREATE TABLE IF NOT EXISTS `s_taxonomy` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(150) DEFAULT NULL COMMENT '标题',
  `text` text COMMENT '内容描述',
  `module` varchar(32) DEFAULT NULL COMMENT '对于的内容模型',
  `count` int(11) unsigned DEFAULT '0' COMMENT '该分类的内容数量',
  `idx` int(11) DEFAULT NULL COMMENT '排序编码',
  `parentId` int(20) unsigned DEFAULT NULL COMMENT '父级分类的ID',
  `cAt` datetime DEFAULT NULL COMMENT '创建日期',
  `dAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `module` (`module`),
  KEY `parentid` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='分类表。标签、专题、类别等都属于taxonomy。';

-- 正在导出表  csap_db.s_taxonomy 的数据：~13 rows (大约)
DELETE FROM `s_taxonomy`;
/*!40000 ALTER TABLE `s_taxonomy` DISABLE KEYS */;
INSERT INTO `s_taxonomy` (`id`, `title`, `text`, `module`, `count`, `idx`, `parentId`, `cAt`, `dAt`) VALUES
	(1, '网站栏目', NULL, 'section', 0, NULL, 0, NULL, NULL),
	(2, '脊髓空洞症常识科普', NULL, 'section', 0, NULL, 1, NULL, NULL),
	(3, '名医简介及预约', NULL, 'section', 0, NULL, 1, NULL, NULL),
	(4, '特点医学案例', NULL, 'section', 0, NULL, 1, NULL, NULL),
	(5, '医患交流', NULL, 'section', 0, NULL, 1, NULL, NULL),
	(6, '关于我们', NULL, 'section', 0, NULL, 1, NULL, NULL),
	(7, '症状类型', NULL, 'symptom', 0, NULL, 0, NULL, NULL),
	(8, '病症分类', NULL, 'disease', 0, NULL, 0, NULL, NULL),
	(9, '康复经验', NULL, 'section', 0, NULL, 5, NULL, '2018-03-14 16:08:01'),
	(10, '病情咨询', NULL, 'section', 0, NULL, 5, NULL, '2018-03-14 16:08:03'),
	(11, '医患交流板块', NULL, 'plate', 0, NULL, 0, NULL, NULL),
	(12, '病情咨询', NULL, 'plate', 0, NULL, 11, NULL, NULL),
	(13, '康复经验', NULL, 'plate', 0, NULL, 11, NULL, NULL);
/*!40000 ALTER TABLE `s_taxonomy` ENABLE KEYS */;

-- 导出  表 csap_db.s_user 结构
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE IF NOT EXISTS `s_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `idcard` varchar(50) DEFAULT NULL COMMENT '证件号',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `score` int(11) DEFAULT '0' COMMENT '积分',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮件',
  `signature` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  `third_id` varchar(100) DEFAULT NULL COMMENT '三方登陆id',
  `access_token` varchar(100) DEFAULT NULL COMMENT '登陆印证token',
  `receive_msg` tinyint(1) DEFAULT NULL COMMENT '是否接受社区消息  1：是，0：否',
  `c_at` datetime DEFAULT NULL COMMENT '创建时间',
  `m_at` datetime DEFAULT NULL COMMENT '更新时间',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号',
  `channel` varchar(100) DEFAULT NULL COMMENT '第三方渠道',
  `status` varchar(1) DEFAULT NULL COMMENT '状体0-默认，1-禁用',
  `forbiddenReason` varchar(1000) DEFAULT NULL COMMENT '禁用原因',
  `third_access_token` varchar(100) DEFAULT NULL COMMENT '第三方登录获取的access_token',
  `logged` datetime DEFAULT NULL COMMENT '最后登录时间',
  `activated` datetime DEFAULT NULL COMMENT '激活时间',
  `email_status` tinyint(1) DEFAULT NULL COMMENT 'email是否认证 1：是，0：否',
  `phone_status` tinyint(1) DEFAULT NULL COMMENT 'phone认证1：是，0：否',
  `content_count` int(11) DEFAULT '0' COMMENT '发布数',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `idcardtype` tinyint(2) DEFAULT NULL COMMENT '身份证件类型',
  `password` varchar(128) DEFAULT NULL COMMENT '登陆密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `loginname` varchar(50) DEFAULT NULL COMMENT '登陆名',
  `isAdmin` varchar(1) DEFAULT NULL COMMENT '是否是管理员',
  `unionid` varchar(50) DEFAULT NULL COMMENT '微信多平台统一登录id',
  `d_at` datetime DEFAULT NULL COMMENT '删除时间',
  `sheng` varchar(100) DEFAULT NULL COMMENT '省',
  `qu` varchar(100) DEFAULT NULL COMMENT '市',
  `shi` varchar(100) DEFAULT NULL COMMENT '区',
  `sex` varchar(100) DEFAULT NULL COMMENT '1男2女',
  `age` varchar(100) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `loginname` (`loginname`),
  KEY `status` (`status`),
  KEY `content_count` (`content_count`),
  KEY `comment_count` (`comment_count`),
  KEY `c_at` (`c_at`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 正在导出表  csap_db.s_user 的数据：~2 rows (大约)
DELETE FROM `s_user`;
/*!40000 ALTER TABLE `s_user` DISABLE KEYS */;
INSERT INTO `s_user` (`id`, `idcard`, `nickname`, `score`, `avatar`, `email`, `signature`, `third_id`, `access_token`, `receive_msg`, `c_at`, `m_at`, `phone`, `channel`, `status`, `forbiddenReason`, `third_access_token`, `logged`, `activated`, `email_status`, `phone_status`, `content_count`, `comment_count`, `idcardtype`, `password`, `salt`, `loginname`, `isAdmin`, `unionid`, `d_at`, `sheng`, `qu`, `shi`, `sex`, `age`) VALUES
	(8, NULL, '管理员', 0, 'http://images.cichlid.cc/image/avatar/dft-avatar.jpg', NULL, '写点啥吧～', NULL, NULL, NULL, '2018-01-27 22:06:12', '2018-01-27 22:06:12', '13998377271', NULL, '0', NULL, NULL, '2018-03-14 11:24:24', NULL, 0, 0, 0, 0, NULL, '$2a$10$ojKn8QD9YHoh52HyVY5R6eoj.uQMGjWndXx8i05uO2nr5U.TGPbNm', '$2a$10$ojKn8QD9YHoh52HyVY5R6e', 'admin', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(9, NULL, '操作员', 0, 'http://images.cichlid.cc/image/avatar/dft-avatar.jpg', NULL, '写点啥吧～', NULL, NULL, NULL, '2018-01-27 22:06:48', '2018-01-27 22:06:48', '18899998888', NULL, '0', NULL, NULL, '2018-01-27 23:40:24', NULL, 0, 0, 0, 0, NULL, '$2a$10$j49JGizB4lunaMfYPQomMupa.SMAw8EY6gYJpbfRfO1BtLsnld6na', '$2a$10$j49JGizB4lunaMfYPQomMu', 'oper', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `s_user` ENABLE KEYS */;

-- 导出  表 csap_db.s_user_role 结构
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE IF NOT EXISTS `s_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `rId` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='用户角色多对多关系表';

-- 正在导出表  csap_db.s_user_role 的数据：~2 rows (大约)
DELETE FROM `s_user_role`;
/*!40000 ALTER TABLE `s_user_role` DISABLE KEYS */;
INSERT INTO `s_user_role` (`id`, `uId`, `rId`) VALUES
	(24, 8, 1),
	(25, 9, 2);
/*!40000 ALTER TABLE `s_user_role` ENABLE KEYS */;

-- 导出  表 csap_db.tipoffs 结构
DROP TABLE IF EXISTS `tipoffs`;
CREATE TABLE IF NOT EXISTS `tipoffs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `targetObj` varchar(50) NOT NULL COMMENT '举报目标',
  `targetId` varchar(50) NOT NULL COMMENT '举报目标ID',
  `cAt` datetime NOT NULL COMMENT '举报时间',
  `userId` int(11) NOT NULL COMMENT '举报人',
  `processerId` int(11) NOT NULL COMMENT '处理人ID',
  `tax` int(11) NOT NULL COMMENT '违规类型',
  `processAt` datetime NOT NULL COMMENT '处理时间',
  `remark` varchar(50) NOT NULL COMMENT '举报原因',
  `processRet` varchar(1000) NOT NULL COMMENT '处理结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报';

-- 正在导出表  csap_db.tipoffs 的数据：~0 rows (大约)
DELETE FROM `tipoffs`;
/*!40000 ALTER TABLE `tipoffs` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoffs` ENABLE KEYS */;

-- 导出  表 csap_db.user_medicalrecords 结构
DROP TABLE IF EXISTS `user_medicalrecords`;
CREATE TABLE IF NOT EXISTS `user_medicalrecords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mrNo` varchar(50) DEFAULT NULL COMMENT '病例号',
  `tel` varchar(50) DEFAULT NULL COMMENT '住院电话',
  `ifCure` int(11) DEFAULT NULL COMMENT '0是 1否',
  `diseaseDesc` text COMMENT '病情描述',
  `idCard` varchar(50) DEFAULT NULL COMMENT '住院身份证号',
  `hospital` varchar(200) DEFAULT NULL COMMENT '治疗医院',
  `fee` varchar(50) DEFAULT NULL COMMENT '治疗费用',
  `hDays` varchar(10) DEFAULT NULL COMMENT '住院天数',
  `cAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  `mAt` datetime DEFAULT NULL,
  `ifPublic` varchar(2) DEFAULT NULL COMMENT '公开 00:完全公开，01:好友可见，03:不公开',
  `surgeryAt` date DEFAULT NULL COMMENT '手术日期',
  `surgeryDuration` varchar(10) DEFAULT NULL COMMENT '手术时长 单位小时',
  `remark` text COMMENT '其他说明',
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mrNo_tel_idCard_cAt_dAt_mAt_ifPublic` (`mrNo`,`tel`,`idCard`,`cAt`,`dAt`,`mAt`,`ifPublic`,`id`,`ifCure`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户医疗档案';

-- 正在导出表  csap_db.user_medicalrecords 的数据：~0 rows (大约)
DELETE FROM `user_medicalrecords`;
/*!40000 ALTER TABLE `user_medicalrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_medicalrecords` ENABLE KEYS */;

-- 导出  表 csap_db.user_medicalrecords_doctor 结构
DROP TABLE IF EXISTS `user_medicalrecords_doctor`;
CREATE TABLE IF NOT EXISTS `user_medicalrecords_doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `umId` int(11) DEFAULT NULL,
  `dId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `umId_dId` (`umId`,`dId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='治疗档案医生关系';

-- 正在导出表  csap_db.user_medicalrecords_doctor 的数据：~0 rows (大约)
DELETE FROM `user_medicalrecords_doctor`;
/*!40000 ALTER TABLE `user_medicalrecords_doctor` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_medicalrecords_doctor` ENABLE KEYS */;

-- 导出  表 csap_db.user_medicalrecords_tax 结构
DROP TABLE IF EXISTS `user_medicalrecords_tax`;
CREATE TABLE IF NOT EXISTS `user_medicalrecords_tax` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taxId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1:治疗效果，2手术方式，3术前症状，4术后症状，5病情，6病症',
  `umId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_taxId_type_umId` (`taxId`,`type`,`umId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='治疗档案分类关系表';

-- 正在导出表  csap_db.user_medicalrecords_tax 的数据：~0 rows (大约)
DELETE FROM `user_medicalrecords_tax`;
/*!40000 ALTER TABLE `user_medicalrecords_tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_medicalrecords_tax` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
