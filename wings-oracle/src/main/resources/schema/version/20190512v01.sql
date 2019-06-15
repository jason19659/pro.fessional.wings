CREATE TABLE IF NOT EXISTS `SYS_SCHEMA_MANAGER`
(
  `TABLE_NAME` varchar(100) NOT NULL COMMENT '主表表名',
  `CREATE_DT`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `MODIFY_DT`  datetime     NOT NULL DEFAULT '1000-01-01' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日时',
  `COMMIT_ID`  bigint(20)   NOT NULL COMMENT '提交ID',
  `LOG_UPDATE` tinyint(1)   NOT NULL DEFAULT '1' COMMENT '保留被更新记录',
  `LOG_DELETE` tinyint(1)   NOT NULL DEFAULT '1' COMMENT '保留被删除记录',
  `SHARD_AUTO` int(11)      NOT NULL DEFAULT '1' COMMENT '自动水平分表数量，1为不分表。',
  PRIMARY KEY (`TABLE_NAME`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='表结构管理';

CREATE TABLE IF NOT EXISTS `SYS_SCHEMA_VERSION`
(
  `REVISION`  bigint(20) NOT NULL COMMENT '版本号+修订号',
  `CREATE_DT` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `MODIFY_DT` datetime   NOT NULL DEFAULT '1000-01-01' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日时',
  `COMMIT_ID` bigint(20) NOT NULL COMMENT '提交ID',
  `UPTO_SQL`  text       NOT NULL COMMENT '升级脚本',
  `UPTO_MD5`  char(32)   NOT NULL DEFAULT '' COMMENT '升级指纹',
  `UNDO_SQL`  text       NOT NULL COMMENT '降级脚本',
  `UNDO_MD5`  char(32)   NOT NULL DEFAULT '' COMMENT '降级指纹',
  `APPLY_DT`  datetime   NOT NULL DEFAULT '1000-01-01' COMMENT '执行日时',
  PRIMARY KEY (`REVISION`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='表结构版本';

-- --

REPLACE INTO `SYS_SCHEMA_MANAGER`(`TABLE_NAME`, `COMMIT_ID`, `LOG_UPDATE`, `LOG_DELETE`)
VALUES ('SYS_SCHEMA_MANAGER', 0, 1, 1),
       ('SYS_SCHEMA_VERSION', 0, 1, 1);

INSERT IGNORE `SYS_SCHEMA_VERSION`(`REVISION`, `COMMIT_ID`, `UPTO_SQL`, `UPTO_MD5`, `UNDO_SQL`, `UNDO_MD5`, `APPLY_DT`)
VALUES (2019051201, 0, '', '', '', '', NOW());