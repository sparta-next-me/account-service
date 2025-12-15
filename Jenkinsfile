pipeline {
    agent any
    environment {
        APP_NAME        = "promotion-service"
        // GHCR 레지스트리 정보
        REGISTRY        = "ghcr.io"
        GH_OWNER        = "sparta-next-me"
        IMAGE_REPO      = "promotion-service"
        FULL_IMAGE      = "${REGISTRY}/${GH_OWNER}/${IMAGE_REPO}:latest"
        CONTAINER_NAME  = "promotion-service"
        HOST_PORT       = "11111"
        CONTAINER_PORT  = "11111"
        ACCESS_TOKEN    = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXJ2aWNlX3R5cGUiOiIxIiwic2NvcGUiOlsicmVhZCJdLCJzZXJ2aWNlX25vIjoiMDAwMDA2NDMzMDAyIiwiZXhwIjoxNzY2MDI0NDc1LCJhdXRob3JpdGllcyI6WyJJTlNVUkFOQ0UiLCJQVUJMSUMiLCJCQU5LIiwiRVRDIiwiU1RPQ0siLCJDQVJEIl0sImp0aSI6IjllNmNhNDgyLTRiNTYtNDJlOS05ZTkyLTY0ZGQ2OWU4YzY3MiIsImNsaWVudF9pZCI6IjQyNzg2Yjg1LTgwMzktNDU3ZC1hNzkzLTRhMjkwN2IzOGU1MCJ9.gNKJnz0s2GT2f5kwmDujmHHZdDj1YiqZkHHvI9_b4q94nxCSzkRDOjpjRHHRjjJYdIA_WjezRZ2AeEuzJy_QKqEC8yE7MjtpN-S9gDUWJo07BjARmN29j0-v3FfHQ4PkKppfKez0K3s5zM3gNO4p--Cz8Eu40Ceke9NlS2PepLF58-hSlGlkrGtITdTHskaNERi5lSwt-0xbZjk0mcV6eEim5qlaCBpjeWeF8qzLTsJBQWIM7YCZ2yTblzEfJHpv0wBdJNG2P-f5qQwuYtV9hHyC1tUwdKMEFIGy42T9gvEVkM31WAwN7BQdHsodArKbWe8qCFDylPve3LL08VUMRA"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                withCredentials([file(credentialsId: 'promotion-env', variable: 'ENV_FILE')]) {
                    sh '''
                      if [ ! -f "$ENV_FILE" ]; then
                        echo "Error: ENV_FILE not found at $ENV_FILE"
                        exit 1
                      fi
                      set -a
                      . "$ENV_FILE"
                      set +a
                      ./gradlew clean test --no-daemon
                      ./gradlew bootJar --no-daemon
                    '''
                }
            }
        }
        stage('Docker Build') {
            steps {
                sh "docker build -t ${FULL_IMAGE} ."
            }
        }
        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'ghcr-credential',
                    usernameVariable: 'REGISTRY_USER',
                    passwordVariable: 'REGISTRY_TOKEN'
                )]) {
                    sh """
                      set -e
                      echo "\$REGISTRY_TOKEN" | docker login ghcr.io -u "\$REGISTRY_USER" --password-stdin
                      docker push ${FULL_IMAGE}
                    """
                }
            }
        }
        stage('Deploy') {
            steps {
                withCredentials([file(credentialsId: 'promotion-env', variable: 'ENV_FILE')]) {
                    sh """
                      if [ \$(docker ps -aq -f name=${CONTAINER_NAME}) ]; then
                        echo "Stopping existing container..."
                        docker stop ${CONTAINER_NAME} || true
                        docker rm ${CONTAINER_NAME} || true
                      fi
                      echo "Starting new ${APP_NAME} container..."
                      docker run -d --name ${CONTAINER_NAME} \\
                        --env-file \${ENV_FILE} \\
                        -e ACCESS_TOKEN="\${ACCESS_TOKEN}" \\
                        -p ${HOST_PORT}:${CONTAINER_PORT} \\
                        ${FULL_IMAGE}
                    """
                }
            }
        }
    }
}