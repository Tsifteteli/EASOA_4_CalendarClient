/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.TimeEditCalendar;

/**
 *
 * @author Caroline
 */
public class ClientControler {

    //Grundsökvägen till webservicarna vi vill anropa
    private static final String TIME_EDIT_URI = "https://cloud.timeedit.net/ltu/"
            + "web/schedule1/ri.json?h=t&sid=3&p=20190902.x,20200906.x&objects=119838.28&ox=0&types=0&fe=0";
    private static final String CANVAS_URI = "https://ltu.instructure.com/api/v1/calendar_events.json";
    
    private void getTimeEditCalendar() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(ClientControler.TIME_EDIT_URI);
        
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        
        TimeEditCalendar timeEditCalendar = response.readEntity(TimeEditCalendar.class);
        List<TimeEditCalendar> listOfTimeEditCalendar = timeEditCalendar.getReservations();
        
    }
    
    public void setCanvasCalendar() {
        
    }
}
