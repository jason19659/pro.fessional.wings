-- sys_light_sequence ddlTable
CREATE TABLE `sys_light_sequence` (
  `seq_name` varchar(100) NOT NULL COMMENT '序列名',
  `block_id` int(11) NOT NULL DEFAULT '0' COMMENT '分块序号',
  `next_val` bigint(20) NOT NULL DEFAULT '1' COMMENT '下一个序号',
  `step_val` int(11) NOT NULL DEFAULT '100' COMMENT '序列步长',
  `comments` varchar(200) NOT NULL COMMENT '注释说明',
  PRIMARY KEY (`seq_name`,`block_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='序号生成器';

-- schema

-- sys_schema_journal ddlTable
CREATE TABLE `sys_schema_journal` (
  `table_name` varchar(100) NOT NULL COMMENT '主表表名',
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `modify_dt` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日时',
  `commit_id` bigint(20) NOT NULL COMMENT '提交ID',
  `ddl_updtbl` text NOT NULL COMMENT '更新的跟踪表DDL',
  `ddl_updtrg` text NOT NULL COMMENT '更新的触发器DDL',
  `ddl_deltbl` text NOT NULL COMMENT '删除的跟踪表DDL',
  `ddl_deltrg` text NOT NULL COMMENT '删除的触发器DDL',
  `log_update` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '开始跟踪更新的日时',
  `log_delete` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '开始跟踪删除的日时',
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表结构管理';

-- sys_schema_version ddlTable
CREATE TABLE `sys_schema_version` (
  `revision` bigint(20) NOT NULL COMMENT '版本号+修订号',
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `modify_dt` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日时',
  `commit_id` bigint(20) NOT NULL COMMENT '提交ID',
  `upto_sql` text NOT NULL COMMENT '升级脚本',
  `undo_sql` text NOT NULL COMMENT '降级脚本',
  `apply_dt` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '执行日时',
  PRIMARY KEY (`revision`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表结构版本';

--

-- WG_ORDER ddlTable
CREATE TABLE `WG_ORDER` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `CREATE_DT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `MODIFY_DT` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日时',
  PRIMARY KEY (`ID`),
  KEY `IDX_CREATE_DT` (`CREATE_DT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试订单';

-- sys_commit_journal ddlTable
CREATE TABLE `sys_commit_journal` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `event_name` varchar(200) NOT NULL COMMENT '事件名称',
  `target_key` varchar(200) NOT NULL DEFAULT '' COMMENT '目标数据特征',
  `login_info` text COMMENT '登陆信息，用户，终端等',
  `other_info` text COMMENT '其他信息，业务侧自定义',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变更日志';

-- tst_中文也分表 ddlTable
CREATE TABLE `tst_中文也分表` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日时',
  `modify_dt` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日时',
  `delete_dt` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '标记删除',
  `commit_id` bigint(20) NOT NULL COMMENT '提交ID',
  `login_info` text COMMENT '登陆信息，用户，终端等',
  `other_info` text COMMENT '其他信息，业务侧自定义',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变更日志';





-- tst_中文也分表$del ddlTable
CREATE TABLE `tst_中文也分表$del` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_dt` datetime DEFAULT NULL,
  `id` bigint(20) DEFAULT NULL COMMENT '主键',
  `create_dt` datetime DEFAULT NULL COMMENT '创建日时',
  `modify_dt` datetime DEFAULT NULL COMMENT '修改日时',
  `delete_dt` datetime DEFAULT NULL COMMENT '标记删除',
  `commit_id` bigint(20) DEFAULT NULL COMMENT '提交ID',
  `login_info` text COMMENT '登陆信息，用户，终端等',
  `other_info` text COMMENT '其他信息，业务侧自定义',
  PRIMARY KEY (`_id`),
  KEY `PLAIN_PK` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- tst_中文也分表$upd ddlTable
CREATE TABLE `tst_中文也分表$upd` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `_dt` datetime DEFAULT NULL,
  `id` bigint(20) DEFAULT NULL COMMENT '主键',
  `create_dt` datetime DEFAULT NULL COMMENT '创建日时',
  `modify_dt` datetime DEFAULT NULL COMMENT '修改日时',
  `delete_dt` datetime DEFAULT NULL COMMENT '标记删除',
  `commit_id` bigint(20) DEFAULT NULL COMMENT '提交ID',
  `login_info` text COMMENT '登陆信息，用户，终端等',
  `other_info` text COMMENT '其他信息，业务侧自定义',
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELIMITER $$

-- tst_中文也分表 ddlTrigger
CREATE TRIGGER `tst_中文也分表$bu` BEFORE UPDATE ON `tst_中文也分表`
FOR EACH ROW BEGIN 
INSERT INTO `tst_中文也分表$upd` SELECT NULL, NOW(), `*` FROM `tst_中文也分表` 
WHERE id= OLD.id ; 
END $$

-- tst_中文也分表 ddlTrigger
CREATE TRIGGER `tst_中文也分表$bd` BEFORE DELETE ON `tst_中文也分表`
FOR EACH ROW BEGIN 
INSERT INTO `tst_中文也分表$del` SELECT NULL, NOW(), `*` FROM `tst_中文也分表` 
WHERE id= OLD.id ; 
END $$

DELIMITER ;