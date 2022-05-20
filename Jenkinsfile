pipeline {
    agent any
    environment {
        PROJECT_ID = 'revature-346918'
        CLUSTER_NAME = 'delivery-cluster'
        CLUSTER_LOCATION = 'northamerica-northeast2'
        REGISTRY_LOCATION = 'northamerica-northeast2'
        REPOSITORY = 'gcp-docker'
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
                    /*
                    sh "docker images prune"
                    sh "docker build -t directionsapi api2"
                    sh "docker build -t notificationapi notificationApi"
                    sh "docker build -t deliveryapi deliveryApi"
                    sh "docker pull mysql"
                    sh "docker pull prom/prometheus"
                    sh "docker pull grafana/grafana"
                    */
                }
            }
        }
        stage ('Docker tag and push to Google Artifact Repository'){
            steps {
                script {
                    echo "Docker push"
                    /*
                    sh "docker tag directionsapi ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/directionsapi"
                    sh "docker tag deliveryapi ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/deliveryapi"
                    sh "docker tag notificationapi ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/notificationapi"
                    sh "docker tag mysql ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/mysql"
                    sh "docker tag prom/prometheus ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/prometheus"
                    sh "docker tag grafana/grafana ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/grafana"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/directionsapi"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/deliveryapi"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/notificationapi"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/mysql"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/prometheus"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/grafana"
                    */
                }
            }
        }
        stage ('Deploy to GKE'){
            steps{
                echo "Deploying to GKE"
                sh "sed 's/image: notificationapi/${REGISTRY_LOCATION}-docker.pkg.dev\/${PROJECT_ID}\/${REPOSITORY}\/notificationapi/g' notificationapi-deployment.yml"
                step([$class: 'KubernetesEngineBuilder',
                    projectId: env.PROJECT_ID,/*'devops-javasre',*/
                    clusterName: env.CLUSTER_NAME,
                    location: env.CLUSTER_LOCATION,
                    manifestPattern: 'deployment',
                    credentialsId: env.CREDENTIALS_ID,
                    verifyDeployments: true])
            }
        }
    }
}