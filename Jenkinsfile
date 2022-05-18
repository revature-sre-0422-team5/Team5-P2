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
    //For this step I had to sign into the jenkins account in the vm, generate artifact permissions file, upload the file, and docker login using that file
    //https://cloud.google.com/artifact-registry/docs/docker/authentication#json-key
    stage ('Docker tag Api2 and push to Google Artifact Repository'){
      steps {
        script {
          echo "Docker push"
          sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
          sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
        }
      }
    }
    stage ('Docker Build Delivery Api'){
      steps {
          echo "Docker Build"

          sh "cd deliveryApi; docker build -t deliveryapi:latest ."
      }
    }
    stage ('Docker Push Delivery Api'){
      steps {
          echo "Docker push"

          sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/deliveryapi"
          sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/deliveryapi"
      }
    }

    stage ('Docker Build Notifications Api'){
      steps {
          echo "Docker Build"

          sh "cd notificationApi; docker build -t notificationapi:latest ."        
      }
    }
    stage ('Docker Push Notifications Api'){
      steps {
          echo "Docker push"

          sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/notificationapi"
          sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/notificationapi"
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


