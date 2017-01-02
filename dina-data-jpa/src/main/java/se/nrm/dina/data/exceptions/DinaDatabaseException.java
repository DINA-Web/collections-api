/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.exceptions;

import java.util.List;
import javax.ejb.ApplicationException; 

/**
 *
 * @author idali
 */
@ApplicationException
public class DinaDatabaseException extends DinaException {
  
    
    public DinaDatabaseException(int errorCode, String errorType, String errorMsg) {
        super(errorCode, errorType, errorMsg);
    }
  
    public DinaDatabaseException(int errorCode, String errorType, List<String> errorMsgs) {
        super(errorCode, errorType, errorMsgs);
    }
    
//    public DinaDatabaseException(String errorMsg, int errorCode) {
//        super(errorMsg, errorCode);
//    }
//        
//    public DinaDatabaseException(Throwable t) { 
//        this.t = t;
//    }
//
//    public Throwable getT() {
//        return t;
//    } 
}
