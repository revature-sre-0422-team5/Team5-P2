pipeline {
<<<<<<< HEAD
  environment {
    registry = 'revature-sre-0422-team5/Team5-P2'
    dockerHubCreds = 'docker_hub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Quality Gate') {
        steps{
            echo 'Quality Gate'
            echo 'test'
        }
    }
    stage('Unit Testing') {
        steps{
            withMaven {
                sh 'cd api2; mvn test'
            }

            junit skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
        }
    }
    stage('Build') {
        when {
            branch 'tony_jenkins'
        }
        steps{
            withMaven {
                sh 'cd api2; mvn package -DskipTests'
            }
        }
    }
    stage('Docker Image') {
        when {
            branch 'tony_jenkins'
        }
        steps{
            script {
                sh 'cd api2'
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build "$registry:$currentBuild.number"
            }
        }
    }
    stage('Docker Deliver') {
        when {
            branch 'tony_jenkins'
        }
        steps{
            script {
                sh 'cd api2'
                docker.withRegistry("", dockerHubCreds) {
                    dockerImage.push("$currentBuild.number")
                    dockerImage.push("latest")
                }
            }
        }
    }
    stage('Wait for approval') {
        when {
            branch 'main'
        }
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
=======
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

          sh "docker tag deliveryapi northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/deliveryapi"
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

          sh "docker tag notificationapi northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/notificationapi"
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


>>>>>>> e8f62216c043eba4206dc44b3cd3a56f8cbeaf28
