/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : yeexang_community_db

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 30/09/2021 15:27:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for y_c_comment
-- ----------------------------
DROP TABLE IF EXISTS `y_c_comment`;
CREATE TABLE `y_c_comment`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `comment_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论id',
  `parent_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `comment_count` int(11) NULL DEFAULT NULL COMMENT '评论数',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `comment_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论类型，1为一级评论，2为二级评论',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_comment
-- ----------------------------
INSERT INTO `y_c_comment` VALUES ('083a7004c1a8462d9aabd531f8380d1e', '2021092721065136325', '20210825201941752278', '第一个测试回复', 3, 0, '1', '2021-09-27 13:06:51', '123456', '2021-09-27 13:06:51', '123456', 0);
INSERT INTO `y_c_comment` VALUES ('2e70996050c04c908c54518875ec0872', '20210813105305321709', '20210813105020523943', '二级评论内容', 0, 0, '2', '2021-08-13 02:53:06', '123456_ooo', '2021-08-13 02:53:06', '123456_ooo', 0);
INSERT INTO `y_c_comment` VALUES ('539a6cb38fd94b4e87b4d02faf3124ca', '20210927212550304631', '2021092721065136325', '第一个二级回复', 0, 0, '2', '2021-09-27 13:25:50', '123456', '2021-09-27 13:25:50', '123456', 0);
INSERT INTO `y_c_comment` VALUES ('7e7fd05cb510496b9829358ec332cb00', '20210927210704102434', '20210825201941752278', '第三个测试回复', 0, 0, '1', '2021-09-27 13:07:05', '123456', '2021-09-27 13:07:05', '123456', 0);
INSERT INTO `y_c_comment` VALUES ('8762f581d40140eba45031081aeffd8b', '20210927212611333751', '2021092721065136325', '第三个二级回复', 0, 0, '2', '2021-09-27 13:26:11', '123456', '2021-09-27 13:26:11', '123456', 0);
INSERT INTO `y_c_comment` VALUES ('ae5e1f7999f44bb18f73a861eb36690b', '20210813105020523943', '20210726003025706704', '一级评论内容', 1, 0, '1', '2021-08-13 02:50:21', '123456_ooo', '2021-08-13 02:50:21', '123456_ooo', 0);
INSERT INTO `y_c_comment` VALUES ('bdeb6c99c7d64e83a23da3cc58f54da5', '20210927212601664711', '2021092721065136325', '第二个二级回复', 0, 0, '2', '2021-09-27 13:26:01', '123456', '2021-09-27 13:26:01', '123456', 0);
INSERT INTO `y_c_comment` VALUES ('dc313ab4567c42db8cb350493dd92546', '20210927210657465241', '20210825201941752278', '第二个测试回复', 0, 0, '1', '2021-09-27 13:06:58', '123456', '2021-09-27 13:06:58', '123456', 0);
INSERT INTO `y_c_comment` VALUES ('e704ac97e4784141bf0b2f1fe4eb5aa5', '20210803210650843519', '20210726003025706704', '一级评论内容', 0, 0, '1', '2021-08-03 13:06:50', '123456_abc', '2021-08-03 13:06:50', '123456_abc', 0);

-- ----------------------------
-- Table structure for y_c_dict
-- ----------------------------
DROP TABLE IF EXISTS `y_c_dict`;
CREATE TABLE `y_c_dict`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `dict_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号id',
  `label` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  `type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述',
  `sort_flag` int(255) UNSIGNED NOT NULL COMMENT '排序标识',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_dict
-- ----------------------------
INSERT INTO `y_c_dict` VALUES ('0f76888916674df590b45ace8e8iwc6b', '20210731132534729928', 'community_announcement_20210928', '快来畅所欲言吧~', 'community_announcement', '论坛公告', 1, '2021-09-28 00:24:46', '123456', '2021-09-28 00:25:02', '123456', 0);

-- ----------------------------
-- Table structure for y_c_notification
-- ----------------------------
DROP TABLE IF EXISTS `y_c_notification`;
CREATE TABLE `y_c_notification`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `notification_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知id',
  `notifier` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知者',
  `receiver` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接收者',
  `outer_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知所属id',
  `notification_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知类型，1为评论帖子，2为回复评论',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态，0为未读，1为已读',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_notification
-- ----------------------------
INSERT INTO `y_c_notification` VALUES ('0a17c67f4e1e463b97f35c7b90fe87dd', '20210927210657372492', '123456', '123456_ooo', '20210825201941752278', '1', 0, '2021-09-27 13:06:58', '123456', '2021-09-27 13:06:58', '123456', 0);
INSERT INTO `y_c_notification` VALUES ('0b60fcee4d574f3ea9e35ea399ecd46b', '20210731132534729040', '123456_abc', '123456_abc', '20210726003025706704', '3', 0, '2021-07-31 05:25:35', '123456_abc', '2021-07-31 05:25:35', '123456_abc', 0);
INSERT INTO `y_c_notification` VALUES ('0f76888916674df590b45ace8ead9c6b', '20210927212550269269', '123456', '123456', '2021092721065136325', '2', 0, '2021-09-27 13:25:51', '123456', '2021-09-27 13:25:51', '123456', 0);
INSERT INTO `y_c_notification` VALUES ('1052e8688d5741ae98b2ea7e0a16f436', '20210813105305266931', '123456_ooo', '123456_ooo', '20210813105020523943', '2', 0, '2021-08-13 02:53:06', '123456_ooo', '2021-08-13 02:53:06', '123456_ooo', 0);
INSERT INTO `y_c_notification` VALUES ('1378c4320ef3400cb01e23c4ed03fe66', '20210927212601162626', '123456', '123456', '2021092721065136325', '2', 0, '2021-09-27 13:26:01', '123456', '2021-09-27 13:26:01', '123456', 0);
INSERT INTO `y_c_notification` VALUES ('5cc344b561274b0fbd62b2cdab1a8c09', '20210803210650394485', '123456_abc', '123456_abc', '20210726003025706704', '1', 0, '2021-08-03 13:06:50', '123456_abc', '2021-08-03 13:06:50', '123456_abc', 0);
INSERT INTO `y_c_notification` VALUES ('648d014b1e57477ca5a2215fd04646fc', '20210927210651896966', '123456', '123456_ooo', '20210825201941752278', '1', 0, '2021-09-27 13:06:51', '123456', '2021-09-27 13:06:51', '123456', 0);
INSERT INTO `y_c_notification` VALUES ('70178eea148d451394775726fcd06048', '20210813105021302734', '123456_ooo', '123456_abc', '20210726003025706704', '1', 0, '2021-08-13 02:50:21', '123456_ooo', '2021-08-13 02:50:21', '123456_ooo', 0);
INSERT INTO `y_c_notification` VALUES ('73190026bed94ce3af6466ecc57fe8a6', '20210927212611608021', '123456', '123456', '2021092721065136325', '2', 0, '2021-09-27 13:26:11', '123456', '2021-09-27 13:26:11', '123456', 0);
INSERT INTO `y_c_notification` VALUES ('8475d8aaab624665b450a8c6569b52b8', '20210813095718793916', '123456_ooo', '123456_abc', '20210726003025706704', '3', 0, '2021-08-13 01:57:18', '123456_ooo', '2021-08-13 01:57:18', '123456_ooo', 0);
INSERT INTO `y_c_notification` VALUES ('c2c449d50fa5411f868f4ebae35ea2d6', '20210927210704608272', '123456', '123456_ooo', '20210825201941752278', '1', 0, '2021-09-27 13:07:05', '123456', '2021-09-27 13:07:05', '123456', 0);
INSERT INTO `y_c_notification` VALUES ('d7a9a09c04ef429ca39470c89f7d8482', '20210813100015446416', '123456_ooo', '123456_abc', '20210726003025706704', '3', 0, '2021-08-13 02:00:16', '123456_ooo', '2021-08-13 02:00:16', '123456_ooo', 0);

-- ----------------------------
-- Table structure for y_c_parameter
-- ----------------------------
DROP TABLE IF EXISTS `y_c_parameter`;
CREATE TABLE `y_c_parameter`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `parameter_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数id',
  `parameter_key` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签',
  `parameter_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_parameter
-- ----------------------------

-- ----------------------------
-- Table structure for y_c_section
-- ----------------------------
DROP TABLE IF EXISTS `y_c_section`;
CREATE TABLE `y_c_section`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `section_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分区id',
  `section_name` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_section
-- ----------------------------
INSERT INTO `y_c_section` VALUES ('1b1ff85ed7774f10b763902d7767h0aa', '#new', '最新', '2021-07-27 19:35:07', '123456_abc', '2021-07-27 19:35:20', '123456_abc', 0);
INSERT INTO `y_c_section` VALUES ('21aff85ed7774f10b763909oa767h0aa', '000001', '灌水', '2021-08-09 21:16:19', '123456_abc', '2021-08-09 21:16:29', '123456_abc', 0);
INSERT INTO `y_c_section` VALUES ('99aff85ed7774f10b763902d7767h0aa', '000002', '技术', '2021-08-09 21:15:39', '123456_abc', '2021-08-09 21:15:47', '123456_abc', 0);
INSERT INTO `y_c_section` VALUES ('99aff85ed7774f10b763909oa767h0aa', '000003', '工作', '2021-08-09 21:16:19', '123456_abc', '2021-08-09 21:16:29', '123456_abc', 0);

-- ----------------------------
-- Table structure for y_c_topic
-- ----------------------------
DROP TABLE IF EXISTS `y_c_topic`;
CREATE TABLE `y_c_topic`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `topic_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子id',
  `topic_title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `topic_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `section` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属分区',
  `comment_count` int(11) NULL DEFAULT NULL COMMENT '评论数',
  `view_count` int(11) NULL DEFAULT NULL COMMENT '浏览数',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `essential_status` tinyint(1) NULL DEFAULT NULL COMMENT '精华，1为加精，0为不加精',
  `recommended_status` tinyint(1) NULL DEFAULT NULL COMMENT '推荐，1为推荐，0为不推荐',
  `last_comment_time` datetime NULL DEFAULT NULL COMMENT '最后一次评论时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_topic
-- ----------------------------
INSERT INTO `y_c_topic` VALUES ('013de8f4a83a466b93ee368dbdb40310', '20210825201941752278', '测试标题', '测试内容', '000001', 6, 12449, 0, 0, 0, '2021-08-25 12:19:42', '2021-08-25 12:19:42', '123456_ooo', '2021-08-25 12:19:42', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('023304e5b2c4457ea94c14b5510284ee', '20210825202201743671', '测试标题', '测试内容', '000001', 0, 3, 0, 0, 0, '2021-08-25 12:22:01', '2021-08-25 12:22:01', '123456_ooo', '2021-08-25 12:22:01', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('03068bcd1d374f7a8d66975b5d9b5e16', '20210825202158429074', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:58', '2021-08-25 12:21:58', '123456_ooo', '2021-08-25 12:21:58', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('0381528d22fb42ec8ff12b9f012c0e41', '2021081223515672567', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-12 15:51:57', '2021-08-12 15:51:57', '123456_ooo', '2021-08-12 15:51:57', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('04e0d56670a442f4a83981c0990d9072', '20210825202202782966', '测试标题', '测试内容', '000001', 0, 2, 0, 0, 0, '2021-08-25 12:22:03', '2021-08-25 12:22:03', '123456_ooo', '2021-08-25 12:22:03', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('050fcbaa03fb4a5395a71337b1e6d2aa', '20210825202147369688', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:48', '2021-08-25 12:21:48', '123456_ooo', '2021-08-25 12:21:48', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('056b4642fc11482f93b8499fa58dc514', '2021082520220882249', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:09', '2021-08-25 12:22:09', '123456_ooo', '2021-08-25 12:22:09', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('0716c6fb40664326a41e720ae260efe9', '20210825202206205152', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:07', '2021-08-25 12:22:07', '123456_ooo', '2021-08-25 12:22:07', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('0779dd03dd344d9db0a0ba15e5e33b73', '20210825202223560597', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:23', '2021-08-25 12:22:23', '123456_ooo', '2021-08-25 12:22:23', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('07bbe567953d4572990918a2fd65b49d', '20210825201935629161', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:35', '2021-08-25 12:19:35', '123456_ooo', '2021-08-25 12:19:35', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('07de972cce154afbad4bfc22286ade6c', '20210825202157617868', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:57', '2021-08-25 12:21:57', '123456_ooo', '2021-08-25 12:21:57', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('0a49ef88cb1e43b6b9b2637e562566fd', '20210825201949892190', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:50', '2021-08-25 12:19:50', '123456_ooo', '2021-08-25 12:19:50', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('0bf8d20fc44c4d1089f973681019290e', '20210825201948687044', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:49', '2021-08-25 12:19:49', '123456_ooo', '2021-08-25 12:19:49', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('138b40cfbabb4e8397113d5a368a596e', '20210813000121806203', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-12 16:01:22', '2021-08-12 16:01:22', '123456_ooo', '2021-08-12 16:01:22', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('13af10ff4c2946c185bbd36d810e8570', '202108252019372065', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:38', '2021-08-25 12:19:38', '123456_ooo', '2021-08-25 12:19:38', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('13fdac35786e4673a9ac3d55609e4ca9', '20210825201928796266', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:28', '2021-08-25 12:19:28', '123456_ooo', '2021-08-25 12:19:28', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('150caf53fffa4df297bb7e77f7a52e9d', '20210825202225346970', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:25', '2021-08-25 12:22:25', '123456_ooo', '2021-08-25 12:22:25', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('16c0a659d2a34b46aa838001699f06ee', '20210825201950602263', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:51', '2021-08-25 12:19:51', '123456_ooo', '2021-08-25 12:19:51', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('17cc11d75c2c4a8ea87d65fd7e155464', '20210825201930909575', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:30', '2021-08-25 12:19:30', '123456_ooo', '2021-08-25 12:19:30', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('17e269b8aa0c40819457989239ba1567', '20210825201938160495', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:39', '2021-08-25 12:19:39', '123456_ooo', '2021-08-25 12:19:39', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('187c8f1448184cbf99439bff26e27699', '20210825201928290659', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:29', '2021-08-25 12:19:29', '123456_ooo', '2021-08-25 12:19:29', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('1b1ff85ed7774f10b763902d7769df5a', '20210726003025706704', '测试标题222', '测试内容', '000001', 3, 46, 3, 0, 0, '2021-07-25 16:30:25', '2021-07-25 16:30:25', '123456_abc', '2021-07-25 16:30:25', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('211c43302f184b94bf501d990e7a00ed', '20210825202159424817', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:59', '2021-08-25 12:21:59', '123456_ooo', '2021-08-25 12:21:59', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('22d239aba59943dd969ced572a9e2915', '20210727194443252791', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-27 11:44:43', '2021-07-27 11:44:43', '123456_abc', '2021-07-27 11:44:43', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('257170518f6c4bcebbe6e05091afc3f2', '20210825202200152264', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:01', '2021-08-25 12:22:01', '123456_ooo', '2021-08-25 12:22:01', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('276507d9719e464d8bf82458ed1c4adc', '20210825201940416280', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:41', '2021-08-25 12:19:41', '123456_ooo', '2021-08-25 12:19:41', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('28eb7b38cd794e3d8f2e79ece1c1bc4e', '20210825202204726331', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:05', '2021-08-25 12:22:05', '123456_ooo', '2021-08-25 12:22:05', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('2b5ec878f013438aa9526a68d7ca6a21', '20210825202214717708', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:14', '2021-08-25 12:22:14', '123456_ooo', '2021-08-25 12:22:14', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('2c124be9f1424fceb808ae759c387cb9', '20210825202214507384', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:15', '2021-08-25 12:22:15', '123456_ooo', '2021-08-25 12:22:15', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('33ed435961984e2eaad2e3334332aec8', '20210825202220627638', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:20', '2021-08-25 12:22:20', '123456_ooo', '2021-08-25 12:22:20', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('34d78852258a4b2fab6043668f17a589', '20210825202155205676', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:56', '2021-08-25 12:21:56', '123456_ooo', '2021-08-25 12:21:56', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('34faf8b059b849e1a86b21e8b8c1d17b', '20210825202222686432', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:23', '2021-08-25 12:22:23', '123456_ooo', '2021-08-25 12:22:23', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('357ccb9e078046b5beba36f6d4b0909d', '20210825201936990118', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:37', '2021-08-25 12:19:37', '123456_ooo', '2021-08-25 12:19:37', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('365de31612714cfcb468e8985e886027', '20210825201942500589', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:42', '2021-08-25 12:19:42', '123456_ooo', '2021-08-25 12:19:42', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('39f9e0f571a6452c96ccdce67a4b0321', '20210825201934236701', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:35', '2021-08-25 12:19:35', '123456_ooo', '2021-08-25 12:19:35', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('3a17c331ae304edc97fd8314aa5e25ea', '20210825202211605079', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:11', '2021-08-25 12:22:11', '123456_ooo', '2021-08-25 12:22:11', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('3a2fb90cf20c4ec4807adb50a9b36b99', '20210825202203978541', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:03', '2021-08-25 12:22:03', '123456_ooo', '2021-08-25 12:22:03', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('3c04e02be0254244b06eb268675fcd38', '2021082520214312379', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:43', '2021-08-25 12:21:43', '123456_ooo', '2021-08-25 12:21:43', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('3c1192cd3bf3412dabe6ee0a29ba618e', '20210825202150967768', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:51', '2021-08-25 12:21:51', '123456_ooo', '2021-08-25 12:21:51', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('3e9ce8d526cb4fd0836a9c34d96854a2', '20210825202216406647', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:16', '2021-08-25 12:22:16', '123456_ooo', '2021-08-25 12:22:16', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('409f89c21f1d4aeda8d32c90507d9eee', '20210825201933409351', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:34', '2021-08-25 12:19:34', '123456_ooo', '2021-08-25 12:19:34', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('443509e4741b409bbf3242f5ada3a132', '20210825202205241463', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:06', '2021-08-25 12:22:06', '123456_ooo', '2021-08-25 12:22:06', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('47dae3c263b942aea3300efcc6ff563d', '20210825201935217626', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:36', '2021-08-25 12:19:36', '123456_ooo', '2021-08-25 12:19:36', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('4d002432cce6482f9adae8efa84c993e', '20210825202217656899', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:17', '2021-08-25 12:22:17', '123456_ooo', '2021-08-25 12:22:17', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('4e8ed88078d04290a01e6d48b7c33813', '20210825202158875656', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:59', '2021-08-25 12:21:59', '123456_ooo', '2021-08-25 12:21:59', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('50d99f2cdbf94e689d040ed33f59a813', '20210825202226972453', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:26', '2021-08-25 12:22:26', '123456_ooo', '2021-08-25 12:22:26', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('50e526d7b39840a8af1a4860273972fd', '20210727193947222205', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-27 11:39:47', '2021-07-27 11:39:47', '123456_abc', '2021-07-27 11:39:47', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('525a5635219c444ea3024624a00aefd6', '20210825202140132049', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:41', '2021-08-25 12:21:41', '123456_ooo', '2021-08-25 12:21:41', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('55f0158cf834429389c9f98c9d533697', '20210825201937215008', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:37', '2021-08-25 12:19:37', '123456_ooo', '2021-08-25 12:19:37', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('56cd6f01610640efb9f3bd743517e462', '20210825202152605635', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:52', '2021-08-25 12:21:52', '123456_ooo', '2021-08-25 12:21:52', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('588d0366dff5403aa2b1909e07d06888', '20210825202205667048', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:05', '2021-08-25 12:22:05', '123456_ooo', '2021-08-25 12:22:05', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('58a72164d3a042cf8284cfd2f658bb0a', '20210825201926963229', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:27', '2021-08-25 12:19:27', '123456_ooo', '2021-08-25 12:19:27', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('5a6c511775474a14ac30ea2abd664408', '20210825202207277706', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:07', '2021-08-25 12:22:07', '123456_ooo', '2021-08-25 12:22:07', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('5b290236040a4346a7a3d4f9cddc7fdd', '20210825202227461608', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:27', '2021-08-25 12:22:27', '123456_ooo', '2021-08-25 12:22:27', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('5e54eb0e6ade42aaaf9412ff2a3eb81c', '20210825202207913479', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:08', '2021-08-25 12:22:08', '123456_ooo', '2021-08-25 12:22:08', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('5fcac374010a480c90109ddd075fc46c', '2021082520215329734', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:53', '2021-08-25 12:21:53', '123456_ooo', '2021-08-25 12:21:53', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('62780d8c682048cd8a3a41f5228895a3', '20210825202148944687', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:48', '2021-08-25 12:21:48', '123456_ooo', '2021-08-25 12:21:48', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('63c254e0444a43e39d1def6c2fa0aff9', '20210825202156246774', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:56', '2021-08-25 12:21:56', '123456_ooo', '2021-08-25 12:21:56', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('66917fb18dd04a0b9265bb929891076a', '20210825202142446676', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:43', '2021-08-25 12:21:43', '123456_ooo', '2021-08-25 12:21:43', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('69565f93cddc416188f4a9fe8f4bea5d', '20210825202221777859', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:22', '2021-08-25 12:22:22', '123456_ooo', '2021-08-25 12:22:22', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('69cbd597919841a78127fb9c77ee9f11', '20210825202145147006', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:45', '2021-08-25 12:21:45', '123456_ooo', '2021-08-25 12:21:45', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('6c7e875d696f4e33adfa775a10060f1e', '20210825202155713570', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:55', '2021-08-25 12:21:55', '123456_ooo', '2021-08-25 12:21:55', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('6c9ee4be9bb64204ae1e8214eaaeb698', '20210825202144691291', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:45', '2021-08-25 12:21:45', '123456_ooo', '2021-08-25 12:21:45', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('6d84038a13354110ba54e595c9cce7b5', '20210727195225331580', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-27 11:52:26', '2021-07-27 11:52:26', '123456_abc', '2021-07-27 11:52:26', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('74294630ccac4f79a6198a009466ee7f', '20210825202154500888', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:54', '2021-08-25 12:21:54', '123456_ooo', '2021-08-25 12:21:54', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('7ff498f87c9c46daa9ecfb0c8516e0de', '20210825202159237610', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:00', '2021-08-25 12:22:00', '123456_ooo', '2021-08-25 12:22:00', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('807f8efee3cb47808a6bdcb6b47c70a1', '20210825201946996399', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:46', '2021-08-25 12:19:46', '123456_ooo', '2021-08-25 12:19:46', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('88acf6394b074b3ba40b8018ef03303f', '20210825202147210782', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:47', '2021-08-25 12:21:47', '123456_ooo', '2021-08-25 12:21:47', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('89295110492443b5a3b1ceaec5ed18b1', '20210825202218198020', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:19', '2021-08-25 12:22:19', '123456_ooo', '2021-08-25 12:22:19', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('8b068adff4ba489885d9701574590891', '20210928012118960546', '第一个测试标题第一个测试标题第一个', 'fafawf', '000001', 0, 0, 0, 0, 0, '2021-09-27 17:21:18', '2021-09-27 17:21:18', '123456', '2021-09-27 17:21:18', '123456', 0);
INSERT INTO `y_c_topic` VALUES ('8d42ef97319f4aeeab81f0c681dbec0c', '20210825202149148564', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:50', '2021-08-25 12:21:50', '123456_ooo', '2021-08-25 12:21:50', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('8fcc00fa85f046c98efa4cd0a9117be7', '20210825201924281205', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:24', '2021-08-25 12:19:24', '123456_ooo', '2021-08-25 12:19:24', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('929a70c2a37949cca62498a1cacbd199', '20210825202212923156', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:13', '2021-08-25 12:22:13', '123456_ooo', '2021-08-25 12:22:13', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('954371004ef6413fa6ccea6b3bd4195c', '20210825201925681657', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:25', '2021-08-25 12:19:25', '123456_ooo', '2021-08-25 12:19:25', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('972fb1e42d954609b2206928ddf92b49', '20210825202222812510', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:22', '2021-08-25 12:22:22', '123456_ooo', '2021-08-25 12:22:22', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('99152f6ad5514551b115628358560f39', '20210825202151406714', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:52', '2021-08-25 12:21:52', '123456_ooo', '2021-08-25 12:21:52', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9c318b140bea4fae99f37f5a1b7823f0', '2021082520194671877', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:47', '2021-08-25 12:19:47', '123456_ooo', '2021-08-25 12:19:47', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9c8aa1f0a90e4464b9a4d37ece433e5f', '20210825202220926467', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:21', '2021-08-25 12:22:21', '123456_ooo', '2021-08-25 12:22:21', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9d444cede5f04f528dc01e7c17e52ef7', '20210825201950366452', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:51', '2021-08-25 12:19:51', '123456_ooo', '2021-08-25 12:19:51', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9dc0520137bf4758bb3222fdd97fbe56', '20210825202209404620', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:10', '2021-08-25 12:22:10', '123456_ooo', '2021-08-25 12:22:10', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9df830c501f64d099ca28c10e007ef82', '20210825202203344388', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:04', '2021-08-25 12:22:04', '123456_ooo', '2021-08-25 12:22:04', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9e5df58e63aa488bb9f5ec0e6088a94a', '20210825202209435954', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:09', '2021-08-25 12:22:09', '123456_ooo', '2021-08-25 12:22:09', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('9f5c8ff4e98d4155ae58edf96753090e', '20210825202144374107', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:44', '2021-08-25 12:21:44', '123456_ooo', '2021-08-25 12:21:44', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('a32518428b8b40cda4b0ffa45972b03a', '20210727194549423355', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-27 11:45:50', '2021-07-27 11:45:50', '123456_abc', '2021-07-27 11:45:50', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('a5e9486ed6834d9cbc929c834f58d750', '2021092801220683792', '第一个测试标题第一个测试标题第一个', 'fwawfawf', '000003', 0, 0, 0, 0, 0, '2021-09-27 17:22:06', '2021-09-27 17:22:06', '123456', '2021-09-27 17:22:06', '123456', 0);
INSERT INTO `y_c_topic` VALUES ('a67f96b55c164a38a5927fba9f801e9c', '20210825201942917980', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:43', '2021-08-25 12:19:43', '123456_ooo', '2021-08-25 12:19:43', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('a7070f3856d444f1add27706c80e7b95', '20210825202204955696', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:04', '2021-08-25 12:22:04', '123456_ooo', '2021-08-25 12:22:04', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('a7ca476ce4e0444c834c17f5fbd2e8ba', '20210825202206919591', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:06', '2021-08-25 12:22:06', '123456_ooo', '2021-08-25 12:22:06', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('a8453ccfebed4ce49802e9fe4869bc69', '20210825202226990875', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:27', '2021-08-25 12:22:27', '123456_ooo', '2021-08-25 12:22:27', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('aa9d83e29e9445a6b7b725ce21271dfe', '20210825201927170938', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:28', '2021-08-25 12:19:28', '123456_ooo', '2021-08-25 12:19:28', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('ac77da10de884b27975f61635eda8146', '20210825202154615354', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:55', '2021-08-25 12:21:55', '123456_ooo', '2021-08-25 12:21:55', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('af505ff78b9b4f35911f91bce7acbe68', '20210825202217492037', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:18', '2021-08-25 12:22:18', '123456_ooo', '2021-08-25 12:22:18', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('b0a4e8bbf3dc41bc80699f9e7c8e17f1', '20210825202146936747', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:47', '2021-08-25 12:21:47', '123456_ooo', '2021-08-25 12:21:47', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('b362b205816942659415dc319339a603', '20210825202208525008', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:08', '2021-08-25 12:22:08', '123456_ooo', '2021-08-25 12:22:08', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('b5a1ddedc9d7421e8805715fd1007cf3', '2021082520214910924', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:49', '2021-08-25 12:21:49', '123456_ooo', '2021-08-25 12:21:49', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('b5bb375fb1684750ab980f53cbf882a7', '20210825202212973338', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:12', '2021-08-25 12:22:12', '123456_ooo', '2021-08-25 12:22:12', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('b5fe64f3fa044aefa6b6f8530d962aff', '20210825202213363064', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:14', '2021-08-25 12:22:14', '123456_ooo', '2021-08-25 12:22:14', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('bc65763b8c8f42b7bc21c08c3f0e22b3', '20210825202153603048', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:54', '2021-08-25 12:21:54', '123456_ooo', '2021-08-25 12:21:54', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c01652e769d04a8599a5928c3b74d555', '20210825201940438646', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:40', '2021-08-25 12:19:40', '123456_ooo', '2021-08-25 12:19:40', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c245af474ce943c0b6ea201d87c2c26a', '20210825201931139427', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:31', '2021-08-25 12:19:31', '123456_ooo', '2021-08-25 12:19:31', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c41aecfb65184b2bb45568615e6cc821', '20210825201944338528', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:45', '2021-08-25 12:19:45', '123456_ooo', '2021-08-25 12:19:45', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c50ed7ee119d44cf9fd641b3b70f4819', '2021082520221996921', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:20', '2021-08-25 12:22:20', '123456_ooo', '2021-08-25 12:22:20', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c535fa9d9aa64f708224856ceadbc22d', '20210825202225643965', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:26', '2021-08-25 12:22:26', '123456_ooo', '2021-08-25 12:22:26', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c6c717abf8a646caba52f23f2323b66c', '20210726002547353384', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-25 16:25:48', '2021-07-25 16:25:48', '123456_abc', '2021-07-25 16:25:48', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('c99966605d1344688ddeddcb6cfc7d0e', '2021082520221662801', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:17', '2021-08-25 12:22:17', '123456_ooo', '2021-08-25 12:22:17', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('c9fd86a4b3364180b1801383ff6bf936', '20210825201926290142', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:26', '2021-08-25 12:19:26', '123456_ooo', '2021-08-25 12:19:26', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('ca327599198a497a845302116b24bc29', '20210825201923200627', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:24', '2021-08-25 12:19:24', '123456_ooo', '2021-08-25 12:19:24', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('cc268f3a80f44250ae773021b72a93df', '2021072600274539945', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-25 16:27:45', '2021-07-25 16:27:45', '123456_abc', '2021-07-25 16:27:45', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('cee060b466e248888f7a4f7422fe3f6e', '20210825201947317986', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:47', '2021-08-25 12:19:47', '123456_ooo', '2021-08-25 12:19:47', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('cfb6774f8fb8482ca79dfd7ab35e441d', '20210825202213788057', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:13', '2021-08-25 12:22:13', '123456_ooo', '2021-08-25 12:22:13', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('d19af2d9eaea4ff3ad7245161da34e9d', '20210825202141849575', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:42', '2021-08-25 12:21:42', '123456_ooo', '2021-08-25 12:21:42', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('d49723d93681461d80273b755537959e', '20210825201949323028', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:49', '2021-08-25 12:19:49', '123456_ooo', '2021-08-25 12:19:49', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('d7376702b2594af1ae369823843ff3f9', '20210825202152543012', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:53', '2021-08-25 12:21:53', '123456_ooo', '2021-08-25 12:21:53', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('d8037305ab164573a11c3709d6359d33', '20210825202201410409', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:02', '2021-08-25 12:22:02', '123456_ooo', '2021-08-25 12:22:02', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('d8a7dae72bf6490791dcf1fa032b9134', '20210928012306488978', '第一个测试标题第一个测试标题第一个', 'fwfawf', '000003', 0, 0, 0, 0, 0, '2021-09-27 17:23:07', '2021-09-27 17:23:07', '123456', '2021-09-27 17:23:07', '123456', 0);
INSERT INTO `y_c_topic` VALUES ('e145eccfd4204d2ebd44d67b2b758a2b', '20210825202211982007', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:12', '2021-08-25 12:22:12', '123456_ooo', '2021-08-25 12:22:12', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('e85ccd85429543c58e0b4ab2654bae7c', '202109280128104424', '第一个测试标题第一个测试标题第一个', 'fwfawf', '000002', 0, 2, 0, 0, 0, '2021-09-27 17:28:10', '2021-09-27 17:28:10', '123456', '2021-09-27 17:28:10', '123456', 0);
INSERT INTO `y_c_topic` VALUES ('e86240e2c4e64a01a32effeb3a0eb136', '20210825201933319242', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:33', '2021-08-25 12:19:33', '123456_ooo', '2021-08-25 12:19:33', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('e9acd3a0b52d45a9bc2978a36f963710', '20210825201948370300', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:48', '2021-08-25 12:19:48', '123456_ooo', '2021-08-25 12:19:48', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('e9d568e2424f4445b888ebc4de41c8ca', '20210825202151265433', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:51', '2021-08-25 12:21:51', '123456_ooo', '2021-08-25 12:21:51', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('eaf7ba1d519a4b08b213c7557256d0d4', '20210825201943488424', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:44', '2021-08-25 12:19:44', '123456_ooo', '2021-08-25 12:19:44', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('eb410608a7644aaa832c2c1d63abd96c', '20210825202224140698', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:25', '2021-08-25 12:22:25', '123456_ooo', '2021-08-25 12:22:25', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('eba5a48ad00b42ebaddf592512d3acd3', '202108252021454402', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:46', '2021-08-25 12:21:46', '123456_ooo', '2021-08-25 12:21:46', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('eea6ed62bf63478e8f3e79e469680632', '2021082520193096883', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:31', '2021-08-25 12:19:31', '123456_ooo', '2021-08-25 12:19:31', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f0236e0571cd45dd8ec00b0aff98114d', '20210825201939989342', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:40', '2021-08-25 12:19:40', '123456_ooo', '2021-08-25 12:19:40', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f0ecb750b67b4c7281c736a6b1a5938f', '2021082520193270524', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:32', '2021-08-25 12:19:32', '123456_ooo', '2021-08-25 12:19:32', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f110545bc244488881887b0042cef1e7', '20210825202146636589', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:46', '2021-08-25 12:21:46', '123456_ooo', '2021-08-25 12:21:46', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f2b5177b3e034c629dbc5d05868e2e1b', '20210825202200269704', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:00', '2021-08-25 12:22:00', '123456_ooo', '2021-08-25 12:22:00', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f2e523999b4d4af6a5f4ac26d4caf2b0', '20210825202143289375', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:44', '2021-08-25 12:21:44', '123456_ooo', '2021-08-25 12:21:44', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f4ecdc76a9b54d8b96c36241bf361c40', '20210825202215699635', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:16', '2021-08-25 12:22:16', '123456_ooo', '2021-08-25 12:22:16', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f591622c04444e9d9c05a1cbbd46bc8d', '20210825202223590996', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:22:24', '2021-08-25 12:22:24', '123456_ooo', '2021-08-25 12:22:24', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f681bd00300c4fb9b5b16f2aefaa1f4f', '20210825202149577364', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:50', '2021-08-25 12:21:50', '123456_ooo', '2021-08-25 12:21:50', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('f8045b69f50a4c7e806dc6d55697731d', '20210727194313484394', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-07-27 11:43:13', '2021-07-27 11:43:13', '123456_abc', '2021-07-27 11:43:13', '123456_abc', 0);
INSERT INTO `y_c_topic` VALUES ('fb13d3147c2847bca8485284b3c603fd', '20210825201945843728', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:19:45', '2021-08-25 12:19:45', '123456_ooo', '2021-08-25 12:19:45', '123456_ooo', 0);
INSERT INTO `y_c_topic` VALUES ('fceb7d9b025543418b4e26dd1503b46e', '20210825202156833650', '测试标题', '测试内容', '000001', 0, 1, 0, 0, 0, '2021-08-25 12:21:57', '2021-08-25 12:21:57', '123456_ooo', '2021-08-25 12:21:57', '123456_ooo', 0);

-- ----------------------------
-- Table structure for y_c_user
-- ----------------------------
DROP TABLE IF EXISTS `y_c_user`;
CREATE TABLE `y_c_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `account` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '账号',
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of y_c_user
-- ----------------------------
INSERT INTO `y_c_user` VALUES ('05d036d46e494c0f8657161981f02f59', '123456_abc', '测试用户2', '123456ab', '2021-07-24 10:32:49', '123456_abc', '2021-07-24 10:32:49', '123456_abc', 0);
INSERT INTO `y_c_user` VALUES ('0cd44ba42e5240319d887a2aace047b0', '123456_pal', '测试用户ool', '123456ab', '2021-09-24 12:51:29', '123456_pal', '2021-09-24 12:51:29', '123456_pal', 0);
INSERT INTO `y_c_user` VALUES ('11e19a16981d41be93704c08cf6425be', '123456_ab', '测试用户', '123456ab', '2021-07-24 10:25:59', '123456_ab', '2021-07-24 10:25:59', '123456_ab', 0);
INSERT INTO `y_c_user` VALUES ('2c2335180917418784fd2d50a9379713', '123456', 'testUser2', '123456', '2021-07-21 14:07:31', '123456', '2021-07-21 14:07:31', '123456', 0);
INSERT INTO `y_c_user` VALUES ('3385f921289149549b657680773a0ab4', '123456_abe', '测试用户4', '123456ab', '2021-07-24 10:38:17', '123456_abe', '2021-07-24 10:38:17', '123456_abe', 0);
INSERT INTO `y_c_user` VALUES ('49a07c9e1a2d49bdbf93d53a1514f61e', '123456_ppp', '测试用户ppp', '123456ab', '2021-08-12 08:53:05', '123456_ppp', '2021-08-12 08:53:05', '123456_ppp', 0);
INSERT INTO `y_c_user` VALUES ('5378a05244254356837c01df005959de', '123456_abd', '测试用户3', '123456ab', '2021-07-24 10:37:09', '123456_abd', '2021-07-24 10:37:09', '123456_abd', 0);
INSERT INTO `y_c_user` VALUES ('60672c5059e348259dc1434e38d5c58f', '123456_aop', '测试用户hhh', '123456ab', '2021-08-12 08:44:12', '123456_aop', '2021-08-12 08:44:12', '123456_aop', 0);
INSERT INTO `y_c_user` VALUES ('8c5fac35c5064fb3918fadf828992dfa', '123456_eee', '测试用户eee', '123456ab', '2021-08-12 09:18:00', '123456_eee', '2021-08-12 09:18:00', '123456_eee', 0);
INSERT INTO `y_c_user` VALUES ('8fbcba94e26c46ce808133e2b35fb47b', '123456_ooo', '测试用户ooo', '123456ab', '2021-08-12 09:23:20', '123456_ooo', '2021-08-12 09:23:20', '123456_ooo', 0);
INSERT INTO `y_c_user` VALUES ('ad874621ebd844529c96d7c37df80507', '123456_faf', '测试用户828', '123456ab', '2021-09-21 09:32:06', '123456_faf', '2021-09-21 09:32:06', '123456_faf', 0);

SET FOREIGN_KEY_CHECKS = 1;
