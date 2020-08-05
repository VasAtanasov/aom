# autohouse

## Web API for managing automotive offers

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

Web client built with React and Redux can be found at the following link: [autohouse-client](https://github.com/VasAtanasov/autohouse-client)

To run the project you need to clone the repository

`git clone https://github.com/VasAtanasov/autohouse-server.git`

Set env variables:

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

`mvn clean verify`

After all the tests complete successfully you can open `target/site/jacoco/index.html` in your browser to view the result from the tests.

Before you run the app you need to set all the env variables stated above and then execute the command:

`java -jar target/authouse-0.0.1-SNAPSHOT.jar`

To test you can use VS Code extension "REST Client" to send http requests or any other http client like "Postman", "HTTPie" etc..

Some Examples with REST Client:

Useful links how to use REST Client:

[https://dev.to/techwatching/testing-your-api-with-rest-client-h23](https://dev.to/techwatching/testing-your-api-with-rest-client-h23)

[https://marketplace.visualstudio.com/items?itemName=humao.rest-client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)

Open VSCode and create a file with extension `.http` for example `requests.http` and inside paste the commands:

Check is database up.

`GET http://localhost:8007/api/health`

```js
HTTP/1.1 200

ok
```

Get latest offers.

`http://localhost:8007/api/vehicles/offers/top`

```js
{
  "data": [
    {
      "id": "3c6f4c89-baa0-49fc-b5fe-34fdd1c5980f",
      "accountId": "1dae6b93-9068-4b0f-8a61-e2017539f75e",
      "accountUserId": "d902e1a3-1929-4d55-871d-26b4f42e9bc6",
      "accountUserEnabled": true,
      "price": 16700,
      "locationCity": "Rakita",
      "locationId": "1129",
      "createdAt": "2020-05-02 06:33:27.959",
      "primaryPhotoKey": "offer-images/2020/05/02/3c6f4c89-baa0-49fc-b5fe-34fdd1c5980f/2014_chevrolet_silverado_2500hd_pic_1588401207992.jpg",
      "vehicleMakerName": "Chevrolet",
      "vehicleMakerId": 28,
      "vehicleModelName": "Silverado 2500HD",
      "vehicleModelId": 393,
      "vehicleTrim": "LT Crew Cab 4WD",
      "vehicleYear": 2014,
      "vehicleMileage": 159658,
      "vehicleDoors": 4,
      "vehicleState": "Used",
      "vehicleBodyStyle": "Pickup",
      "vehicleTransmission": "Automatic",
      "vehicleDrive": "Four Wheel Drive",
      "vehicleColor": "White",
      "vehicleFuelType": "Diesel",
      "vehicleHasAccident": "true",
      "hitCount": 7,
      "savedCount": 0,
      "active": true
    },
    {
      "id": "7dbd0862-0f95-4ba8-bd61-6da7df9fc24b",
      "accountId": "1dae6b93-9068-4b0f-8a61-e2017539f75e",
      "accountUserId": "d902e1a3-1929-4d55-871d-26b4f42e9bc6",
      "accountUserEnabled": true,
      "price": 4999,
      "locationCity": "Rakovitsa",
      "locationId": "2444",
      "createdAt": "2020-05-02 06:33:24.93",
      "primaryPhotoKey": "offer-images/2020/05/02/7dbd0862-0f95-4ba8-bd61-6da7df9fc24b/2010_ford_escape_pic_1588401206416.jpg",
      "vehicleMakerName": "Ford",
      "vehicleMakerId": 60,
      "vehicleModelName": "Escape",
      "vehicleModelId": 862,
      "vehicleTrim": "Limited FWD",
      "vehicleYear": 2010,
      "vehicleMileage": 108886,
      "vehicleDoors": 4,
      "vehicleState": "Used",
      "vehicleBodyStyle": "Crossover",
      "vehicleTransmission": "Automatic",
      "vehicleDrive": "Front Wheel Drive",
      "vehicleColor": "Gray",
      "vehicleFuelType": "Flex Fuel Vehicle",
      "vehicleHasAccident": "false",
      "hitCount": 1,
      "savedCount": 0,
      "active": true
    },
    ....
```

Get list of all makers and their models.

`GET http://localhost:8007/api/vehicles/makers`

```js
{
  "message": "MAKERS_GET_SUCCESSFUL",
  "data": {  "Seat": {
      "id": 157,
      "name": "Seat",
      "models": [
        {
          "id": 2266,
          "name": "600"
        },
        {
          "id": 2267,
          "name": "Alhambra"
        },
        {
          "id": 2268,
          "name": "Altea"
        },
        {
          "id": 2269,
          "name": "Arosa"
        },
        {
          "id": 2270,
          "name": "Cordoba"
        },
        {
          "id": 2271,
          "name": "Ibiza"
        },
        {
          "id": 2272,
          "name": "Leon"
        },
        {
          "id": 2273,
          "name": "Ronda"
        },
        {
          "id": 2274,
          "name": "Toledo"
        }
      ]
    },
    ....
  }
```

Register user and get verification token.

```js

POST http://localhost:8007/api/auth/register HTTP/1.1
Accept: application/bg.autohouse.api-v1+json
Content-Type: application/bg.autohouse.api-v1+json

{
  "username": "test@mail.com",
  "password": "123456",
  "confirmPassword": "123456"
}

```

```js

{
  "message": "REGISTRATION_VERIFICATION_TOKEN_SENT",
  "data": {
    "code": {verification code}
  }
}
```

Confirm the registration:

```js

GET http://localhost:8007/api/auth/register/verify?code={verification token}&username=test@emai.com

```

```js
{
  "message" : USER_REGISTRATION_VERIFIED:
}

```

Perform login:

```js


POST  http://localhost:8007/api/auth/login HTTP/1.1
Content-Type: application/bg.autohouse.api-v1+json


{
    "username": "test@mail.com",
    "password": "123456"
}
```

Response with user information and jwt. Note only some of the headers are include in the example.

```js
HTTP/1.1 200
Authorization: Bearer eyJraWQiOiI3NzI0ZmNhZC01NDUzLTQ5NGEtYmQyMC05NTU0YjE2YzhmNjciLCJhbGciOiJIUzUxMiJ9.eyJST0xFIjoiQURNSU4sUk9PVCxVU0VSIiwiVVNFUl9VU0VSTkFNRSI6InZhc0BtYWlsLmNvbSIsIkpXVF9VSUQiOiJjODhkMTBmMy0zYjQ5LTQxMDEtYjFmZS1jMTJhY2M1M2M2ZGEiLCJKV1RfVFlQRSI6IkFQSV9DTElFTlQiLCJVU0VSX1VJRCAiOiJkOTAyZTFhMy0xOTI5LTRkNTUtODcxZC0yNmI0ZjQyZTliYzYiLCJleHAiOjE2MTU2MDgwODgsImlhdCI6MTU5NjYwODA4OH0.AEGQbwQghYD6f7pe50Uc0E1o2zwRmOQXpuH5bkIzGTrlSmTh_8GyFpXrZyhJJO0bMCgN0Jkf7-Cy11e3avwmSQ
UserID: d902e1a3-1929-4d55-871d-26b4f42e9bc6
Content-Type: application/bg.autohouse.api-v1+json

{
  "message": "USER_LOGIN_SUCCESSFUL",
  "data": {
    "userId": "d902e1a3-1929-4d55-871d-26b4f42e9bc6",
    "username": "test@mail.com",
    "hasAccount": true,
    "roles": [
      "USER"
    ],
    "role": "USER",
    "token": "eyJraWQiOiI3NzI0ZmNhZC01NDUzLTQ5NGEtYmQyMC05NTU0YjE2YzhmNjciLCJhbGciOiJIUzUxMiJ9.eyJST0xFIjoiQURNSU4sUk9PVCxVU0VSIiwiVVNFUl9VU0VSTkFNRSI6InZhc0BtYWlsLmNvbSIsIkpXVF9VSUQiOiJjODhkMTBmMy0zYjQ5LTQxMDEtYjFmZS1jMTJhY2M1M2M2ZGEiLCJKV1RfVFlQRSI6IkFQSV9DTElFTlQiLCJVU0VSX1VJRCAiOiJkOTAyZTFhMy0xOTI5LTRkNTUtODcxZC0yNmI0ZjQyZTliYzYiLCJleHAiOjE2MTU2MDgwODgsImlhdCI6MTU5NjYwODA4OH0.AEGQbwQghYD6f7pe50Uc0E1o2zwRmOQXpuH5bkIzGTrlSmTh_8GyFpXrZyhJJO0bMCgN0Jkf7-Cy11e3avwmSQ",
    "favorites": []
  }
}
```

Lists all offers posted by the logged user.

```js

GET http://localhost:8007/api/users/offer/list
Authorization: Bearer {{token}}

```
