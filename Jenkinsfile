pipeline {
  agent any
  stages {
    stage('') {
      steps {
        sh 'echo "Hello world"'
      }
    }
    stage ('Unit Testing'){
      when {
        anyOf {branch  'ft_*'; branch 'bg_*'}
      }
      steps {
        withMaven {
          sh 'mvn test'
        }
        junit skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
      }
    }
    stage ('Test 2'){
      when {
        anyOf 
      }
      steps {

      }
    }
  }
}