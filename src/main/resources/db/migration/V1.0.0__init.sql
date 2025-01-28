-- 账户表----------------------------
CREATE TABLE `t_account`
(
    `id`          bigint                NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32)                NOT NULL COMMENT '用户id',
    `avatar`      varchar(120) default null COMMENT '头像',
    `nick_name`   varchar(50)  default null COMMENT '昵称',
    `mobile`      varchar(20)  default null COMMENT '手机号',
    `username`    varchar(20)  default null COMMENT '用户名',
    `gender`      tinyint   default 0 COMMENT '性别 0-未知 1-男 2-女',
    `status`      tinyint   DEFAULT 0 COMMENT '状态 0-初始化 1-正常 2-冻结 3-注销',
    `deleted`     bit          default b'0' null comment '逻辑删除',
    `create_by`   varchar(32)  DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(32)  DEFAULT NULL COMMENT '更新人',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id` (`user_id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '账户表';