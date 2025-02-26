def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t dancedevops/my-app:jma-1.2.3 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push dancedevops/my-app:jma-1.2.3'
    }
}

def deployApp() {
    echo 'deploying the application...'
}

return this
