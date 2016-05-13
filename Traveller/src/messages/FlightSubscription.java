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
public class FlightSubscription implements Serializable {
    public enum SubscriptionType {PRICE_DROP, NEW_ITEM}
    private TravellerInterface subscriber;
    private String origin;
    private String destination;
    private String departureDate;
    private String returnDate;
    private String notificationTimeSpan;
    private SubscriptionType type;

    public FlightSubscription(TravellerInterface subscriber, String origin, String destination, String departureDate, String returnDate, String notificationTimeSpan, SubscriptionType type) {
        this.subscriber = subscriber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.notificationTimeSpan = notificationTimeSpan;
        this.type = type;
    }

    public TravellerInterface getSubscriber() {
        return subscriber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getNotificationTimeSpan() {
        return notificationTimeSpan;
    }

    public SubscriptionType getType() {
        return type;
    }
    
    
    
}
