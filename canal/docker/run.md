## pull image
```shell
docker pull canal/canal-server:v1.1.1
```
## down shell
```shell
wget https://github.com/alibaba/canal/blob/master/docker/run.sh
```
## run
```shell
# build destination queue which named "test"
sh run.sh -e canal.auto.scan=false 
		  -e canal.destinations=test 
		  -e canal.instance.master.address=127.0.0.1:3306  
		  -e canal.instance.dbUsername=root  
		  -e canal.instance.dbPassword=123456  
		  -e canal.instance.connectionCharset=UTF-8 
		  -e canal.instance.tsdb.enable=true 
		  -e canal.instance.gtidon=false  
```

## test
```
mysql> use test;
Database changed
mysql> CREATE TABLE `xdual` (
->   `ID` int(11) NOT NULL AUTO_INCREMENT,
->   `X` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
->   PRIMARY KEY (`ID`)
-> ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ;
Query OK, 0 rows affected (0.06 sec)
mysql> insert into xdual(id,x) values(null,now());Query OK, 1 row affected (0.06 sec)

```