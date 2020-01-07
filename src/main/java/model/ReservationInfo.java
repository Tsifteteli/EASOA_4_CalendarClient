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

    public void getReservationlimit(int reservationlimit) {
        this.reservationlimit = reservationlimit;
    }

    public int setReservationlimit() {
        return reservationlimit;
    }

    public void getReservationcount(int reservationcount) {
        this.reservationcount = reservationcount;
    }

    public int setReservationcount() {
        return reservationcount;
    }

}
