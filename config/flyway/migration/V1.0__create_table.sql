CREATE TABLE `tbl_user` (
  `id` VARCHAR(100) NOT NULL COMMENT 'ユーザID',
  `name` VARCHAR(64) NOT NULL COMMENT 'ユーザ名',
  `birthday` DATE NOT NULL COMMENT '生年月日',
  `email_address` VARCHAR(255) NOT NULL COMMENT 'メールアドレス',
  `created_by` VARCHAR(100) NOT NULL COMMENT '作成者',
  `created_at` DATETIME NOT NULL COMMENT '作成日時' DEFAULT CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) COMMENT '更新者',
  `updated_at` DATETIME COMMENT '更新日時' DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(`id`),
  UNIQUE unique_user_email_address(`email_address`)
) ENGINE = InnoDB, COMMENT 'ユーザ情報管理テーブル';


