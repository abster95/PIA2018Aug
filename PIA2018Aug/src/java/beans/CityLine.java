package beans;
// Generated Aug 12, 2018 10:50:49 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CityLine generated by hbm2java
 */
@Entity
@Table(name="city_line"
    ,catalog="piabus"
)
public class CityLine  implements java.io.Serializable {


     private Integer id;
     private Bus bus;
     private String firstStation;
     private String otherStations;
     private int lastStation;
     private String departureTimes;
     private Set<MonthlyCards> monthlyCardses = new HashSet<MonthlyCards>(0);

    public CityLine() {
    }

	
    public CityLine(Bus bus, String firstStation, String otherStations, int lastStation, String departureTimes) {
        this.bus = bus;
        this.firstStation = firstStation;
        this.otherStations = otherStations;
        this.lastStation = lastStation;
        this.departureTimes = departureTimes;
    }
    public CityLine(Bus bus, String firstStation, String otherStations, int lastStation, String departureTimes, Set<MonthlyCards> monthlyCardses) {
       this.bus = bus;
       this.firstStation = firstStation;
       this.otherStations = otherStations;
       this.lastStation = lastStation;
       this.departureTimes = departureTimes;
       this.monthlyCardses = monthlyCardses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="bus_id", nullable=false)
    public Bus getBus() {
        return this.bus;
    }
    
    public void setBus(Bus bus) {
        this.bus = bus;
    }

    
    @Column(name="first_station", nullable=false, length=50)
    public String getFirstStation() {
        return this.firstStation;
    }
    
    public void setFirstStation(String firstStation) {
        this.firstStation = firstStation;
    }

    
    @Column(name="other_stations", nullable=false, length=65535)
    public String getOtherStations() {
        return this.otherStations;
    }
    
    public void setOtherStations(String otherStations) {
        this.otherStations = otherStations;
    }

    
    @Column(name="last_station", nullable=false)
    public int getLastStation() {
        return this.lastStation;
    }
    
    public void setLastStation(int lastStation) {
        this.lastStation = lastStation;
    }

    
    @Column(name="departure_times", nullable=false, length=65535)
    public String getDepartureTimes() {
        return this.departureTimes;
    }
    
    public void setDepartureTimes(String departureTimes) {
        this.departureTimes = departureTimes;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="cityLine")
    public Set<MonthlyCards> getMonthlyCardses() {
        return this.monthlyCardses;
    }
    
    public void setMonthlyCardses(Set<MonthlyCards> monthlyCardses) {
        this.monthlyCardses = monthlyCardses;
    }




}


