package beans;
// Generated Aug 12, 2018 9:48:06 PM by Hibernate Tools 4.3.1



/**
 * BusPictures generated by hbm2java
 */
public class BusPictures  implements java.io.Serializable {


     private int id;
     private Bus bus;
     private String name;

    public BusPictures() {
    }

    public BusPictures(int id, Bus bus, String name) {
       this.id = id;
       this.bus = bus;
       this.name = name;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Bus getBus() {
        return this.bus;
    }
    
    public void setBus(Bus bus) {
        this.bus = bus;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


