package scanner;
import java.io.*;

import parser.MyParser;

/**
 * Scanner is a simple scanner for Compilers and Interpreters 
 * @author Anjali Yella
 * with some help from seat neighbors
 * @version 1/26/2024
 * Usage:
 * The scanner reads inputs and creates tokens by finding partemes
 *
 * 
 * Notebook: If the next character is a new line, we don't know if it is valid. If it is parenthesis, this shows 
 * that it comes after an if keyword statement, showing us thats the token it represents. 
 * 
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a Scanner 
     * also uses InputStream object
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File (name));
     * Scanner part = new Scanner(inStream);
     * 
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor, creates scanner that scans input string
     * sets end of file to false then reads first character
     * Usage: Scanner part = new Scanner(stringused)
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Method: getNextChar
     * sets currentChar to the next character
     * sets eof to true if it reaches end of file
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
                eof = true;
            else 
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Method: eat
     * Checks if the current character is what is expected. If so, advances
     * to the next character. If not,
     * throws a ScanErrorException
     * 
     * @param expected the expected char
     * @throws ScanErrorException if currentChar does not equal expected.
     */
    
    private void eat(char expected) throws ScanErrorException
    {
        if(expected == currentChar)
            getNextChar();
        else
            throw new ScanErrorException("Illegal Character. This was expected: " +
             currentChar + " but this was found:" + expected + ".");
    }
    /**
     * Method: hasNext
     * checks if currentChar is at end of file, therefore if it has a next character or not
     * @return true if currentChar is not at the end of the file
     * else false
     */
    public boolean hasNext()
    {
        return (!eof);
    }

   
    /**
     * Method: nextToken
     * returns next token as a string
     * utilizes one of various helper methods after determining if the currentChar is a letter, digit, 
     * whitespace, or more. 
     * 
     * throws exception of currentChar isn't any of those
     * 
     * @return the next token as a String
     * @throws ScanErrorException if next currentChar isn't any of the specified character types 
     */
    public String nextToken() throws ScanErrorException
    {
        try
        {
            if(!removeWhiteSpace()==true) //means white spaces all removed
            {
                return "EOF";
            }
            
    
            if(isDigit(currentChar))
            {
                return scanNumber(); //returns corresponding scan method depending on what currentChar is
            }
            else if(isLetter(currentChar))
            {
                return scanIdentifier(); 
            }
            else if(isOperand(currentChar))
            {
                return scanOperand();
            }
            else if (currentChar == '.')
            {
                eof = true; //now at end of file
                return "EOF";
            }
            else 
                throw new ScanErrorException("This character is not recognized: " + currentChar);
        }
        catch (ScanErrorException e)
        {
            throw new ScanErrorException("This character is not recognized: " + currentChar);
        }
    } 
    
    //Formatting and Writing part Begins 

     /**
     * removes whitespace until eof or until real character (not whitespace) is reacheed
     * checks if whitespaces removed
     * @return true if there is a character after currentChar;
     *         otherwise, false.
     * @throws ScanErrorException if expected char doesnt match currentchar
     */
    private boolean removeWhiteSpace() throws ScanErrorException
    {
        while (isWhiteSpace(currentChar) && hasNext())
        {
            eat(currentChar);
        }

        if(hasNext()==true)
        {
            return true;
        }
        else
        {
        return false;
        }

    }
    /**
     * Checks if the input is digit
     * 
     * regex: digit:=[0,9].
     * 
     * @param input char checked
     * @return true if input is a digit
     * else false
     */
    public static boolean isDigit(char input)
    {
        return (input >= '0' && input <= '9');
    }

    /**
     * Checks if the input is letter
     * regex: letter := [a-z A-Z].
     * 
     * @param input char checked
     * @return true if the input is a letter else false
     */
    public static boolean isLetter(char input)
    {
        return (input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z');
    }

    /**
     * Checks if the input is whitespace
     * regex: white space := [‘ ‘ ‘\t’ ‘\r’ ‘\n’].
     * 
     * @param input char checked
     * @return true if the input is a white space
     * else false
     */
    public static boolean isWhiteSpace(char input)
    {
        return (input == '\r' || input == '\n'||input == ' ' || input == '\t' );
    }

    /**
     * Checks if the input is an operand
     * regex: operand := [ ‘%’ ‘(‘ ‘)’ ';' ‘=’ ‘+’ ‘-‘ ‘*’ ‘/’].
     * 
     * @param input the char to be checked
     * @return true if the input is an operand;
     *         otherwise, false.
     */
    public static boolean isOperand(char input)
    {
        return (input == '%' || input == '(' || input == ')'
            || input == ';' || input == ':' || input == '<' || input == '>'||input == '=' || input == '+' || input == '-' || input == 
            '*' || input == '/' );
    }
    
    /**
     * Scans currentChar and returns as string
     * if its not a digit, it throws an exception
     * 
     * @return currentChar as a String
     * @throws ScanErrorException if the method is called when currentChar
     * is not a digit
     */
    private String scanNumber() throws ScanErrorException
    {
        try
        {
            String part = "";
            while(isDigit(currentChar))
            {
                part = part+ currentChar;
                eat(currentChar);

                if(eof)
                    return part;
            }
            return part;
        }

        catch (ScanErrorException e)
        {
            e.printStackTrace();
            return "Not used on digit!";
        }
    }

     /**
     * Scans currentChar and returns as string
     * if its not a identifier, it throws an exception
     * 
     * @return currentChar as a String
     * @throws ScanErrorException if the method is called when currentChar
     * is not a identifier
     */
    private String scanIdentifier() throws ScanErrorException
    {
        try
        {
            String part = "";
            while(isLetter(currentChar))
            {
                part += currentChar;
                eat(currentChar);

                if(eof)
                    return part;
            }
            return part;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
            return "Not used on letter";
        }
    }

     /**
     * Scans currentChar and returns as string
     * if its not a operand, it throws an exception
     * 
     * @return currentChar as a String
     * @throws ScanErrorException if the method is called when currentChar
     * is not a operand
     */
    private String scanOperand() throws ScanErrorException
    {
        try
        {
            String part = "";
            if(isOperand(currentChar))
            {
                part += currentChar;
                if(hasNext())
                    eat(currentChar);
                else
                    return part;
            }
            
            if (part.equals("<") && currentChar == '>')
            {
                part += currentChar;

                if (hasNext())
                    eat(currentChar);
            } 

            else if (currentChar == '=')
            {
                part += currentChar;

                if (hasNext())
                    eat(currentChar);
            }
            return part;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
            return "Not used on operand";
        }
    }
    //Space for Commented out part of nextToken 
    //// remove single-line comments
            /* 
            while (currentChar == '/')
            {
                eat(currentChar);

                if (currentChar == '=') 
                {
                    if (hasNext())
                        eat(currentChar);
                    return "/=";
                }
                else if(currentChar == '/')
                    while ((currentChar != '\n') && (currentChar != '\r') && hasNext())
                        eat(currentChar);
                if(hasNext())
                    eat(currentChar);
                else
                    return "/";
                if(!removeWhiteSpace())
                    return "EOF";
            }*/
    //end of Space for nextToken
    
    /**
     * Main class that runs the program
     */
    public class Main
    {
    /**
     * Tests scanner and parser using an input file
     * 
     * @param args          info for the command line
     * @throws IOException  IOException if FileIO goes wrong
     * @throws ScanErrorException if scanning goes wrong
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        FileInputStream fileInputStream = new FileInputStream(new File ("ScannerTestAdvanced.txt"));
        Scanner scanner = new Scanner(fileInputStream);
        MyParser parser = new MyParser(scanner);
        parser.parseStatement();
        parser.parseStatement();
        parser.parseStatement();
    }
    }
}

