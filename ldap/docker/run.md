### ldap setup
```shell
docker-compose -f docker-compose-ldap.yml -p ldap up -d
```
open http://localhost:8080/

### default account
```
username：cn=admin,dc=example,dc=org
password：admin
```