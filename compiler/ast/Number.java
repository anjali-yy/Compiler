package ast;
import environment.Environment;

/**
 * The Number class is for numbers, which are expressions. 
 *
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructor for objects of the Number class
     * @param num   the number
     */
    public Number(int num)
    {
        value = num;
    }

    /**
     * Evaluates the  number
     * 
     * @param env the environment in which the classes evaluate code
     * @return the value of the number
     */
    @Override
    public int eval(Environment env) 
    {
        return value;
    }
}
