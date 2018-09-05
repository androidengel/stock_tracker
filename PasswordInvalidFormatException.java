/*
	Module 3:	Course Project
	Programmer:	Andrew Engel
	Date:		07/20/2017
	Filename:	PasswordInvalidFormatException.java
	Purpose:	Provides an exception for invalid password format
*/

public class PasswordInvalidFormatException extends PasswordException
{
	public PasswordInvalidFormatException()
	{
		super("Invalid password format");
	}

	public PasswordInvalidFormatException(String msg)
	{
		super(msg);
	}

	public String usage()
	{
		return new String("This password's format is invalid.");
	}
}