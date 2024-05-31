## pull images
```shell
docker pull saplabs/hanaexpress:latest

```

## run 

```shell
docker run -p 39013:39013 -p 39017:39017 -p 39041-39045:39041-39045 -p 1128-1129:1128-1129 -p 59013-59014:59013-59014 -v D:/IdeaProjects/ETFramework/hana/docker/hana/data/sap:/hana/mounts --name hana1 saplabs/hanaexpress:latest --passwords-url file:///hana/mounts/passwd.json --agree-to-sap-license

```

## Connect via JDBC driver

To log into your system database via JDBC, use the following command:

> jdbc:sap://<ip_address>:39017/?databaseName=<database_name>

To log into your tenant database via JDBC, use the following command:

> jdbc:sap://<ip_address>:39041/?databaseName=<tenant_name>



### passwd.json
${path}/hana/data/sap/passwd.json
```
{
"system_user_password" : "Hyd20240531",
"default_tenant_system_user_password" : "Hyd20240531"
}
```
system_user： SYSTEM  
default_tenant_system_user：SYSTEM


## init data
```
-- 创建新数据库
CREATE DATABASE demo;

-- 授予权限给特定用户
GRANT ALL PRIVILEGES ON DATABASE demo TO my_user;

-- 创建表
CREATE TABLE Employees (
EmployeeID INT PRIMARY KEY,
FirstName NVARCHAR(50),
LastName NVARCHAR(50),
Department NVARCHAR(50),
Salary DECIMAL(10, 2)
);

-- 插入测试数据
INSERT INTO Employees (EmployeeID, FirstName, LastName, Department, Salary)
SELECT 1, 'John', 'Doe', 'Engineering', 60000.00 FROM DUMMY UNION ALL
SELECT 2, 'Jane', 'Smith', 'HR', 55000.00 FROM DUMMY UNION ALL
SELECT 3, 'Alice', 'Johnson', 'Finance', 65000.00 FROM DUMMY UNION ALL
SELECT 4, 'Bob', 'Brown', 'Marketing', 58000.00 FROM DUMMY UNION ALL
SELECT 5, 'Emma', 'Lee', 'Engineering', 62000.00 FROM DUMMY;

```