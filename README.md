# Internet-shop
# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developers](#for-developers)
* [Authors](#authors)
# <a name="purpose">Project purpose</a>

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

# <a name="structure">Project Structure</a>
* Java 11
* Maven 4.0.0
* javax.servlet-api 3.1.0
* jstl 1.2
* log4j 1.2.17
* maven-checkstyle-plugin
* mysql-connector-java 8.0.20
<hr>

# <a name="for-developers">For developers</a>
Open the project in your IDE.
Add it as maven project.
Configure Tomcat:
* add artifact
* add sdk 11.0.3
Add sdk 11.0.3 in project struсture.
Use file /mate/academy/internetshop/src/main/resources/init_db.sql to create schema and all the tables required by this app in MySQL database.
At /mate/academy/internetshop/src/main/java/mate/academy/internetshop/util/ConnectionUtil.class set username and password for your DB to create a Connection.
Change a path in /mate/academy/internetshop/src/main/resources/log4j.properties. It has to reach your logFile.
Run the project.

If you first time launch this project: 
 * Run InjectDataController by URL = .../inject-data to create default users and products.

By default there are: 
 * one user with an USER role (login = "user", password = "1111") 
 * one user with an ADMIN role (login = "admin", password = "1111")
 * one product with (name = "product-1", price = 10.0) 
 * one product with (name = "product-2", price = 11.0) 
 
 To test the application without creating a database, you must change all Dao implementations from dao.jdbc package to 
 all implementation from dao.impl package.
 <hr>

# <a name="authors">Authors</a>
* [Maksym Durov](https://github.com/maks45)