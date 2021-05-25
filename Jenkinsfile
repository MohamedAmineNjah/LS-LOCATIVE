pipeline {
agent any
stages{
stage('clone and clean repo'){
steps {
bat "rmdir /Q /S LS-LOCATIVE"
bat "git clone https://github.com/MohamedAmineNjah/LS-LOCATIVE.git"
bat "mvn clean -f LS-LOCATIVE"
}
}

stage('Deploy'){
steps {
bat "mvn package -f LS-LOCATIVE"
// bat "docker rmi -f locative"
dir("LS-LOCATIVE") {
// sh label: '', script: 'chmod +x Dockerfile'
bat 'docker build -f Dockerfile -t 16121612/locative:latest .'
}
}
}
}
}
