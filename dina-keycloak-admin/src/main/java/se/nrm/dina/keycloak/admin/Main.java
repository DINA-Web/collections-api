/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.keycloak.admin;
  
import java.util.Scanner;

/**
 *
 * @author idali
 */
public class Main {
                                                       
    public static void main(String[] args) {
      
        String serviceUrl;
        String tsvPath;
         
        
        if (args != null && args.length >= 2) {
            serviceUrl = args[0];
            tsvPath = args[1];
            System.out.println(serviceUrl);
            System.out.println(tsvPath);
            
        } else { 
            Scanner scanner = new Scanner(System.in); 
            System.out.print("Enter keycloak url: "); 
            serviceUrl = scanner.next();
            
            System.out.print("Enter tsv file path: "); 
            tsvPath = scanner.next();  
        }  
        new AdminClient().uploadUser(serviceUrl, tsvPath);
    }
}
