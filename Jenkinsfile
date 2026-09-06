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
