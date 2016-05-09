/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Diogo
 */
public class FlightBooking extends Booking implements Serializable {
    private String departingFlightNumber;
    private String returningFlightNumber;
    private String departureDate;
    private String returnDate;
    private boolean roundTrip;
    private ArrayList<Customer> passengers;

    public FlightBooking(String departingFlightNumber, String returningFlightNumber, String departureDate, String returnDate, boolean roundTrip, ArrayList<Customer> passengers) {
        this.bookingId = UUID.randomUUID().toString();
        this.departingFlightNumber = departingFlightNumber;
        this.returningFlightNumber = returningFlightNumber;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.roundTrip = roundTrip;
        this.passengers = passengers;
    }

    public String getDepartingFlightNumber() {
        return departingFlightNumber;
    }

    public String getReturningFlightNumber() {
        return returningFlightNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public ArrayList<Customer> getPassengers() {
        return passengers;
    }
}
