/*
	Module 3:	Course Project
	Programmer:	Andrew Engel
	Date:		07/20/2017
	Filename:	PasswordException.java
	Purpose:	An abstract class for password exceptions
*/

public abstract class PasswordException extends Exception
{
	public PasswordException()
	{
		super("Your password is invalid.");
	}

	public PasswordException(String msg)
	{
		super(msg);
	}

	public abstract String usage();
}