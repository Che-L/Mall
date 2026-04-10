// Mall-System：多模块 Maven（Java 21）+ 可选 Docker Compose 构建
//
// 要求：
// - Agent 的 PATH 上为 Java 21（java -version），或取消注释下方 tools { jdk '...' } 并在 Jenkins → Tools 里配置同名 JDK
// - 使用项目自带 ./mvnw（首次需能访问外网下载 Maven 发行包）
//
// 可选：用 Maven 容器做构建节点（需本机 Docker）——将 agent any 改为：
//   agent { docker { image 'maven:3.9.9-eclipse-temurin-21' args '-v maven-repo-cache:/root/.m2' } }

pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timestamps()
        timeout(time: 90, unit: 'MINUTES')
    }

    // tools {
    //     jdk 'temurin-21-jdk'
    // }

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: '为 true 时跳过测试（CI 无 MySQL/ES 等时可勾选）')
        booleanParam(name: 'BUILD_DOCKER_IMAGES', defaultValue: false, description: '为 true 时执行 docker compose build（节点需已安装 Docker 与 Compose 插件）')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    def skip = params.SKIP_TESTS ? '-DskipTests' : ''
                    if (isUnix()) {
                        sh 'chmod +x mvnw'
                        sh "./mvnw -B -ntp clean verify ${skip}"
                    } else {
                        bat "mvnw.cmd -B -ntp clean verify ${skip}"
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive artifacts') {
            steps {
                archiveArtifacts artifacts: '**/mall-admin/target/mall-admin-*.jar,' +
                        '**/mall-portal/target/mall-portal-*.jar,' +
                        '**/mall-search/target/mall-search-*.jar,' +
                        '**/mall-demo/target/mall-demo-*.jar',
                        allowEmptyArchive: true,
                        fingerprint: true
            }
        }

        stage('Docker images') {
            when {
                expression { return params.BUILD_DOCKER_IMAGES }
            }
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker compose version && docker compose build'
                    } else {
                        bat 'docker compose version && docker compose build'
                    }
                }
            }
        }
    }

    post {
        success {
            echo '构建成功：可执行 JAR 已归档。'
        }
        failure {
            echo '构建失败：查看日志与 JUnit。无中间件时可勾选 SKIP_TESTS 重试。'
        }
    }
}
