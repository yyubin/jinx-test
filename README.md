# jinx-test

Jinx 기능을 검증하기 위한 **샘플 프로젝트**입니다.
간단한 JPA 엔티티를 정의하고, Jinx Annotation Processor/CLI를 통해 **스키마 스냅샷(JSON)** 과 **마이그레이션 SQL**을 생성합니다.

---

## 📂 프로젝트 구조

```
org.jinx.jinxtest
 ├── domain/           # 샘플 엔티티
 │   ├── Animal
 │   ├── Bird
 │   ├── Mammal
 │   ├── MammalProfile
 │   ├── Reptile
 │   └── Zoo
 │
 └── jinxoutput/
      ├── json/        # 스키마 스냅샷
      │   └── schema-20250922011352.json
      └── sql/         # 마이그레이션 SQL
          ├── V20250922010911__migration__jinxHead_.sql
          └── V20250922011352__migration__jinxHead_.sql
```

---

## 🚀 실행 방법

### 1. 의존성 추가

`build.gradle`에 Jinx를 추가하세요:

```gradle
dependencies {
    implementation "io.github.yyubin:jinx-core:0.0.7"
    annotationProcessor "io.github.yyubin:jinx-processor:0.0.7"
}
```

CLI 태스크 실행을 원한다면:

```gradle
configurations {
    jinxCli
}

dependencies {
    jinxCli "io.github.yyubin:jinx-cli:0.0.7"
}

tasks.register('jinxMigrate', JavaExec) {
    group = 'jinx'
    classpath = configurations.jinxCli
    mainClass = 'org.jinx.cli.JinxCli'
    args 'db', 'migrate', '-d', 'mysql'
    dependsOn 'classes'
}
```

---

### 2. 빌드 및 스냅샷 생성

```bash
./gradlew build
```

→ `jinxoutput/json/`에 `schema-*.json` 파일이 생성됩니다.

---

### 3. 마이그레이션 실행

```bash
./gradlew jinxMigrate
```

→ `jinxoutput/sql/`에 마이그레이션 SQL이 생성됩니다.

---

## 📝 예시 결과

Jinx가 생성한 SQL 일부입니다:

```sql
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
);
CREATE INDEX `ix_bird__zoo_id` ON `Bird` (`zoo_id`);

CREATE TABLE `Zoo` (
  `location` VARCHAR(100),
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
);

CREATE TABLE `MammalProfile` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `diet` VARCHAR(255),
  `mammal_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE INDEX `ix_mammalprofile__mammal_id` ON `MammalProfile` (`mammal_id`);

CREATE TABLE `Animal` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `zoo_id` BIGINT,
  `name` VARCHAR(255) NOT NULL,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE INDEX `ix_animal__zoo_id` ON `Animal` (`zoo_id`);

CREATE TABLE `Mammal` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `zoo_id` BIGINT,
  `hasFur` TEXT,
  `name` VARCHAR(255) NOT NULL,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE INDEX `ix_mammal__zoo_id` ON `Mammal` (`zoo_id`);

CREATE TABLE `Reptile` (
  `zoo_id` BIGINT,
  `venomous` TEXT,
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `isVenomous` TEXT,
  `type` TEXT NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE INDEX `ix_reptile__zoo_id` ON `Reptile` (`zoo_id`);
```
