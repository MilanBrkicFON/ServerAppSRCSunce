/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greske;

/**
 *
 * @author Milan
 */
public class SQLObjekatPostojiException extends Throwable{
   
    public SQLObjekatPostojiException(String message) {
        super(message);
    }

    public SQLObjekatPostojiException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
