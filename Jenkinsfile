pipeline {
  agent any
  environment {
      PROJECT_ID = 'devops-javasre'
      CLUSTER_NAME = 'autopilot-cluster-1'
      LOCATION = 'northamerica-northeast1'
      CREDENTIALS_ID = 'devops-javasre'
  }
  stages {
    stage('Begin Pipeline') {
      steps {
        sh 'echo "Hello world"'
        sh 'echo "Test1"'
      }
    }
    stage ('Docker Build Api2'){
      steps {
        script {
          echo "Docker Build"

          sh "cd api2; docker build -t api2:latest ."
        }
      }
    }
    stage ('Docker Build Delivery Api'){
      steps {
          echo "Docker Build"

          sh "cd deliveryApi; docker build -t deliveryapi:latest ."
      }
    }
    stage ('Docker Build Notifications Api'){
      steps {
          echo "Docker Build"

          sh "cd notificationApi; docker build -t notificationapi:latest ."        
      }
    }
    stage ('Deploy to GKE'){
      steps{
          echo "Deploying to GKE"

          step([
            $class: 'KubernetesEngineBuilder',
            projectId: env.PROJECT_ID,
            clusterName: env.CLUSTER_NAME,
            location: env.LOCATION,
            manifestPattern: 'kubernetes',
            credentialsId: env.CREDENTIALS_ID,
            verifyDeployments: true
          ])
      }
    }

  }
}
