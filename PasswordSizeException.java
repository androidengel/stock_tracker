/*
	Module 3:	Course Project
	Programmer:	Andrew Engel
	Date:		07/20/2017
	Filename:	PasswordSizeException.java
	Purpose:	Provides an exception for invalid password sizes
*/

public class PasswordSizeException extends PasswordInvalidFormatException
{
	private int passwordSize;
	private int min;
	private int max;

	public PasswordSizeException(String msg, int pwSize, int min, int max)
	{
		super(msg);
		this.passwordSize = pwSize;
		this.min = min;
		this.max = max;
	}

	public int getPasswordSize()
	{
		return passwordSize;
	}

	public int getMinRequirement()
	{
		return min;
	}

	public int getMaxRequirement()
	{
		return max;
	}
}