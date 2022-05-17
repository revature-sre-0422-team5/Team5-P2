pipeline {
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
    stage ('Docker Build'){ 
      steps {
        script {
          echo "Docker Build"

          sh "cd api2; docker build --no-cache -t notificationapi:latest ."          
        }
      }
    }
    stage('Docker Deliver') {
        steps {
            echo 'Docker Deliver'
            script {
                sh "docker tag notificationapi northamerica-northeast2-docker.pkg.dev/devops-javasre/gcp-docker/notificationapi"
                sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/gcp-docker/notificationapi"
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