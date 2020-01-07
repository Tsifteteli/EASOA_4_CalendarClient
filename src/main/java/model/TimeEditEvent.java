/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private String[] columns;

    public void getId(String id) {
        this.id = id;
    }

    public String setId() {
        return id;
    }

    public void getStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String setStartdate() {
        return startdate;
    }

    public void getStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String setStarttime() {
        return starttime;
    }

    public void getEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String setEnddate() {
        return enddate;
    }

    public void getEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String setEndtime() {
        return endtime;
    }

    public void getColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] setColumns() {
        return columns;
    }

}
