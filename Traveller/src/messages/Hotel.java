/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Diogo
 */
public class Hotel implements Serializable {
    private String hotelId;
    private String hotelName;
    private String city;
    private int availableRooms;
    private double pricePerNight;
    
    public Hotel(String hotelName, String city, int availableRooms, double pricePerNight) {
        this.hotelId = UUID.randomUUID().toString();
        this.hotelName = hotelName;
        this.city = city;
        this.availableRooms = availableRooms;
        this.pricePerNight = pricePerNight;
    }
    
    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
    
}
