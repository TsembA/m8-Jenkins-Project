#!/user/bin/env groovy
@Library('jenkins-shared-library')_
def gv

pipeline {   
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()

                }
            }
        }

        stage("build and push image") {
            steps {
                script {
                    buildImage 'dancedevops/my-app:jma-1.2.3'
                    dockerLogin()
                    dockerPush 'dancedevops/my-app:jma-1.2.3'
                }
            }
        }

        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }               
    }
}
