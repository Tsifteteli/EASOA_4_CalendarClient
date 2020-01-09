/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Caroline
 */
public class CanvasEvent {

    private String context_code;
    private String title;
    private String description;
    private String start_at;
    private String end_at;
    private String location_name;
    private String location_address;

    public void setContextCode(String context_code) {
        this.context_code = context_code;
    }

    public String getContextCode() {
        return context_code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

   public String getStartAt() {
      return start_at;
   }

   public void setStartAt(String start_at) {
      this.start_at = start_at;
   }

   public String getEndAt() {
      return end_at;
   }

   public void setEndAt(String end_at) {
      this.end_at = end_at;
   }

    public void setLocationName(String location_name) {
        this.location_name = location_name;
    }

    public String getLocationName() {
        return location_name;
    }

    public void setLocationAddress(String location_address) {
        this.location_address = location_address;
    }

    public String getLocationAddress() {
        return location_address;
    }
}
