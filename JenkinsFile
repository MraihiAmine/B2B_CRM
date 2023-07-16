//update the jenkins file configuration
pipeline {
  
          agent any

          tools {
            maven 'maven'
          }

          environment {
            DOCKERHUB_CREDENTIALS = credentials('aminemm_dockerhub')
           }


          stages{

           // Create a new .jar file 

            stage('Create a new .jar') {

                steps {
                    
                   sh 'mvn clean install -DskipTests'
                
                      }

          
            }


            // Create a docker image

            stage('Création d"une image docker') {

                steps {
                    
                   sh 'docker build -t spring_data_aminemm .'
                
                      }

            }

            // Push docker image to dockerhub

            stage('Push image to dockerhub') {

                 steps {

                  sh 'docker tag spring_data_aminemm aminemm/spring_data_aminemm'

                  sh 'echo $DOCKERHUB_CREDENTIALS_PSW \
                  | docker login -u $DOCKERHUB_CREDENTIALS_USR \
                  --password-stdin'

                  sh 'docker push aminemm/spring_data_aminemm'

                       }
                
                post {

                  always {

                  sh 'docker logout'

                         }

                     }

            }

            // Run Docker-compose

             stage('RUN Docker-compose') {

                steps {

                sh 'docker-compose -f docker-compose.yml up -d'

                      }

              }


            stage('Docker-compose show services') {

                steps {

                sh 'docker-compose -f docker-compose.yml ps'

                      }

              }



          }

        

     }




