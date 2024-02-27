properties([
    parameters([
        [$class: 'DynamicReferenceParameter', 
            choiceType: 'ET_FORMATTED_HTML', 
            name: 'ProjectName', omitValueField: true, 
             description: 'devops-argocd : נא הקלד את שם הפרויקט לדוגמה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], 
            script: [classpath: [], oldScript: '', sandbox: false, script: """ 
return "<input name='value' type='text' placeholder='[Example: devops-argocd]'/>"            
"""
]]],
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
            name: 'Port', omitValueField: true, 
             description: '8080: נא הקלד את מספר הפורט לדוגמה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], 
            script: [classpath: [], oldScript: '', sandbox: false, script: """ 
return "<input name='value' type='text' placeholder='[Example: 8080]'/>"            
"""
]]],
[$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'ExposeApp', description: 'route or ingress : נא בחר',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["route","ingress"] 
"""
]]],
[$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'HostType', description: 'לבד או בתצורה אוטומטית host נא בחר האם לייצר',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["auto","custom"] 
"""
]]],
string(name: 'CertPath', defaultValue: 'non-prod/apps', description: """ non-prod/apps :לדוגמה .JFrog Artifactory -נא ציין את נתיב התעודות שנמצא ב
non-prod/apps אם אין תעודה ייעודית נא להשאיר את הערך הקיים"""),
[$class: 'DynamicReferenceParameter', 
            choiceType: 'ET_FORMATTED_HTML', defaultValue: 'empty', 
            name: 'HostName', omitValueField: true, description: 'openshift.apps.non-prod-ocp4.migdal-group.co.il :לדוגמה host -נא ציין את שם ה',
            referencedParameters: 'HostType', 
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], 
            script: [classpath: [], oldScript: '', sandbox: false, script: """
if (HostType.equals("custom")){
  return "<input name='value' type='text' style='min-width:400px' placeholder='i.e: https://openshift.apps.openshift.co.il'/>"
}
else{
  return "<input name='value' type='text' placeholder='available only on custom HostType' disabled=true/>"
}
"""
]]],
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'Replicas', description: '4, 3, 2 :שאתה רוצה לאפליקציה Replicas -נא בחר את מספר ה ',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["2","3","4"] 
"""
]]],
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'RequestsCPU', description: '10m :לאפליקציה, לדוגמה CPU Request -נא בחר את ה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["10m","50m","100m","150m","200m","250m"] 
"""
]]],
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'RequestsMemory', description: '50Mi :לאפליקציה, לדוגמה Memory Request -נא בחר את ה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["50Mi","100Mi","150Mi","200Mi","500Mi","1Gi"] 
"""
]]],
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'LimitsCPU', description: '10m :לאפליקציה, לדוגמה CPU Limit -נא בחר את ה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["10m","50m","100m","150m","200m","250m"] 
"""
]]],
        [$class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', filterLength: 1, filterable: false, 
            name: 'LimitsMemory', description: '50Mi :לאפליקציה, לדוגמה Memory Limit -נא בחר את ה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], script: [classpath: [], oldScript: '', sandbox: false, 
            script: """
return ["50Mi","100Mi","150Mi","200Mi","500Mi","1Gi"] 
"""
]]],
        [$class: 'DynamicReferenceParameter', 
            choiceType: 'ET_FORMATTED_HTML', 
            name: 'ImageName', omitValueField: true, 
             description: 'ocp-docker-repo.repo/images/nginx-hello :לדוגמה , Image -נא הקלד את שם ה',
            script: [$class: 'GroovyScript', fallbackScript: [classpath: [], oldScript: '', sandbox: false, script: 'return ["error"]'], 
            script: [classpath: [], oldScript: '', sandbox: false, script: """ 
return "<input name='value' type='text' placeholder='[Example: ocp-docker-repo.repo/images/nginx-hello]'/>"            
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
        MESSAGE = "Added new app"
    }
    stages {
		stage ('Git clone Helm repository'){
		    steps{
                git credentialsId: '$', url: 'https://gitlab.co.il/DevOps/overlay-template.git'
            }
		}
        stage ('Generate app files with Helm'){
            steps{
                sh "helm template helm-chart/. --set kustomize.namespace=${ProjectName} --set host.type=${HostType} --set host.name=${HostName} --set kustomize.namePrefix=${AppName} --set route.type=${ExposeApp} --set applicationPort=${Port} --set kustomize.image.newName=${ImageName} --set kustomize.image.newTag=${TagName} --set kustomize.resources.requests.cpu=${RequestsCPU} --set kustomize.resources.requests.memory=${RequestsMemory} --set kustomize.resources.limits.cpu=${LimitsCPU} --set kustomize.resources.limits.memory=${LimitsMemory} --set kustomize.replicas=${Replicas} --output-dir ./${AppName}"
                sh "mv ${AppName}/overlay-template/templates/* ${AppName}/"
                sh "rm -rf ${AppName}/overlay-template"
                sh "cp -r helm-chart/certs ${AppName}/"
            }
        }
        stage ('Git clone'){
		    steps{
                git branch: 'main', credentialsId: '$', url: 'https://gitlab.co.il/DevOps/devops.git'
            }
		}
        stage ('Copy files to git repository'){
		    steps{
                sh "mv ${AppName} ${AppName}-test"
                sh "mv ${AppName}-test overlays/test/${SystemName}"
            }
		}
        stage ('Copy certificate'){
            steps{
                withCredentials([usernamePassword(credentialsId: '$ID', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                    sh "curl -u ${USER}:${PASS} -O https://tls.crt"
                    sh "curl -u ${USER}:${PASS} -O https://tls.key"
                    sh "mv -f tls.* overlays/test/${SystemName}/${AppName}-test/certs/"
                }
            }
		}        
        stage ('Push Changes to git repository'){
		    steps{
                withCredentials([usernamePassword(credentialsId: '$ID', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                    sh "git config --global user.name username"
                    sh "git config --global user.email username@migdal.co.il"
                    sh "git add ."
                    sh "git commit -m '${MESSAGE}'"
                    sh "git push https://$USER:$PASS@gitlab.co.il/DevOps/devops.git"
                }
            }
		}
	}
}
