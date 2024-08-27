## install minikube

### Download and run the installer for the latest release.
Or if using PowerShell, use this command:
```shell
New-Item -Path 'c:\' -Name 'minikube' -ItemType Directory -Force
Invoke-WebRequest -OutFile 'c:\minikube\minikube.exe' -Uri 'https://github.com/kubernetes/minikube/releases/latest/download/minikube-windows-amd64.exe' -UseBasicParsing
```
### Add the minikube.exe binary to your PATH.
Make sure to run PowerShell as Administrator.
```shell
$oldPath = [Environment]::GetEnvironmentVariable('Path', [EnvironmentVariableTarget]::Machine)
if ($oldPath.Split(';') -inotcontains 'C:\minikube'){
[Environment]::SetEnvironmentVariable('Path', $('{0};C:\minikube' -f $oldPath), [EnvironmentVariableTarget]::Machine)
}
```

If you used a terminal (like powershell) for the installation, please close the terminal and reopen it before running minikube

### other plateform, 
please visit at:https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download


## install kubectl
https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/

## Start your cluster
```
minikube start
```

## Interact with your cluster
```
kubectl get po -A
```

Alternatively, minikube can download the appropriate version of kubectl and you should be able to use it like this:
```
minikube kubectl -- get po -A
```
You can also make your life easier by adding the following to your shell config: (for more details see: kubectl)
```
alias kubectl="minikube kubectl --"
```
Initially, some services such as the storage-provisioner, may not yet be in a Running state. This is a normal condition during cluster bring-up, and will resolve itself momentarily. For additional insight into your cluster state, minikube bundles the Kubernetes Dashboard, allowing you to get easily acclimated to your new environment:
```
minikube dashboard
```