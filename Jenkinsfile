pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew --no-daemon clean build'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts 'build/libs/**.jar'
      }
    }

    stage('Publish') {
      when { 
        branch 'master' 
      }
      steps {
        sh './gradlew publish'
      }
    }

  }
  environment {
    local_maven = '/var/www/maven'
  }
}
