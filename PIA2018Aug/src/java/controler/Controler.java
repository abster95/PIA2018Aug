/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import beans.InterCityLine;
import beans.User;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Session;
import util.NewHibernateUtil;

/**
 *
 * @author Abi
 */
@ManagedBean(name = "controler")
@SessionScoped
public class Controler implements Serializable {

    private User user;
    private String username;
    private String password;
    private String newPassword = null;
    private String confirmPassword;
    private List<InterCityLine> interCityLines;
    private List<InterCityLine> filteredInterCityLines;
    public static Session session = null;


    public String logIn() {
        return null;
    }

    public String register() {
        return "register";
    }

    public String checkAndRegister() {
        return null;
    }

    public String showDefaultPage() {
        return "default";
    }
    
    @PostConstruct
    public void init() {
        session =NewHibernateUtil.getSessionFactory().openSession();
        user = new User();
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

    public List<InterCityLine> getInterCityLines() {
        return interCityLines;
    }

    public void setInterCityLines(List<InterCityLine> interCityLines) {
        this.interCityLines = interCityLines;
    }

    public List<InterCityLine> getFilteredInterCityLines() {
        return filteredInterCityLines;
    }

    public void setFilteredInterCityLines(List<InterCityLine> filteredInterCityLines) {
        this.filteredInterCityLines = filteredInterCityLines;
    }

}
