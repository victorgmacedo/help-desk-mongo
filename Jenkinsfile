pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew build -x test' 
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}