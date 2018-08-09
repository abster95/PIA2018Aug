/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Abi
 */
@Named(value = "interCityLine")
@Dependent
public class InterCityLine {

    /**
     * Creates a new instance of InterCityLine
     */
    public InterCityLine() {
    }
    
}
