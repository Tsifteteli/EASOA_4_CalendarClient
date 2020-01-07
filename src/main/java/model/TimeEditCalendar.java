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

    public void getColumnheaders(String[] columnheaders) {
        this.columnheaders = columnheaders;
    }

    public String[] setColumnheaders() {
        return columnheaders;
    }

    public void getInfo(ReservationInfo info) {
        this.info = info;
    }

    public ReservationInfo setInfo() {
        return info;
    }

    public void getReservations(TimeEditEvent[] reservations) {
        this.reservations = reservations;
    }

    public TimeEditEvent[] setReservations() {
        return reservations;
    }
}
