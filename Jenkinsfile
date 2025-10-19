pipeline {
    agent any

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
        IMAGE_NAME = 'devops_demo_app:latest'
    }

    stages {
        // 1️⃣ Checkout du code
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        // 2️⃣ Build Maven
        stage('Build') {
            steps {
                echo 'Building the application...'
                script {
                    if (isUnix()) {
                        sh './mvnw clean package -DskipTests'
                    } else {
                        bat '.\\mvnw.cmd clean package -DskipTests'
                    }
                }
            }
        }

        // 3️⃣ Test JUnit
        stage('Test') {
            steps {
                echo 'Running tests...'
                script {
                    if (isUnix()) {
                        sh './mvnw test'
                    } else {
                        bat '.\\mvnw.cmd test'
                    }
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        // 4️⃣ Build & Deploy Docker
        stage('Docker Build & Deploy') {
            steps {
                echo 'Building and running Docker container...'
                script {
                    if (isUnix()) {
                        sh "docker build -t ${IMAGE_NAME} ."
                        sh "docker stop devops_demo_app || true"
                        sh "docker rm devops_demo_app || true"
                        sh "docker run -d --name devops_demo_app -p 8080:8080 ${IMAGE_NAME}"
                    } else {
                        bat "docker build -t ${IMAGE_NAME} ."
                        bat "docker stop devops_demo_app || true"
                        bat "docker rm devops_demo_app || true"
                        bat "docker run -d --name devops_demo_app -p 8080:8080 ${IMAGE_NAME}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
