pipeline {
  agent any
  environment {
      PROJECT_ID = 'my-first-project'
      CLUSTER_NAME = 'autopilot-cluster-1'
      LOCATION = 'northamerica-northeast1'
      CREDENTIALS_ID = 'my-first-project'
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
