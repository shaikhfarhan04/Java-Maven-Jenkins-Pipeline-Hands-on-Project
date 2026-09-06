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
