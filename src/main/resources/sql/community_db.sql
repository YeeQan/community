/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : community_db

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 07/10/2022 20:51:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for y_c_comment
-- ----------------------------
DROP TABLE IF EXISTS `y_c_comment`;
CREATE TABLE `y_c_comment`  (
    `parent_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '父id',
    `id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
    `comment_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
    `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for y_c_dict
-- ----------------------------
DROP TABLE IF EXISTS `y_c_dict`;
CREATE TABLE `y_c_dict`  (
     `dict_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
     `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
     `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '值',
     `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
     `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
     `sort_flag` int NULL DEFAULT NULL COMMENT '排序标识',
     `create_time` datetime NOT NULL COMMENT '创建时间',
     `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
     PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for y_c_tag
-- ----------------------------
DROP TABLE IF EXISTS `y_c_tag`;
CREATE TABLE `y_c_tag`  (
    `id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
    `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for y_c_topic
-- ----------------------------
DROP TABLE IF EXISTS `y_c_topic`;
CREATE TABLE `y_c_topic`  (
      `id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
      `topic_title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
      `topic_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
      `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签',
      `comment_count` int NULL DEFAULT NULL COMMENT '评论数',
      `view_count` int NULL DEFAULT NULL COMMENT '浏览数',
      `last_comment_time` datetime NULL DEFAULT NULL COMMENT '最后一次评论时间',
      `create_time` datetime NOT NULL COMMENT '创建时间',
      `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
      `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
      `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for y_c_user
-- ----------------------------
DROP TABLE IF EXISTS `y_c_user`;
CREATE TABLE `y_c_user`  (
     `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号，唯一，用于登录',
     `user_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人资料',
     `homepage_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人主页id',
     `create_time` datetime NOT NULL COMMENT '创建时间',
     `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
     PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for y_c_user_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `y_c_user_dynamic`;
CREATE TABLE `y_c_user_dynamic`  (
     `id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
     `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
     `target_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标id',
     `source_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '源id',
     `create_time` datetime NOT NULL COMMENT '创建时间',
     `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for y_c_user_info
-- ----------------------------
DROP TABLE IF EXISTS `y_c_user_info`;
CREATE TABLE `y_c_user_info`  (
      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
      `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
      `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
      `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
      `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人介绍',
      `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
      `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司',
      `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
      `head_portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像 AliyunOSS url',
      `create_time` datetime NOT NULL COMMENT '创建时间',
      `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
      `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
      `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Records of y_c_dict
-- ----------------------------
INSERT INTO `y_c_dict` VALUES ('1', 'user__head_portrait_monkey', 'https://comubucket.oss-cn-shenzhen.aliyuncs.com/static/headPortrait/default/default.jpg', 'user_default_head_portrait', '用户默认头像', 1, '2021-11-26 16:32:14', '123456', '2021-11-26 16:34:59', '123456');
INSERT INTO `y_c_dict` VALUES ('2', 'user_sex_male', '1', 'user_sex', '用户性别男', 1, '2021-12-18 12:52:26', '123456', '2021-12-18 12:52:33', '123456');
INSERT INTO `y_c_dict` VALUES ('3', 'user_sex_female', '0', 'user_sex', '用户性别女', 2, '2021-12-18 12:53:27', '123456', '2021-12-18 12:53:32', '123456');
INSERT INTO `y_c_dict` VALUES ('4', 'user_sex_unknown', '2', 'user_sex', '用户性别未知', 3, '2021-12-18 12:55:08', '123456', '2021-12-18 12:55:39', '123456');


-- ----------------------------
-- Records of y_c_tag
-- ----------------------------
INSERT INTO `community_db`.`y_c_tag` (`id`, `name`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('104000000001', '生活', '2022-10-03 19:24:04', '1', '2022-10-03 19:24:09', '1');
INSERT INTO `community_db`.`y_c_tag` (`id`, `name`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('104000000002', '工作', '2022-10-03 19:24:34', '1', '2022-10-03 19:24:38', '1');
INSERT INTO `community_db`.`y_c_tag` (`id`, `name`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('104000000003', '灌水', '2022-10-03 19:25:29', '1', '2022-10-03 19:25:32', '1');
INSERT INTO `community_db`.`y_c_tag` (`id`, `name`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('104000000004', '娱乐', '2022-10-03 19:27:13', '1', '2022-10-03 19:27:16', '1');
INSERT INTO `community_db`.`y_c_tag` (`id`, `name`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('104000000005', '美容', '2022-10-03 19:27:35', '1', '2022-10-03 19:27:39', '1');
INSERT INTO `community_db`.`y_c_tag` (`id`, `name`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('104000000006', '舞蹈', '2022-10-03 19:27:55', '1', '2022-10-03 19:27:58', '1');
