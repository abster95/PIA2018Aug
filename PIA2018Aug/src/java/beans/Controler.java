/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<InterCityLine> interCityLines;
    private List<InterCityLine> filteredInterCityLines;

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

    /**
     * Creates a new instance of Controler
     */
    public Controler() {
        user = new User();
        interCityLines = new ArrayList<>();
        //FIXME: query the db for the interCityLines
        interCityLines.add(new InterCityLine("Busko", "00:00", "01:00", "Beograd", "Novi Pazar", "Beograd#Kragujevac#Kraljevo#Novi Pazar"));
        interCityLines.add(new InterCityLine("AutoBUS", "00:30", "15:00", "Beograd", "Subotica", "Beograd#Novi Sad#Backa Topola#Subotica"));
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
