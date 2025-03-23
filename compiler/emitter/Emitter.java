package emitter;
import java.io.*;
/**
 * converts code from pascal to MIPS
 * 
 * 
 * @author Anjali Yella
 * worked with katelyn and nikhil
 * @version 5/17/2024
 */

public class Emitter
{
    private PrintWriter out;
    private int labelD;
    /**
     * 
     * @param name the name of the output file that will be generated
     */
    public Emitter(String name)
    {
        labelD = 0;
       
    }

    
    public void emit(String code)
    {
        if (!code.endsWith(":"))
        {
            code = "\t" + code;
        }
        out.println(code);
       

   
    public void close()
    {
        out.close();
    }

   
    public void nline()
    {
        emit("la $a0 newline");
        emit("li $v0 4");
        emit("syscall  #prints newline");
    }

    
    public void emitPush(String r)
    {
        emit("subu $sp $sssp 4");
        emit("sw " + r +  " ($sp)  # pushes");
    }

    
    public void emitPop(String r)
    {
        emit("li " + r + " ($sp)");
        emit("addu $sp $sp 4 # pops");
    }

    
    public int nextLabelID()
    {
        return ++labelD;
    }
}