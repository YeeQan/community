ALTER TABLE `community_db`.`y_c_user_info`
ADD COLUMN `birthday` datetime NULL COMMENT '生日' AFTER `password`,
ADD COLUMN `city` varchar(255) NULL COMMENT '现居城市' AFTER `introduction`;
ADD COLUMN `school` varchar(255) NULL COMMENT '学校' AFTER `city`,
ADD COLUMN `major` varchar(255) NULL COMMENT '专业' AFTER `school`;