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
public class HotelBooking extends Booking implements Serializable {
    private String hotelId;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfRooms;
    private ArrayList<Customer> guests;

    public HotelBooking(String hotelId, String checkInDate, String checkOutDate, int numberOfRooms, ArrayList<Customer> guests) {
        this.bookingId = UUID.randomUUID().toString();
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfRooms = numberOfRooms;
        this.guests = guests;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public ArrayList<Customer> getGuests() {
        return guests;
    }
    
    
}
