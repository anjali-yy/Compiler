package ast;
import environment.Environment;

/**
 * The Condition class is for expressions with conditional operands ex: ==
 * 
 * 
 * @author Anjali Yella
 * with help from seat neighbors
 * 
 */
public class Condition extends Expression
{
    private String op;
    private Expression left;
    private Expression right;

    /**
     * Constructor for objects of the Condition class
     * 
     * @param operator  the operator the expressions will be evaluated with
     * @param exp1     the expression left of the operator
     * @param exp2    the expression right of the operator
     */
    public Condition(String operator, Expression exp1, Expression exp2)
    {
        op = operator;
        left = exp1;
        right = exp2;
    }

    /**
     * Evaluates conditional expressions
     * @param env    the environment in which the classes evaluate code
     * @return 0 or 1; 0 when expression is evaluated to be false, and 1 when evaluated to be true
     */
    @Override
    public int eval(Environment env)
    {
        int val1 = left.eval(env);
        int val2 = right.eval(env);
        int ret = 0;
        if (op.equals("=="))
        {
            if (val1 == val2)
            {
                ret = 1;
            }
        }
        else if (op.equals("<>"))
        {
            if (val1 != val2)
            {
                ret = 1;
            }
        }
        else if (op.equals(">"))
        {
            if (val1 > val2)
            {
                ret = 1;
            }
        }
        else if (op.equals("<"))
        {
            if (val1 < val2)
            {
                ret = 1;
            }
        }
        else if (op.equals("<="))
        {
            if (val1 <= val2)
            {
                ret = 1;
            }
        }
        else if (op.equals(">="))
        {
            if (val1 >= val2)
            {
                ret = 1;
            }
        }
        return ret;
    }
}