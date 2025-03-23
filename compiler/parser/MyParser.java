package parser;

import scanner.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Expression;
import ast.If;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.Statement;
import ast.Variable;
import ast.While;
import ast.Writeln;

/**
 * Parser is a simple parser for Compilers and Interpreters 
 * @author Anjali Yella
 * 
 * with help from seat neighbors
 * @version 3/14/2024
 * Usage:
 * The parser executes statements as it parses them. 
 *
 * 
 */
public class MyParser 
{
    private Scanner scan;
    private String currentTok;
    private Map<String, Integer> map;

    /**
     * Constructor for objects of the Parser class
     * @param scanner the Scanner to use for the Parser
     * 
     * @throws ScanErrorException if currentTok does not equal expected
     */
    public MyParser(Scanner scanner)
    {
        scan = scanner;
        try
        {
            currentTok = scan.nextToken();
            map = new HashMap<String, Integer>();
        }
        catch (ScanErrorException e) //Illegal argument exception??
        {
            e.printStackTrace();
        }
    }//
    /**
     * 
     * this method comes from Scanner
     * Checks if the current *token* is what is expected. If so, advances
     * to the next token. If not,
     * throws a ScanErrorException
     * //different from method in scanner
     * 
     * @param expected the expected char
     * @throws ScanErrorException if currentChar does not equal expected.
     */
    
     private void eat(String expected) throws ScanErrorException
     {
         if(expected.equals(currentTok))
             currentTok = scan.nextToken();
         else
             throw new ScanErrorException("Illegal Character. This was expected: " +
             expected + " but this was found:" + currentTok + ".");
     }
    /**
     * Parses a number
     * 
     * @return  the number in the string 
     * 
     * @throws ScanErrorException if currentTok does not equal expected
     */
    private int parseNumber() throws ScanErrorException
    {
        int test = Integer.parseInt(currentTok);
        eat(currentTok);
        return test;
    }

    /**
     * Parses statements using writeln and prints the
     * value of the intended statement.
     * 
     * got help from neighbors for this method
     * 
     * @throws ScanErrorException if currentTok does not equal expected
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if(currentTok.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if(currentTok.equals("BEGIN"))
        {
            eat("BEGIN");
            ArrayList<Statement>list = new ArrayList<>();
            while(!currentTok.equals("END"))
            {
                list.add(parseStatement());
            }
            Block block = new Block(list);
            eat("END");
            eat(";");
            return block;
        }
        else if(currentTok.equals("IF"))
        {
            eat("IF");
            Condition cond = parseConditional();
            eat("THEN");
            Statement stat = parseStatement();
            return new If(cond, stat);
        }
        else if(currentTok.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseConditional();
            eat("DO");
            Statement stat = parseStatement();
            return new While(cond, stat);
        }
        else
        {
            String curr = currentTok;
            eat(curr);
            eat(":=");
            Assignment assignment = new Assignment(curr, parseExpression());
            eat(";");
            return assignment;
        }
    }

    /**
     * Parses a conditional object
     * @return the conditional object consisting of the parsed expression and comparative operator
     * @throws ScanErrorException if scanning goes wrong
     */
    public Condition parseConditional() throws ScanErrorException
    {
        Expression exp1 = parseExpression();
        String compOp = parseCompOp();
        Expression exp2 = parseExpression();
        return new Condition(compOp, exp1, exp2);
    }

    /**
     * Eats the comparative operator and returns its string version
     * @return the string version of the compOp
     * @throws ScanErrorException
     */
    public String parseCompOp() throws ScanErrorException
    {
        if(currentTok.equals("="))
        {
            eat("=");
            return "=";
        }
        else if(currentTok.equals("<>"))
        {
            eat("<>");
            return "<>";
        }
        else if(currentTok.equals("<"))
        {
            eat("<");
            return "<";
        }
        else if(currentTok.equals(">"))
        {
            eat(">");
            return ">";
        }
        else if(currentTok.equals("<="))
        {
            eat("<=");
            return "<=";
        }
        else
        {
            eat(">=");
            return ">=";
        }
    }
    

        /**
     * Parses factors which are complex to use on expressions
     * removes all unnecessary characters
     * @return value of number after taking into account negatives
     * @throws ScanErrorException if scanning fails
     */
    private Expression parseFactor() throws ScanErrorException
    {
        if(currentTok.equals("("))
        {
            eat("(");
            Expression expr = parseExpression();
            eat(")");
            return expr;
        }
        else if(currentTok.equals("-"))
        {
            eat("-");
            return new BinOp("-", new ast.Number(0), parseFactor());
        }
        else
        {
            String token = currentTok;
            try
            {
                int val = Integer.parseInt(currentTok);
                eat(currentTok);
                ast.Number num = new ast.Number(val);
                return num;
            }
            catch(Exception e)
            {
                eat(currentTok);
                // check if there is a ( after to return a procedure call
                if (currentTok.equals("("))
                {
                    eat(currentTok);
                    ArrayList<Expression> params = new ArrayList();
                    while(!currentTok.equals(")"))
                    {
                        Expression newargs = parseExpression();
                        params.add(newargs);
                        if(currentTok.equals(","))
                        {
                            eat(",");
                        }
                    }
                    eat(")");
                    return new ProcedureCall(token, params);
                }
                // else variable)
                return new Variable(token);
            }
        }
    }
     /**
     * Parses a term with many different characters
     * 
     * @return the parsed term 
     * @throws ScanErrorException if scanning goes wrong
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression val = parseFactor();
        while (currentTok.equals("*") || currentTok.equals("/"))
        {
            String oldToken = currentTok;
            eat(currentTok);  
            if(oldToken.equals("*"))
                val = new BinOp("*", val, parseFactor());
            else
                val = new BinOp("/", val, parseFactor());
        }
        return val;
    }
    /**
     * Parses an expression with many terms
     * @return value of expression once evaluated
     * @throws ScanErrorException if scanning doesn't work
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression val = parseTerm();
        while (currentTok.equals("+") || currentTok.equals("-"))
        {
            if(currentTok.equals("+"))
            {
                eat("+");
                val = new BinOp("+", val, parseTerm());
            }
            else if(currentTok.equals("-"))
            {
                eat("-");
                val = new BinOp("-", val, parseTerm());
            }
        }
        return val;
    }
    /**
     * public static void main(String[] args) throws Exception
    {
        Scanner sc = new Scanner(new FileInputStream(
                new File("parser/ParserTester.txt")));
        MyParser pa = new MyParser(sc);
        //Environment env = new Environment();
        while (sc.hasNext())
        {
            pa.parseStatement();
            //Statement s = pa.parseStatement();
            //s.exec(env);
        }
    }
     */

     /**
      * Parses the program and returns the Program which is parsed
      */
      public Program parseProgram() throws ScanErrorException
    {
        List<ProcedureDeclaration> pd = new ArrayList<ProcedureDeclaration>();
        while(currentTok.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String name = currentTok;
            eat(name);
            eat("(");
            ArrayList<String> params = new ArrayList();
            while(!currentTok.equals(")"))
            {
                String newparam = currentTok;
                eat(newparam);
                params.add(newparam);
                if(currentTok.equals(","))
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            pd.add(new ProcedureDeclaration(name, parseStatement(), params));
        }
        Statement stmt = parseStatement();
        return new Program(pd, stmt);
    }

}


