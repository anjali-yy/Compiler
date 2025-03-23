package ast;
import environment.Environment;

/**
 * This is a subclass of statement. It is used when a variable is assigned to a value. 
 *
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public class Assignment extends Statement
{
    private String variable;
    private Expression expression;

    /**
     * Constructor for objects of the Assignment class
     * 
     * @param variable      the variable in the statement
     * @param expression2    the value assigned to the variable
     */
    public Assignment(String variable, Expression expression2)
    {
        this.variable = variable;
        this.expression = expression2;
    }

    /**
     * Executes objects of the Assignment class by assigning
     * the value to the variable in the given environment
     * 
     * @param env           the environment in which the class executes code
     */
    @Override
    public void exec(Environment env)
    {
        env.setVariable(variable, expression.eval(env));
    }
}
