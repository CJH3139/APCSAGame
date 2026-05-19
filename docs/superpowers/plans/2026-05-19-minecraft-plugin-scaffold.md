# Minecraft Plugin Scaffold Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create a working Gradle-based Paper 1.21 Minecraft plugin scaffold that compiles to a jar.

**Architecture:** Standard Gradle Kotlin DSL project with the Paper API as a `compileOnly` dependency. A single plugin entrypoint class extends `JavaPlugin` and a `plugin.yml` provides server metadata. No game logic yet.

**Tech Stack:** Java 21, Gradle 8.13 (Kotlin DSL), Paper API 1.21.4-R0.1-SNAPSHOT

---

## File Map

| Action | Path | Responsibility |
|--------|------|----------------|
| Create | `settings.gradle.kts` | Declares root project name |
| Create | `build.gradle.kts` | Java toolchain, repos, Paper dependency |
| Create | `gradle/wrapper/gradle-wrapper.properties` | Pins Gradle 8.13 |
| Generate | `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar` | Wrapper executables (via `gradle wrapper`) |
| Create | `src/main/java/com/cjh3139/apcsa/APCSAGame.java` | Plugin entrypoint |
| Create | `src/main/resources/plugin.yml` | Plugin metadata for the server |

---

### Task 1: Bootstrap the Gradle wrapper

**Prerequisite:** Gradle CLI must be installed. Verify with `gradle --version`. If not installed on Windows, install via `winget install Gradle.Gradle` or Chocolatey (`choco install gradle`). Restart your terminal after installing.

**Files:**
- Generate: `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`, `gradle/wrapper/gradle-wrapper.properties`

- [ ] **Step 1: Verify Gradle is installed**

```powershell
gradle --version
```
Expected: output showing `Gradle 8.x` or any version. If command not found, install Gradle first (see prerequisite above).

- [ ] **Step 2: Generate the Gradle wrapper at version 8.13**

Run from the project root (`C:\Users\roych\OneDrive\Documents\GitHub\APCSAGame`):

```powershell
gradle wrapper --gradle-version 8.13
```

Expected output:
```
BUILD SUCCESSFUL in Xs
1 actionable task: 1 executed
```

This creates `gradlew`, `gradlew.bat`, and `gradle/wrapper/` with the jar and properties file.

- [ ] **Step 3: Verify wrapper files exist**

```powershell
ls gradle/wrapper/
```
Expected: `gradle-wrapper.jar` and `gradle-wrapper.properties` are present.

- [ ] **Step 4: Commit**

```powershell
git add gradlew gradlew.bat gradle/
git commit -m "chore: add Gradle 8.13 wrapper"
```

---

### Task 2: Create Gradle build files

**Files:**
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts`

- [ ] **Step 1: Create `settings.gradle.kts`**

Create `settings.gradle.kts` in the project root with this exact content:

```kotlin
rootProject.name = "APCSAGame"
```

- [ ] **Step 2: Create `build.gradle.kts`**

Create `build.gradle.kts` in the project root with this exact content:

```kotlin
plugins {
    java
}

group = "com.cjh3139"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}
```

- [ ] **Step 3: Verify Gradle can resolve dependencies**

```powershell
.\gradlew dependencies --configuration compileClasspath
```

Expected: dependency tree printed with `io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT` listed. No BUILD FAILED.

- [ ] **Step 4: Commit**

```powershell
git add settings.gradle.kts build.gradle.kts
git commit -m "chore: add Gradle build config for Paper 1.21"
```

---

### Task 3: Create the plugin entrypoint

**Files:**
- Create: `src/main/java/com/cjh3139/apcsa/APCSAGame.java`

- [ ] **Step 1: Create the package directory**

```powershell
New-Item -ItemType Directory -Force -Path "src/main/java/com/cjh3139/apcsa"
```

- [ ] **Step 2: Create `APCSAGame.java`**

Create `src/main/java/com/cjh3139/apcsa/APCSAGame.java` with this exact content:

```java
package com.cjh3139.apcsa;

import org.bukkit.plugin.java.JavaPlugin;

public class APCSAGame extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("APCSAGame has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("APCSAGame has been disabled!");
    }
}
```

- [ ] **Step 3: Verify it compiles**

```powershell
.\gradlew compileJava
```

Expected:
```
BUILD SUCCESSFUL in Xs
1 actionable task: 1 executed
```

If you get `error: package org.bukkit.plugin.java does not exist`, the Paper API dependency in `build.gradle.kts` isn't resolving -- double-check the repo URL and dependency string from Task 2.

- [ ] **Step 4: Commit**

```powershell
git add src/main/java/com/cjh3139/apcsa/APCSAGame.java
git commit -m "feat: add plugin entrypoint class"
```

---

### Task 4: Create plugin.yml

**Files:**
- Create: `src/main/resources/plugin.yml`

- [ ] **Step 1: Create the resources directory**

```powershell
New-Item -ItemType Directory -Force -Path "src/main/resources"
```

- [ ] **Step 2: Create `plugin.yml`**

Create `src/main/resources/plugin.yml` with this exact content:

```yaml
name: APCSAGame
version: ${version}
main: com.cjh3139.apcsa.APCSAGame
api-version: "1.21"
description: A Paper plugin scaffold for the APCSAGame project
author: CJH3139
```

The `${version}` token is replaced at build time by the `processResources` task in `build.gradle.kts` with the value `1.0` defined in `build.gradle.kts`.

- [ ] **Step 3: Commit**

```powershell
git add src/main/resources/plugin.yml
git commit -m "feat: add plugin.yml metadata"
```

---

### Task 5: Full build verification

**Files:** No new files -- verification only.

- [ ] **Step 1: Run a full build**

```powershell
.\gradlew build
```

Expected:
```
BUILD SUCCESSFUL in Xs
2 actionable tasks: 2 executed
```

- [ ] **Step 2: Verify the jar was produced**

```powershell
ls build/libs/
```

Expected: `APCSAGame-1.0.jar` (or similar) is present.

- [ ] **Step 3: Verify plugin.yml inside the jar**

```powershell
.\gradlew jar
jar tf build/libs/APCSAGame-1.0.jar
```

Expected output includes:
```
com/cjh3139/apcsa/APCSAGame.class
plugin.yml
```

- [ ] **Step 4: Final commit**

```powershell
git add .
git commit -m "chore: verify scaffold builds successfully"
```

---

## Done

The scaffold is complete when:
- `.\gradlew build` succeeds with `BUILD SUCCESSFUL`
- `build/libs/APCSAGame-1.0.jar` exists
- The jar contains `APCSAGame.class` and `plugin.yml`
- Dropping the jar into a Paper 1.21 server's `plugins/` folder and starting the server prints `[APCSAGame] APCSAGame has been enabled!` in the console