package beans;
// Generated Aug 12, 2018 9:48:06 PM by Hibernate Tools 4.3.1



/**
 * MonthlyCards generated by hbm2java
 */
public class MonthlyCards  implements java.io.Serializable {


     private Integer id;
     private CityLine cityLine;
     private User user;

    public MonthlyCards() {
    }

    public MonthlyCards(CityLine cityLine, User user) {
       this.cityLine = cityLine;
       this.user = user;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public CityLine getCityLine() {
        return this.cityLine;
    }
    
    public void setCityLine(CityLine cityLine) {
        this.cityLine = cityLine;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}

