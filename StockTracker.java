/*
    Module 2:   Course Project
    Programmer: Andrew Engel
    Date:       07/13/2017
    Filename:   StockTracker.java
    Purpose:    A class that manages stocks and users of the program
*/

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import java.sql.*;
import java.io.*;

public class StockTracker
{
    private ArrayList<User> users;
    private ArrayList<Stock> stocks;
    private StockTrackerDB db;

    //constructor
    public StockTracker() throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        users = new ArrayList<>();
        stocks = new ArrayList<>();
        db = new StockTrackerDB();
    }
    
    //validate user login credentials
    public boolean validateUser(String ID, String password)
            throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        boolean validated = false;
        User dbUser = getDBUser(ID);
        
        if(dbUser.validatePassword(password))
        {
            validated = true;
        }
        return validated;
    }
    
    //get user from database
    private User getDBUser(String ID) 
            throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        User dbUser = db.getUser(ID);
        return dbUser;
    }
    
    public void closeDB() throws SQLException, IOException, ClassNotFoundException
    {
        db.close();
    }

    //create new user and add it to arraylist
    public void addUser(String userID, String fName, String lName, String password, boolean admin) throws PasswordException
    {
        users.add(new User(userID, fName, lName, password, admin));
    }
    
    //change user admin priviledges
    public boolean setAdmin(String ID, boolean admin) throws PasswordException
    {
        boolean updated = false;
        User u = getUser(ID);
        if(u.setAdmin(admin))   //if admin rights change from current state
            updated = true;
            
        return updated;
    }

    //add to arraylist
    public void addStock(Stock stock)
    {
        stocks.add(stock);
    }

    //add stock holding to user
    public boolean addStockHolding(String userID, String stockSymbol, String stockDesc) throws PasswordException
    {
        boolean added = false;
        User u = getUser(userID);
        if(u != null)
        {
            if(!stockExists(stockSymbol))   //if stock does not exist in StockTracker ArrayList
            {                               //add stock to StockTracker and User ArrayLists
                Stock s = new Stock(stockSymbol, stockDesc);
                addStock(s);
                u.addStock(s);
                added = true;
            }
            else
            {
                Stock s = getStock(stockSymbol);    //get stock object from ArrayList
                if(!userStockDupCheck(u, s))        //and check to see if it is in User stock ArrayList
                {                                   //if it does not exist in User ArrayList, add it
                    u.addStock(s);
                    added = true;
                }
            }
        }
        
        return added;        
    }
    
    //checks user stocks for duplicate
    private boolean userStockDupCheck(User user, Stock stock)
    {
        return user.stockDupCheck(stock);
    }

    //remove stock holding from user
    public boolean deleteStockHolding(String userID, String stockSymbol) throws PasswordException
    {
        boolean deleted = false;
        User u = null;
        Stock s = null;
        u = getUser(userID);
        s = getStock(stockSymbol);
        
        if(u != null && s != null)
        {
            u.deleteStock(s);
            deleted = true;
            validateStocks();   //check that all stocks in array are being used
        }
        
        return deleted;
    }

    //delete user from arraylist
    public boolean deleteUser(String userID) throws PasswordException
    {
        boolean deleted = false;
        
        if(userExists(userID))
        {
            User u = getUser(userID);
            Iterator<User> it = users.iterator();
            while(it.hasNext())
            {
                User current = it.next();
                if(u == current)
                    it.remove();
            }
            //check that all stocks in array are being used
            validateStocks();
            deleted = true;
        }
        
        return deleted;       
    }

    //find user by ID
    public User getUser(String userID) throws PasswordException
    {
        User foundUser = null;
        if(userExists(userID))
        {
            for(User user : users)
            {
                if(user.getUserID().equals(userID))
                    foundUser = user;
            }
        }
        
        return foundUser;
    }
    
    //determine if user exists in array
    private boolean userExists(String ID)
    {
        boolean exists = false;
        for(User user : users)
        {
            if(user.getUserID().equals(ID))
            exists = true;
        }
        
        return exists;
    }

    //find stock by symbol
    public Stock getStock(String symbol)
    {
        Stock foundStock = null;
        if(stocks != null)
        {
            for(Stock stock : stocks)
            {
                if(stock.getStockSymbol().equals(symbol))
                    foundStock = stock;
            }
        }
        
        return foundStock;
    }
    
    //determines if stock item exists in array
    private boolean stockExists(String symbol)
    {
        boolean exists = false;
        for(Stock stock : stocks)
        {
            if(stock.getStockSymbol().equals(symbol))
                exists = true;
        }
        
        return exists;
    }

    //find stock description by symbol
    public String getStockDescription(String symbol)
    {
        String desc = "";
        Stock foundStock = new Stock();

        for(Stock stock : stocks)
        {
            if(stock.getStockSymbol().equals(symbol))
                desc = stock.getStockDesc();
        }
        return desc;
    }

    //displays all stocks for a given user
    public void listUserStocks(String userID) throws PasswordException
    {
        User u = getUser(userID);
        String userStocks = "";
        ArrayList<Stock> uStocks = u.getStocks();

        for(Stock stock : uStocks)
        {
            userStocks += stock.getStockDesc() + "\n";
        }

        //display results

        JOptionPane.showMessageDialog(null, userStocks,
            userID + " Stock List", JOptionPane.INFORMATION_MESSAGE);
    }

    //return list of users
    public ArrayList<User> getUsers()
    {
        return users;
    }

	public String listAllUsers()
	{
		String allUsers = "";
		String fileData = "";
		
		//put all users' data into a string
		for(User person : users)
		{
			String userInfo = "";
			userInfo += person.getUserID() + " "
					+ person.getUserFName() + " "
					+ person.getUserLName() + " "
					+ person.isAdmin() + "\n";

			allUsers += userInfo;
		}
		
		//send string to create a file
		//create FileHandler object
		FileHandler fh = new FileHandler("users.txt");
		fh.writeData(allUsers);
		fileData = fh.readData("users.txt");
		return fileData;
	}	

    //removes any stock that is not used
    public void validateStocks()
    {
        if(!stocks.isEmpty())
        {
            ArrayList<Stock> allStocks = null;
            ArrayList<Stock> userStocks = null;

            //compile all users stocks into one arraylist
            for(User u : users)
            {
                userStocks = u.getStocks(); //returns ArrayList
                if(userStocks != null)
                    for(Stock ustk : userStocks)
                    {
                        allStocks.add(ustk);
                    }
            }
            //check each stock in stocks arrayList
            //to see if it is in allStocks arraylist;
            //if it is not, remove that stock from the stocks arraylist
            if(allStocks != null)
            {
                for(Stock stock : stocks)
                {
                    if(!allStocks.contains(stock))
                        stocks.remove(stock);
                }
            }
            else
                stocks.clear();          //if no stocks returned from any user, clear stocks array
        }

    }
}