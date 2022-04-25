pipeline {
  environment {               
  imageName = '/frontend/cartonplastng'   //path project gitlab
  gitlabCredential = 'gitlab-info-user'
  argoCDFolderApp = 'carton-plast/frontend/carton-plast-ng'   //path argocd
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
          - name: node
            image: node:14.15.4        
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
            - name: pvol-node
              mountPath: /usr/local/share/.cache
          volumes:
          - name: pvol-node
            persistentVolumeClaim:
              claimName: "jenkins-node-modules"
        '''
    }
  }
  stages {
    stage('Install dependencies') {
      when {
        expression { BRANCH_NAME ==~ /(master|develop)/ }
      }
      steps {
        container('node') {
          sh '''
            node --version
            yarn --version
            yarn install --frozen-lockfile
          '''
        }
      }
    }
    stage('Build project test') {
      when {
        branch 'develop'
      }
      steps {
        container('node') {
          sh '''
            npm run build -- --outputPath=./dist/out --c test
          '''
        }
      }
    }
    stage('Build project master') {
      when {
        branch 'master'
      }
      steps {
        container('node') {
          sh '''
            npm run build -- --outputPath=./dist/out --configuration production
          '''
        }
      }
    }
    stage('Build docker image') {
      when {
        expression { BRANCH_NAME ==~ /(master|develop)/ }
      }
      steps {
        container('dind') {
          echo 'Building docker images..'
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
            branches: [[name: '*/master' ]],
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
              sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${env.ARGO_APPS_TEST} HEAD:master || echo 'no changes'"
            }
          }
        }
      }
    }
 
    // stage('Deploy project master') {
    //   when {
    //     expression { BRANCH_NAME ==~ /(master)/ }
    //   }
    //   steps {
    //     container('kustomize') {
    //       echo 'Deploying to test environment..'
    //       checkout([$class: 'GitSCM',
    //         branches: [[name: '*/master' ]],
    //         extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'argocd-master-frontend'], [$class: 'ScmName', name: 'argocd-master-frontend']],
    //         userRemoteConfigs: [[
    //           url: 'https://gitlab.espe.edu.ec/devops/argocd-master-frontend.git',
    //           credentialsId: gitlabCredential
    //         ]]
    //       ])
    //       dir('./argocd-master-frontend/' + argoCDFolderApp + '/settings') {
    //         sh('kustomize edit set image ' + env.REGISTRY + imageName + ':' + env.GIT_COMMIT)
    //         sh('git config --global user.email jenkinsci@ci.com')
    //         sh('git config --global user.name Jenkins Pipeline')
    //         sh('git add .')
    //         sh "git commit -m 'Publish new version ${argoCDFolderApp}'"
    //         withCredentials([usernamePassword(credentialsId: gitlabCredential, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
    //           sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${env.ARGO_APPS_URL_MASTER_FRONTEND} HEAD:master || echo 'no changes'"
    //         }
    //       }
    //     }
    //   }
    // }
  }
}