pipeline {
  agent any
  /*
  environment {
    registry = 'dockerusername/project'
    dockerHubCreds = 'docker_hub' # name / id of credentials in jenkins
    dockerImage = ''
  }*/
  stages {
    stage('Begin Pipeline') {
      steps {
        sh 'echo "Hello world"'
      }
    }
    /*
    stage ('Notifications Unit Testing'){
      when {
        anyOf {branch  'daewoon/*'/*; branch 'bg_*'*//*}
      }
      /*
      steps {
        withMaven {
          sh 'cd notificationApi; mvn test'
        }
        junit skipPublishingChecks: true, testResults: 'notificationApi/target/surefire-reports/*.xml'
      }
    }
    */
    //Tests are currently failing
    /*
    stage ('Delivery Api Unit Testing'){
      when {
        anyOf {branch 'daewoon/*'}
      }
      steps {
        withMaven {
          sh 'cd deliveryApi; mvn test'
        }
        junit skipPublishingChecks: true, testResults: 'deliveryApi/target/surefire-reports/*.xml'
      }
    }
    */
    /*
    stage ('Build'){

    }
    stage ('Run on Daewoon Instance'){

    }
    */
    /*
    stage ('Test 2'){
      steps {
        echo 'Test 2'
      }
      steps {
        script {
          try {
            
            timeout (time: 1, unit: 'MINUTES'){
              approved = input message: 'Deploy to production?', ok: 'Continue',
              parameters: [choice(name: 'approved'), choices: 'Yes\nNo', describe ]
            }
          }
          catch (error){
            error('Build failed because timeout was exceeded');
          }
        }
      }
    }
    */
    //In order to run this step, had to install google cloud authentication, create a service account, and attach it to the gke instance, and give jenkins permission to dockergroup
    stage ('Docker Build'){ 
      /*
      when {
        branch 'main'
      }*/
      steps {
        script {
          echo "Docker Build"
          docker.withRegistry ('northamerica-northeast2-docker.pkg.dev') {
            docker.build("api2:latest", "./api2")
          }
        }
      }
    }
    stage ('Docker tag and push to Google Artifact Repository'){
      steps {
        script {
          echo "Docker push"
          sh "gcloud auth configure-docker"
          sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
          sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
        }
      }
    }
  }
}


