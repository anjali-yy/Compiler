package ast;
import java.util.*;
import environment.Environment;

/**
 * ProcedureCall objects have a name and call to procedure. 
 * 
 *
 * @author Anjali Yella
 * with help from seat partners
 * @version 5/14/24
 */
public class ProcedureCall extends Expression
{
    private String name;
    private ArrayList<Expression> p;
    
    /**
     * Constructor for objects of class ProcedureCall
     * 
     * @param name the name of the ProcedureCall
     * @param p the list of parameters the ProcedureCall is based on
     */
    public ProcedureCall(String name, ArrayList<Expression> p)
    {
        this.name = name;
        this.p = p;
    }

    /**
     * Evaluates the environment's procedures connecting environment (child environment)
     * 
     * @param env the environment whose procedures are evaluated
     * @return the corresponding value of the name variable in the child environment's
     * HashMap
     */
    public int eval(Environment env)
    {
        ProcedureDeclaration pd = env.getProcedure(name);
        Environment temp = env;
        if(temp.getParent() != null)
        {
            temp = temp.getParent();
        }
        Environment child = new Environment(temp);
        child.declareVariable(name, 0);
        ArrayList<String> parameters = pd.getParameters();
        for(int i = 0; i < p.size(); i++)
        {
            child.declareVariable(parameters.get(i), p.get(i).eval(env));
        }
        pd.getBody().exec(child);
        return child.getVariable(name);
    }
}
