/*
    Java Programming:   Course Project
    Programmer:         Andrew Engel
    Date:               08/03/2017
    Filename:           FileHandler.java
    Purpose:            Provide a class to handle writing and reading files
*/

import java.io.*;
import java.util.*;

public class FileHandler
{
    DataOutputStream out;
    
    //constructor to create a file
    public FileHandler(String fileName)
    {
        out = createFile(fileName);
    }

    private DataOutputStream createFile(String fileName)
    {
        try
        {
            File file = new File(fileName);
            
            DataOutputStream infoToWrite = new DataOutputStream(
                new BufferedOutputStream(
                    new FileOutputStream(file)));
                    
            return infoToWrite;
        }
        catch(IOException ex)
        {
            System.out.println("I/O Error Occurred");
            System.exit(0);
        }
        
        return null;
    }
    
    public void writeData(String data)
    {
        try
        {
            out.writeUTF(data);
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println("I/O Error Occurred");
            System.exit(0); 
        }
    }
    
    public String readData(String fileName)
    {
        boolean endOfFile = false;
        String data = "";
        
        try
        {
            //construct file object
            File file = new File(fileName);
            
            //create file framework
            DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                    new FileInputStream(fileName)));
                    
            data = in.readUTF();
        }
        catch(EOFException ex)
        {
            endOfFile = true;
        }
        catch(IOException ex)
        {
            System.out.println("IOException occurred: " + ex.getMessage());
        }
        
        return data;
    }
}
