# Stock Tracker Course Projevt
This application is a Java applet that allows a user to manage stocks and other users. It was created as a college course project to showcase the following Java skills:
* GUI design
* Database connectivity
* I/O handling and file streams
* User administration
* Object-orientation fundamentals
* Exception handling 

If cloned, this application will require a modification to the StockTrackerDB.java file. The StockTrackerDB() constructor is as follows:

```java
public StockTrackerDB() throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        if(con == null)
        {
            String url = "jdbc:ucanaccess://C:\\Users\\Andrew\\Desktop\\CourseProject\\CourseProjectHelper\\StockTracker.accdb";
            try
            {
                con = DriverManager.getConnection(url);
                JOptionPane.showMessageDialog(null, "Database Connection Successful", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(SQLException ex)
            {
                throw new SQLException(ex.getMessage() +
                                        "\nCannot connect to Database");
            }                
        }
        
        createAdmin();
    }
```

The url variable will need to be modified to the path of your Microsoft Access database. The application enters a default user with the username and password of "admin01."