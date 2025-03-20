# Jenkins Pipeline

```groovy
pipeline {
    agent any

    parameters {
        string(name: 'testUrl', defaultValue: 'https://youtube.com', description: 'Enter the URL to test')
        choice(name: 'browser', choices: ['chrome', 'firefox'], description: 'Select the browser for testing')
        string(name: 'driversPath', defaultValue: 'C:\\Users\\PSYCHOPENGUIN\\Downloads\\drivers', description: 'Path to the drivers folder')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Killer29997/expt-7.git'
            }
        }
        stage('Build') {
            steps {
                dir('selenium-tests') {
                    bat 'mvn clean package -DdriversPath=%driversPath%'
                }
            }
        }
        stage('Test') {
            steps {
                dir('selenium-tests') {
                    bat "mvn test -DtestUrl=${params.testUrl} -Dbrowser=${params.browser} -DdriversPath=${params.driversPath}"
                }
            }
        }
    }
}
