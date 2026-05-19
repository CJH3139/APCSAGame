---
name: minecraft-plugin-scaffold-design
description: Design spec for the Gradle-based Paper 1.21 Minecraft plugin scaffold for APCSAGame
metadata:
  type: project
---

# APCSAGame -- Minecraft Plugin Scaffold Design

## Overview

Set up a Gradle-based Paper 1.21 Minecraft plugin project from scratch. The goal is a working scaffold: correct Gradle config, a minimal plugin entrypoint, and plugin metadata. No game logic yet.

## Platform

- **Server platform:** Paper 1.21.4
- **Java version:** 21 (required by Paper 1.21)
- **Gradle DSL:** Kotlin DSL (`build.gradle.kts`)
- **Group ID:** `com.cjh3139`
- **Artifact name:** `APCSAGame`

## Project Structure

```
APCSAGame/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── src/
    └── main/
        ├── java/
        │   └── com/cjh3139/apcsa/
        │       └── APCSAGame.java
        └── resources/
            └── plugin.yml
```

## Gradle Configuration

**`settings.gradle.kts`**
- Sets `rootProject.name = "APCSAGame"`

**`build.gradle.kts`**
- Applies `java` plugin
- Java toolchain set to version 21
- Repositories: `mavenCentral()` + PaperMC Maven snapshot repo (`https://repo.papermc.io/repository/maven-public/`)
- Dependencies: `compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")`
- `processResources` task copies `plugin.yml` into the jar

**Gradle wrapper**
- Uses Gradle 8.x (compatible with Java 21)

## Plugin Entrypoint

`src/main/java/com/cjh3139/apcsa/APCSAGame.java`

- Extends `org.bukkit.plugin.java.JavaPlugin`
- `onEnable()`: logs `"APCSAGame has been enabled!"` via `getLogger().info(...)`
- `onDisable()`: logs `"APCSAGame has been disabled!"`

## Plugin Metadata

`src/main/resources/plugin.yml`

```yaml
name: APCSAGame
version: 1.0
main: com.cjh3139.apcsa.APCSAGame
api-version: "1.21"
description: A Paper plugin scaffold for the APCSAGame project
author: CJH3139
```

## Out of Scope

- Game logic, commands, events, or any plugin functionality
- Shadow/fat jar (no third-party runtime dependencies needed yet)
- Unit tests