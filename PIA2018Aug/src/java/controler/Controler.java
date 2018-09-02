/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import beans.Bus;
import beans.MonthlyCards;
import beans.BusCompanys;
import beans.BusPictures;
import beans.CityLine;
import beans.Driver;
import beans.Employment;
import beans.InterCityLine;
import beans.Reservations;
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

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
    private List<Driver> availableDrivers;
    private CityLine cityLine;
    private Bus cityLineBus;
    private List<Bus> availableBuses;
    private String selectedBus;
    private String selectedDriver;
    private List<BusCompanys> companies;
    private String strSelectedCompany;
    private BusCompanys currCompany;
    private List<CityLine> allCityLines;
    private List<CityLine> filteredCityLines;
    private List<Reservations> myReservations;
    private List<Reservations> filteredReservations;
    private UploadedFile busImage;
    private List<String> images;
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

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

    public String register() {
        user = new User();
        return "register";
    }

    public String showDefaultPage() {
        return "default";
    }

    @Deprecated
    public String addInterCityLine() {
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

    // Adminland
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
        SQLQuery query = session.createSQLQuery("SELECT * FROM monthly_cards WHERE card_status != 1");
        query.addEntity(MonthlyCards.class);
        List<MonthlyCards> monthly_cards = (List<MonthlyCards>) query.list();
        this.session.getTransaction().commit();
        return monthly_cards;
    }
    public List<Reservations> getPendingReservations(){
        List<Reservations> reservations = null;
        this.session.beginTransaction();
        try{
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM reservations WHERE status != 1");
            query.addEntity(Reservations.class);
            reservations = (List<Reservations>)query.list();
            this.session.getTransaction().commit();
        } catch(Exception e){
            this.session.getTransaction().rollback();
            e.printStackTrace();
        }
        return reservations;
    }
    public void approveReservation(Integer id){
        if(null == id)
            return;
        List<Reservations> pending = getPendingReservations();
        Reservations res = null;
        for(Reservations r : pending){
            if(r.getId().intValue() == id.intValue()){
                res = r;
                break;
            }
        }
        if(null == res)
            return;
        res.setStatus(new Integer(1));
        this.session.beginTransaction();
        try{
            this.session.save(res);
            this.session.getTransaction().commit();
        } catch (Exception e){
            this.session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public String aprroveMonthlyCardWithId(Integer id) {
        if (id == null) {
            return null;
        }
        this.session.beginTransaction();
        try {
            SQLQuery query = session.createSQLQuery("SELECT * FROM monthly_cards WHERE id = " + id.toString());
            query.addEntity(MonthlyCards.class);
            List<MonthlyCards> monthlyCardses = (List<MonthlyCards>) query.list();
            MonthlyCards card = monthlyCardses.get(0);
            //@TODO: re-generate the class with the corresponding field
            card.setCardStatus(new Integer(1));
            this.session.save(card);
            this.session.getTransaction().commit();
        } catch (Exception e) {
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
        try {
            session.save(driver);
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
        }
        driver = new Driver();
        return null;
    }

    public String manageCity() {
        driver = new Driver();
        cityLine = new CityLine();
        this.session.beginTransaction();
        try {
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM  driver");
            query.addEntity(Driver.class);
            availableDrivers = (List<Driver>) query.list();
            availableBuses = new ArrayList<>();
            SQLQuery busQuery = this.session.createSQLQuery("SELECT * FROM bus WHERE bus.id NOT IN (SELECT bus_id FROM city_line) "
                    + "AND bus.id NOT IN (SELECT bus_id FROM inter_city_line)");
            busQuery.addEntity(Bus.class);
            availableBuses = (List<Bus>) busQuery.list();
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
        }
        return "manageCity";
    }

    public void addCityLine() {
        String[] stations = this.cityLine.getOtherStations().split("#");
        this.cityLine.setFirstStation(stations[0]);
        this.cityLine.setLastStation(stations.length);
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(this.selectedBus);
        m.find();
        String busID = m.group();

        this.session.beginTransaction();
        try {
            SQLQuery busQuery = this.session.createSQLQuery("SELECT * FROM bus WHERE id =" + busID);
            busQuery.addEntity(Bus.class);
            List<Bus> buses = (List<Bus>) busQuery.list();
            this.cityLine.setBus(buses.get(0));
            this.session.save(this.cityLine);
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
        }
        this.cityLine = new CityLine();
        this.selectedBus = null;
        this.selectedDriver = null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        busImage = event.getFile();
    }

    public String manageInterCity() {
        this.interCityLine = new InterCityLine();
        this.currCompany = new BusCompanys();
        this.cityLineBus = new Bus();
        this.driver = new Driver();
        this.strSelectedCompany = null;
        this.selectedDriver = null;
        this.busImage = null;
        this.session.beginTransaction();
        try {
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM  driver");
            query.addEntity(Driver.class);
            availableDrivers = (List<Driver>) query.list();
            availableBuses = new ArrayList<>();
            SQLQuery busQuery = this.session.createSQLQuery("SELECT * FROM bus WHERE bus.id NOT IN (SELECT bus_id FROM city_line) "
                    + "AND bus.id NOT IN (SELECT bus_id FROM inter_city_line)");
            busQuery.addEntity(Bus.class);
            availableBuses = (List<Bus>) busQuery.list();
            SQLQuery companiesQuery = this.session.createSQLQuery("SELECT * FROM bus_companys");
            companiesQuery.addEntity(BusCompanys.class);
            companies = (List<BusCompanys>) companiesQuery.list();
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
        }
        return "manageInterCity";
    }

    public void updateCurrCompany() {
        if ((null != this.strSelectedCompany) && !this.strSelectedCompany.isEmpty()) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(this.strSelectedCompany);
            m.find();
            String companyId = m.group();
            this.session.beginTransaction();
            try {
                SQLQuery query = this.session.createSQLQuery("SELECT * FROM bus_companys WHERE id = " + companyId);
                query.addEntity(BusCompanys.class);
                List<BusCompanys> companies = (List<BusCompanys>) query.list();
                this.currCompany = companies.get(0);
                this.session.getTransaction().commit();
            } catch (Exception e) {
                this.session.getTransaction().rollback();
            }
        }
    }

    public void updateCurrDriver() {
        if ((null != this.selectedDriver) && !this.selectedDriver.isEmpty()) {
            String[] splitted = selectedDriver.split(" ");
            this.session.beginTransaction();
            try {
                SQLQuery query = this.session.createSQLQuery("SELECT * FROM driver WHERE firstname = '" + splitted[0]
                        + "' AND lastname = '" + splitted[1] + "'");
                query.addEntity(Driver.class);
                List<Driver> drivers = (List<Driver>) query.list();
                this.driver = drivers.get(0);
                this.session.getTransaction().commit();
            } catch (Exception e) {
                this.session.getTransaction().rollback();
            }
        }
    }

    public void updateCurrBus() {
        if ((null != this.selectedBus) && !this.selectedBus.isEmpty()) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(this.strSelectedCompany);
            m.find();
            String busId = m.group();
            this.session.beginTransaction();
            try {
                SQLQuery query = this.session.createSQLQuery("SELECT * FROM bus WHERE id = " + busId);
                query.addEntity(Bus.class);
                List<Bus> buses = (List<Bus>) query.list();
                this.cityLineBus = buses.get(0);
                this.session.getTransaction().commit();
            } catch (Exception e) {
                this.session.getTransaction().rollback();
            }
        }
    }

    public String wizardListener(FlowEvent event) {
        updateCurrCompany();
        updateCurrDriver();
        updateCurrBus();
        return event.getNewStep();
    }

    public String saveInterCityLine() {
        this.interCityLine.setBus(cityLineBus);
        this.interCityLine.setDriver(driver);
        this.interCityLine.setBusCompanys(currCompany);
        String[] stations = this.interCityLine.getOtherStations().split(" ");
        this.interCityLine.setFirstStation(stations[0]);
        this.interCityLine.setLastStation(stations[stations.length - 1]);
        this.session.beginTransaction();
        try {

            if ((null == this.selectedBus) || this.selectedBus.isEmpty()) {
                this.session.save(this.cityLineBus);
            }
            if ((null == this.selectedDriver) || this.selectedDriver.isEmpty()) {
                this.session.save(this.driver);
            }
            if ((null == this.strSelectedCompany) || this.strSelectedCompany.isEmpty()) {
                this.currCompany.setLogo("default");
                this.session.save(this.currCompany);
            }
            if (null != busImage) {
                BusPictures picture = new BusPictures();
                picture.setBus(cityLineBus);
                String path = "bus" + cityLineBus.getId() + busImage.getFileName();
                picture.setName(path);
                busImage.write(path);
                this.session.save(picture);
            }
            this.session.save(this.interCityLine);
            this.session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            this.session.getTransaction().rollback();
        }
        return "admin";
    }

    // Userland
    public void requestMonthly() {
        MonthlyCards cardRequest = new MonthlyCards();
        cardRequest.setUser(user);
        cardRequest.setCardStatus(0);
        this.session.beginTransaction();
        try {
            SQLQuery check = this.session.createSQLQuery("SELECT * FROM monthly_cards WHERE user_id = " + user.getId());
            check.addEntity(MonthlyCards.class);
            List<MonthlyCards> cards = (List<MonthlyCards>) check.list();
            if (!cards.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("showUserMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "There's already request pending admin approval", null));
                this.session.getTransaction().commit();
                return;
            }
            // HACK: Database requires this even though we don't really need it
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM city_line");
            query.addEntity(CityLine.class);
            List<CityLine> lines = (List<CityLine>) query.list();
            cardRequest.setCityLine(lines.get(0));
            this.session.save(cardRequest);
            this.session.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage("showUserMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Request succesfuly submitted", null));
        } catch (Exception e) {
            this.session.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage("showUserMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Submitting request failed", null));
        }
    }

    public String searchCity() {
        this.session.beginTransaction();
        try {
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM city_line");
            query.addEntity(CityLine.class);
            allCityLines = (List<CityLine>) query.list();
            this.session.getTransaction().commit();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("showUserMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Couldn't load the city lines :(", null));
            this.session.getTransaction().rollback();
            e.printStackTrace();
        }
        return "searchCity";
    }

    public String searchInterCity() {
        return "searchInterCity";
    }

    public String lineDetails(Integer id) {
        if (null == id) {
            return null;
        }
        this.session.beginTransaction();
        try {
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM inter_city_line WHERE id = " + id);
            query.addEntity(InterCityLine.class);
            List<InterCityLine> lines = (List<InterCityLine>) query.list();
            this.interCityLine = lines.get(0);
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
            return null;
        }
        return "details";
    }

    public void makeReservation() {
        Reservations reservation = new Reservations();
        reservation.setInterCityLine(interCityLine);
        reservation.setUser(user);
        reservation.setNumberOfCards(1);
        reservation.setStatus(0);
        this.session.beginTransaction();
        try {
            this.session.save(reservation);
            this.session.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage("makeUserReservation", new FacesMessage(FacesMessage.SEVERITY_INFO, "Request succesfuly submitted", null));
        } catch (Exception e) {
            e.printStackTrace();
            this.session.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage("makeUserReservation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Couldn't submit reservation request", null));
        }
    }

    public void cancelReservation(Integer id) {
        if (null == id) {
            return;
        }
        Reservations  res = null;
        for (Reservations r : this.myReservations) {
            if (r.getId().intValue() == id.intValue()) {
                res = r;
                break;
            }
        }
        if (null == res) {
            return;
        }
        this.session.beginTransaction();
        try {
            this.session.delete(res);
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM reservations WHERE user_id = " + user.getId());
            query.addEntity(Reservations.class);
            this.myReservations = (List<Reservations>)query.list();
            this.filteredReservations = null;
            this.session.getTransaction().commit();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public String myReservations() {
        this.session.beginTransaction();
        try {
            SQLQuery query = this.session.createSQLQuery("SELECT * FROM reservations WHERE user_id = " + user.getId());
            query.addEntity(Reservations.class);
            myReservations = (List<Reservations>)query.list();
            this.session.getTransaction().commit();
        } catch(Exception e){
            this.session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
        return "myReservations";
    }

    @PostConstruct
    public void init() {
        session = NewHibernateUtil.getSessionFactory().openSession();
        user = new User();
        this.images = new ArrayList<>();
        images.add("bus.png");
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

    public List<Driver> getAvailableDrivers() {
        return availableDrivers;
    }

    public void setAvailableDrivers(List<Driver> availableDrivers) {
        this.availableDrivers = availableDrivers;
    }

    public Bus getCityLineBus() {
        return cityLineBus;
    }

    public void setCityLineBus(Bus cityLineBus) {
        this.cityLineBus = cityLineBus;
    }

    public List<Bus> getAvailableBuses() {
        return availableBuses;
    }

    public void setAvailableBuses(List<Bus> availableBuses) {
        this.availableBuses = availableBuses;
    }

    public String getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(String selectedBus) {
        this.selectedBus = selectedBus;
    }

    public String getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(String selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public List<BusCompanys> getCompanies() {
        return companies;
    }

    public void setCompanies(List<BusCompanys> companies) {
        this.companies = companies;
    }

    public String getStrSelectedCompany() {
        return strSelectedCompany;
    }

    public void setStrSelectedCompany(String strSelectedCompany) {
        this.strSelectedCompany = strSelectedCompany;
    }

    public BusCompanys getCurrCompany() {
        return currCompany;
    }

    public void setCurrCompany(BusCompanys currCompany) {
        this.currCompany = currCompany;
    }

    public UploadedFile getBusImage() {
        return busImage;
    }

    public void setBusImage(UploadedFile busImage) {
        this.busImage = busImage;
    }

    public List<CityLine> getAllCityLines() {
        return allCityLines;
    }

    public void setAllCityLines(List<CityLine> allCityLines) {
        this.allCityLines = allCityLines;
    }

    public List<CityLine> getFilteredCityLines() {
        return filteredCityLines;
    }

    public void setFilteredCityLines(List<CityLine> filteredCityLines) {
        this.filteredCityLines = filteredCityLines;
    }

    public List<Reservations> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(List<Reservations> myReservations) {
        this.myReservations = myReservations;
    }

    public List<Reservations> getFilteredReservations() {
        return filteredReservations;
    }

    public void setFilteredReservations(List<Reservations> filteredReservations) {
        this.filteredReservations = filteredReservations;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    
}
