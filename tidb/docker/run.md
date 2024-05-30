### pull images
```shell
docker pull xuxuclassmate/tidb
```

### start tidb
```shell
docker run --name tidb -d --privileged=true -p 4000:4000 xuxuclassmate/tidb

```


### use mysql client to connect tidb
```shell
mysql -h 127.0.0.1 -P 4000 -u root
```
### init data

```shell

CREATE DATABASE demo;

CREATE USER 'test'@'%' IDENTIFIED BY 'test';
GRANT ALL PRIVILEGES ON demo.* TO 'test'@'%';
FLUSH PRIVILEGES;

use mysql
update user set authentication_string = password('123456') where User = 'root';
FLUSH PRIVILEGES;

CREATE TABLE `user` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(100) DEFAULT NULL,
 `age` int(11) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB ;
INSERT INTO demo.`user`(id, name, age)VALUES(1, 'jack', 18);
INSERT INTO demo.`user`(id, name, age)VALUES(2, 'alyssa', 19);



```
