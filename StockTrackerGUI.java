/*
    Module 2:   Menu Project
    Programmer: Andrew Engel
    Date:       07/13/2017
    Filename:   StockTrackerGUI.java
    Purpose:    Provide a menu system for the Stock Tracker course project
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.sql.*;
import java.io.*;

public class StockTrackerGUI extends JFrame implements ActionListener
{
    //declare tracker class
    StockTracker tracker;
    
    JFrame loginFrame;
    JFrame applicationFrame;
    
    //construct login page labels and fields
    JLabel loginIDLabel = new JLabel("User ID: ");
    JTextField loginID = new JTextField(10);
    JLabel loginPasswordLabel = new JLabel("Password: ");
    JPasswordField loginPassword = new JPasswordField(10);
    
    //construct Add User page labels, fields and radios
    JLabel addUserIDLabel = new JLabel("User ID: ");
    JLabel addUserPwLabel = new JLabel("Password: ");
    JLabel addUserFNameLabel = new JLabel("First Name: ");
    JLabel addUserLNameLabel = new JLabel("Last Name: ");
    JLabel addUserAdminLabel = new JLabel("Admin? ");
    JTextField addUserID = new JTextField(15);
    JPasswordField addUserPw = new JPasswordField(15);
    JTextField addUserFName = new JTextField(15);
    JTextField addUserLName = new JTextField(15);
    CheckboxGroup addUserAdminGroup = new CheckboxGroup();
        Checkbox addUserYesRadio = new Checkbox("Yes", false, addUserAdminGroup);
        Checkbox addUserNoRadio = new Checkbox("No", false, addUserAdminGroup);
        Checkbox addUserHiddenRadio = new Checkbox("", true, addUserAdminGroup);

    //construct Add Stock Holding page labels and fields
    JLabel ashIDLabel = new JLabel("User ID: ");
    JLabel ashSymbolLabel = new JLabel("Stock Symbol: ");
    JLabel ashDescLabel = new JLabel("Stock Desc: ");
    JTextField ashID = new JTextField(10);
    JTextField ashSymbol = new JTextField(10);
    JTextField ashDesc = new JTextField(10);
    
    //construct Update Access page labels and fields
    JLabel updAccIDLabel = new JLabel("User ID: ");
    JTextField updAccID = new JTextField(10);
    JLabel updAccAdminLabel = new JLabel("Admin? ");
    CheckboxGroup updAccAdminGroup = new CheckboxGroup();
        Checkbox updAccYesRadio = new Checkbox("Yes", false, updAccAdminGroup);
        Checkbox updAccNoRadio = new Checkbox("No", false, updAccAdminGroup);
        Checkbox updAccHiddenRadio = new Checkbox("", true, updAccAdminGroup);
        
    //construct Delete User page labels and fields
    JLabel deleteUserIDLabel = new JLabel("User ID: ");
    JTextField deleteUserID = new JTextField(10);
    
    //construct Delete Stock Holding page labels and fields
    JLabel dshIDLabel = new JLabel("User ID: ");
    JTextField dshID = new JTextField(10);
    JLabel dshSymbolLabel = new JLabel("Stock Symbol: ");
    JTextField dshSymbol = new JTextField(10);
    
    //construct Find Stock Item labels and fields
    JLabel fsiSymbolLabel = new JLabel("Stock Symbol" );
    JTextField fsiSymbol = new JTextField(10);
    
    //construct Find User label and field
    JLabel findUserIDLabel = new JLabel("User ID: ");
    JTextField findUserID = new JTextField(10);

    //declare list all users page text area
    JTextArea allUsersArea;

    //declare GUI main panel
    JPanel cardHolder = new JPanel(new CardLayout());

    //constants for menu items
    final static String ABOUT = "About",
                        EXIT = "Exit",
                        ADD_USER = "Add New User",
                        ADD_HOLDING = "Add Stock Holding",
                        UPDATE_ACCESS = "Update Access",
                        DELETE_USER = "Delete User",
                        DELETE_HOLDING = "Delete Stock Holding",
                        ADD_STOCK = "Add Stock Item",
                        FIND_STOCK = "Find Stock Item",
                        FIND_USER = "Find User",
                        FIND_ALL_USERS = "List All Users";

    //constructor
    public StockTrackerGUI() throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        try
        {
            //create new tracker and display login page
            tracker = new StockTracker();
            buildLoginPage();
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Class not found exception: " +
                                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "SQL exception: " +
                                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//end constructor
    
    //login page builder
    private void buildLoginPage()
    {        
        JButton loginButton = new JButton("Login");
            loginButton.setActionCommand("Login");
            loginButton.addActionListener(this);
            
        JPanel userIDPanel = new JPanel(new GridLayout(1, 2));
            userIDPanel.add(loginIDLabel);
            userIDPanel.add(loginID);
        JPanel passwordPanel = new JPanel(new GridLayout(1, 2));
            passwordPanel.add(loginPasswordLabel);
            passwordPanel.add(loginPassword);
        JPanel buttonPanel = new JPanel();
            buttonPanel.add(loginButton);
            
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
            inputPanel.add(userIDPanel);
            inputPanel.add(passwordPanel);
            inputPanel.setBorder(new TitledBorder("Login"));
        JPanel contentPanel = new JPanel(new GridLayout(4, 1));
            contentPanel.add(inputPanel);
            contentPanel.add(buttonPanel);
            
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);   
            
        loginFrame = new JFrame();
        loginFrame.setTitle("Stock Tracker Application Login");
        loginFrame.add(mainPanel);
        loginFrame.setBounds(400, 400, 500, 350);
        loginFrame.setVisible(true);            
        
    }
    
    //build application after login verification
    private void openApplication()
    {     
        JPanel homePage = buildHomePage();
        JPanel addUserPage = buildAddUserPage();
        JPanel addStockHolding = buildAddStockHoldingPage();
        JPanel updateAccessPage = buildUpdateAccessPage();
        JPanel deleteUserPage = buildDeleteUserPage();
        JPanel deleteStockHolding = buildDeleteStockHoldingPage();
        JPanel findStockItemPage = buildFindStockItemPage();
        JPanel findUserPage = buildFindUserPage();
        JPanel allUsersPage = buildAllUsersPage();

        //add panels to panel that holds all cards
        cardHolder.add(homePage);
        cardHolder.add(addUserPage, ADD_USER);
        cardHolder.add(addStockHolding, ADD_HOLDING);
        cardHolder.add(updateAccessPage, UPDATE_ACCESS);
        cardHolder.add(deleteUserPage, DELETE_USER);
        cardHolder.add(deleteStockHolding, DELETE_HOLDING);
        cardHolder.add(findStockItemPage, FIND_STOCK);
        cardHolder.add(findUserPage, FIND_USER);
        cardHolder.add(allUsersPage, FIND_ALL_USERS);
        
        applicationFrame = new JFrame();
        applicationFrame.setTitle("Stock Tracker Application");
        menuBuilder();
        applicationFrame.setBounds(400, 400, 500, 350);
        applicationFrame.setVisible(true);
        applicationFrame.add(cardHolder);   
    }

    //menu builder
    private void menuBuilder()
    {
        //create menuBar object
        JMenuBar menuBar = new JMenuBar();
        applicationFrame.setJMenuBar(menuBar);

        //construct and populate the File menu
        JMenu menuFile = new JMenu("File", true);
        menuBar.add(menuFile);
            JMenuItem menuFileAbout = new JMenuItem(ABOUT);
            menuFile.add(menuFileAbout);
            JMenuItem menuFileExit = new JMenuItem(EXIT);
            menuFile.add(menuFileExit);

        //construct and populate the User menu
        JMenu menuUser = new JMenu("Edit");
        menuBar.add(menuUser);
            JMenuItem menuUserAddUser = new JMenuItem(ADD_USER);
            menuUser.add(menuUserAddUser);
            JMenuItem menuUserAddStock = new JMenuItem(ADD_HOLDING);
            menuUser.add(menuUserAddStock);
            menuUser.insertSeparator(3); //separator
            JMenuItem menuUserUpdateAccess = new JMenuItem(UPDATE_ACCESS);
            menuUser.add(menuUserUpdateAccess);
            menuUser.insertSeparator(5); //separator
            JMenuItem menuUserDeleteUser = new JMenuItem(DELETE_USER);
            menuUser.add(menuUserDeleteUser);
            JMenuItem menuUserDeleteStock = new JMenuItem(DELETE_HOLDING);
            menuUser.add(menuUserDeleteStock);

        //construct and populate the Search menu
        JMenu menuSearch = new JMenu("Search");
        menuBar.add(menuSearch);
            JMenuItem menuSearchStock = new JMenuItem(FIND_STOCK);
            menuSearch.add(menuSearchStock);
            JMenuItem menuSearchFindUser = new JMenuItem(FIND_USER);
            menuSearch.add(menuSearchFindUser);
            JMenuItem menuSearchAllUsers = new JMenuItem(FIND_ALL_USERS);
            menuSearch.add(menuSearchAllUsers);

        //add ActionListener to each menu item
        menuFileAbout.addActionListener(this);
        menuFileExit.addActionListener(this);
        menuUserAddUser.addActionListener(this);
        menuUserAddStock.addActionListener(this);
        menuUserUpdateAccess.addActionListener(this);
        menuUserDeleteUser.addActionListener(this);
        menuUserDeleteStock.addActionListener(this);
        menuSearchStock.addActionListener(this);
        menuSearchFindUser.addActionListener(this);
        menuSearchAllUsers.addActionListener(this);

        //set action command for each menu item
        menuFileAbout.setActionCommand(ABOUT);
        menuFileExit.setActionCommand(EXIT);
        menuUserAddUser.setActionCommand(ADD_USER);
        menuUserAddStock.setActionCommand(ADD_HOLDING);
        menuUserUpdateAccess.setActionCommand(UPDATE_ACCESS);
        menuUserDeleteUser.setActionCommand(DELETE_USER);
        menuUserDeleteStock.setActionCommand(DELETE_HOLDING);
        menuSearchStock.setActionCommand(FIND_STOCK);
        menuSearchFindUser.setActionCommand(FIND_USER);
        menuSearchAllUsers.setActionCommand(FIND_ALL_USERS);
    }

    //build home page
    private JPanel buildHomePage()
    {
        JLabel homeLabel = new JLabel("Welcome to the Stock Tracker Application!");
        JPanel contentPanel = new JPanel();
            contentPanel.add(homeLabel);
        JPanel borderPanel = new JPanel();
        JPanel homeCard = new JPanel(new BorderLayout(0, 40));
            homeCard.add(contentPanel, BorderLayout.CENTER);
            homeCard.add(borderPanel, BorderLayout.NORTH);

        return homeCard;
    }

    //build add user page
    private JPanel buildAddUserPage()
    {
        //construct button
        JButton addUserAddButton = new JButton("Add");
            addUserAddButton.addActionListener(this);
            addUserAddButton.setActionCommand("AddNewUser");

        //add user panels
        JPanel addUserIDPanel = new JPanel(new GridLayout(1, 2));     //holds userID panel
            addUserIDPanel.add(addUserIDLabel);
            addUserIDPanel.add(addUserID);
        JPanel addUserPwPanel = new JPanel(new GridLayout(1, 2));     //holds password panel
            addUserPwPanel.add(addUserPwLabel);
            addUserPwPanel.add(addUserPw);
        JPanel addUserFNamePanel = new JPanel(new GridLayout(1, 2));  //holds firstname panel
            addUserFNamePanel.add(addUserFNameLabel);
            addUserFNamePanel.add(addUserFName);
        JPanel addUserLNamePanel = new JPanel(new GridLayout(1, 2));  //holds lastname panel
            addUserLNamePanel.add(addUserLNameLabel);
            addUserLNamePanel.add(addUserLName);
        JPanel addUserAdminPanel = new JPanel(new GridLayout(1, 3));  //holds admin panel
            addUserAdminPanel.add(addUserAdminLabel);
            addUserAdminPanel.add(addUserYesRadio);
            addUserAdminPanel.add(addUserNoRadio);
        JPanel addUserButtonPanel = new JPanel();                   //holds button panel
            addUserButtonPanel.add(addUserAddButton);

        //add user panel construction
        JPanel inputPanel = new JPanel(new GridLayout(5, 1));
            inputPanel.add(addUserIDPanel);
            inputPanel.add(addUserPwPanel);
            inputPanel.add(addUserFNamePanel);
            inputPanel.add(addUserLNamePanel);
            inputPanel.add(addUserAdminPanel);
            inputPanel.setBorder(new TitledBorder("Add User"));
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
            contentPanel.add(inputPanel);
            contentPanel.add(addUserButtonPanel);
        
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);

        return mainPanel;
    }

    //build add stock holding
    private JPanel buildAddStockHoldingPage()
    {
        //construct button
        JButton ashAddButton = new JButton("Add");
            ashAddButton.setActionCommand("AddStockHolding");
            ashAddButton.addActionListener(this);

        //construct panels
        JPanel userIDPanel = new JPanel(new GridLayout(1, 2));
            userIDPanel.add(ashIDLabel);
            userIDPanel.add(ashID);
        JPanel stockSymbolPanel = new JPanel(new GridLayout(1, 2));
            stockSymbolPanel.add(ashSymbolLabel);
            stockSymbolPanel.add(ashSymbol);
        JPanel ashDescPanel = new JPanel(new GridLayout(1, 2));
            ashDescPanel.add(ashDescLabel);
            ashDescPanel.add(ashDesc);
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
            inputPanel.add(userIDPanel);
            inputPanel.add(stockSymbolPanel);
            inputPanel.add(ashDescPanel);
            inputPanel.setBorder(new TitledBorder("Add Stock Holding"));
        JPanel buttonPanel = new JPanel();
            buttonPanel.add(ashAddButton);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
            contentPanel.add(inputPanel);
            contentPanel.add(buttonPanel);
            
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);
            
        return mainPanel;
    }
    
    //build update access page
    private JPanel buildUpdateAccessPage()
    {
        //construct button
        JButton updAccButton = new JButton("Update");
            updAccButton.setActionCommand("UpdateUserAccess");
            updAccButton.addActionListener(this);
            
        //construct panels
        JPanel userIDPanel = new JPanel(new GridLayout(1, 2));
            userIDPanel.add(updAccIDLabel);
            userIDPanel.add(updAccID);
        JPanel adminPanel = new JPanel(new GridLayout(1,2));
            adminPanel.add(updAccAdminLabel);
            adminPanel.add(updAccYesRadio);
            adminPanel.add(updAccNoRadio);
        JPanel buttonPanel = new JPanel();
            buttonPanel.add(updAccButton);
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
            inputPanel.add(userIDPanel);
            inputPanel.add(adminPanel);
            inputPanel.setBorder(new TitledBorder("Update User Access"));
            
        JPanel contentPanel = new JPanel(new GridLayout(4, 1));
            contentPanel.add(inputPanel);
            contentPanel.add(buttonPanel);
            
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);
            
        return mainPanel;
            
    }

    //build delete user page
    private JPanel buildDeleteUserPage()
    {
        JButton deleteUserButton = new JButton("Delete");
            deleteUserButton.setActionCommand("DeleteUser");
            deleteUserButton.addActionListener(this);
            
        //construct panels
        JPanel userIDPanel = new JPanel(new GridLayout(1, 2));
            userIDPanel.add(deleteUserIDLabel);
            userIDPanel.add(deleteUserID);
            userIDPanel.setBorder(new TitledBorder("Delete User"));
        JPanel deleteButtonPanel = new JPanel();
            deleteButtonPanel.add(deleteUserButton);
            
        JPanel contentPanel = new JPanel(new GridLayout(6, 1));
            contentPanel.add(userIDPanel);
            contentPanel.add(deleteButtonPanel);
           
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);
            
        return mainPanel;
    }
    
    //build delete stock holding page
    private JPanel buildDeleteStockHoldingPage()
    {
        JButton dshButton = new JButton("Delete");
            dshButton.setActionCommand("DeleteStockHolding");
            dshButton.addActionListener(this);
            
        JPanel userIDPanel = new JPanel(new GridLayout(1, 2));
            userIDPanel.add(dshIDLabel);
            userIDPanel.add(dshID);
        JPanel symbolPanel = new JPanel(new GridLayout(1, 2));
            symbolPanel.add(dshSymbolLabel);
            symbolPanel.add(dshSymbol);
        JPanel buttonPanel = new JPanel();
            buttonPanel.add(dshButton);
            
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
            inputPanel.add(userIDPanel);
            inputPanel.add(symbolPanel);
            inputPanel.setBorder(new TitledBorder("Delete Stock Holding"));
        JPanel contentPanel = new JPanel(new GridLayout(4, 1));
            contentPanel.add(inputPanel);
            contentPanel.add(buttonPanel);
            
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);
            
        return mainPanel;
    }
    
    //build find stock item page
    private JPanel buildFindStockItemPage()
    {
        JButton fsiButton = new JButton("Find");
            fsiButton.setActionCommand("FindStockItem");
            fsiButton.addActionListener(this);
            
        //construct panels
        JPanel symbolPanel = new JPanel(new GridLayout(1, 2));
            symbolPanel.add(fsiSymbolLabel);
            symbolPanel.add(fsiSymbol);
            symbolPanel.setBorder(new TitledBorder("Find Stock Item"));
        JPanel fsiButtonPanel = new JPanel();
            fsiButtonPanel.add(fsiButton);
            
        JPanel contentPanel = new JPanel(new GridLayout(6, 1));
            contentPanel.add(symbolPanel);
            contentPanel.add(fsiButtonPanel);
            
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);
            
        return mainPanel;
    }
    
    //build find user page
    private JPanel buildFindUserPage()
    {
        JButton findUserButton = new JButton("Find");
            findUserButton.setActionCommand("FindUser");
            findUserButton.addActionListener(this);
            
        //construct panels
        JPanel userIDPanel = new JPanel(new GridLayout(1, 2));
            userIDPanel.add(findUserIDLabel);
            userIDPanel.add(findUserID);
            userIDPanel.setBorder(new TitledBorder("Find User"));
        JPanel buttonPanel = new JPanel();
            buttonPanel.add(findUserButton);
            
        JPanel contentPanel = new JPanel(new GridLayout(6, 1));
            contentPanel.add(userIDPanel);
            contentPanel.add(buttonPanel);
        
        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(140, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);
            
        return mainPanel;
    }
    
    //list all users page
    private JPanel buildAllUsersPage()
    {
        //display list in label
        allUsersArea = new JTextArea("No users have been added");
        allUsersArea.setEditable(false);
        allUsersArea.setBackground(new Color(240, 240, 240));

        //construct panels
        JPanel allUsersPanel = new JPanel();
            allUsersPanel.add(allUsersArea);
        JPanel contentPanel = new JPanel();
            contentPanel.add(allUsersPanel);

        JPanel northSpacerPanel = new JPanel(new BorderLayout());    
        JPanel westSpacerPanel = new JPanel(new BorderLayout());
        JPanel eastSpacerPanel = new JPanel(new BorderLayout());
            
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(northSpacerPanel, BorderLayout.NORTH);
            mainPanel.add(westSpacerPanel, BorderLayout.WEST);
            mainPanel.add(eastSpacerPanel, BorderLayout.EAST);

        return contentPanel;
    }

    public void actionPerformed(ActionEvent e) 
    {
        String arg = e.getActionCommand();
        CardLayout cl = (CardLayout)(cardHolder.getLayout());
        cl.show(cardHolder, arg);

        switch(arg)
        {
            case "Login":
                try
                {
                    String loginIDEntry = loginID.getText();
                    String passwordEntry = loginPassword.getText();
                    if(validateUser(loginIDEntry, passwordEntry))
                    {
                        loginFrame.dispose();
                        openApplication();                    
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Invalid Login Credentials", "Access Denied", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                catch(Exception ex)
                {
                    System.out.println("Exception: " + ex.getMessage());
                }
                break;
            case ABOUT:
                String about = "Stock Tracker Ver 1.0\nCreated by Andrew Engel\nCopyright 2017\nAll rights reserved";
                JOptionPane.showMessageDialog(null, about, "About Stock Tracker", JOptionPane.INFORMATION_MESSAGE);
                break;
            case EXIT:
                System.exit(0);
                break;
            case "AddNewUser":
                try
                {
                    String id = addUserID.getText();
                    String pw = addUserPw.getText();
                    String fn = addUserFName.getText();
                    String ln = addUserLName.getText();
                    boolean admn = addUserYesRadio.getState();

                    if(id.length() == 0)
                        JOptionPane.showMessageDialog(null, "You need to enter a User ID", "Error", JOptionPane.ERROR_MESSAGE);
                    else if(fn.length() == 0)
                        JOptionPane.showMessageDialog(null, "You need to enter a first name", "Error", JOptionPane.ERROR_MESSAGE);
                    else if(ln.length() == 0)
                        JOptionPane.showMessageDialog(null, "You need to enter a last name", "Error", JOptionPane.ERROR_MESSAGE);
                    else if(!addUserYesRadio.getState() && !addUserNoRadio.getState())
                        JOptionPane.showMessageDialog(null, "You need to choose an admin status", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                    {
                        //add user
                        tracker.addUser(id, fn, ln, pw, admn);
                        //confirmation message
                        JOptionPane.showMessageDialog(null, "User Added", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        //reformat fields
                        clearFields();
                        addUserID.requestFocus();
                    }
                }
                catch(PasswordSizeException ex)
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage() + "\n"
                            + ex.usage(), "Invalid Password", JOptionPane.ERROR_MESSAGE);
                }
                catch(PasswordInvalidFormatException ex)
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage() + "\n"
                            + ex.usage(), "Invalid Password", JOptionPane.ERROR_MESSAGE);
                }
                catch(PasswordException ex)
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage() + "\n"
                            + ex.usage(), "Invalid Password", JOptionPane.ERROR_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Unknown Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "AddStockHolding":
                try
                {
                    String id = ashID.getText();
                    String symbol = ashSymbol.getText();
                    String desc = ashDesc.getText();
                    
                    if(tracker.addStockHolding(id, symbol, desc))
                        JOptionPane.showMessageDialog(null, "Stock: " + symbol + " added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Either user does not exist or user already owns stock.", "Cannot add Stock", JOptionPane.ERROR_MESSAGE);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                ashID.requestFocus();
                break;
            case "DeleteStockHolding":
                try
                {
                    String id = dshID.getText();
                    String symbol = dshSymbol.getText();
                    
                    if(tracker.deleteStockHolding(id, symbol))
                    {
                        JOptionPane.showMessageDialog(null, "Stock: " 
                            + symbol + " deleted from " + id + "." , 
                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "User ID and stock combination do not exist." , 
                            "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                dshID.requestFocus();
                break;
            case "DeleteUser":
                try
                {
                    String id = deleteUserID.getText();
                    
                    if(tracker.deleteUser(id))
                    {
                        JOptionPane.showMessageDialog(null, "User \"" 
                            + id + "\" and their stocks have been deleted", 
                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "User ID does not exist." ,
                            "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                deleteUserID.requestFocus();
                break;
            case "UpdateUserAccess":
                try
                {
                    String ID = updAccID.getText();
                    boolean admin = updAccYesRadio.getState();
                    boolean hidden = updAccHiddenRadio.getState();
                    
                    if(!hidden)
                    {
                        if(tracker.setAdmin(ID, admin))
                        {
                            JOptionPane.showMessageDialog(null, "User \"" 
                                        + ID + "\" admin privileges have been updated to " 
                                        + admin + ".", "Success!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "User \"" 
                                        + ID + "\" admin privileges are already set to " 
                                        + admin + ".", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "No admin settings were selected.",
                                        "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                updAccID.requestFocus();
                break;
            case "FindUser":
                try
                {
                    String ID = findUserID.getText();
                    User user = null;
                    user = tracker.getUser(ID);
                    
                    if(user != null)
                    {
                        ArrayList<Stock> userStocks = user.getStocks();
                        String stockList = "";
                        if(userStocks != null)
                        {
                            for(Stock stock : userStocks)
                            {
                                stockList += stock.getStockSymbol() + " - " 
                                + stock.getStockDesc() + "\n                 ";
                            }

                        }
                        else
                        {
                            stockList = "None";
                        }
                        JOptionPane.showMessageDialog(null, "User ID:  "
                                        + user.getUserID() + "\nName:     "
                                        + user.getUserFName() + " "
                                        + user.getUserLName() + "\nAdmin:    "
                                        + user.isAdmin() + "\nStocks:  "
                                        + stockList, "User Information",
                                        JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "User not found.",
                                        "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                findUserID.requestFocus();
            case "FindStockItem":
                try
                {   
                    String symbol = fsiSymbol.getText();
                    Stock stk = null;
                    stk = tracker.getStock(symbol);
                    
                    if(stk != null)
                    {
                        JOptionPane.showMessageDialog(null, "Symbol:         "
                                                    + stk.getStockSymbol()
                                                    + "\nDescription: " + stk.getStockDesc(),
                                                    "Stock Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Stock not found.",
                                                    "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                fsiSymbol.requestFocus();
            case FIND_ALL_USERS:
                String all = tracker.listAllUsers();
                allUsersArea.setText(all);
                break;
        }
    }
        
    //validate user and password combination
    private boolean validateUser(String id, String pw)throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        return tracker.validateUser(id, pw);
    }

    public void clearFields()
    {
        loginID.setText("");
        loginPassword.setText("");
        addUserID.setText("");
        addUserPw.setText("");
        addUserFName.setText("");
        addUserLName.setText("");
        addUserHiddenRadio.setState(true);
        ashID.setText("");
        ashSymbol.setText("");
        ashDesc.setText("");
        updAccID.setText("");
        updAccHiddenRadio.setState(true);
        deleteUserID.setText("");
        dshID.setText("");
        dshSymbol.setText("");
        fsiSymbol.setText("");
        findUserID.setText("");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, PasswordException
    {
        try
        {
            StockTrackerGUI st = new StockTrackerGUI();
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Class not found exception: " +
                                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "SQL exception: " +
                                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}