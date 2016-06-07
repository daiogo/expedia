/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Serializable;

/**
 *
 * @author Diogo
 */
public class FlightSearch implements Serializable {
    private String origin;
    private String destination;
    private boolean roundTrip;
    private String departureDate;
    private String returnDate;
    private int numberOfPassengers;

    public FlightSearch(String origin, String destination, boolean roundTrip, String departureDate, String returnDate, int numberOfPassengers) {
        this.origin = origin;
        this.destination = destination;
        this.roundTrip = roundTrip;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean getRoundTrip(){
        return roundTrip;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
