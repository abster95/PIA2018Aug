/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Abi
 */
public class InterCityLine {
    private String busCompany;
    private String startTime;
    private String endTime;
    private String firstStation;
    private String lastStation;
    private String otherStations;

    public InterCityLine(String busCompany, String startTime, String endTime, String firstStation, String lastStation, String otherStations) {
        this.busCompany = busCompany;
        this.startTime = startTime;
        this.endTime = endTime;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.otherStations = otherStations;
    }
    
    /**
     * Creates a new instance of InterCityLine
     */
    public InterCityLine() {
    }

    public String getBusCompany() {
        return busCompany;
    }

    public void setBusCompany(String busCompany) {
        this.busCompany = busCompany;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(String firstStation) {
        this.firstStation = firstStation;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

    public String getOtherStations() {
        String tmp = otherStations;
        return tmp.replace('#', ' ');
    }

    public void setOtherStations(String otherStations) {
        this.otherStations = otherStations;
    }

    
}
