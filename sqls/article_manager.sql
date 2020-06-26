/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : publication_manager

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 21/06/2020 11:01:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pm_diction
-- ----------------------------
DROP TABLE IF EXISTS `pm_diction`;
CREATE TABLE `pm_diction`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `pid` int(255) NULL DEFAULT NULL COMMENT '上级字典',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据名称',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pm_file
-- ----------------------------
DROP TABLE IF EXISTS `pm_file`;
CREATE TABLE `pm_file`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `filetype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `filesize` bigint(255) NULL DEFAULT NULL COMMENT '文件大小(Byte)',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(3) NULL DEFAULT NULL COMMENT '删除状态\r\n1：删除\r\n0：未删除',
  `uid` int(255) NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_file
-- ----------------------------
INSERT INTO `pm_file` VALUES ('2e14b75c46e1cbd974b7b0d89ef07a75', '01.docx', 'docx', 492469, '2020-06-19 14:56:07', '2020-06-19 14:56:07', 0, NULL);
INSERT INTO `pm_file` VALUES ('4a3b4bbf594352e0110f428dde3e6fac', '标题.docx', 'docx', 13016, '2020-06-20 17:09:10', '2020-06-20 17:09:10', 0, NULL);
INSERT INTO `pm_file` VALUES ('59929cd827dd9000c0d1d7724568aabc', '3任务书-林贤亮.docx', 'docx', 17966, '2019-04-10 17:38:38', '2019-04-10 17:38:38', 0, NULL);
INSERT INTO `pm_file` VALUES ('9082284c69f575d2c053b961915df1eb', '1开题报告-林贤亮.docx', 'docx', 42747, '2019-04-10 17:38:38', '2019-04-10 17:38:38', 0, NULL);
INSERT INTO `pm_file` VALUES ('9de3fdc0f79ec1f7941006f6e31b8277', '标题.docx', 'docx', 13016, '2020-06-20 17:09:11', '2020-06-20 17:09:11', 0, NULL);
INSERT INTO `pm_file` VALUES ('ad1ad1411597344eb9043e803ea9b8b5', '01.pdf', 'pdf', 768163, '2020-06-20 10:08:42', '2020-06-20 10:08:42', 0, NULL);

-- ----------------------------
-- Table structure for pm_msg
-- ----------------------------
DROP TABLE IF EXISTS `pm_msg`;
CREATE TABLE `pm_msg`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知信息',
  `type` int(255) NULL DEFAULT NULL COMMENT '通知类型\r\n1：回复\r\n2：消息\r\n3：全局',
  `user_sender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送者',
  `user_receer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收者',
  `sid` int(255) NULL DEFAULT NULL COMMENT '稿件ID',
  `is_read` int(3) NULL DEFAULT NULL COMMENT '是否已读',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(3) NULL DEFAULT NULL COMMENT '删除状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_msg
-- ----------------------------
INSERT INTO `pm_msg` VALUES (8, '我觉得你标题可以改改', 1, 'admin', NULL, 2, 0, '2020-06-19 14:57:04', NULL);
INSERT INTO `pm_msg` VALUES (9, '好的', 1, 'admin', NULL, 2, 0, '2020-06-19 14:58:48', NULL);
INSERT INTO `pm_msg` VALUES (10, '写的挺好，继续努力！', 1, 'admin', NULL, 3, 0, '2020-06-20 10:15:11', NULL);
INSERT INTO `pm_msg` VALUES (11, '文化具有深度！', 1, 'admin', NULL, 2, NULL, '2020-06-20 10:15:44', NULL);
INSERT INTO `pm_msg` VALUES (12, '审核原因', 1, 'admin', NULL, 3, NULL, '2020-06-20 17:01:38', NULL);
INSERT INTO `pm_msg` VALUES (13, '我觉得可以修改一下', 1, 'admin', NULL, 4, 0, '2020-06-20 17:09:47', NULL);
INSERT INTO `pm_msg` VALUES (14, '没问题！', 1, 'cmw天下第一', NULL, 4, 0, '2020-06-20 17:10:18', NULL);
INSERT INTO `pm_msg` VALUES (15, '有一定的文章深度', 1, 'admin', NULL, 4, NULL, '2020-06-20 17:11:03', NULL);

-- ----------------------------
-- Table structure for pm_permission
-- ----------------------------
DROP TABLE IF EXISTS `pm_permission`;
CREATE TABLE `pm_permission`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `pid` int(255) NULL DEFAULT NULL COMMENT '上级权限ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `type` int(3) NULL DEFAULT NULL COMMENT '类型\r\n0：目录\r\n1：菜单\r\n2：按钮\r\n3：接口',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `perm_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `seq` int(255) NULL DEFAULT NULL COMMENT '排序:越大级别越高',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_permission
-- ----------------------------
INSERT INTO `pm_permission` VALUES (1, 0, '系统管理', 1, '#', 'ROLE_SYSTEM', NULL, 9, NULL, '2019-04-10 14:28:05', '2019-04-10 17:35:35');
INSERT INTO `pm_permission` VALUES (2, 1, '用户管理', 1, '/admin/user/index', 'ROLE_USER', NULL, 1, NULL, '2019-04-10 14:28:05', '2019-04-10 14:28:05');
INSERT INTO `pm_permission` VALUES (3, 1, '权限管理', 1, '/admin/permission/index', 'ROLE_PERMISSION', NULL, 1, NULL, '2019-04-10 14:28:05', '2019-04-10 14:28:05');
INSERT INTO `pm_permission` VALUES (4, 1, '角色管理', 1, '/admin/role/index', 'ROLE_ROLE', NULL, 1, NULL, '2019-04-10 14:28:05', '2019-04-10 14:28:05');
INSERT INTO `pm_permission` VALUES (8, 2, '用户列表', 3, '/admin/user/list', 'ROLE_USER_LIST', '', 1, '', '2019-04-10 16:11:50', '2019-04-10 16:13:42');
INSERT INTO `pm_permission` VALUES (9, 3, '权限列表', 3, '/admin/permission/list', 'ROLE_PERMISSION_LIST', '', 1, '', '2019-04-10 16:13:05', '2019-04-10 16:13:42');
INSERT INTO `pm_permission` VALUES (10, 4, '角色列表', 3, '/admin/role/list', 'ROLE_ROLE_LIST', '', 1, '', '2019-04-10 16:13:29', '2019-04-10 16:13:43');
INSERT INTO `pm_permission` VALUES (11, 2, '用户操作', 3, '/admin/user/opt/', 'ROLE_USER_OPT', '', 1, '', '2019-04-10 16:19:56', '2019-04-10 17:34:04');
INSERT INTO `pm_permission` VALUES (12, 0, '稿件管理', 1, '#', 'ROLE_SUB', '', 1, '', '2019-04-10 16:21:19', '2019-04-10 17:34:04');
INSERT INTO `pm_permission` VALUES (13, 12, '在线投稿', 1, '/admin/sub/put', 'ROLE_SUB_PUT', '', 1, '', '2019-04-10 16:22:48', '2019-04-10 17:34:05');
INSERT INTO `pm_permission` VALUES (14, 12, '在线审稿', 1, '/admin/sub/index', 'ROLE_SUB_INDEX', '', 1, '', '2019-04-10 16:23:29', '2019-04-10 17:33:53');
INSERT INTO `pm_permission` VALUES (15, 0, '系统统计', 1, '#', 'ROLE_DRUID', '', 8, '', '2019-04-10 16:39:09', '2019-04-10 17:35:43');
INSERT INTO `pm_permission` VALUES (16, 15, '数据统计', 1, '/druid', 'ROLE_DRUID', '', 1, '', '2019-04-10 16:40:35', '2019-04-10 17:33:54');
INSERT INTO `pm_permission` VALUES (17, 0, '图标字体', 1, '#', 'ROLE_ICON', '', 2, '', '2019-04-10 16:41:48', '2019-04-10 17:35:45');
INSERT INTO `pm_permission` VALUES (18, 17, 'font_awesome', 1, 'https://www.thinkcmf.com/font/font_awesome/icons.html', 'ROLE_FA', '', 1, '', '2019-04-10 16:48:40', '2019-04-10 17:33:55');
INSERT INTO `pm_permission` VALUES (19, 15, '容器信息', 1, '/actuator', 'ROLE_ACTUATOR', '', 1, '', '2019-04-10 17:30:02', '2019-04-10 17:34:48');
INSERT INTO `pm_permission` VALUES (20, 12, '我的稿件', 1, '/admin/sub/self', 'ROLE_SUB_SELF', '', 1, '', '2019-04-10 17:31:49', '2019-04-10 17:33:56');
INSERT INTO `pm_permission` VALUES (21, 0, '其它管理', 1, '#', 'ROLE_OTHER', '', 10, '', '2019-04-10 17:32:43', '2019-04-10 17:34:08');
INSERT INTO `pm_permission` VALUES (22, 21, '回复管理', 1, '/admin/msg/index', 'ROLE_MSG_INDEX', '', 1, '', '2019-04-10 17:33:06', '2019-04-10 17:34:00');

-- ----------------------------
-- Table structure for pm_role
-- ----------------------------
DROP TABLE IF EXISTS `pm_role`;
CREATE TABLE `pm_role`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(3) NULL DEFAULT NULL COMMENT '状态\r\n1：启用\r\n2：禁止',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_role
-- ----------------------------
INSERT INTO `pm_role` VALUES (1, '管理员', 'ROLE_ADMIN', '具有最高权限', 1, '2019-04-10 14:16:27', '2019-04-10 17:33:16');
INSERT INTO `pm_role` VALUES (2, '编辑', 'ROLE_EXPERT', '有审稿功能', 1, '2019-04-10 14:16:27', '2019-04-10 18:42:21');
INSERT INTO `pm_role` VALUES (3, '会员', 'ROLE_AUTHOR', '有投稿功能', 1, '2019-04-10 14:16:27', '2019-04-10 18:42:29');

-- ----------------------------
-- Table structure for pm_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `pm_role_permission`;
CREATE TABLE `pm_role_permission`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `rid` int(255) NULL DEFAULT NULL COMMENT '角色ID',
  `pid` int(255) NULL DEFAULT NULL COMMENT '权限ID',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_role_permission
-- ----------------------------
INSERT INTO `pm_role_permission` VALUES (24, 5, 1, '2019-04-10 16:32:22', '2019-04-10 16:32:22');
INSERT INTO `pm_role_permission` VALUES (25, 5, 2, '2019-04-10 16:32:22', '2019-04-10 16:32:22');
INSERT INTO `pm_role_permission` VALUES (26, 5, 8, '2019-04-10 16:32:22', '2019-04-10 16:32:22');
INSERT INTO `pm_role_permission` VALUES (42, 1, 1, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (43, 1, 2, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (44, 1, 8, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (45, 1, 11, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (46, 1, 3, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (47, 1, 9, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (48, 1, 4, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (49, 1, 10, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (50, 1, 12, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (51, 1, 13, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (52, 1, 14, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (53, 1, 20, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (54, 1, 15, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (55, 1, 16, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (56, 1, 19, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (57, 1, 17, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (58, 1, 18, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (59, 1, 21, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (60, 1, 22, '2019-04-10 17:33:16', '2019-04-10 17:33:16');
INSERT INTO `pm_role_permission` VALUES (61, 2, 12, '2019-04-10 18:42:21', '2019-04-10 18:42:21');
INSERT INTO `pm_role_permission` VALUES (62, 2, 14, '2019-04-10 18:42:21', '2019-04-10 18:42:21');
INSERT INTO `pm_role_permission` VALUES (63, 3, 12, '2019-04-10 18:42:29', '2019-04-10 18:42:29');
INSERT INTO `pm_role_permission` VALUES (64, 3, 13, '2019-04-10 18:42:29', '2019-04-10 18:42:29');
INSERT INTO `pm_role_permission` VALUES (65, 3, 20, '2019-04-10 18:42:29', '2019-04-10 18:42:29');

-- ----------------------------
-- Table structure for pm_sub
-- ----------------------------
DROP TABLE IF EXISTS `pm_sub`;
CREATE TABLE `pm_sub`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `title_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题(英文)',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '摘要',
  `summary_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '摘要(英文)',
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键词',
  `keyword_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键词(英文)',
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学科',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `reviewer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `status` int(3) NULL DEFAULT NULL COMMENT '审核状态\r\n     1：待审核\r\n     2：审核中\r\n     3：审核成功\r\n     0：审核失败',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_sub
-- ----------------------------
INSERT INTO `pm_sub` VALUES (2, '嘤嘤怪', '我不是嘤嘤怪', 'I am not yingyingguai', '我不是嘤嘤怪', 'I am not yingyingguai', '嘤嘤怪', 'yingyingguai', '1', 'admin', NULL, 3, '2020-06-19 14:56:16', '2020-06-20 10:15:44');
INSERT INTO `pm_sub` VALUES (3, 'cmw天下第一', '改进KNN算法进行fmri数据分类综述', 'A survey of fMRI data classification based on improved KNN algorithm', '使用基于信息熵和Gini指数的改进KNN算法进行MFIR数据的分类', 'Mfir data classification using improved KNN algorithm based on information entropy and Gini index\r\n\r\n', '信息熵，Ginii指数，KNN', 'Information entropy, ginii index, KNN', '0', 'cmw天下第一', NULL, 3, '2020-06-20 10:08:45', '2020-06-20 17:01:38');
INSERT INTO `pm_sub` VALUES (4, 'cmw天下第一', '标题', 'title', '内容', 'content', '关键字', 'keywords', '2', 'cmw天下第一', NULL, 3, '2020-06-20 17:09:13', '2020-06-20 17:11:03');

-- ----------------------------
-- Table structure for pm_sub_file
-- ----------------------------
DROP TABLE IF EXISTS `pm_sub_file`;
CREATE TABLE `pm_sub_file`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `fid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件ID',
  `sid` int(11) NULL DEFAULT NULL COMMENT '稿件ID',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_sub_file
-- ----------------------------
INSERT INTO `pm_sub_file` VALUES (3, '2e14b75c46e1cbd974b7b0d89ef07a75', 2, '2020-06-19 14:56:16', '2020-06-19 14:56:16');
INSERT INTO `pm_sub_file` VALUES (4, 'ad1ad1411597344eb9043e803ea9b8b5', 3, '2020-06-20 10:08:45', '2020-06-20 10:08:45');
INSERT INTO `pm_sub_file` VALUES (5, '9de3fdc0f79ec1f7941006f6e31b8277', 4, '2020-06-20 17:09:13', '2020-06-20 17:09:13');

-- ----------------------------
-- Table structure for pm_user
-- ----------------------------
DROP TABLE IF EXISTS `pm_user`;
CREATE TABLE `pm_user`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(255) NULL DEFAULT NULL COMMENT '状态\r\n 1：启用 2：禁用',
  `type` int(255) NULL DEFAULT NULL COMMENT '用户类型\r\n1：管理员\r\n2：专家\r\n3：作者',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_user
-- ----------------------------
INSERT INTO `pm_user` VALUES (1, 'admin', '$2a$10$vyVd27QuLihbJxpm9GB0Tu.HbYKNC4JuztKA8NAnwMZW1wijWVQZy', 'admin', 'admin@test.com', 1, 1, '2019-04-10 14:16:27', '2019-04-10 21:22:33');
INSERT INTO `pm_user` VALUES (5, 'cmw天下第一', '$2a$10$qRomB27FuskpXW0y01zB3.FqbDP8eO.9GlTWeicWhQhFRIed75ISa', 'cmw天下第一', '845751770@qq.com', 1, NULL, '2020-06-19 15:20:54', '2020-06-19 22:37:01');
INSERT INTO `pm_user` VALUES (28, 'admin123', '$2a$10$May8cijT4k/WhKZaymIlUea0nzHrx0IMOcMB8LAYORcFyAw//Yjma', NULL, NULL, 1, NULL, '2020-06-20 12:14:34', '2020-06-20 12:14:34');
INSERT INTO `pm_user` VALUES (32, 'rbc777', '$2a$10$eshJq1IvPJ8d.CL4oFAh2O19S0a5EecGRcBamlA0P3MXrkezCjDQK', 'rbc777', '123@163.com', 1, NULL, '2020-06-21 10:57:55', '2020-06-21 10:57:55');

-- ----------------------------
-- Table structure for pm_user_role
-- ----------------------------
DROP TABLE IF EXISTS `pm_user_role`;
CREATE TABLE `pm_user_role`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `rid` int(255) NULL DEFAULT NULL COMMENT '角色ID',
  `uid` int(255) NULL DEFAULT NULL COMMENT '用户ID',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_user_role
-- ----------------------------
INSERT INTO `pm_user_role` VALUES (1, 1, 1, '2019-04-10 14:16:27', '2019-04-10 14:16:27');
INSERT INTO `pm_user_role` VALUES (2, 2, 1, '2019-04-10 14:16:30', '2019-04-10 14:16:27');
INSERT INTO `pm_user_role` VALUES (3, 3, 1, '2019-04-10 14:16:27', '2019-04-10 14:16:27');
INSERT INTO `pm_user_role` VALUES (4, 2, 2, '2019-04-10 14:16:27', '2019-04-10 14:16:27');
INSERT INTO `pm_user_role` VALUES (5, 3, 3, '2019-04-10 14:16:27', '2019-04-10 14:16:27');
INSERT INTO `pm_user_role` VALUES (6, 5, 4, '2019-04-10 16:30:12', '2019-04-10 16:30:12');
INSERT INTO `pm_user_role` VALUES (8, 3, 5, '2020-06-19 22:37:01', '2020-06-19 22:37:01');
INSERT INTO `pm_user_role` VALUES (9, 2, 6, '2020-06-20 10:47:05', '2020-06-20 10:47:05');
INSERT INTO `pm_user_role` VALUES (10, 3, 20, '2020-06-20 11:40:00', '2020-06-20 11:40:00');
INSERT INTO `pm_user_role` VALUES (11, 3, 21, '2020-06-20 11:40:40', '2020-06-20 11:40:40');
INSERT INTO `pm_user_role` VALUES (12, 3, 23, '2020-06-20 11:49:57', '2020-06-20 11:49:57');
INSERT INTO `pm_user_role` VALUES (13, 3, 25, '2020-06-20 11:52:36', '2020-06-20 11:52:36');
INSERT INTO `pm_user_role` VALUES (15, 3, 27, '2020-06-20 12:12:31', '2020-06-20 12:12:31');
INSERT INTO `pm_user_role` VALUES (16, 3, 28, '2020-06-20 12:14:34', '2020-06-20 12:14:34');
INSERT INTO `pm_user_role` VALUES (17, 2, 32, '2020-06-21 10:57:55', '2020-06-21 10:57:55');

-- ----------------------------
-- Table structure for pm_userdata
-- ----------------------------
DROP TABLE IF EXISTS `pm_userdata`;
CREATE TABLE `pm_userdata`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `graduation_school` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业学校',
  `education` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历',
  `degree` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学位',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(255) NULL DEFAULT NULL COMMENT '修改状态\r\n0：审核中\r\n1：审核成功',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通信地址',
  `zipcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pm_userdata
-- ----------------------------
INSERT INTO `pm_userdata` VALUES (1, 'admin', 'admin', 1, '18652250365', '2392900639@163.com', '天津大学', '本科', '理学士', '2020-06-19 20:53:32', '2020-06-19 22:42:32', 1, '天津大学', '362701');
INSERT INTO `pm_userdata` VALUES (2, 'cmw天下第一', 'cmw', 1, '18856325422', '2392900639@Google.com', '天津大学', '本科', '工学士', '2020-06-19 22:38:25', '2020-06-19 22:38:27', 1, '天津大学', '365200');
INSERT INTO `pm_userdata` VALUES (4, 'admin123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-20 12:14:35', '2020-06-20 12:14:35', 1, NULL, NULL);
INSERT INTO `pm_userdata` VALUES (5, 'rbc777', 'rbc777', NULL, NULL, '123@163.com', NULL, NULL, NULL, '2020-06-21 10:57:55', '2020-06-21 10:57:55', 1, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
