package ast;
import environment.Environment;
import java.util.*;

/**
 * ProcedureDeclarations contain a name, statement, and methods to execute. 
 *
 * @author Anjali Yella
 * with help from seat partners
 * @version 5/14/24
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement stmt;
    private ArrayList<String> parameters;
    /**
     * Constructor for objects of the ProcedureDeclaration class
     * 
     * @param name the name of the ProcedureDeclaration
     * @param stmt the statment of the ProcedureDeclaration
     * @param parameters the parameters that are the basis of the ProcedureDeclaration
     */
    public ProcedureDeclaration(String name, Statement stmt, ArrayList<String> parameters)
    {
        this.name = name;
        this.stmt = stmt;
        this.parameters = parameters;
    }

    /**
     * Creates an object in the given
     * 
     * @param env the given environment whose HashMap will be modified
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }

    /**
     * Returns the statement of the ProcedureDeclaration
     * 
     * @return the statement of the ProcedureDeclaration
     */
    public Statement getBody()
    {
        return stmt;
    }
    
    /**
     * Returns the parameters of the ProcedureDeclaration
     * 
     * @return the paramters of the ProcedureDeclaration
     */
    public ArrayList<String> getParameters()
    {
        return parameters;
    }
}
