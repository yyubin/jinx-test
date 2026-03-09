-- Jinx Migration Header
-- jinx:baseline=sha256:initial
-- jinx:head=sha256:1d228b21157becb321a8a3e8990e8430f9e2b4c645f5e4d4cf3a479942442981
-- jinx:version=20260309225310
-- jinx:generated=2026-03-09T22:53:11.415363


CREATE TABLE `digital_product` (
  `digital_delivery_method` ENUM('IMMEDIATE','EMAIL_LINK','STREAMING','IN_APP') NOT NULL,
  `id` BIGINT NOT NULL,
  `download_url` VARCHAR(200),
  `digital_has_drm` TINYINT(1) NOT NULL DEFAULT '0',
  `digital_max_downloads` INT NOT NULL,
  `digital_file_formats` VARCHAR(200),
  `file_size_mb` DOUBLE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_digital_product__id` ON `digital_product` (`id`);

CREATE TABLE `shipment` (
  `shipping_cost` VARCHAR(30),
  `tracking_url` VARCHAR(200),
  `estimated_delivery_at` TIMESTAMP(6),
  `order_id` BIGINT NOT NULL,
  `shipped_at` TIMESTAMP(6),
  `actual_delivery_at` TIMESTAMP(6),
  `shipment_status` ENUM('PREPARING','HANDED_TO_CARRIER','IN_TRANSIT','OUT_FOR_DELIVERY','DELIVERED','FAILED_DELIVERY','RETURNED_TO_SENDER','LOST') NOT NULL,
  `carrier_name` VARCHAR(100) NOT NULL,
  `weight_kg` DOUBLE NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP(6) NOT NULL,
  `delivery_note` VARCHAR(200),
  `tracking_number` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_shipment__tracking_number` UNIQUE (`tracking_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_shipment__order_id` ON `shipment` (`order_id`);

CREATE TABLE `manager` (
  `manager_level` ENUM('TEAM_LEAD','SENIOR_MANAGER','DIRECTOR','VP','C_LEVEL') NOT NULL,
  `max_direct_reports` INT NOT NULL,
  `team_name` VARCHAR(100) NOT NULL,
  `team_budget` VARCHAR(30),
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_manager__id` ON `manager` (`id`);

CREATE TABLE `employee` (
  `employee_number` VARCHAR(20) NOT NULL,
  `position` VARCHAR(100) NOT NULL,
  `salary` VARCHAR(30),
  `id` BIGINT NOT NULL,
  `supervisor_id` BIGINT,
  `department` VARCHAR(100) NOT NULL,
  `ssn_encrypted` VARCHAR(500),
  `employment_type` ENUM('FULL_TIME','PART_TIME','CONTRACT','FREELANCE','INTERN') NOT NULL,
  `termination_date` DATE,
  `hire_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_employee__employee_number` UNIQUE (`employee_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_employee__supervisor_id` ON `employee` (`supervisor_id`);
CREATE INDEX `ix_employee__id` ON `employee` (`id`);

CREATE TABLE `payment` (
  `amount` VARCHAR(30) NOT NULL,
  `refunded_at` TIMESTAMP(6),
  `gateway_reference` VARCHAR(100),
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `transaction_id` VARCHAR(100) NOT NULL,
  `failure_reason` VARCHAR(500),
  `status` ENUM('PENDING','PROCESSING','COMPLETED','FAILED','REFUNDED','PARTIALLY_REFUNDED','CANCELLED','EXPIRED') NOT NULL,
  `initiated_at` TIMESTAMP(6) NOT NULL,
  `refunded_amount` VARCHAR(30),
  `order_id` BIGINT NOT NULL,
  `completed_at` TIMESTAMP(6),
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_payment__transaction_id` UNIQUE (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_payment__order_id` ON `payment` (`order_id`);

CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sort_order` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '0',
  `description` VARCHAR(200),
  `parent_id` BIGINT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_category__parent_id` ON `category` (`parent_id`);

CREATE TABLE `crypto_payment` (
  `blockchain_tx_hash` VARCHAR(200),
  `wallet_address` VARCHAR(200) NOT NULL,
  `crypto_currency` ENUM('BTC','ETH','USDT','USDC','BNB','SOL','ADA','DOGE','XRP','MATIC') NOT NULL,
  `sender_wallet_address` VARCHAR(200),
  `network_name` VARCHAR(50) NOT NULL,
  `required_confirmations` INT NOT NULL,
  `current_confirmations` INT NOT NULL,
  `exchange_rate_at_payment` DECIMAL(20,8) NOT NULL,
  `id` BIGINT NOT NULL,
  `crypto_amount` DECIMAL(20,8) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_crypto_payment__blockchain_tx_hash` UNIQUE (`blockchain_tx_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_crypto_payment__id` ON `crypto_payment` (`id`);

CREATE TABLE `review` (
  `status` ENUM('PENDING_MODERATION','APPROVED','REJECTED','HIDDEN_BY_REPORT') NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `customer_id` BIGINT NOT NULL,
  `rating` INT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `helpful_count` INT NOT NULL,
  `image_urls` VARCHAR(2000),
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP(6) NOT NULL,
  `visible` TINYINT(1) NOT NULL DEFAULT '0',
  `report_count` INT NOT NULL,
  `order_item_id` BIGINT,
  `parent_review_id` BIGINT,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `updated_at` TIMESTAMP(6),
  `verified_purchase` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_review__order_item_id` UNIQUE (`order_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_review__parent_review_id` ON `review` (`parent_review_id`);
CREATE INDEX `ix_review__product_id` ON `review` (`product_id`);
CREATE INDEX `ix_review__customer_id` ON `review` (`customer_id`);

CREATE TABLE `electronics` (
  `length_cm` DOUBLE,
  `smart_device` TINYINT(1) NOT NULL DEFAULT '0',
  `connectors` VARCHAR(300),
  `fragile` TINYINT(1) NOT NULL DEFAULT '0',
  `id` BIGINT NOT NULL,
  `voltage` INT NOT NULL,
  `has_remote_control` TINYINT(1) NOT NULL DEFAULT '0',
  `country_of_origin` VARCHAR(100),
  `energy_rating` ENUM('A_TRIPLE_PLUS','A_DOUBLE_PLUS','A_PLUS','A','B','C','D','E','F','G'),
  `warranty_months` INT NOT NULL,
  `weight_kg` DOUBLE,
  `brand` VARCHAR(100),
  `requires_shipping` TINYINT(1) NOT NULL DEFAULT '0',
  `width_cm` DOUBLE,
  `height_cm` DOUBLE,
  `model_number` VARCHAR(100),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_electronics__id` ON `electronics` (`id`);

CREATE TABLE `customer` (
  `grade` ENUM('BRONZE','SILVER','GOLD','PLATINUM','DIAMOND') NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `loyalty_points` INT NOT NULL,
  `id` BIGINT NOT NULL,
  `marketing_consent` TINYINT(1) NOT NULL DEFAULT '0',
  `last_login_at` TIMESTAMP(6),
  `registered_at` TIMESTAMP(6) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_customer__username` UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_customer__id` ON `customer` (`id`);

CREATE TABLE `organization` (
  `representative_phone` VARCHAR(20),
  `website` VARCHAR(200),
  `business_registration_number` VARCHAR(50) NOT NULL,
  `organization_type` ENUM('CORPORATION','LLC','PARTNERSHIP','SOLE_PROPRIETORSHIP','NON_PROFIT','GOVERNMENT') NOT NULL,
  `ceo_name` VARCHAR(200),
  `parent_org_id` BIGINT,
  `legal_name` VARCHAR(200) NOT NULL,
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_organization__business_registration_number` UNIQUE (`business_registration_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_organization__parent_org_id` ON `organization` (`parent_org_id`);
CREATE INDEX `ix_organization__id` ON `organization` (`id`);

CREATE TABLE `vendor` (
  `total_reviews` INT NOT NULL,
  `categories` VARCHAR(500),
  `commission_rate` DECIMAL(3,2) NOT NULL,
  `average_rating` DOUBLE NOT NULL,
  `id` BIGINT NOT NULL,
  `store_name` VARCHAR(100) NOT NULL,
  `store_description` TEXT,
  `vendor_status` ENUM('PENDING_APPROVAL','APPROVED','SUSPENDED','BLACKLISTED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_vendor__id` ON `vendor` (`id`);

CREATE TABLE `inventory_transaction` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `note` VARCHAR(200),
  `inventory_id` BIGINT NOT NULL,
  `transaction_type` ENUM('PURCHASE_ORDER_RECEIPT','SALE_RESERVATION','SALE_DISPATCH','RETURN_RECEIPT','ADJUSTMENT_INCREASE','ADJUSTMENT_DECREASE','DAMAGE_WRITE_OFF','TRANSFER_IN','TRANSFER_OUT') NOT NULL,
  `quantity_after` INT NOT NULL,
  `occurred_at` TIMESTAMP(6) NOT NULL,
  `quantity_delta` INT NOT NULL,
  `reference_id` VARCHAR(100),
  `performed_by` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_inventory_transaction__inventory_id` ON `inventory_transaction` (`inventory_id`);

CREATE TABLE `supplier` (
  `certification_info` VARCHAR(200),
  `supply_category` VARCHAR(100) NOT NULL,
  `supplier_tier` ENUM('STANDARD','PREFERRED','STRATEGIC','EXCLUSIVE') NOT NULL,
  `average_lead_time_days` INT NOT NULL,
  `preferred_supplier` TINYINT(1) NOT NULL DEFAULT '0',
  `id` BIGINT NOT NULL,
  `minimum_order_amount` VARCHAR(30),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_supplier__id` ON `supplier` (`id`);

CREATE TABLE `credit_card_payment` (
  `card_network` ENUM('VISA','MASTERCARD','AMEX','UNIONPAY','JCB','DISCOVER','DINERSCLUB') NOT NULL,
  `three_ds_verified` TINYINT(1) NOT NULL DEFAULT '0',
  `card_number_encrypted` VARCHAR(500),
  `card_holder_name` VARCHAR(200) NOT NULL,
  `expiry_month` VARCHAR(4) NOT NULL,
  `expiry_year` VARCHAR(4) NOT NULL,
  `id` BIGINT NOT NULL,
  `installment_months` INT NOT NULL,
  `masked_card_number` VARCHAR(25) NOT NULL,
  `authorization_code` VARCHAR(100),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_credit_card_payment__id` ON `credit_card_payment` (`id`);

CREATE TABLE `order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `selected_options` TEXT,
  `sku_snapshot` VARCHAR(50) NOT NULL,
  `line_total` VARCHAR(30) NOT NULL,
  `returned_quantity` INT NOT NULL,
  `product_name_snapshot` VARCHAR(200) NOT NULL,
  `quantity` INT NOT NULL,
  `item_status` ENUM('ORDERED','PREPARING','SHIPPED','DELIVERED','CANCELLED','RETURN_REQUESTED','RETURNED') NOT NULL,
  `order_id` BIGINT NOT NULL,
  `discount_rate` DOUBLE NOT NULL,
  `product_id` BIGINT NOT NULL,
  `unit_price_snapshot` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_order_item__product_id` ON `order_item` (`product_id`);
CREATE INDEX `ix_order_item__order_id` ON `order_item` (`order_id`);

CREATE TABLE `premium_customer` (
  `auto_renew` TINYINT(1) NOT NULL DEFAULT '0',
  `id` BIGINT NOT NULL,
  `exclusive_discount_rate` DECIMAL(5,4) NOT NULL,
  `membership_end_date` DATE,
  `dedicated_manager_id` BIGINT,
  `premium_tier` ENUM('BASIC','PLUS','ELITE','VIP') NOT NULL,
  `membership_start_date` DATE NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_premium_customer__id` ON `premium_customer` (`id`);
CREATE INDEX `ix_premium_customer__dedicated_manager_id` ON `premium_customer` (`dedicated_manager_id`);

CREATE TABLE `bank_transfer_payment` (
  `bank_name` VARCHAR(100) NOT NULL,
  `actual_transfer_date` DATE,
  `account_number` VARCHAR(50) NOT NULL,
  `swift_code` VARCHAR(100),
  `verified` TINYINT(1) NOT NULL DEFAULT '0',
  `account_holder_name` VARCHAR(100) NOT NULL,
  `transfer_note` VARCHAR(200),
  `iban_number` VARCHAR(100),
  `expected_transfer_date` DATE NOT NULL,
  `bank_code` VARCHAR(50) NOT NULL,
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_bank_transfer_payment__id` ON `bank_transfer_payment` (`id`);

CREATE TABLE `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cost_price` VARCHAR(30),
  `updated_at` TIMESTAMP(6),
  `category_id` BIGINT,
  `product_status` ENUM('DRAFT','PENDING_REVIEW','ACTIVE','OUT_OF_STOCK','DISCONTINUED','RECALLED') NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `average_rating` DOUBLE NOT NULL,
  `vendor_id` BIGINT NOT NULL,
  `specifications` TEXT,
  `price` VARCHAR(30) NOT NULL,
  `image_urls` VARCHAR(2000),
  `search_tags` VARCHAR(1000),
  `review_count` INT NOT NULL,
  `sku` VARCHAR(50) NOT NULL,
  `created_at` TIMESTAMP(6) NOT NULL,
  `description` TEXT,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_product__sku` UNIQUE (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_product__category_id` ON `product` (`category_id`);
CREATE INDEX `ix_product__vendor_id` ON `product` (`vendor_id`);

CREATE TABLE `person` (
  `gender` ENUM('MALE','FEMALE','NON_BINARY','PREFER_NOT_TO_SAY'),
  `phone` VARCHAR(20),
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(320),
  `id` BIGINT NOT NULL,
  `bio` TEXT,
  `birth_date` DATE,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_person__email` UNIQUE (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_person__id` ON `person` (`id`);

CREATE TABLE `software_license` (
  `delivery_method` ENUM('IMMEDIATE','EMAIL_LINK','STREAMING','IN_APP') NOT NULL,
  `software_name` VARCHAR(100),
  `validity_days` INT NOT NULL,
  `subscription_based` TINYINT(1) NOT NULL DEFAULT '0',
  `max_downloads` INT NOT NULL,
  `file_formats` VARCHAR(200),
  `version` VARCHAR(50),
  `license_type` ENUM('SINGLE_USER','MULTI_USER','SITE','ENTERPRISE','OPEN_SOURCE','FREEWARE'),
  `has_drm` TINYINT(1) NOT NULL DEFAULT '0',
  `supports_offline_mode` TINYINT(1) NOT NULL DEFAULT '0',
  `file_size_mb` DOUBLE,
  `activation_instructions` VARCHAR(200),
  `max_installations` INT NOT NULL,
  `id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_software_license__id` ON `software_license` (`id`);

CREATE TABLE `customer_wishlist` (
  `customer_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  PRIMARY KEY (`customer_id`, `product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `clothing` (
  `brand` VARCHAR(100),
  `primary_color` VARCHAR(10),
  `washable` TINYINT(1) NOT NULL DEFAULT '0',
  `target_gender` ENUM('MEN','WOMEN','UNISEX','KIDS','BABY'),
  `country_of_origin` VARCHAR(100),
  `length_cm` DOUBLE,
  `secondary_color` VARCHAR(10),
  `width_cm` DOUBLE,
  `available_sizes` VARCHAR(200),
  `id` BIGINT NOT NULL,
  `height_cm` DOUBLE,
  `requires_shipping` TINYINT(1) NOT NULL DEFAULT '0',
  `weight_kg` DOUBLE,
  `care_instructions` VARCHAR(50),
  `materials` VARCHAR(300),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_clothing__id` ON `clothing` (`id`);

CREATE TABLE `warehouse` (
  `warehouse_type` ENUM('STANDARD','COLD_STORAGE','BONDED','FULFILLMENT_CENTER','CROSS_DOCK') NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `wh_state` VARCHAR(100),
  `used_capacity_sqm` DOUBLE NOT NULL,
  `wh_postal_code` VARCHAR(20),
  `wh_street` VARCHAR(200),
  `name` VARCHAR(100) NOT NULL,
  `wh_country` VARCHAR(100),
  `contact_phone` VARCHAR(20),
  `warehouse_code` VARCHAR(50) NOT NULL,
  `wh_city` VARCHAR(100),
  `active` TINYINT(1) NOT NULL DEFAULT '0',
  `total_capacity_sqm` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_warehouse__warehouse_code` UNIQUE (`warehouse_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `order` (
  `ship_postal_code` VARCHAR(20),
  `shipping_fee` VARCHAR(30),
  `paid_at` TIMESTAMP(6),
  `bill_city` VARCHAR(100),
  `total_amount` VARCHAR(30) NOT NULL,
  `cancelled_at` TIMESTAMP(6),
  `delivered_at` TIMESTAMP(6),
  `ship_country` VARCHAR(100),
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ship_city` VARCHAR(100),
  `bill_postal_code` VARCHAR(20),
  `coupon_code` VARCHAR(50),
  `ship_street` VARCHAR(200),
  `customer_id` BIGINT NOT NULL,
  `subtotal` VARCHAR(30) NOT NULL,
  `customer_note` TEXT,
  `ordered_at` TIMESTAMP(6) NOT NULL,
  `status` ENUM('CART','PENDING_PAYMENT','PAYMENT_CONFIRMED','PREPARING','SHIPPED','DELIVERED','CANCELLED','RETURN_REQUESTED','RETURNED','PARTIALLY_SHIPPED','ON_HOLD') NOT NULL,
  `shipped_at` TIMESTAMP(6),
  `bill_state` VARCHAR(100),
  `order_number` VARCHAR(50) NOT NULL,
  `ship_state` VARCHAR(100),
  `bill_country` VARCHAR(100),
  `order_metadata` TEXT,
  `discount_amount` VARCHAR(30),
  `bill_street` VARCHAR(200),
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_order__order_number` UNIQUE (`order_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_order__customer_id` ON `order` (`customer_id`);

CREATE TABLE `physical_product` (
  `height_cm` DOUBLE,
  `country_of_origin` VARCHAR(100),
  `length_cm` DOUBLE,
  `weight_kg` DOUBLE,
  `hazardous` TINYINT(1) NOT NULL DEFAULT '0',
  `id` BIGINT NOT NULL,
  `width_cm` DOUBLE,
  `requires_shipping` TINYINT(1) NOT NULL DEFAULT '0',
  `fragile` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_physical_product__id` ON `physical_product` (`id`);

CREATE TABLE `party` (
  `updated_at` TIMESTAMP(6) NOT NULL,
  `status` ENUM('ACTIVE','SUSPENDED','DORMANT','TERMINATED') NOT NULL,
  `created_at` TIMESTAMP(6) NOT NULL,
  `addr_state` VARCHAR(100),
  `addr_city` VARCHAR(100),
  `addr_country` VARCHAR(100),
  `extra_attributes` TEXT,
  `addr_street` VARCHAR(200),
  `tags` VARCHAR(500),
  `display_name` VARCHAR(200) NOT NULL,
  `addr_postal_code` VARCHAR(20),
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `coupon` (
  `discount_amount` VARCHAR(30),
  `per_customer_limit` INT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `minimum_order_amount` VARCHAR(30),
  `valid_until` TIMESTAMP(6) NOT NULL,
  `used_count` INT NOT NULL,
  `first_order_only` TINYINT(1) NOT NULL DEFAULT '0',
  `max_discount_amount` VARCHAR(30),
  `total_usage_limit` INT NOT NULL,
  `discount_type` ENUM('FIXED_AMOUNT','PERCENTAGE','FREE_SHIPPING','BUY_X_GET_Y') NOT NULL,
  `code` VARCHAR(50) NOT NULL,
  `valid_from` TIMESTAMP(6) NOT NULL,
  `premium_customer_only` TINYINT(1) NOT NULL DEFAULT '0',
  `discount_rate` DOUBLE,
  `description` TEXT,
  `status` ENUM('DRAFT','ACTIVE','EXPIRED','EXHAUSTED','CANCELLED') NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `applicable_category_ids` VARCHAR(500),
  `excluded_product_ids` VARCHAR(500),
  PRIMARY KEY (`id`),
  CONSTRAINT `uq_coupon__code` UNIQUE (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `downloadable_media` (
  `publisher` VARCHAR(200),
  `language` VARCHAR(10),
  `media_max_downloads` INT NOT NULL,
  `age_rating` VARCHAR(20),
  `artist` VARCHAR(200),
  `page_count` INT,
  `media_file_formats` VARCHAR(200),
  `id` BIGINT NOT NULL,
  `duration_seconds` INT,
  `media_type` ENUM('AUDIO','VIDEO','EBOOK','AUDIOBOOK','COMIC','MAGAZINE','COURSE'),
  `explicit_content` TINYINT(1) NOT NULL DEFAULT '0',
  `isbn` VARCHAR(50),
  `file_size_mb` DOUBLE,
  `media_has_drm` TINYINT(1) NOT NULL DEFAULT '0',
  `media_delivery_method` ENUM('IMMEDIATE','EMAIL_LINK','STREAMING','IN_APP') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_downloadable_media__id` ON `downloadable_media` (`id`);

CREATE TABLE `inventory` (
  `reorder_quantity` INT NOT NULL,
  `version` BIGINT,
  `quantity_reserved` INT NOT NULL,
  `supplier_id` BIGINT,
  `quantity_on_hand` INT NOT NULL,
  `updated_at` TIMESTAMP(6),
  `warehouse_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `reorder_point` INT NOT NULL,
  `safety_stock` INT NOT NULL,
  `created_at` TIMESTAMP(6) NOT NULL,
  `quantity_on_order` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_inventory__product_id` ON `inventory` (`product_id`);
CREATE INDEX `ix_inventory__supplier_id` ON `inventory` (`supplier_id`);
CREATE INDEX `ix_inventory__warehouse_id` ON `inventory` (`warehouse_id`);

ALTER TABLE `digital_product` ADD CONSTRAINT `fk_digital_product__id__product` FOREIGN KEY (`id`) REFERENCES `product` (`id`);
ALTER TABLE `shipment` ADD CONSTRAINT `fk_shipment__order_id__order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `manager` ADD CONSTRAINT `fk_manager__id__employee` FOREIGN KEY (`id`) REFERENCES `employee` (`id`);
ALTER TABLE `employee` ADD CONSTRAINT `fk_employee__id__person` FOREIGN KEY (`id`) REFERENCES `person` (`id`);
ALTER TABLE `employee` ADD CONSTRAINT `fk_employee__supervisor_id__employee` FOREIGN KEY (`supervisor_id`) REFERENCES `employee` (`id`);
ALTER TABLE `payment` ADD CONSTRAINT `fk_payment__order_id__order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `category` ADD CONSTRAINT `fk_category__parent_id__category` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`);
ALTER TABLE `crypto_payment` ADD CONSTRAINT `fk_crypto_payment__id__payment` FOREIGN KEY (`id`) REFERENCES `payment` (`id`);
ALTER TABLE `review` ADD CONSTRAINT `fk_review__customer_id__customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
ALTER TABLE `review` ADD CONSTRAINT `fk_review__parent_review_id__review` FOREIGN KEY (`parent_review_id`) REFERENCES `review` (`id`);
ALTER TABLE `review` ADD CONSTRAINT `fk_review__product_id__product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `review` ADD CONSTRAINT `fk_review__order_item_id__order_item` FOREIGN KEY (`order_item_id`) REFERENCES `order_item` (`id`);
ALTER TABLE `electronics` ADD CONSTRAINT `fk_electronics__id__product` FOREIGN KEY (`id`) REFERENCES `product` (`id`);
ALTER TABLE `customer` ADD CONSTRAINT `fk_customer__id__person` FOREIGN KEY (`id`) REFERENCES `person` (`id`);
ALTER TABLE `organization` ADD CONSTRAINT `fk_organization__id__party` FOREIGN KEY (`id`) REFERENCES `party` (`id`);
ALTER TABLE `organization` ADD CONSTRAINT `fk_organization__parent_org_id__organization` FOREIGN KEY (`parent_org_id`) REFERENCES `organization` (`id`);
ALTER TABLE `vendor` ADD CONSTRAINT `fk_vendor__id__organization` FOREIGN KEY (`id`) REFERENCES `organization` (`id`);
ALTER TABLE `inventory_transaction` ADD CONSTRAINT `fk_inventory_transaction__inventory_id__inventory` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`id`);
ALTER TABLE `supplier` ADD CONSTRAINT `fk_supplier__id__organization` FOREIGN KEY (`id`) REFERENCES `organization` (`id`);
ALTER TABLE `credit_card_payment` ADD CONSTRAINT `fk_credit_card_payment__id__payment` FOREIGN KEY (`id`) REFERENCES `payment` (`id`);
ALTER TABLE `order_item` ADD CONSTRAINT `fk_order_item__product_id__product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `order_item` ADD CONSTRAINT `fk_order_item__order_id__order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);
ALTER TABLE `premium_customer` ADD CONSTRAINT `fk_premium_customer__id__customer` FOREIGN KEY (`id`) REFERENCES `customer` (`id`);
ALTER TABLE `premium_customer` ADD CONSTRAINT `fk_premium_customer__dedicated_manager_id__manager` FOREIGN KEY (`dedicated_manager_id`) REFERENCES `manager` (`id`);
ALTER TABLE `bank_transfer_payment` ADD CONSTRAINT `fk_bank_transfer_payment__id__payment` FOREIGN KEY (`id`) REFERENCES `payment` (`id`);
ALTER TABLE `product` ADD CONSTRAINT `fk_product__category_id__category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
ALTER TABLE `product` ADD CONSTRAINT `fk_product__vendor_id__vendor` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`);
ALTER TABLE `person` ADD CONSTRAINT `fk_person__id__party` FOREIGN KEY (`id`) REFERENCES `party` (`id`);
ALTER TABLE `software_license` ADD CONSTRAINT `fk_software_license__id__product` FOREIGN KEY (`id`) REFERENCES `product` (`id`);
ALTER TABLE `customer_wishlist` ADD CONSTRAINT `fk_customer_wishlist__customer_id__customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
ALTER TABLE `clothing` ADD CONSTRAINT `fk_clothing__id__product` FOREIGN KEY (`id`) REFERENCES `product` (`id`);

ALTER TABLE `order` ADD CONSTRAINT `fk_order__customer_id__customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
ALTER TABLE `physical_product` ADD CONSTRAINT `fk_physical_product__id__product` FOREIGN KEY (`id`) REFERENCES `product` (`id`);


ALTER TABLE `downloadable_media` ADD CONSTRAINT `fk_downloadable_media__id__product` FOREIGN KEY (`id`) REFERENCES `product` (`id`);
ALTER TABLE `inventory` ADD CONSTRAINT `fk_inventory__product_id__product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `inventory` ADD CONSTRAINT `fk_inventory__supplier_id__supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`);
ALTER TABLE `inventory` ADD CONSTRAINT `fk_inventory__warehouse_id__warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`id`);