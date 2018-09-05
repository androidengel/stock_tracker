/*
    Module 4:   Course Project
    Programmer: Andrew Engel
    Date:       07/28/2017
    Filename:   StockTrackerDB.java
    Purpose:    Provides a database connection and a way to interact 
                with the database
*/

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class StockTrackerDB {
    
    private Connection con = null;
    
    //constructor makes database connection
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
    
    //close database connection
    public void close() throws SQLException, IOException, ClassNotFoundException
    {
        con.close();
        con = null;
    }
    
    //create an admin record in the database
    private void createAdmin() throws SQLException, IOException, ClassNotFoundException, PasswordException
    {
        String userID = "admin01";
        String firstName = "Andrew";
        String lastName = "Engel";
        String initialPw = "admin01";
        Password pswd = new Password(initialPw);
        boolean admin = true;
        
        PreparedStatement pStmt = con.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?)");
        
        try
        {
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setBoolean(3, admin);
            pStmt.setString(4, userID);
            pStmt.setBytes(5, serializeObj(pswd));
            pStmt.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.println("exception inserting user: " + e.getMessage());
        }
    }
    
    //serialize object
    public byte[] serializeObj(Object obj) throws IOException
    {
        ByteArrayOutputStream baOStream = new ByteArrayOutputStream();
        ObjectOutputStream objOStream = new ObjectOutputStream(baOStream);
        
        objOStream.writeObject(obj); 
        objOStream.flush();
        objOStream.close();
        return baOStream.toByteArray(); //returns stream as byte array
    }
    
    //deserialize object
    public Object deserializeObj(byte[] buf) throws IOException, ClassNotFoundException
    {
        Object obj = null;
        
        if(buf != null)
        {
            ObjectInputStream objIStream = new ObjectInputStream(new ByteArrayInputStream(buf));
            
            obj = objIStream.readObject(); //throws IOException, ClassNotFoundException
        }
        return obj;
    }
    
    //method to access a user record
    public User getUser(String ID) throws SQLException, IOException, ClassNotFoundException, PasswordException
    {
        Statement stmt = con.createStatement();
        
        String dbUserID;
        String dbFirstName;
        String dbLastName;
        Password dbPassword;
        boolean dbAdmin;
        
        byte[] buf = null;
        User user = null;
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE userID = '" + ID + "'");
        
        if(rs.next())
        {
            dbUserID = rs.getString("userID");
            dbFirstName = rs.getString("firstName");
            dbLastName = rs.getString("lastName");
            buf = rs.getBytes("password");
            dbPassword = (Password)deserializeObj(buf);
            
            dbAdmin = rs.getBoolean("admin");
            user = new User(dbUserID, dbFirstName, dbLastName, dbPassword, dbAdmin);
        }
        
        rs.close();
        stmt.close();
        
        return user;
    }
    
    //method to access user records
    public User getallUsers(String ID) throws SQLException, IOException, ClassNotFoundException, PasswordException
    {
        Statement stmt = con.createStatement();
        
        String userID;
        String lastName;
        String firstName;
        String password;
        boolean admin;
        
        User user = null;
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE userID = '" + ID + "'");
                                        
        if(rs.next())
        {
            userID = rs.getString("userID");
            firstName = rs.getString("firstName");            
            lastName = rs.getString("lastName");
            password = rs.getString("password");
            admin = rs.getBoolean("admin");
            user = new User(userID, firstName, lastName, password, admin);
        }
        
        rs.close();
        stmt.close();
        
        return user;
    }
}
