pipeline {
  environment {
    imageName = '/microservices/cartonplast/cp-core-ws'     //path project gitlab
    gitlabCredential = 'gitlab-info-user'
    argoCDFolderApp = 'carton-plast/microservices/cp-core-ws'     //path argocd
    dockerImage = ''
  }

  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: dind
            image: docker:19.03.1-dind
            securityContext:
              privileged: true
            env:
              - name: DOCKER_TLS_CERTDIR
                value: ""
          - name: kustomize
            image: k8s.gcr.io/kustomize/kustomize:v4.1.3
            command:
            - cat
            tty: true
          - name: gradle
            image: gradle:6.8.3-jdk11
            command:
            - cat
            resources:
              requests:
                memory: "512Mi"
                cpu: "1"
              limits:
                memory: "4Gi"
                cpu: "2"
            tty: true
            volumeMounts:
            - name: pvol
              mountPath: /root/.m2
          volumes:
          - name: pvol
            persistentVolumeClaim:
              claimName: "jenkins-maven-m2"
        '''
    }
  }
  stages {
      stage('Run Tests') {
          steps {
              container('gradle') {
                  sh 'gradle -version'
                  echo 'Running tests..'
                  sh 'gradle test'
              }
          }
      }
      stage('SonarQube Analysis') {
          when {
              not {
                  expression { BRANCH_NAME ==~ /(master|develop)/ }
              }
          }
          steps {
              withSonarQubeEnv('sonar') {
                  container('gradle') {
                      echo 'SonarQube Analysis project..'
                      sh "gradle sonarqube"
                  }
              }
          }
      }
      stage('Build project') {
          when {
              expression { BRANCH_NAME ==~ /(master|develop)/ }
          }
          steps {
              container('gradle') {
                  echo 'Building project..'
                  sh 'gradle clean build -x test'
                  archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
             }
          }
      }
      stage('Build docker image') {
          when {
              expression { BRANCH_NAME ==~ /(master|develop)/ }
          }
          steps {
              container('dind') {
                  echo 'Building docker image..'
                  script {
                      dockerImage = docker.build env.REGISTRY + imageName + ":${env.GIT_COMMIT}"
                  }
              }
          }
      }
      stage('Publish container') {
          when {
              expression { BRANCH_NAME ==~ /(master|develop)/ }
          }
          steps {
              container('dind') {
                  echo 'Deploying docker image to registry..'
                  script {
                      docker.withRegistry('https://' + env.REGISTRY, gitlabCredential) {
                          dockerImage.push()
                      }
                  }
              }
          }
      }
      stage('Deploy project develop') {
          when {
            expression { BRANCH_NAME ==~ /(develop)/ }
          }
          steps {
              container('kustomize') {
                  echo 'Deploying to test environment..'
                  checkout([$class: 'GitSCM',
                      branches: [[name: '*/main' ]],
                      extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'argocd-test-apps'], [$class: 'ScmName', name: 'argocd-test-apps']],
                      userRemoteConfigs: [[
                          url: 'https://gitlab.crsoft.org/devops/kubernetes/argocd-test-apps.git',
                          credentialsId: gitlabCredential
                      ]]
                  ])
                  dir('./argocd-test-apps/' + argoCDFolderApp + '/settings' ) {
                      sh('kustomize edit set image ' + env.REGISTRY + imageName + ':' + env.GIT_COMMIT)
                      sh('git config --global user.email jenkinsci@ci.com')
                      sh('git config --global user.name Jenkins Pipeline')
                      sh "git commit -am 'Publish new version ${argoCDFolderApp}'"
                      withCredentials([usernamePassword(credentialsId: gitlabCredential, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                          sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${env.ARGO_APPS_TEST} HEAD:main || echo 'no changes'"
                      }
                  }
              }
          }
      }
  }
}
