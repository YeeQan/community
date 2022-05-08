/* 删除通知表的 del_flag */
ALTER TABLE `yeexang_community_db`.`y_c_notification`
DROP COLUMN `del_flag`;

/* 消息通知表 */
CREATE TABLE `y_c_notifcation`  (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
    `notification_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知id',
    `notifier` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知者',
    `receiver` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收者',
    `outer_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知所属id',
    `inner_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知内容id',
    `notification_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知类型,1为评论帖子,2为回复评论',
    `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态,0为未读,1为已读',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者，一般用account标识',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者，一般用account标识',
    PRIMARY KEY (`notification_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;