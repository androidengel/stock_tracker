/*
    Module 2:   Course Project
    Programmer: Andrew Engel
    Date:       07/13/2017
    Filename:   User.java
    Purpose:    Provide a class to create user objects
*/

import java.util.ArrayList;
import java.util.Iterator;

public class User
{
    private String userID;
    private String firstName;
    private String lastName;
    private Password password;
    private boolean admin;
    private ArrayList<Stock> stocks;

    //default constructor
    public User() throws PasswordException
    {}

    //constructor with assigned values
    public User(String id, String fName, String lName, String pwd, boolean admn) throws PasswordException
    {
        userID = id;
        firstName = fName;
        lastName = lName;
        password = new Password(pwd);
        admin = admn;
        stocks = new ArrayList<>();
    }
    
     //constructor passing Password object
    public User(String id, String fName, String lName, Password pwd, boolean admn) throws PasswordException
    {
        userID = id;
        firstName = fName;
        lastName = lName;
        password = pwd;
        admin = admn;
        stocks = new ArrayList<>();
    }

    //return user ID
    public String getUserID()
    {
        return userID;
    }

    //return user first name
    public String getUserFName()
    {
        return firstName;
    }

    //return user last name
    public String getUserLName()
    {
        return lastName;
    }
    
    //returns password
    public Password getPassword()
    {
        return password;
    }

    //return array list of stocks
    public ArrayList<Stock> getStocks()
    {
        return stocks;
    }

    //checks whether user is an admin
    public boolean isAdmin()
    {
        return admin;
    }

    //updates first name
    public void setFirstName(String name)
    {
        firstName = name;
    }

    //updates last name
    public void setLastName(String name)
    {
        lastName = name;
    }

    //changes admin rights
    public boolean setAdmin(boolean rights)
    {
        boolean updated = false;
        if(rights != admin)
        {
            admin = rights;
            updated = true;
        }
        
        return updated;
    }

    //changes user ID
    public void setID(String id)
    {
        userID = id;
    }

    public void setPassword(String pw) throws PasswordException
    {
        password = new Password(pw);
    }

    //add stock holding
    public void addStock(Stock stock)
    {
        stocks.add(stock);
    }
    
    //validate user password
    public boolean validatePassword(String pw)
    {
        return password.validatePassword(pw);
    }
    
    //check if user already holds a stock item
    public boolean stockDupCheck(Stock stk)
    {
        boolean duplicate = false;
        
        if(stocks.isEmpty())
        {
            addStock(stk);
        }
        else
        {
            if(stocks.contains(stk))
                duplicate = true;
            else
                addStock(stk);
        }
        
        return duplicate;
    }

    //remove stock holding
    public void deleteStock(Stock stock)
    {
        Iterator<Stock> it = stocks.iterator();
        while(it.hasNext())
        {
            Stock current = it.next();
            if(stock == current)
                it.remove();
        }
    }

}