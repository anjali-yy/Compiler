package ast;
import environment.Environment;

/**
 * The Writeln class can print expressions
 * 
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of the Writeln class
     * 
     * @param exp the expression to be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Prints expression
     * 
     * @param env the environment which executes 
     */
    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}