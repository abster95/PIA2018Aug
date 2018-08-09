/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Abi
 */
@ManagedBean
@SessionScoped
public class Controler implements Serializable {
    private User user;
    
    public String logIn(){
        return null;
    }
    
    public String register(){
        return "register";
    }
    public String checkAndRegister(){
        return null;
    }
    /**
     * Creates a new instance of Controler
     */
    public Controler() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
