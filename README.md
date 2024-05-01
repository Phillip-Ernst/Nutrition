**-----WORK IN PROGRESS-----**
- General UI Improvements
 
**-----HOW TO GET THE DATABASE CONNECTED-----**
- You need to download the JDBC driver
      - We used this website: https://dev.mysql.com/downloads/connector/j/
- Add the JDBC jar file to the project's dependencies
- Make sure that the application for the database you want to use is set to run on localhost
- On line 34 in FoodDatabaseManagement for DriverManager.getConnection() change the port to the port you are running the database on and the user and password to the credentials of your database's root/admin

    - so it should be something like this: DriverManager.getConnection("jdbc:mysql://localhost:***port you are using***", "***root/admin username***", "***root/admin password***");
