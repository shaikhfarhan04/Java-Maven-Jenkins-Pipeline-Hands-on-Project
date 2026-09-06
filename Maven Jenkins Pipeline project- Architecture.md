Absolutely. Below is a **complete GitHub-ready Java/Maven Jenkins Pipeline project**. You can copy these files directly into your repository.

I’ve kept it aligned with your assignment while making it suitable for actual Jenkins practice.

## Project structure

```text
java-maven-jenkins-pipeline/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── cloudnautic/
│   │               └── App.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── cloudnautic/
│                   └── AppTest.java
│
├── pom.xml
├── Jenkinsfile
└── README.md
```

---

## 1. `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <!-- Project Information -->
    <groupId>com.cloudnautic</groupId>
    <artifactId>java-maven-jenkins-pipeline</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>Java Maven Jenkins Pipeline</name>
    <description>
        Hands-on Java Maven Jenkins CI Pipeline Project
    </description>

    <!-- Java Version -->
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!-- Dependencies -->
    <dependencies>

        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

    <!-- Build Configuration -->
    <build>

        <plugins>

            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>

            <!-- JUnit Test Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>

            <!-- Execute Java Application -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.5.0</version>
            </plugin>

        </plugins>

    </build>

</project>
```

---

# 2. `App.java`

Location:

```text
src/main/java/com/cloudnautic/App.java
```

```java
package com.cloudnautic;

public class App {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" Java Maven Jenkins Pipeline");
        System.out.println("========================================");
        System.out.println("Application started successfully!");
        System.out.println("Maven build completed successfully.");
        System.out.println("Jenkins Pipeline is working!");
        System.out.println("========================================");
    }

    public static String getMessage() {

        return "Java Maven Jenkins Pipeline is working!";
    }

    public static int add(int a, int b) {

        return a + b;
    }
}
```

---

# 3. `AppTest.java`

Location:

```text
src/test/java/com/cloudnautic/AppTest.java
```

```java
package com.cloudnautic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testGetMessage() {

        String expected =
                "Java Maven Jenkins Pipeline is working!";

        String actual = App.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void testAddition() {

        int expected = 10;

        int actual = App.add(5, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void testAnotherAddition() {

        int expected = 25;

        int actual = App.add(10, 15);

        assertEquals(expected, actual);
    }
}
```

This gives Jenkins **three unit tests** to execute.

---

# 4. `Jenkinsfile`

Put this file in the **root** of the repository:

```text
Jenkinsfile
```

Use:

```groovy
pipeline {

    agent any

    /*
     * Jenkins tools
     *
     * These names must match the tools configured in:
     *
     * Manage Jenkins
     *      ->
     * Tools
     */

    tools {
        jdk 'myJDK'
        maven 'myMaven'
    }

    /*
     * Poll GitHub every minute.
     *
     * This is included because the assignment specifically
     * asks for:
     *
     * * * * *
     *
     * For production, GitHub Webhook is generally preferable.
     */

    triggers {
        pollSCM('* * * * *')
    }

    stages {

        /*
         * Stage 1
         * Checkout source code
         */

        stage('Checkout') {

            steps {

                echo '========================================'
                echo 'Checking out source code from GitHub'
                echo '========================================'

                checkout scm
            }
        }

        /*
         * Stage 2
         * Clean previous Maven build
         */

        stage('Maven Clean') {

            steps {

                echo '========================================'
                echo 'Running Maven Clean'
                echo '========================================'

                sh 'mvn clean'
            }
        }

        /*
         * Stage 3
         * Compile Java source code
         */

        stage('Compile') {

            steps {

                echo '========================================'
                echo 'Compiling Java Application'
                echo '========================================'

                sh 'mvn compile'
            }
        }

        /*
         * Stage 4
         * Run unit tests
         */

        stage('Unit Test') {

            steps {

                echo '========================================'
                echo 'Running Unit Tests'
                echo '========================================'

                sh 'mvn test'
            }

            post {

                always {

                    /*
                     * Publish JUnit test results
                     */

                    junit testResults:
                        'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                }
            }
        }

        /*
         * Stage 5
         * Package application
         */

        stage('Package') {

            steps {

                echo '========================================'
                echo 'Packaging Maven Application'
                echo '========================================'

                sh 'mvn package'
            }
        }

        /*
         * Stage 6
         * Run Java application
         */

        stage('Run Application') {

            steps {

                echo '========================================'
                echo 'Running Java Application'
                echo '========================================'

                sh '''
                    mvn exec:java \
                    -Dexec.mainClass="com.cloudnautic.App"
                '''
            }
        }

        /*
         * Stage 7
         * Archive generated JAR
         */

        stage('Archive Artifact') {

            steps {

                echo '========================================'
                echo 'Archiving JAR Artifact'
                echo '========================================'

                archiveArtifacts artifacts:
                    'target/*.jar',
                    fingerprint: true
            }
        }
    }

    /*
     * Pipeline result handling
     */

    post {

        success {

            echo '''
            ========================================
              PIPELINE SUCCESSFUL
            ========================================
              Java application built successfully.
              Unit tests passed.
              JAR created successfully.
              Artifact archived.
            ========================================
            '''
        }

        failure {

            echo '''
            ========================================
              PIPELINE FAILED
            ========================================
              Please check the Jenkins console log.
            ========================================
            '''
        }

        always {

            echo '''
            ========================================
              PIPELINE EXECUTION COMPLETED
            ========================================
            '''
        }
    }
}
```

### Why I used `pollSCM`

Your assignment says:

```text
triggering * * * * *
```

`pollSCM('* * * * *')` tells Jenkins to check the SCM every minute and trigger a build when changes are detected.

If your instructor specifically wants **Build periodically**, you can instead use:

```groovy
triggers {
    cron('* * * * *')
}
```

But for a Git-based Pipeline, `pollSCM` is the more meaningful exercise.

---

# 5. `README.md`

````markdown
# Java Maven Jenkins Pipeline

A hands-on DevOps project demonstrating how to build, test, package, and execute a Java application using Maven and Jenkins Pipeline.

---

## Project Overview

This project demonstrates a basic CI pipeline using:

- Java
- Maven
- JUnit 5
- Git
- GitHub
- Jenkins
- Jenkins Pipeline

The pipeline automatically performs:

1. Checkout source code
2. Maven Clean
3. Compile
4. Unit Test
5. Package
6. Run Java Application
7. Archive JAR Artifact

---

## Architecture

```text
                    GitHub
                       |
                       |
                       v
                +--------------+
                |    Jenkins   |
                +--------------+
                       |
                       v
                +--------------+
                |   Checkout   |
                +--------------+
                       |
                       v
                +--------------+
                | Maven Clean  |
                +--------------+
                       |
                       v
                +--------------+
                |   Compile    |
                +--------------+
                       |
                       v
                +--------------+
                |  Unit Tests  |
                +--------------+
                       |
                       v
                +--------------+
                |   Package    |
                +--------------+
                       |
                       v
                +--------------+
                | Run Java App |
                +--------------+
                       |
                       v
                +--------------+
                | Archive JAR  |
                +--------------+
                       |
                       v
                 Build SUCCESS
````

---

# Project Structure

```text
java-maven-jenkins-pipeline/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── cloudnautic/
│   │               └── App.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── cloudnautic/
│                   └── AppTest.java
│
├── pom.xml
├── Jenkinsfile
└── README.md
```

---

# Prerequisites

Install the following:

* Java JDK 17
* Apache Maven
* Git
* Jenkins

Verify the installations:

```bash
java --version
```

```bash
mvn --version
```

```bash
git --version
```

Expected Java version:

```text
Java 17
```

---

# Clone Repository

Clone your GitHub repository:

```bash
git clone https://github.com/YOUR_USERNAME/java-maven-jenkins-pipeline.git
```

Enter the project:

```bash
cd java-maven-jenkins-pipeline
```

---

# Manual Maven Build

Before configuring Jenkins, test the project manually.

## Step 1 - Clean

```bash
mvn clean
```

This removes the previous `target` directory.

---

## Step 2 - Compile

```bash
mvn compile
```

This compiles the Java source code.

---

## Step 3 - Run Tests

```bash
mvn test
```

The project contains three JUnit tests.

Expected result:

```text
Tests run: 3
Failures: 0
Errors: 0
Skipped: 0
```

---

## Step 4 - Package

```bash
mvn package
```

This creates the JAR file under:

```text
target/
```

Example:

```text
target/java-maven-jenkins-pipeline-1.0-SNAPSHOT.jar
```

---

## Step 5 - Run Application

```bash
mvn exec:java -Dexec.mainClass="com.cloudnautic.App"
```

Expected output:

```text
========================================
 Java Maven Jenkins Pipeline
========================================
Application started successfully!
Maven build completed successfully.
Jenkins Pipeline is working!
========================================
```

---

# Jenkins Configuration

## Step 1 - Install Jenkins Plugins

Install/check the following plugins:

```text
Git
Pipeline
Pipeline: Stage View
Pipeline Maven Integration
JUnit
GitHub
```

---

# Step 2 - Configure JDK

Go to:

```text
Manage Jenkins
    |
    +-- Tools
```

Add a JDK installation:

```text
Name: myJDK
```

Use Java 17.

Verify Java on the Jenkins server:

```bash
java --version
```

Find Java path:

```bash
readlink -f $(which java)
```

---

# Step 3 - Configure Maven

Under Maven installations add:

```text
Name: myMaven
```

Use the installed Maven version or allow Jenkins to install Maven automatically.

Verify:

```bash
mvn --version
```

---

# Step 4 - Create Jenkins Pipeline

From Jenkins:

```text
New Item
```

Enter:

```text
java-maven-jenkins-pipeline
```

Select:

```text
Pipeline
```

Click:

```text
OK
```

---

# Step 5 - Configure Pipeline

Under:

```text
Pipeline
```

Select:

```text
Definition:
Pipeline script from SCM
```

Select:

```text
SCM:
Git
```

Enter your GitHub repository:

```text
https://github.com/YOUR_USERNAME/java-maven-jenkins-pipeline.git
```

Branch:

```text
*/main
```

Script Path:

```text
Jenkinsfile
```

Save the configuration.

---

# Jenkins Pipeline Stages

The Jenkins Pipeline contains the following stages:

```text
Checkout
    |
    v
Maven Clean
    |
    v
Compile
    |
    v
Unit Test
    |
    v
Package
    |
    v
Run Application
    |
    v
Archive Artifact
```

---

# Pipeline Commands

The Jenkins Pipeline executes these Maven commands:

```bash
mvn clean
```

```bash
mvn compile
```

```bash
mvn test
```

```bash
mvn package
```

```bash
mvn exec:java -Dexec.mainClass="com.cloudnautic.App"
```

---

# Jenkins Build

Click:

```text
Build Now
```

Open:

```text
Build #1
```

Then:

```text
Console Output
```

The build should complete successfully.

---

# Expected Pipeline Result

```text
Checkout          SUCCESS
Maven Clean       SUCCESS
Compile           SUCCESS
Unit Test         SUCCESS
Package           SUCCESS
Run Application   SUCCESS
Archive Artifact  SUCCESS
```

Final result:

```text
SUCCESS
```

---

# Unit Test Results

Jenkins publishes JUnit test results from:

```text
target/surefire-reports/*.xml
```

The project currently contains three tests:

```text
testGetMessage
testAddition
testAnotherAddition
```

---

# Artifact

After a successful Maven package operation, Jenkins archives:

```text
target/*.jar
```

The generated artifact can be downloaded from the Jenkins build page.

---

# Automatic Trigger

The Jenkinsfile contains:

```groovy
triggers {
    pollSCM('* * * * *')
}
```

This means Jenkins checks the Git repository every minute.

When a new commit is detected, Jenkins automatically starts the Pipeline.

---

# Test Automatic Build

Make a change to `App.java`.

For example:

```java
System.out.println("Version 2 deployed!");
```

Then:

```bash
git add .
```

```bash
git commit -m "Update application message"
```

```bash
git push
```

Jenkins should detect the new commit and start a new build.

---

# Failure Testing

To practice Jenkins troubleshooting, intentionally break a test.

Change:

```java
String expected =
        "Java Maven Jenkins Pipeline is working!";
```

to:

```java
String expected =
        "Wrong Message";
```

Commit and push:

```bash
git add .
git commit -m "Test pipeline failure"
git push
```

The Jenkins Pipeline should fail at:

```text
Unit Test
```

Expected:

```text
Checkout          SUCCESS
Maven Clean       SUCCESS
Compile           SUCCESS
Unit Test         FAILED
Package           NOT RUN
Run Application   NOT RUN
Archive Artifact  NOT RUN
```

---

# Fix the Pipeline

Change the expected value back:

```java
String expected =
        "Java Maven Jenkins Pipeline is working!";
```

Commit:

```bash
git add .
git commit -m "Fix unit test"
```

Push:

```bash
git push
```

Jenkins should execute the Pipeline again.

Expected:

```text
SUCCESS
```

---

# Learning Objectives

After completing this project, you should understand:

* Git repository integration with Jenkins
* Maven project structure
* Maven lifecycle
* `pom.xml`
* Java compilation
* JUnit testing
* Maven packaging
* Jenkins Pipeline syntax
* Jenkinsfile
* Jenkins tools configuration
* SCM polling
* Jenkins build stages
* JUnit test reporting
* Jenkins artifact archiving
* Pipeline failure troubleshooting
* Continuous Integration

---

# Useful Commands

## Java

```bash
java --version
```

## Maven

```bash
mvn --version
```

## Git

```bash
git status
```

```bash
git add .
```

```bash
git commit -m "commit message"
```

```bash
git push
```

## Maven

```bash
mvn clean
```

```bash
mvn compile
```

```bash
mvn test
```

```bash
mvn package
```

```bash
mvn exec:java -Dexec.mainClass="com.cloudnautic.App"
```

---

# Troubleshooting

## Maven command not found

Check:

```bash
which mvn
```

```bash
mvn --version
```

Install Maven if necessary.

---

## Java command not found

Check:

```bash
which java
```

```bash
java --version
```

Install/configure JDK 17.

---

## Jenkins cannot find Maven

Verify Jenkins:

```text
Manage Jenkins
    |
    +-- Tools
```

Make sure the Maven installation name matches:

```text
myMaven
```

---

## Jenkins cannot find Java

Make sure the JDK installation name matches:

```text
myJDK
```

---

## Permission denied during Maven build

Check workspace permissions:

```bash
ls -la
```

Make sure the Jenkins user has permission to use the workspace.

---

## Unit test failure

Check:

```text
target/surefire-reports/
```

Also open:

```text
Jenkins
    |
    +-- Build
         |
         +-- Console Output
```

---

# Future Improvements

This project can later be extended with:

1. SonarQube code quality analysis
2. Docker image creation
3. Docker Hub push
4. AWS ECR
5. Kubernetes deployment
6. Terraform infrastructure
7. Jenkins credentials
8. GitHub Webhooks
9. Deployment to AWS EC2
10. Complete CI/CD pipeline

---

# Author

Java/Maven Jenkins Pipeline Hands-on Project

Built for DevOps and Jenkins practice.

````

---

# 6. Create everything quickly on Ubuntu

If you're doing this on your Jenkins/Ubuntu lab machine, you can create the structure with:

```bash
mkdir -p java-maven-jenkins-pipeline/src/main/java/com/cloudnautic
mkdir -p java-maven-jenkins-pipeline/src/test/java/com/cloudnautic

cd java-maven-jenkins-pipeline
````

Then create:

```bash
touch pom.xml
touch Jenkinsfile
touch README.md
touch src/main/java/com/cloudnautic/App.java
touch src/test/java/com/cloudnautic/AppTest.java
```

Paste the corresponding content above into each file.

---

# 7. Test the complete project

From the project root:

```bash
java --version
```

```bash
mvn --version
```

Then:

```bash
mvn clean
mvn compile
mvn test
mvn package
mvn exec:java -Dexec.mainClass="com.cloudnautic.App"
```

You can also do the whole Maven build in one command:

```bash
mvn clean test package
```

---

# 8. Push to GitHub

If this is a new repository:

```bash
git init
```

```bash
git branch -M main
```

```bash
git remote add origin https://github.com/YOUR_USERNAME/java-maven-jenkins-pipeline.git
```

Then:

```bash
git add .
```

```bash
git commit -m "Initial Java Maven Jenkins Pipeline project"
```

```bash
git push -u origin main
```

Verify:

```bash
git status
```

You should see:

```text
nothing to commit, working tree clean
```

---

## Final Jenkins flow

Once GitHub and Jenkins are connected, your complete hands-on exercise becomes:

```text
              DEVELOPER
                  |
                  | git push
                  v
             ┌─────────┐
             │ GitHub  │
             └────┬────┘
                  |
                  | SCM Poll
                  v
             ┌─────────┐
             │ Jenkins │
             └────┬────┘
                  |
        ┌─────────┴─────────┐
        │                   │
        v                   v
    Checkout            Jenkinsfile
                            |
                            v
                       Maven Clean
                            |
                            v
                         Compile
                            |
                            v
                        Unit Test
                            |
                     ┌──────┴──────┐
                     │             │
                  FAILED         PASSED
                     │             |
                     v             v
                   STOP         Package
                                   |
                                   v
                              Run Application
                                   |
                                   v
                              Archive JAR
                                   |
                                   v
                                SUCCESS
```

**This is now a complete practice project rather than just a Jenkins assignment:** you can demonstrate manual Maven execution, Git/GitHub integration, Jenkins Pipeline, automated testing, failure handling, artifact archiving, and automatic triggering.
