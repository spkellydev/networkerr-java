pipeline {
  agent any
  stages {
    stage("Build") {
      steps {
        sh 'mvn clean compile assembly:single'
      }
    }
    stage("Test") {
      agent {
        dockerfile {
          filename 'Dockerfile'
        }
      }
      steps {
        sh 'echo "image built successfully"'
      }
    }
    stage("push to docker hub") {
      steps {
        sh 'mvn clean compile assembly:single'
        sh 'docker login -u spkellydev -p BoonSaints22'
        sh 'docker build -f Dockerfile -t networkerr-java .'
        sh 'docker tag networkerr-java spkellydev/networkerr-java:${BUILD_NUMBER}'
        sh 'docker push spkellydev/networkerr-java'
      }
    }
  }
  post {
    always {
      echo 'jenkins finished'
      deleteDir()
    }
    success {
      echo 'success!!'
    }
    unstable {
      echo 'unstable'
    }
    failure {
      echo 'failure'
    }
    changed {
      echo 'changed'
    }
  }
}