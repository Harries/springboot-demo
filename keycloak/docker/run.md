
## Start Keycloak
```shell
docker run -d -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:18.0.2 start-dev
```
## Log in to the Admin Console

- http://localhost:8080/admin
- Log in with the username and password you created earlier.(admin/admin)

## Create a realm
Use these steps to create the first realm.

- Open the Keycloak Admin Console.
- Click Keycloak next to master realm, then click Create Realm.
- Enter myrealm in the Realm name field.
- Click Create.

## Create a user
Initially, the realm has no users. Use these steps to create a user:

- Verify that you are still in the myrealm realm, which is shown above the word Manage.
- Click Users in the left-hand menu.
- Click Add user.
- Fill in the form with the following values:
- Click Create.

## Secure the first application
- Open the Keycloak Admin Console.
- Click the word master in the top-left corner, then click myrealm.
- Click Clients.
- Click Create client
- Fill in the form with the following values
- Click Next
- Confirm that Standard flow is enabled.
- Click Next.
- Make these changes under Login settings.
- Click Save.

https://www.keycloak.org/getting-started/getting-started-docker