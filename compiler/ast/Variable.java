package ast;
import environment.Environment;
/**
 * The Variable class can instantiate variables. This extends Expression because variabales are expressions. 
 *
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructor for objects of the Variable class
     * @param name  the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Evaluates objects of the Variable class 
     * 
     * @param env the environment in which the classes evaluate code
     * @return the value of the variable

     */
    @Override
    public int eval(Environment env) 
    {
        return env.getVariable(name);
    }
}
