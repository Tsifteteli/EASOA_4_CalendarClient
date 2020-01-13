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
