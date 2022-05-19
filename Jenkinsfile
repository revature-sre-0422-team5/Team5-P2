pipeline {
    agent any
    environment {
        PROJECT_ID = 'revature-346918'
        CLUSTER_NAME = 'delivery-cluster'
        LOCATION = 'northamerica-northeast2-a'
        CREDENTIALS_ID = 'Team5-P2'
    }
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
        stage ('Docker tag and push to Google Artifact Repository'){
            steps {
                script {
                    echo "Docker push"
                    sh "docker tag api2 northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
                    sh "docker push northamerica-northeast2-docker.pkg.dev/devops-javasre/test-p2/api2"
                }
            }
        }
        stage ('Deploy to GKE'){
            steps{
                echo "Deploying to GKE"
                step([$class: 'KubernetesEngineBuilder',
                    projectId: env.PROJECT_ID,/*'devops-javasre',*/
                    clusterName: env.CLUSTER_NAME,
                    zone: env.LOCATION,
                    manifestPattern: 'deployment.yml',
                    credentialsId: env.CREDENTIALS_ID,
                    verifyDeployments: true])
            }
        }
    }
}