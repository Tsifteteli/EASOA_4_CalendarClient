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
public class ReservationInfo {

    private int reservationlimit;
    private int reservationcount;

    public void setReservationlimit(int reservationlimit) {
        this.reservationlimit = reservationlimit;
    }

    public int getReservationlimit() {
        return reservationlimit;
    }

    public void setReservationcount(int reservationcount) {
        this.reservationcount = reservationcount;
    }

    public int getReservationcount() {
        return reservationcount;
    }

}
