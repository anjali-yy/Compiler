package ast;
import environment.Environment;

/**
 * The Statement class is abstract and can be refered to by all statement classes. 
 * 
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public abstract class Statement 
{
    /**
     * subclasses can use
     * 
     * @param env the environment in which the subclasses execute code
     */
    public abstract void exec(Environment env);
}
