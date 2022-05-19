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
    stage ('Docker tag Api2 and push to Google Artifact Repository'){
      steps {
        script {
          echo "Docker push"
          sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/logical-veld-346918/my-first-project/api2"
          sh "docker push northamerica-northeast2-docker.pkg.dev/logical-veld-346918/my-first-project/api2"
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
