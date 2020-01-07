/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
