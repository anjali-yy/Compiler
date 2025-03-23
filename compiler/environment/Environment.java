package environment;
import java.util.HashMap;

import ast.ProcedureDeclaration;

/**
 * The Environment class creates environments that hold instructions
 * on how to execute code
 *
 * @author Anjali Yella
 * @version 4/15/2024
 */
public class Environment
{
    private HashMap<String, Integer> variables;
    private HashMap<String, ProcedureDeclaration> procedures;
    private Environment parent;
    /**
     * Constructor for objects of the Environment class
     */
    public Environment()
    {
        this.variables = new HashMap<String, Integer>();
        this.procedures = new HashMap<String, ProcedureDeclaration>();
    }

    /**
     * Constructor for objects of the Environment class when given a parent Environment
     * 
     * @param parent the parent Environment
     */
    public Environment(Environment parent)
    {
        this.variables = new HashMap<String, Integer>();
        this.procedures = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
    }

    /**
     * Adds variable to environment map 
     * 
     * @param var   the variable to be stored
     * @param val   the value to be stored by the variable
     */
    public void setVariable(String var, int val)
    {
        if(variables.containsKey(var))
        {
            variables.put(var, val);
            return;
        }
        else 
        {
            Environment temp = this;
            while(temp.parent != null)
            {
                if(temp.parent.variables.containsKey(var))
                {
                    temp.parent.variables.put(var, val);
                    return;
                }
                else 
                {
                    temp = temp.parent;
                }
            }
        }
        variables.put(var, val);   
    }

    /**
     * Adds  a variable to current environmentt 
     * 
     * @param var   the variable to be stored
     * @param val   the value to be stored by the variable
     */
    public void declareVariable(String var, int val)
    {
        variables.put(var, val);
    }
    

    /**
     * Returns the value or the variable 
     * 
     * @param var the name of the variable to be found in the map
     * @return the value of the variable
     */
    public int getVariable(String variable)
    {
    
        if(variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        else 
        {
            Environment temp = this;
            while(temp.parent != null)
            {
                if(temp.parent.variables.containsKey(variable))
                {
                    return temp.parent.variables.get(variable);
                }
                else
                {
                    temp = temp.parent;
                }
            }
        }
        return variables.get(variable);
        
    }

    /**
     * Sets a ProcedureDeclaration into the HashMap with name var
     * 
     * @param var   the name (or key) of the ProcedureDeclaration
     * @param dec   the ProcedureDeclaration that is inserted into the HashMap
     */
    public void setProcedure(String var, ProcedureDeclaration dec)
    {
        procedures.put(var, dec);
    }

    /**
     * Returns the ProcedureDeclaration with the given name in the HashMap
     * 
     * @param search the name of the ProcedureDeclaration to search for
     * @return the ProcedureDeclaration with name search
     */
    public ProcedureDeclaration getProcedure(String search)
    {
        Environment temp = this;
        while (temp.parent!=null)
        {
            temp = temp.parent;
        }
        return temp.procedures.get(search);
    }

     /**
     * Returns the parent environment of the current
     * 
     * @return the parent Environment
     */
    public Environment getParent()
    {
        return parent;
    }
}
