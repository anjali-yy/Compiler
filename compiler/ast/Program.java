package ast;
import java.util.*;
import environment.Environment;

/**
 * The Program class contains getters and setters related to AST and Procedures. 
 * 
 * @author Anjali Yella
 * with help from seat partners
 * @version 5/14/24
 */

public class Program {
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }


    /**
     * Returns the list of ProcedureDeclarations contained in the Program
     * 
     * @return the list of ProcedureDeclarations
     */
    public List<ProcedureDeclaration> getProcedures()
    {
        return procedures;
    }
    
    /**
     * Returns the Statement contained in the Program
     * 
     * @return the Statement
     */
    public Statement getStatement()
    {
        return stmt;
    }

    /**
     * Executes the list of procedure declarations and returns 0 when the statement is left. 
     * 
     * @param env the given environment where code is executed
     * @return 0
     */
    public int exec(Environment env)
    {
        for(ProcedureDeclaration proc : procedures)
        {
            proc.exec(env);
        }
        stmt.exec(env);
        return 0;
    }

}