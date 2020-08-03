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

Web client build with React and Redux can be found at the following link: [autohouse-client](https://github.com/VasAtanasov/autohouse-client)

To run the project you need to clone the repository

`git clone https://github.com/VasAtanasov/autohouse-server.git`

Set env variables:

DB_NAME

DB_USERNAME

DB_PASSWORD

SECRET

TOKEN_EXPIRATION

PASSWORD_RESET_EXPIRATION_TIME

MAIL_ADDRESS

MAIL_PASSWORD

LOCAL_STORAGE_FOLDER

DROP_BOX_KEY

DROPBOX_BASE_FOLDER
