package beans;
// Generated Aug 12, 2018 10:50:49 PM by Hibernate Tools 4.3.1


import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * InterCityLine generated by hbm2java
 */
@Entity
@Table(name="inter_city_line"
    ,catalog="piabus"
)
public class InterCityLine  implements java.io.Serializable {


     private Integer id;
     private Bus bus;
     private BusCompanys busCompanys;
     private Driver driver;
     private Date departure;
     private String firstStation;
     private Date arrival;
     private String lastStation;
     private String otherStations;
     private boolean isActive;
     private Set<Reservations> reservationses = new HashSet<Reservations>(0);

    public InterCityLine() {
    }

	
    public InterCityLine(Bus bus, BusCompanys busCompanys, Driver driver, Date departure, String firstStation, Date arrival, boolean isActive) {
        this.bus = bus;
        this.busCompanys = busCompanys;
        this.driver = driver;
        this.departure = departure;
        this.firstStation = firstStation;
        this.arrival = arrival;
        this.isActive = isActive;
    }
    public InterCityLine(Bus bus, BusCompanys busCompanys, Driver driver, Date departure, String firstStation, Date arrival, String lastStation, String otherStations, boolean isActive, Set<Reservations> reservationses) {
       this.bus = bus;
       this.busCompanys = busCompanys;
       this.driver = driver;
       this.departure = departure;
       this.firstStation = firstStation;
       this.arrival = arrival;
       this.lastStation = lastStation;
       this.otherStations = otherStations;
       this.isActive = isActive;
       this.reservationses = reservationses;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bus_id", nullable=false)
    public Bus getBus() {
        return this.bus;
    }
    
    public void setBus(Bus bus) {
        this.bus = bus;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bus_company_id", nullable=false)
    public BusCompanys getBusCompanys() {
        return this.busCompanys;
    }
    
    public void setBusCompanys(BusCompanys busCompanys) {
        this.busCompanys = busCompanys;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="driver_id", nullable=false)
    public Driver getDriver() {
        return this.driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="departure", nullable=false, length=19)
    public Date getDeparture() {
        return this.departure;
    }
    
    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    
    @Column(name="first_station", nullable=false, length=50)
    public String getFirstStation() {
        return this.firstStation;
    }
    
    public void setFirstStation(String firstStation) {
        this.firstStation = firstStation;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="arrival", nullable=false, length=19)
    public Date getArrival() {
        return this.arrival;
    }
    
    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    
    @Column(name="last_station", length=50)
    public String getLastStation() {
        return this.lastStation;
    }
    
    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

    
    @Column(name="other_stations", length=65535)
    public String getOtherStations() {
        return this.otherStations;
    }
    
    public void setOtherStations(String otherStations) {
        this.otherStations = otherStations;
    }

    
    @Column(name="is_active", nullable=false)
    public boolean isIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="interCityLine")
    public Set<Reservations> getReservationses() {
        return this.reservationses;
    }
    
    public void setReservationses(Set<Reservations> reservationses) {
        this.reservationses = reservationses;
    }




}


