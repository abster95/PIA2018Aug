/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Abi
 */
@Named(value = "bus")
@SessionScoped
public class Bus implements Serializable {

    /**
     * Creates a new instance of Bus
     */
    public Bus() {
    }
    
}
