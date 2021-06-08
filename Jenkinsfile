pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                ./gradlew build -x test 
            }
        }
        stage('Test') {
            steps {
                ./gradlew test
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}