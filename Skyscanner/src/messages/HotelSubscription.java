/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import interfaces.TravellerInterface;
import java.io.Serializable;

/**
 *
 * @author Diogo
 */
public class HotelSubscription implements Serializable {
    public enum SubscriptionType {PRICE_DROP, NEW_ITEM}
    private TravellerInterface subscriber;
    private String city;
    private int numberOfRooms;
    private String checkInDate;
    private String checkOutDate;
    private String notificationTimeSpan;
    private SubscriptionType type;

    public HotelSubscription(TravellerInterface subscriber, String city, int numberOfRooms, String checkInDate, String checkOutDate, String notificationTimeSpan, SubscriptionType type) {
        this.subscriber = subscriber;
        this.city = city;
        this.numberOfRooms = numberOfRooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.notificationTimeSpan = notificationTimeSpan;
        this.type = type;
    }

    public TravellerInterface getSubscriber() {
        return subscriber;
    }

    public String getCity() {
        return city;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getNotificationTimeSpan() {
        return notificationTimeSpan;
    }

    public SubscriptionType getType() {
        return type;
    }
    
    
    
}
