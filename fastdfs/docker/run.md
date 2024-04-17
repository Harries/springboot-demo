### 搜索镜像
```
docker search fastdfs
```

### 拉取镜像（已经内置Nginx）
```
docker pull delron/fastdfs
```

### 构建Tracker
```
# 22122 => Tracker默认端口
docker run --name=tracker-server --privileged=true -p 22122:22122 -v /var/fdfs/tracker:/var/fdfs  --network=host -d delron/fastdfs tracker
```
### 构建Storage
```
# 23000 => Storage默认端口
# 8888 => 内置Nginx默认端口
# TRACKER_SERVER => 执行Tracker的ip和端口
# --net=host => 避免因为Docker网络问题导致外网客户端无法上传文件，因此使用host网络模式
docker run --name=storage-server --privileged=true -p 23000:23000 -p 8888:8888 -v /var/fdfs/storage:/var/fdfs -e TRACKER_SERVER=10.11.68.77:22122 -e GROUP_NAME=group1  --network=host -d delron/fastdfs storage
```
### 查看容器
```
docker ps
```