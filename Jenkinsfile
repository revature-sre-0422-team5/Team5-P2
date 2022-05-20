pipeline {
    agent any
    environment {
        PROJECT_ID = 'devopssre-346918'
        CLUSTER_NAME = 'autopilot-cluster-1'
        LOCATION = 'us-central1'
        CREDENTIALS_ID = 'Project_2_DevOpsSre'
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
                    // sh "docker build -t notificationapi notificationApi"
                    // sh "docker build -t deliveryapi deliveryApi"
                    // sh "docker pull mysql"
                }
            }
        }
        stage ('Docker tag and push to Google Artifact Repository'){
            steps {
                script {
                    echo "Docker push"
                    /*
                    sh "docker tag deliveryapi northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/deliveryapi"
                    sh "docker push northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/deliveryapi"
                    sh "docker tag notificationapi northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/notificationapi"
                    sh "docker push northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/notificationapi"
                    sh "docker tag mysql northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/mysql"
                    sh "docker push northamerica-northeast2-docker.pkg.dev/revature-346918/gcp-docker/mysql"
                    */
                }
            }
        }
        stage ('Deploy to GKE'){
            steps{
                echo "Deploying to GKE"
                step([$class: 'KubernetesEngineBuilder',
                    projectId: env.PROJECT_ID,/*'devops-javasre',*/
                    clusterName: env.CLUSTER_NAME,
                    location: env.LOCATION,
                    manifestPattern: 'deployment.yml',
                    credentialsId: env.CREDENTIALS_ID,
                    verifyDeployments: true])
            }
        }
    }
}