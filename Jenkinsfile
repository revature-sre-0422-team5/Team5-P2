pipeline {
  agent any
  environment {
      PROJECT_ID = 'revature-346918'
      CLUSTER_NAME = 'delivery-cluster'
      LOCATION = 'northamerica-northeast2-a'
      CREDENTIALS_ID = 'revature-346918'
      }
  stages {
    stage('Quality Gate') {
        steps{
            echo 'Quality Gate '
        }
    }
    stage ('Docker Build'){ 
      steps {
        script {
          echo "Docker Build"

          sh "cd notificationApi; docker build --no-cache -t notificationapi:latest ."          
        }
      }
    }
    stage('Docker Deliver') {
        steps {
            echo 'Docker Deliver'
            script {
                sh "docker tag notificationapi northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/notificationapi"
                sh "docker push northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/notificationapi"
            }
        }
    }
    stage('Deploy'){
        steps{
            echo 'Deploy'
            step([
                $class: 'KubernetesEngineBuilder',
                projectId: env.PROJECT_ID,
                clusterName: env.CLUSTER_NAME,
                zone: 'northamerica-northeast2-a',
                manifestPattern: 'notificationapi.yaml',
                credentialsId: env.CREDENTIALS_ID,
            verifyDeployments: true])
        }
    }
  }
}