CREATE TABLE `cdc`.`database_history`
(
    `id`                VARCHAR(36) NOT NULL,
    `history_data`      MEDIUMTEXT,
    `history_data_seq`  BIGINT NOT NULL,
    `record_insert_ts`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `record_insert_seq` BIGINT NOT NULL,
    PRIMARY KEY (`id`, `history_data_seq`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `cdc`.`offset_storage`
(
    `id`                VARCHAR(36) NOT NULL,
    `offset_key`        TEXT DEFAULT NULL,
    `offset_val`        TEXT DEFAULT NULL,
    `record_insert_ts`  TIMESTAMP NOT NULL,
    `record_insert_seq` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;