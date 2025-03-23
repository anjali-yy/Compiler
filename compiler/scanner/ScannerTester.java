package scanner;
import java.io.*;

import parser.MyParser;

/**
 * 
 */
public class ScannerTester
{
    /**
     * @throws ScanErrorException 
     * 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ScanErrorException
    {
         Scanner sc = new Scanner(new FileInputStream(
                new File("parser/ParserTester.txt")));
       
      // Environment env = new Environment();
        while (sc.hasNext())
        {
            System.out.println(sc.nextToken());
        }
    }
}