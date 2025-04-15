pipeline {
    agent any
    environment {
        BRANCH_NAME = 'Hammock'
        EMAIL_RECIPIENTS = 'tibor.kiss@ms.sapientia.ro'
        POSTGRES_CONTAINER = 'postgres-container'
        POSTGRES_USER = 'postgres'
        POSTGRES_PASSWORD = 'postgres'
        POSTGRES_DB = 'furniture'
        POSTGRES_PORT = '5432'
        SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:${POSTGRES_PORT}/furniture"
        REPOSITORY_URL = "https://github.com/Actulus/sapifurniture2024.git"
        REPOSITORY_NAME = "sapifurniture2024"
    }
    stages {
        stage('Checkout Code') {
            steps {
                script {
                    // Manually clone the repository from the remote Git repository
                    sh """
                    git clone --branch ${env.BRANCH_NAME} ${env.REPOSITORY_URL}
                    cd ${env.REPOSITORY_NAME}
                    """
                }
            }
        }

        stage('Start PostgreSQL Container') {
            steps {
                script {
                    sh '''
                    CONTAINER_NAME="postgres-container"

                    # Check if the container exists
                    if docker ps -a --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
                        echo "Container ${CONTAINER_NAME} already exists."

                        # Check if the container is running
                        if docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
                            echo "Container ${CONTAINER_NAME} is already running. Reusing it."
                        else
                            echo "Container ${CONTAINER_NAME} exists but is not running. Starting it."
                            docker start ${CONTAINER_NAME}
                        fi
                    else
                        echo "Container ${CONTAINER_NAME} does not exist. Creating a new one."
                        docker run -d --name ${CONTAINER_NAME} \
                        	--network="host" \
                            -e POSTGRES_USER=postgres \
                            -e POSTGRES_PASSWORD=postgres \
                            -e POSTGRES_DB=furniture \
                            -p 5432:5432 postgres:latest
                    fi
                    '''
                }
            }
        }

        stage('Build with unit testing') {
            steps {
                script {
                    // Ensure we're in the right directory (repository directory)
                    dir("${env.REPOSITORY_NAME}") {
                        echo 'Pulling...' + env.BRANCH_NAME
                        def mvnHome = tool 'Maven'
                        if (isUnix()) {
                            def targetVersion = getDevVersion()
                            sh "'${mvnHome}/bin/mvn' -Dintegration-tests.skip=true -Dbuild.number=${targetVersion} clean package"
                            def pom = readMavenPom file: 'pom.xml'
                            developmentArtifactVersion = "${pom.version}-${targetVersion}"
                            junit '**//*target/surefire-reports/TEST-*.xml'
                            archive 'target*//*.jar'
                        } else {
                            bat(/"${mvnHome}\bin\mvn" -Dintegration-tests.skip=true clean package/)
                            def pom = readMavenPom file: 'pom.xml'
                            junit '**//*target/surefire-reports/TEST-*.xml'
                            archive 'target*//*.jar'
                        }
                    }
                }
            }
        }

        stage('Integration tests') {
            steps {
                script {
                    // Ensure we're in the right directory (repository directory)
                    dir("${env.REPOSITORY_NAME}") {
                        def mvnHome = tool 'Maven'
                        if (isUnix()) {
                            sh "'${mvnHome}/bin/mvn' verify -Dunit-tests.skip=true"
                        } else {
                            bat(/"${mvnHome}\bin\mvn" verify -Dunit-tests.skip=true/)
                        }
                    }
                }
                cucumber buildStatus: null, fileIncludePattern: '**/cucumber.json', jsonReportDirectory: 'target', sortingMethod: 'ALPHABETICAL'
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        timeout(time: 25, unit: 'MINUTES')
    }
}

def getDevVersion() {
    def gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
    def versionNumber = gitCommit ? gitCommit.take(8) : env.BUILD_NUMBER
    return versionNumber
}
