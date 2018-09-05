/*
    Module 3:   Course Project
    Programmer: Andrew Engel
    Date:       07/20/2017
    Filename:   Password.java
    Purpose:    Provide a class to create password objects
*/

import java.io.Serializable;

public class Password implements Serializable
{
    final static int MIN = 5;
    final static int MAX = 15;
    String pswd;

    public Password(String newPw) throws PasswordException
    {
        set(newPw);
    }

    public void set(String newPw) throws PasswordException
    {
        newPw = newPw.trim();
        verifyFormat(newPw);
        pswd = newPw;
    }
    
    public boolean validatePassword(String newPw)
    {
        if(newPw.equals(pswd))
            return true;
        else
            return false;
    }

    private void verifyFormat(String newPw) throws PasswordException
    {
        if(newPw.length() == 0)
            throw new PasswordInvalidFormatException("You need to enter a password!");
        if(newPw.length() < MIN)
            throw new PasswordSizeException("Password needs to be at least " + MIN + " characters.", newPw.length(), MIN, MAX);
        if(newPw.length() > MAX)
            throw new PasswordSizeException("Passwords cannot be greater than " + MAX + " characters.", newPw.length(), MIN, MAX);
    }
    

}
