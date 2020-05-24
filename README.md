# Internet-shop
# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Authors](#authors)
# <a name="purpose"></a>Project purpose

This is a template for creating an simple version of internet-shop.
<hr>
It implements typical functions for an online store. 
It has login and registration forms.

Available functions for users with a USER role only: 
* add a product to users shopping cart
* delete a product from user's shopping cart
* view all user's own orders
* complete order
* view a list of selected products in user`s shopping cart

Available functions for users with an ADMIN role only same as for users with USER role and:
* add products to the store
* delete products from the store
* view a list of all users
* delete users from the store
<hr>
# <a name="structure"></a>Project Structure
* Java 11
* Maven 4.0.0
* javax.servlet-api 3.1.0
* jstl 1.2
* log4j 1.2.17
* maven-checkstyle-plugin
* mysql-connector-java 8.0.20
<hr>
# <a name="developer-start"></a>For developer
Open the project in your IDE.
Add it as maven project.
Configure Tomcat:
* add artifact
* add sdk 11.0.3
Add sdk 11.0.3 in project struсture.
Use file /mate/academy/internetshop/src/main/resources/init_db.sql to create schema and all the tables required by this app in MySQL database.
At /mate/academy/internetshop/src/main/java/mate/academy/internetshop/factory/DaoAndServiceFactory class use username and password for your DB to create a Connection.
Change a path in /mate/academy/internetshop/src/main/resources/log4j.properties. It has to reach your logFile.
Run the project.
If you first time launch this project: 
 * Run InjectDataController by URL = .../inject-data to create default users.
By default there are one user with an USER role (login = "user", password = "1111") 
and one with an ADMIN role (login = "admin", password = "1111"). 
<hr>
# <a name="authors"></a>Authors
*[Maksym Durov](https://github.com/maks45)