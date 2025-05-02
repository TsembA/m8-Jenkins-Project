# 🚀 Jenkins CI/CD Pipeline for Java Application with Docker

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Docker Image](https://img.shields.io/badge/docker-ready-blue)

This project defines a **Jenkins pipeline** to automate the CI/CD process for a Java-based web application. It includes compilation, Docker image creation, pushing to Docker Hub, and deployment via a shared library.

---

## 📁 Project Structure

```
├── Jenkinsfile               # Main Jenkins pipeline configuration
├── script.groovy            # Shared deployment logic
└── jenkins-shared-library/  # Jenkins shared library (referenced in the pipeline)
```

---

## 🔧 Prerequisites

To run this pipeline:

* Jenkins installed with:

  * **Pipeline Plugin**
  * **Docker Pipeline Plugin**
  * **Shared Libraries configured**
* Maven and Docker installed on Jenkins agent
* Jenkins credentials set up for Docker Hub (if private repo)
* Docker Hub account

---

## 📦 What This Pipeline Does

1. 🧪 **Builds the Java App** using Maven.
2. 📦 **Creates a JAR** file.
3. 🐳 **Builds a Docker Image** from the JAR.
4. 📤 **Pushes the Image** to Docker Hub.
5. 🚀 **Deploys the App** using a custom shared script.

---

## 🗺️ CI/CD Pipeline Flow

```text
     ┌──────────────┐
     │  Developer   │
     └─────┬────────┘
           │
           ▼
     ┌──────────────┐
     │   Jenkins    │
     │   Pipeline   │
     └────┬─┬─────┬─┘
          │ │     │
          │ │     └────────┐
          │ ▼              ▼
     ┌─────────┐      ┌────────────┐
     │  Build  │      │   Deploy   │
     │   JAR   │      │   App      │
     └─────────┘      └────────────┘
          │
          ▼
     ┌────────────┐
     │  Docker    │
     │  Image     │
     └────┬───────┘
          ▼
     ┌────────────┐
     │ Docker Hub │
     └────────────┘
```

---

## 🔨 Jenkinsfile Overview

```groovy
#!/user/bin/env groovy
@Library('jenkins-shared-library')_
def gv

pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
                    buildImage 'dancedevops/my-app:jma-1.2.3'
                    dockerLogin()
                    dockerPush 'dancedevops/my-app:jma-1.2.3'
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
```

---

## 🔧 Custom Shared Functions

Defined in `script.groovy` or Jenkins Shared Library:

| Function        | Description                              |
| --------------- | ---------------------------------------- |
| `buildJar()`    | Compiles and packages Java app as `.jar` |
| `buildImage()`  | Builds Docker image from the JAR         |
| `dockerLogin()` | Authenticates Docker Hub login           |
| `dockerPush()`  | Pushes image to Docker Hub               |
| `deployApp()`   | Deploys containerized app (custom logic) |

---

## 🚀 Usage Instructions

1. Clone this repo to your Jenkins job workspace.
2. Ensure shared library `jenkins-shared-library` is defined in Jenkins.
3. Configure required credentials (e.g., Docker Hub).
4. Click **Build Now** in Jenkins.
