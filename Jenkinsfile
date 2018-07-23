#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "mvn clean"
    }

    stage('backend tests') {
        try {
            sh "mvn test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('packaging') {
        sh "mvn verify -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    stage('upload package to instance 2'){
        sh "ssh -i 'gateway.pem' ec2-user@ec2-35-174-136-82.compute-1.amazonaws.com sudo service insuranceMicroservice stop"
        sh "scp -i 'gateway.pem' '../Insurance Microservice/target/insurance-microservice-0.0.1-SNAPSHOT.war' ec2-user@ec2-35-174-136-82.compute-1.amazonaws.com:/home/ec2-user/app/insuranceMicroservice/"
        sh "ssh -i 'gateway.pem' ec2-user@ec2-35-174-136-82.compute-1.amazonaws.com sudo service insuranceMicroservice start"
    }
}
