pipeline {
  environment {
    registry = 'jamesty2114/deliveryapi'
    dockerHubCreds = 'docker_hub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Quality Gate') {
        steps{
            echo 'Quality Gate'
        }
    }
    stage('Unit Testing') {
        steps {
            withMaven {
                sh 'mvn -f notificationApi/pom.xml test'
            }
        }
    }
    stage('Build') {
        steps{
            withMaven {
                sh 'mvn -f notificationApi/pom.xml package -DskipTests'
            }
        }
    }
    stage('Docker Image') {
        steps {
            script {
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build notificationApi -t "$registry:$currentBuild.number"
            }
        }
    }
    stage('Docker Deliver') {
        steps {
            echo 'Docker Deliver'
            script {
                docker.withRegistry("", dockerHubCreds) {
                    dockerImage.push("$currentBuild.number")
                    dockerImage.push("latest")
                }
            }
        }
    }
    stage('Wait for approval') {
        steps {
            script {
            try {
                timeout(time: 1, unit: 'MINUTES') {
                    approved = input message: 'Deploy to production?', ok: 'Continue',
                        parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Deploy build to production')]

                    if(approved != 'Yes') {
                        error('Build did not pass approval')
                    }
                }
            } catch(error) {
                error('Build failed because timeout was exceeded');
            }
        }
        }
    }
    stage('Deploy'){
        steps{
            echo 'Deploy'
        }
    }
  }
}