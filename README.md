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

### Search

| Method | Url | Description | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/vehicles/offers/search | Filter available offers | [JSON](#filter) |
| POST   | /api/vehicles/offers/search/favorites | Get user's favorites offers by ids | [JSON](#favorites) |
| POST   | /api/vehicles/offers/search/save | Saves filter for later easy search | [JSON](#save-filter) |
| GET    | /api/vehicles/offers/search/list | List saved searches |  |
| DEL    | /api/vehicles/offers/search/saved-search/{offerId} | Delete saved search |  |

### Offer

| Method | Url | Description | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/vehicles/offers | Create offer | [JSON](#offer-create) |
| POST   | /api/vehicles/offers/update/{offerId} | Update offer | [JSON](#offer-update) |
| GET    | /api/vehicles/offers/load-for-edit/{offerId} | Load offer for update |  |
| GET    | /api/vehicles/offers/details/{offerId} | Load offer for detailed view |  |
| DEL    | /api/vehicles/offers/{offerId} | Delete offer by id |  |
| GET    | /api/vehicles/offers/top | Get list of latest offers |   |
| GET    | /api/vehicles/offers/statistics | Get general data like total active offers, max price, min price etc. |  |
| GET    | /api/vehicles/offers/{accountId}/count | Get user's taken offers slots |   |


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

##### <a id="filter">Search active offers</a>
```http request
POST http://localhost:8007/api/vehicles/offers/search?page=0&size=20&sort=createdAt,desc HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json

{
   "bodyStyle": "SEDAN",
   "color": "",
   "doorsFrom": null,
   "doorsTo": null,
   "drive": "",
   "features": [],
   "fuelType": "",
   "hasAccident": "",
   "makerName": "BMW",
   "mileageFrom": "245",
   "mileageTo": "266899",
   "modelName": "",
   "priceFrom": "1988",
   "priceTo": "145000",
   "seller": [],
   "state": [],
   "transmission": "",
   "trim": null,
   "yearFrom": "1991",
   "yearTo": "2020"
}
```

##### <a id="favorites">Search favorite offers</a>
```http request
POST http://localhost:8007/api/vehicles/offers/search/favorites?page=0&size=20&sort=createdAt,desc HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json
Authorization: Bearer {{token}}

[
    "e5bca629-b61d-4e73-bb34-1111cccfe04e",
    "6101918a-65ae-40bd-9c56-aed6bf22ef52",
    "5d8136a8-2085-4cef-93ba-046c66645fe6",
    "c3b3d007-bce7-4e28-b12f-5b9863e2f9bd"
]
```

##### <a id="save-filter">Saves current filter</a>
```http request
POST http://localhost:8007/api/vehicles/offers/search/save HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json
Authorization: Bearer {{token}}

{
   "bodyStyle": "SEDAN",
   "color": "",
   "doorsFrom": null,
   "doorsTo": null,
   "drive": "",
   "features": [],
   "fuelType": "",
   "hasAccident": "",
   "makerName": "BMW",
   "mileageFrom": "245",
   "mileageTo": "266899",
   "modelName": "",
   "priceFrom": "1988",
   "priceTo": "145000",
   "seller": [],
   "state": [],
   "transmission": "",
   "trim": null,
   "yearFrom": "1991",
   "yearTo": "2020"
}
```

##### <a id="offer-create">Create offer</a>
```http request
POST http://localhost:8007/api/vehicles/offers HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json
Authorization: Bearer {{token}}

{
    "contactDetailsPhoneNumber": "(875) 156-5031 x7090",
    "contactDetailsWebLink": "https://emiliano.org",
    "addressLocationPostalCode": "5791",
    "description": "Price includes warranty!  Fuel economy up to 38 MPG featuring a Power Moonroof, Backup Camera,...",
    "hasAccident": "false",
    "price": "10495",
    "images": ["multipart_file1", "multipart_file1"],
    "vehicle": {
        "bodyStyle": "SEDAN",
        "color": "BLACK",
        "doors": "4",
        "drive": "FRONT_WHEEL_DRIVE",
        "fuelType": "GASOLINE",
        "makerName": "Volkswagen",
        "mileage": "40000",
        "modelName": "Jetta",
        "state": "USED",
        "transmission": "AUTOMATIC",
        "trim": "1.4T SE FWD",
        "year": "2017",
        "features": [
            "CD_PLAYER",
            "CLOTH",
            "DAYTIME_RUNNING_LIGHTS",
            "ELECTRONIC_STABILITY_CONTROL",
            "PASSENGER_SIDE_AIRBAG",
            "TRAILER_HITCH"
        ]
    }
}
```

##### <a id="offer-update">Update offer</a>
```http request
POST http://localhost:8007/api/vehicles/offers/859c62a7-d4a2-41b7-86dc-2baed439707a HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json
Authorization: Bearer {{token}}

{
    "contactDetailsPhoneNumber": "(875) 156-5031 x7090",
    "contactDetailsWebLink": "https://emiliano.org",
    "addressLocationPostalCode": "5791",
    "description": "Price includes warranty!  Fuel economy up to 38 MPG featuring a Power Moonroof, Backup Camera,...",
    "hasAccident": "false",
    "price": "10495",
    "images": ["multipart_file1", "multipart_file1"],
    "vehicle": {
        "bodyStyle": "SEDAN",
        "color": "BLACK",
        "doors": "4",
        "drive": "FRONT_WHEEL_DRIVE",
        "fuelType": "GASOLINE",
        "makerName": "Volkswagen",
        "mileage": "40000",
        "modelName": "Jetta",
        "state": "USED",
        "transmission": "AUTOMATIC",
        "trim": "1.4T SE FWD",
        "year": "2017",
        "features": [
            "CD_PLAYER",
            "CLOTH",
            "DAYTIME_RUNNING_LIGHTS",
            "ELECTRONIC_STABILITY_CONTROL",
            "PASSENGER_SIDE_AIRBAG",
            "TRAILER_HITCH"
        ]
    }
}
```