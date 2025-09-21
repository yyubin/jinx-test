-- Jinx Migration Header
-- jinx:baseline=sha256:initial
-- jinx:head=sha256:622c3cf3e69fc8285ad39e1e9a714dda23bc02c0c534907f9c59fa51ff05181c
-- jinx:version=20250922011352
-- jinx:generated=2025-09-22T01:13:53.257514


CREATE TABLE `Bird` (
  `name` VARCHAR(255) NOT NULL,
  `zoo_id` BIGINT,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `wingspan` TEXT,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_bird__zoo_id` ON `Bird` (`zoo_id`);

CREATE TABLE `Zoo` (
  `location` VARCHAR(100),
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `MammalProfile` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `diet` VARCHAR(255),
  `mammal_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_mammalprofile__mammal_id` ON `MammalProfile` (`mammal_id`);

CREATE TABLE `Animal` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `zoo_id` BIGINT,
  `name` VARCHAR(255) NOT NULL,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_animal__zoo_id` ON `Animal` (`zoo_id`);

CREATE TABLE `Mammal` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `zoo_id` BIGINT,
  `hasFur` TEXT,
  `name` VARCHAR(255) NOT NULL,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_mammal__zoo_id` ON `Mammal` (`zoo_id`);

CREATE TABLE `Reptile` (
  `zoo_id` BIGINT,
  `venomous` TEXT,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `isVenomous` TEXT,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE INDEX `ix_reptile__zoo_id` ON `Reptile` (`zoo_id`);