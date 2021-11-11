/* y_c_user 表增加 head_portrait 字段，表示用户头像 url */
ALTER TABLE `yeexang_community_db`.`y_c_user` ADD COLUMN `head_portrait` varchar(255) NOT NULL COMMENT '用户头像url' AFTER `password`;

/* y_c_dict 表修改 value 字段长度 */
ALTER TABLE `yeexang_community_db`.`y_c_dict` MODIFY COLUMN `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值' AFTER `label`;

/* y_c_dict 表修改 type 字段长度 */
ALTER TABLE `yeexang_community_db`.`y_c_dict` MODIFY COLUMN `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型' AFTER `value`;

/* y_c_dict 表增加用户默认头像记录 */
INSERT INTO `yeexang_community_db`.`y_c_dict` (`id`, `dict_id`, `label`, `value`, `type`, `description`, `sort_flag`, `create_time`, `create_user`, `update_time`, `update_user`, `del_flag`) VALUES ('0f76888916674df590b45ace8e8iw87b', '20210731132534720928', 'head_portrait_one', 'https://comubucket.oss-cn-shenzhen.aliyuncs.com/static/headPortrait/default/yellow_monkey.jpg', 'user_default_head_portrait', '用户默认头像url', 1, '2021-09-28 00:24:46', '123456', '2021-09-28 00:25:02', '123456', 0);
