/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.util.ArrayList;

/**
 *
 * @author Diogo
 */
public class FlightBooking extends Booking {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private boolean roundTrip;
    private String departureDate;
    private String returnDate;
    private ArrayList<Customer> passengers;
    private double airfare;
}
