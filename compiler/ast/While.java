package ast;
import environment.Environment;

/**
 * The While class is for while loops. These are statements so it extends statement. 
 * 
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public class While extends Statement
{
    private Condition conditional;
    private Statement stat1;

    /**
     * Constructor for objects of the While class
     * @param cond   the conditional expression to be executed
     * @param s1     the statement to be executed if conditional is true
     */
    public While(Condition cond, Statement s1)
    {
        conditional = cond;
        stat1 = s1;
    }

    /**
     * Executes the while loop
     * 
     * @param env the environment in which the classes execute code

     */
    @Override
    public void exec(Environment env) 
    {
        int ret = conditional.eval(env);
        while (ret == 1)
        {
            if (stat1 != null)
            {
                stat1.exec(env);
            }
            ret = conditional.eval(env);
        }
    }
}