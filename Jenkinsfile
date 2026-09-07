pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/shaikhfarhan04/Java-Maven-Jenkins-Pipeline-Hands-on-Project.git'

                sh 'mvn clean package'
            }
        }

    }
}
