import environment.Environment;
import parser.MyParser;
import scanner.*;

import java.io.*;

/**
 * Runs testers for the AST lab using an input tester file
 *
 * @author  Anjali Yella
 * @version 4/14/2024
 */
public class Main
{
    /**
     * Tests the scanner and parser given input files
     * @param args          info from the command line
     * @throws IOException  IOException if FileIO goes wrong
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        FileInputStream fileInputStream = new FileInputStream(new File(
                "/Users/anjali/Desktop/Compilers/compiler/ast/parserTest8_5.txt"));
        Scanner scanner = new Scanner(fileInputStream);
        MyParser parser = new MyParser(scanner);

        parser.parseProgram().exec(new Environment());
    }
}