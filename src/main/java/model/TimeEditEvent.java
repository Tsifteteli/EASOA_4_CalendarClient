/*
2020-01-13
D0031N Enterprise Architecture och SOA
Examinationsuppgift 4, Schemahantering LTU - EA-modellering och Systemintegration
Caroline Blomgren, carbol-8@student.ltu.se
Robin Hellström, robhel-4@student.ltu.se
*/
package model;

/**
 *
 * @author Caroline
 */
public class TimeEditEvent {

    private String id;
    private String startdate;
    private String starttime;
    private String enddate;
    private String endtime;
    //Plats & Lokal ; Lärare ; Aktivitet ; Kurs/Program ; Campus ; Text ; Text ; Syfte ; Kurs/Program ; Kund ; Utrustning
    private String[] columns;

    
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getColumns() {
        return columns;
    }

}
