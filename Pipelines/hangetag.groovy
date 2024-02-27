properties([
    parameters([
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'SystemName', description: 'devops-argocd :לדוגמה Openshift- נא בחר את שם המערכת ב',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["devops-platform"] 
"""
]]],
        [$class: 'DynamicReferenceParameter', 
            choiceType: 'ET_FORMATTED_HTML', 
            name: 'AppName', omitValueField: true, 
             description: 'app : נא הקלד את שם האפליקציה לדוגמה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], 
            script: [classpath: [], oldScript: '', sandbox: false, script: """ 
return "<input name='value' type='text' placeholder='[Example: app]'/>"            
"""
]]],
        [$class: 'DynamicReferenceParameter', 
            choiceType: 'ET_FORMATTED_HTML', 
            name: 'TagName', omitValueField: true, 
             description: 'latest , לדוגמה: 1.0, 1.1, Image -של ה Tag -נא הקלד את ה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], 
            script: [classpath: [], oldScript: '', sandbox: false, script: """ 
return "<input name='value' type='text' placeholder='[Example: latest]'/>"            
"""
]]]


])])

pipeline {
    agent { label 'helm' }
    environment {
        MESSAGE = "Changed image tag version"
    }
    stages {
		stage ('Git clone'){
		    steps{
                git branch: 'main', credentialsId: '$ID', url: 'https://gitlab.co.il/DevOps/devops.git'
            }
		}
        stage ('Change Image Tag'){
            steps{
                script{
                    sh "sed -i 's|newTag:.*|newTag: \"${TagName}\"|g' overlays/test/${SystemName}/${AppName}-test/kustomization.yaml"       
                }
            }
        }
        stage ('Push Changes to git repository'){
		    steps{
                withCredentials([usernamePassword(credentialsId: '$ID', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                    sh "git config --global user.name username"
                    sh "git config --global user.email username@domain.co.il"
                    sh "git add ."
                    sh "git commit -m '${MESSAGE}'"
                    sh "git push https://$USER:$PASS@gitlab.co.il/DevOps/devops.git"
                }
            }
		}
	}
}
