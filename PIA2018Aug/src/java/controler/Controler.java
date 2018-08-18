/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import beans.Bus;
import beans.MonthlyCards;
import beans.BusCompanys;
import beans.CityLine;
import beans.Driver;
import beans.Employment;
import beans.InterCityLine;
import beans.User;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.NewHibernateUtil;

/**
 *
 * @author Abi
 */
@ManagedBean(name = "controler")
@SessionScoped
public class Controler implements Serializable {

    private User user;
    private InterCityLine interCityLine;
    private String username;
    private String password;
    private String newPassword = null;
    private String confirmPassword;
    private List<InterCityLine> interCityLines;
    private List<InterCityLine> filteredInterCityLines;
    private Driver driver;
    private CityLine cityLine;
    private Bus cityLineBus;
    public static Session session = null;

    public String logIn() {
        try {
            this.session.beginTransaction();
            SQLQuery query = session.createSQLQuery("SELECT * FROM User WHERE username ='" + this.username + "'");
            query.addEntity(User.class);
            List<User> users = (List<User>) query.list();
            if (users.isEmpty()) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Invalid username"));
                throw new Exception();
            }
            this.user = users.get(0);
            if (!(user.getPass().equals(password))) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Invalid pass"));
                throw new Exception();
            }
            String ret = "";
            switch (user.getUserType().intValue()) {
                case 0:
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "User not approved by admin"));
                    throw new Exception();
                case 1:
                    ret = "user";
                    break;
                case 2:
                    ret = "admin";
                    break;
            }
            this.session.getTransaction().commit();
            return ret;
        } catch (Exception e) {
            this.session.getTransaction().rollback();
        }
        return null;
    }

    public String register() {
        user = new User();
        return "register";
    }

    public String addInterCityLine(){
        this.session.beginTransaction();          
        this.session.save(interCityLine);
        this.session.getTransaction().commit();
        return "manageInterCity";
    }

    
    public String checkAndRegister() {
        this.session.beginTransaction();
        SQLQuery query = this.session.createSQLQuery("SELECT * FROM User WHERE username='" + user.getUsername() + "'");
        query.addEntity(User.class);
        List<User> users = (List<User>) query.list();
        if (!users.isEmpty()) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Username taken, choose another one"));
            return null;
        }
        query = this.session.createSQLQuery("SELECT * FROM employment WHERE id = 2");
        query.addEntity(Employment.class);
        List<Employment> employments = query.list();
        Employment employment = employments.get(0);
        user.setUserType(new Integer(0));
        user.setMonthlyCardses(null);
        user.setReservationses(null);
        user.setEmployment(employment);
        this.session.save(user);
        this.session.getTransaction().commit();
        return "index";
    }

    public List<User> getNewUsers() {
        this.session.beginTransaction();
        SQLQuery query = session.createSQLQuery("SELECT * FROM User WHERE user_type = 0");
        query.addEntity(User.class);
        List<User> users = (List<User>) query.list();
        this.session.getTransaction().commit();
        return users;
    }
    
    public List<MonthlyCards> getMonthlyCards() {
        this.session.beginTransaction();
        SQLQuery query = session.createSQLQuery("SELECT * FROM monthly_cards");
        query.addEntity(MonthlyCards.class);
        List<MonthlyCards> monthly_cards = (List<MonthlyCards>) query.list();
        this.session.getTransaction().commit();
        return monthly_cards;
    }
    

//Add aproved column to database and it will work like approve user    
    public String aprroveMonthlyCardWithId(Integer id) {
        if (id == null) {
            return null;
        }
        this.session.beginTransaction();
        try {
            SQLQuery query = session.createSQLQuery("SELECT * FROM monthly_cards WHERE id = " + id.toString());
            query.addEntity(User.class);
            List<MonthlyCards> monthlyCardses = (List<MonthlyCards>) query.list();
            MonthlyCards card = MonthlyCards.get(0);
            card.setApproved(new Integer(1));
            this.session.save(card);
            this.session.getTransaction().commit();
        } catch(Exception e){
            this.session.getTransaction().rollback();
        }
        return null;
    }
        
    public String aprroveUserWithId(Integer id) {
        if (id == null) {
            return null;
        }
        this.session.beginTransaction();
        try {
            SQLQuery query = session.createSQLQuery("SELECT * FROM User WHERE id = " + id.toString());
            query.addEntity(User.class);
            List<User> users = (List<User>) query.list();
            User usr = users.get(0);
            usr.setUserType(new Integer(1));
            this.session.save(usr);
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
        }
        return null;
    }

    public String addNewDriver() {
        this.session.beginTransaction();
        try{
            session.save(driver);
            this.session.getTransaction().commit();
        }catch(Exception e){
            this.session.getTransaction().rollback();
        }
        driver = new Driver();
        return null;
    }

    public String manageCity() {
        driver = new Driver();
        cityLine = new CityLine();
        return "manageCity";
    }

    @PostConstruct
    public void init() {
        session = NewHibernateUtil.getSessionFactory().openSession();
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
        if (interCityLines != null) {
            return interCityLines;
        }
        //interCityLines = new ArrayList<>();
        Transaction tx = session.beginTransaction();
        SQLQuery query = session.createSQLQuery("SELECT * FROM inter_city_line AS i, bus_companys AS bc, bus AS b "
                + "WHERE i.bus_company_id = bc.id AND i.bus_id = b.id");
        query.addEntity(InterCityLine.class);
        interCityLines = (List<InterCityLine>) query.list();
        tx.commit();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public InterCityLine getInterCityLine() {
        return interCityLine;
    }

    public void setInterCityLine(InterCityLine interCityLine) {
        this.interCityLine = interCityLine;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CityLine getCityLine() {
        return cityLine;
    }

    public void setCityLine(CityLine cityLine) {
        this.cityLine = cityLine;
    }

    
}
