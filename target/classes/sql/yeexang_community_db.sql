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

 Date: 01/12/2021 15:57:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for y_c_comment
-- ----------------------------
DROP TABLE IF EXISTS `y_c_comment`;
CREATE TABLE `y_c_comment`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
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
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for y_c_dict
-- ----------------------------
DROP TABLE IF EXISTS `y_c_dict`;
CREATE TABLE `y_c_dict`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `dict_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号id',
  `label` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述',
  `sort_flag` int(255) UNSIGNED NOT NULL COMMENT '排序标识',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for y_c_notification
-- ----------------------------
DROP TABLE IF EXISTS `y_c_notification`;
CREATE TABLE `y_c_notification`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
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
  PRIMARY KEY (`notification_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for y_c_parameter
-- ----------------------------
DROP TABLE IF EXISTS `y_c_parameter`;
CREATE TABLE `y_c_parameter`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `parameter_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数id',
  `parameter_key` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签',
  `parameter_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`parameter_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for y_c_section
-- ----------------------------
DROP TABLE IF EXISTS `y_c_section`;
CREATE TABLE `y_c_section`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `section_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分区id',
  `section_name` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`section_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分区表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for y_c_topic
-- ----------------------------
DROP TABLE IF EXISTS `y_c_topic`;
CREATE TABLE `y_c_topic`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
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
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for y_c_user
-- ----------------------------
DROP TABLE IF EXISTS `y_c_user`;
CREATE TABLE `y_c_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `account` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '账号',
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `head_portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户头像url',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除标识，0为不删除，1为删除',
  PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
