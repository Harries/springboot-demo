## pull images
```shell
sudo docker pull mcr.microsoft.com/mssql/server:2022-latest
```

## create a container
```shell
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Y.sa123456" -p 1433:1433 --name mssql2022 -d mcr.microsoft.com/mssql/server:2022-latest
```

default user/password: sa / Y.sa123456


## init datas
```shell
CREATE DATABASE SampleDB;
USE SampleDB;

#JPA AUTO CREATE TABLE

INSERT INTO Employees (first_name, last_name, birth_date, hire_date, position)
VALUES 
('John', 'Doe', '1980-01-15', '2010-06-01', 'Manager'),
('Jane', 'Smith', '1990-07-22', '2015-09-15', 'Developer'),
('Emily', 'Jones', '1985-03-10', '2012-11-20', 'Designer');


```
