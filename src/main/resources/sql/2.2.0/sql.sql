ALTER TABLE `community_db`.`y_c_comment`
    ADD COLUMN `reply_ta_id` varchar(24) NULL COMMENT '评论taId' AFTER `comment_content`;