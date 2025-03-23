package ast;
import environment.Environment;

/**
 * Expression class is an abstract class and can be used by other classes that are types of expressions. 
 *
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public abstract class Expression
{
    /**
     * subclasses can use this code
     * 
     * @param env   the environment in which the classes evaluate code
     * @return the value of the given object of the particular subclass
     */
    public abstract int eval(Environment env);
}

