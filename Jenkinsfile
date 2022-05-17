pipeline {
  agent any
  stages {
    stage('Begin Pipeline') {
      steps {
        sh 'echo "Hello world"'
      }
    }
    stage ('Docker Build'){ 
      steps {
        script {
          echo "Docker Build"

          sh "cd api2; docker build -t api2:latest ."
        }
      }
    }
    //For this step I had to sign into the jenkins account in the vm, generate artifact permissions file, upload the file, and docker login using that file
    //https://cloud.google.com/artifact-registry/docs/docker/authentication#json-key
    stage ('Docker tag and push to Google Artifact Repository'){
      steps {
        script {
          echo "Docker push"
          sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
          sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
        }
      }
    }

  }
}


