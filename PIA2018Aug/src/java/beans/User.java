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
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="piabus"
    , uniqueConstraints = @UniqueConstraint(columnNames="username") 
)
public class User  implements java.io.Serializable {


     private Integer id;
     private Employment employment;
     private String firstname;
     private String lastname;
     private String username;
     private String pass;
     private String adress;
     private Date dob;
     private String phone;
     private String email;
     private Integer userType;
     private Set<Reservations> reservationses = new HashSet<Reservations>(0);
     private Set<MonthlyCards> monthlyCardses = new HashSet<MonthlyCards>(0);

    public User() {
    }

	
    public User(Employment employment, String firstname, String lastname, String username, String pass, String adress, String phone) {
        this.employment = employment;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.pass = pass;
        this.adress = adress;
        this.phone = phone;
    }
    public User(Employment employment, String firstname, String lastname, String username, String pass, String adress, Date dob, String phone, String email, Integer userType, Set<Reservations> reservationses, Set<MonthlyCards> monthlyCardses) {
       this.employment = employment;
       this.firstname = firstname;
       this.lastname = lastname;
       this.username = username;
       this.pass = pass;
       this.adress = adress;
       this.dob = dob;
       this.phone = phone;
       this.email = email;
       this.userType = userType;
       this.reservationses = reservationses;
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

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="emplotment_cat_id", nullable=false)
    public Employment getEmployment() {
        return this.employment;
    }
    
    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    
    @Column(name="firstname", nullable=false, length=50)
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    
    @Column(name="lastname", nullable=false, length=50)
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    
    @Column(name="username", unique=true, nullable=false, length=50)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="pass", nullable=false)
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }

    
    @Column(name="adress", nullable=false, length=50)
    public String getAdress() {
        return this.adress;
    }
    
    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="dob", length=10)
    public Date getDob() {
        return this.dob;
    }
    
    public void setDob(Date dob) {
        this.dob = dob;
    }

    
    @Column(name="phone", nullable=false, length=50)
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    @Column(name="email")
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="user_type")
    public Integer getUserType() {
        return this.userType;
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<Reservations> getReservationses() {
        return this.reservationses;
    }
    
    public void setReservationses(Set<Reservations> reservationses) {
        this.reservationses = reservationses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<MonthlyCards> getMonthlyCardses() {
        return this.monthlyCardses;
    }
    
    public void setMonthlyCardses(Set<MonthlyCards> monthlyCardses) {
        this.monthlyCardses = monthlyCardses;
    }




}


