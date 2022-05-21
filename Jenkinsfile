pipeline {
    agent any
    environment {
        PROJECT_ID = 'project-id'
        CLUSTER_NAME = 'cluster-name'
        CLUSTER_LOCATION = 'northamerica-northeast2'
        REGISTRY_LOCATION = 'northamerica-northeast2'
        REPOSITORY = 'repository-name'
        CREDENTIALS_ID = 'credentials-id'
    }
    stages {
        stage ('Docker Build'){
            steps {
                script {
                    echo "Docker Build"
                    sh "docker images prune"
                    sh "docker build -t directionsapi api2"
                    sh "docker build -t notificationapi notificationApi"
                    sh "docker build -t deliveryapi deliveryApi"
                }
            }
        }
        stage ('Docker tag and push to Google Artifact Repository'){
            steps {
                script {
                    echo "Docker push"
                    sh "docker tag directionsapi ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/directionsapi"
                    sh "docker tag deliveryapi ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/deliveryapi"
                    sh "docker tag notificationapi ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/notificationapi"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/directionsapi"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/deliveryapi"
                    sh "docker push ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/notificationapi"
                }
            }
        }
        stage ('Deploy to GKE'){
            steps{
                echo "Deploying to GKE"
                sh "sed -i 's|image: directionsapi|image: ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/directionsapi|g' deployment/directionsapi-deployment.yml"
                sh "sed -i 's|image: deliveryapi|image: ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/deliveryapi|g' deployment/deliveryapi-deployment.yml"
                sh "sed -i 's|image: notificationapi|image: ${REGISTRY_LOCATION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/notificationapi|g' deployment/notificationapi-deployment.yml"
                step([$class: 'KubernetesEngineBuilder',
                    projectId: env.PROJECT_ID,
                    clusterName: env.CLUSTER_NAME,
                    location: env.CLUSTER_LOCATION,
                    manifestPattern: 'deployment',
                    credentialsId: env.CREDENTIALS_ID,
                    verifyDeployments: true])
            }
        }
    }
}