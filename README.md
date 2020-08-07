# Spring Boot, MySQL, Spring Security, JWT, JPA, Rest API

## Autohouse - REST API for managing automotive offers


This is a defence project build for educational purpose in the course of SoftUni Java Web Development with Spring.

Main technologies used are:

-   OpenJDK
-   Maven
-   Spring Boot
-   MySQL
-   ModelMapper
-   Lombok
-   Dropbox
-   Jacoco (Test coverage)

### Main features of the API are:

##### Anonymous users can:

-   Searching and filtering for offers;
-   Register and create an account (Dealer or Private);

##### Registered users can;

-   Create Dealer or Private account;
-   Dealers can manage up to 200 free offers;
-   Private accounts can maintain up to 5 free offers;
-   Save filters for later search;
-   Track offers by adding them to favorites collection;
-   Can activate/disable their offers so they do not appear in searches;
-   Have roles like "Administrator" and "User". Such with "Administrator" role can manage other users accounts.

The API also supports versioning through Content-Type. For example now the available version is `application/bg.autohouse.api-v1+json`

SPA web client built with React and Redux can be found at the following link: [autohouse-client](https://github.com/VasAtanasov/autohouse-client)

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/VasAtanasov/autohouse-server.git
```

**2. Set env variables**

##### application.properties

DB_NAME

DB_USERNAME

DB_PASSWORD

LOCAL_STORAGE_FOLDER

DROP_BOX_KEY

DROPBOX_BASE_FOLDER

##### security.properties

SECRET - Used for JWT creation.

TOKEN_EXPIRATION

##### mail.properties

MAIL_ADDRESS (Gmail account)

MAIL_PASSWORD

To run the test and check code coverage run:

```bash
mvn clean verify
```

After all the tests complete successfully you can open `target/site/jacoco/index.html` in your browser to view the result from the tests.

**3. Run the application**

Before you run the app you need to set all the env variables stated above and then execute the command:

```bash
java -jar target/authouse-0.0.1-SNAPSHOT.jar
```

or

```bash
mvn spring-boot:run
```

The app will start running at http://localhost:8007

## Explore Rest APIs

The app defines following CRUD APIs.

### Auth

| Method | Url | Description | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/login | Log in | [JSON](#login) |
| GET    | /api/auth/logout | Submits auth token to blacklist | [JSON](#logout) |
| POST   | /api/auth/register | Sign up | [JSON](#register) |
| POST   | /api/auth/login-or-register | Check if user can login or need to register | [JSON](#login-or-register) |
| POST   | /api/auth/register/verify?username={}&password={} | Send OTP for verification registered user |  |
| POST   | /api/auth/password-reset-request | Rest forgotten password | [JSON](#password-reset-request) |
| GET    | /api/auth/password-reset-complete?username={}&password={}&code={} | Complete password Reset |  |
| GET    | /api/auth/token/validate?token={} | Check JWT is valid |  |
| GET    | /api/auth/token/refresh?token={} | Refreshes valid JWT | [JSON](#password-reset-request) |

### User

| Method | Url | Description | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/users/password/update | Change own password when logged | [JSON](#password-update) |
| GET    | /api/users/offer/add-to-favorites/{offerId} | Mark others offers as favorite | |
| GET    | /api/users/offer/list?page={}&size={}&sort={} | List pages from all user's offers active and inactive | |
| GET    | /api/users/offer/toggle-active/{offerId} | Set offer active(visible for others) or inactive | |

### Account

| Method | Url | Description | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| GET    | /api/accounts/user-account | Gets user account information if he has one | |
| POST   | /api/accounts/private-create | Creates privet seller account | [JSON](#create-private) |
| POST   | /api/accounts/dealer-create | Creates dealer account | [JSON](#create-dealer) |

### Images
| Method | Url | Description | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| GET    | /api/images/{folder}/{year}/{month}/{day}/{offerId}/{fileName:.+} | Serves offers images by image key from offer entity | |


## Sample Valid JSON Request Bodies

##### <a id="login">Login</a>
```http request
POST  http://localhost:8007/api/auth/login HTTP/1.1
Content-Type: application/bg.autohouse.api-v1+json

{
    "username": "test@mail.com",
    "password": "123456"
}
```

##### <a id="logout">Logout</a>
```http request
GET  http://localhost:8007/api/auth/logout HTTP/1.1
Authorization: Bearer {{token}}
```

##### <a id="login-or-register">Login or register</a>
```http request
POST http://localhost:8007/api/auth/login-or-register HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json

{
  "username": "test@mail.com"
}
```

##### <a id="register">Register userr</a>
```http request
POST http://localhost:8007/api/auth/register HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json

{
  "username": "test@mail.com",
  "password": "123456",
  "confirmPassword": "123456"
}
```

##### <a id="password-reset-request">Send request to reset forgotten password</a>
```http request
POST http://localhost:8007/api/auth/password-reset-request HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json

{
  "username": "test@mail.com"
}
```

##### <a id="password-update">Change password</a>
```http request
POST http://localhost:8007/api/users/password/update HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json

{
  "oldPassword": "123",
  "newPassword": "123456",
  "confirmPassword": "123456"
}
```

##### <a id="create-private">Create private seller's account</a>
```http request
POST http://localhost:8007/api/accounts/private-create HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json
Authorization: Bearer {{token}}

{
    "firstName": "Rhiannon",
    "lastName": "Hilpert",
    "displayName": "Barton - Lockman LLC",
    "description": "Dolorem dolor reiciendis.",
    "contactDetailsPhoneNumber": "1-108-227-7318 x0771",
    "contactDetailsWebLink": "https://landen.info",
    "accountType": "PRIVATE"
}
```

##### <a id="create-dealer">Create dealer's account</a>
```http request
POST http://localhost:8007/api/accounts/dealer-create HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json
Authorization: Bearer {{token}}

{
    "firstName": "Willa",
    "lastName": "Hodkiewicz",
    "displayName": "Schuppe Group and Sons",
    "description": "Nostrum saepe ut perferendis aperiam similique ipsum et.",
    "contactDetailsPhoneNumber": "970.105.0507",
    "contactDetailsWebLink": "http://wilbert.biz",
    "addressLocationId": 2521,
    "addressStreet": "5505 N. Summit Street",
    "accountType": "DEALER"
}
```