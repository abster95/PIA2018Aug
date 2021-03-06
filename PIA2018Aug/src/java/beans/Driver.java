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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Driver generated by hbm2java
 */
@Entity
@Table(name = "driver",
         catalog = "piabus"
)
public class Driver implements java.io.Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private Date dob;
    private Date startedDriving;
    private Set<InterCityLine> interCityLines = new HashSet<InterCityLine>(0);

    public Driver() {
    }

    public Driver(String firstname, String lastname, Date dob, Date startedDriving) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.startedDriving = startedDriving;
    }

    public Driver(String firstname, String lastname, Date dob, Date startedDriving, Set<InterCityLine> interCityLines) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.startedDriving = startedDriving;
        this.interCityLines = interCityLines;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "firstname", nullable = false, length = 50)
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", nullable = false, length = 50)
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dob", nullable = false, length = 10)
    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "started_driving", nullable = false, length = 10)
    public Date getStartedDriving() {
        return this.startedDriving;
    }

    public void setStartedDriving(Date startedDriving) {
        this.startedDriving = startedDriving;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "driver")
    public Set<InterCityLine> getInterCityLines() {
        return this.interCityLines;
    }

    public void setInterCityLines(Set<InterCityLine> interCityLines) {
        this.interCityLines = interCityLines;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

}
