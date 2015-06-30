/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50144
Source Host           : localhost:3306
Source Database       : db_apps

Target Server Type    : MYSQL
Target Server Version : 50144
File Encoding         : 65001

Date: 2013-05-30 19:08:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` mediumtext,
  `time` datetime DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `type` bigint(20) DEFAULT NULL,
  `views` bigint(20) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('112', '昨日成都发生呢个暴雨天气', '<p>\r\n	倒萨倒萨倒萨倒萨倒萨<img alt=\"\" src=\"http://api.map.baidu.com/staticimage?center=121.473704%2C31.230393&zoom=11&width=558&height=360&markers=121.473704%2C31.230393&markerStyles=l%2CA\" /> \r\n</p>', '2013-05-25 09:23:07', '跌名倒萨', null, '121', 'dsa dsa ', '倒萨 倒萨', '3');
INSERT INTO `article` VALUES ('113', ' 从新爱', '', '2013-05-25 09:24:47', '跌名', null, '5', null, '', '3');

-- ----------------------------
-- Table structure for `channel`
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `rows` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `template` varchar(255) DEFAULT NULL,
  `hide` tinyint(1) DEFAULT NULL,
  `keywords` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `module` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=72 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('1', '0', '1', '10', '首页', 'index.html', '1', '雨林建站系统新闻中心', '雨林内容管理系统是有marker结合各大内容管理系统开发的一套解决企业建站方案的产品', 'index', 'chat', 'channel');
INSERT INTO `channel` VALUES ('2', '0', '1', '2', '发布', 'publish.html', '1', '雨林建站系统新闻中心', '雨林内容管理系统是有marker结合各大内容管理系统开发的一套解决企', 'publish', 'chat', 'channel');
INSERT INTO `channel` VALUES ('3', '0', '1', '10', '附近', 'news.html', '1', '雨林建站系统新闻中心', '雨林内容管理系统是有marker结合各大内容管理系统开发的一套解决企', 'grand', 'chat', 'channel');
INSERT INTO `channel` VALUES ('15', '0', '11', '10', '设置', 'guestbook.html', '1', '设置', '设置', 'guestbook', 'email', 'channel');

-- ----------------------------
-- Table structure for `chip`
-- ----------------------------
DROP TABLE IF EXISTS `chip`;
CREATE TABLE `chip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `content` text,
  `mark` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chip
-- ----------------------------
INSERT INTO `chip` VALUES ('115', '多杀多撒旦', null, '倒萨倒萨', '倒萨倒萨倒萨', null);

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `power` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', '超级管理员', '1,2,3,4,5,6');

-- ----------------------------
-- Table structure for `guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `guestbook`;
CREATE TABLE `guestbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of guestbook
-- ----------------------------
INSERT INTO `guestbook` VALUES ('19', '倒萨倒萨打算倒萨打算', '127.0.0.1', '2013-01-20 22:54:19', '吴伟');
INSERT INTO `guestbook` VALUES ('27', '我aid撒的撒', '127.0.0.1', '2013-01-27 21:54:10', '吴伟');
INSERT INTO `guestbook` VALUES ('28', 'dsa fdsf dsaf dsfdsafdsfdsafsdafdsafdsafdsafdsfdsafdsa', '127.0.0.1', '2013-01-27 21:58:46', '吴伟');
INSERT INTO `guestbook` VALUES ('23', '倒萨倒萨倒萨倒萨', '127.0.0.1', '2013-01-20 23:52:30', '吴伟');
INSERT INTO `guestbook` VALUES ('26', '242142314231432143214321421423143', '127.0.0.1', '2013-01-27 21:32:25', '吴伟');

-- ----------------------------
-- Table structure for `jokes`
-- ----------------------------
DROP TABLE IF EXISTS `jokes`;
CREATE TABLE `jokes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `content` varchar(250) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jokes
-- ----------------------------
INSERT INTO `jokes` VALUES ('13', '30.658601', '104.064856', '文庙街发生了件很奇怪的事情哦，有人跳楼了哈！', '四川省成都市青羊区文庙前街');
INSERT INTO `jokes` VALUES ('14', '30.6507527', '104.1005785', '多杀多撒倒萨倒萨倒萨', '四川省成都市锦江区锦东路8号');
INSERT INTO `jokes` VALUES ('15', '30.6506646', '104.1006053', '这个是真的吗？', '四川省成都市锦江区锦东路8号');
INSERT INTO `jokes` VALUES ('16', '30.6506865', '104.1005018', '嘿嘿', '四川省成都市锦江区锦东路8号');
INSERT INTO `jokes` VALUES ('17', '30.6507058', '104.1005921', 'LBS地理定位服务测试，其实这个就是一则笑话，哟呵！', '四川省成都市锦江区锦东路8号');
INSERT INTO `jokes` VALUES ('12', '30.6506681', '104.1007017', '有一天，有一个美女来到成都，因为成都美女多，她就找了一个帅哥问，我和他们比，谁更漂亮，帅哥说当然是我啊，嘿嘿。', '大撒旦撒');
INSERT INTO `jokes` VALUES ('18', '30.6504699', '104.1003187', 'dsadsadsadsadsadsadsadsadsdsadsadsadsadsadsadsadsads', '四川省成都市锦江区宏济中路39号');
INSERT INTO `jokes` VALUES ('19', '30.6506361', '104.1009513', '意思走了破罐子', '四川省成都市锦江区锦东路26号');
INSERT INTO `jokes` VALUES ('20', '30.6506361', '104.1009513', '意思走了破罐子', '四川省成都市锦江区锦东路26号');
INSERT INTO `jokes` VALUES ('21', '30.6506361', '104.1009513', '意思走了破罐子', '四川省成都市锦江区锦东路26号');
INSERT INTO `jokes` VALUES ('22', '30.6506361', '104.1009513', '意思走了破罐子', '四川省成都市锦江区锦东路26号');
INSERT INTO `jokes` VALUES ('23', '30.6504699', '104.1003187', '', '四川省成都市锦江区宏济中路39号');
INSERT INTO `jokes` VALUES ('24', '30.6506361', '104.1009513', '意思走了破罐子8680855', '四川省成都市锦江区锦东路26号');
INSERT INTO `jokes` VALUES ('25', '30.6506361', '104.1009513', '意思走了破罐子8680855天山哭图片浏览旅途吐了咕噜咕噜兔兔是咯在图书馆本土顺利吧吐图书馆刻苦兔兔呢图书馆路途那个就会不会的人际遇难免吧台球迷糊里糊涂路', '四川省成都市锦江区锦东路26号');
INSERT INTO `jokes` VALUES ('26', '30.6506505', '104.1009137', 'dsa ', '四川省成都市锦江区锦东路22号');
INSERT INTO `jokes` VALUES ('27', '30.6506163', '104.1006607', '', '');
INSERT INTO `jokes` VALUES ('28', '30.6506163', '104.1006607', '大撒旦撒', '');

-- ----------------------------
-- Table structure for `links`
-- ----------------------------
DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
  `yl_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`yl_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of links
-- ----------------------------

-- ----------------------------
-- Table structure for `module`
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `template` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('2', '文章模型', 'org.marker.mushroom.module.impl.ArticleModule', 'content', 'article.html', 'article');
INSERT INTO `module` VALUES ('1', '栏目模型', 'org.marker.mushroom.module.impl.ChannelModule', '', '', 'channel');
INSERT INTO `module` VALUES ('3', '模板模型', 'org.marker.mushroom.module.impl.TemplateModule', '', 'template_content.html', 'template');

-- ----------------------------
-- Table structure for `plugin`
-- ----------------------------
DROP TABLE IF EXISTS `plugin`;
CREATE TABLE `plugin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `ver` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plugin
-- ----------------------------
INSERT INTO `plugin` VALUES ('8', '留言插件', 'org.marker.mushroom.plugins.impl.CommentPluginImpl', '1', 'comment', 'marker', '1.0');
INSERT INTO `plugin` VALUES ('9', '搜索插件', 'org.marker.mushroom.plugins.impl.WebSearchPluginImpl', '1', 'search', 'marker', '1.0');
INSERT INTO `plugin` VALUES ('14', 'APP插件', 'org.marker.mushroom.plugins.impl.WebAppPluginImpl', '1', 'mobileapp', 'marker', '1.0');

-- ----------------------------
-- Table structure for `searchinfo`
-- ----------------------------
DROP TABLE IF EXISTS `searchinfo`;
CREATE TABLE `searchinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of searchinfo
-- ----------------------------
INSERT INTO `searchinfo` VALUES ('118', 'a', '0:0:0:0:0:0:0:1', '2013-03-12 10:27:40');
INSERT INTO `searchinfo` VALUES ('119', 'a', '0:0:0:0:0:0:0:1', '2013-03-12 10:27:43');
INSERT INTO `searchinfo` VALUES ('120', 'æ', '0:0:0:0:0:0:0:1', '2013-03-12 10:28:32');

-- ----------------------------
-- Table structure for `template`
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `durl` varchar(255) DEFAULT NULL,
  `dloaded` bigint(20) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `description` mediumtext,
  `views` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('1', '的撒到底是啊', '2013-04-23 23:48:23', 'http://dsadsa.dsadsadsa,com/dsadsa', '0', '0', '40', '跌名', 'upload/images/shuangzhi.png', '的撒的撒', '312');
INSERT INTO `template` VALUES ('2', 'dsadsad', '2013-04-11 00:09:24', 'http://dsadsa.dsadsadsa,com/dsadsa', '0', '0', '40', 'sad', 'upload/images/shuangzhi.png', '的撒的撒', '197');
INSERT INTO `template` VALUES ('3', 'sadsadsad', '2013-04-24 00:20:03', 'http://dsadsa.dsadsadsa,com/dsadsa', '0', '0', '40', '跌名sad', 'upload/images/shuangzhi.png', 'sad', '21');

-- ----------------------------
-- Table structure for `tmp_for_channel`
-- ----------------------------
DROP TABLE IF EXISTS `tmp_for_channel`;
CREATE TABLE `tmp_for_channel` (
  `id` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tmp_for_channel
-- ----------------------------
INSERT INTO `tmp_for_channel` VALUES ('2', '0');
INSERT INTO `tmp_for_channel` VALUES ('40', '2');
INSERT INTO `tmp_for_channel` VALUES ('41', '2');
INSERT INTO `tmp_for_channel` VALUES ('42', '2');
INSERT INTO `tmp_for_channel` VALUES ('43', '2');
INSERT INTO `tmp_for_channel` VALUES ('44', '2');
INSERT INTO `tmp_for_channel` VALUES ('45', '2');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `group` int(11) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=129 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('95', 'admin', '123', '1', '雨林博客', '2013-02-01 20:57:00', '2013-01-31 20:59:49', '1', '网站超级管理员');
INSERT INTO `user` VALUES ('117', 'wuwei', '123456', '1', '雨林博客', '2013-05-14 20:53:19', null, '0', '普通编辑');
