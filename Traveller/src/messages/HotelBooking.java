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
public class HotelBooking extends Booking {
    private String hotelId;
    private String hotelName;
    private String city;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfRooms;
    private ArrayList<Customer> guests;
}
