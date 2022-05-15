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
    stage ('Notifications Unit Testing'){
      when {
        anyOf {branch  'daewoon/*'; branch 'bg_*'}
      }
      steps {
        withMaven {
          sh 'cd notificationApi; mvn test'
        }
        junit skipPublishingChecks: true, testResults: 'notificationApi/target/surefire-reports/*.xml'
      }
    }
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
    stage ('Docker Deliver'){
      when {
        branch 'main'
      }
      steps {
        script {
          echo "$registry":"$currentBuild.number"
          dockerImage = docker.build "$registry:$currentBuild.number"
          //create a dockerfile
          docker.withRegistry("", dockerHubCreds)
          {
            dockerImage.push("$currentBuild.number")
            dockerImage.push("latest")
          }
        }
      }
    }
    */
  }
}