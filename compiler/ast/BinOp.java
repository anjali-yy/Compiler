package ast;
import environment.Environment;

/**
 * The BinOp class is for arithmetic Expressions that use the + or - sign. 
 * 
 * @author Anjali Yella
 * with help from seat neighbors
 * @version 4/15/2024
 */
public class BinOp extends Expression
{
    private String operand;
    private Expression left;
    private Expression right;

    /**
     * Constructor for objects of the BinOp class
     * 
     * @param operand   the operand used to evalute the expression
     * @param left      the expression left of the operator
     * @param right     the expression right of the operator
     */
    public BinOp(String operand, Expression left, Expression right)
    {
        this.operand = operand;
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates a binary operation
     * 
     * @param env    the environment in which the classes evaluate code
     * @return the evaluated expression
     */
    @Override
    public int eval(Environment env) 
    {
        if (operand.equals("*"))
        {
            return left.eval(env) * right.eval(env);
        }
        if (operand.equals("/"))
        {
            return left.eval(env) / right.eval(env);
        }
        if (operand.equals("+"))
        {
            return left.eval(env) + right.eval(env);
        }
        if (operand.equals("-"))
        {
            return left.eval(env) - right.eval(env);
        }
        else
        {
            return left.eval(env) % right.eval(env);
        }
    }
}
