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
 * @author robin
 */
public class TimeEditCalendar {

    private String[] columnheaders;
    private ReservationInfo info;
    private TimeEditEvent[] reservations;

    
    public void setColumnheaders(String[] columnheaders) {
        this.columnheaders = columnheaders;
    }

    public String[] getColumnheaders() {
        return columnheaders;
    }

    public void setInfo(ReservationInfo info) {
        this.info = info;
    }

    public ReservationInfo getInfo() {
        return info;
    }

    public void setReservations(TimeEditEvent[] reservations) {
        this.reservations = reservations;
    }

    public TimeEditEvent[] getReservations() {
        return reservations;
    }
}
