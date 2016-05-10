/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyscanner;

import interfaces.SkyscannerInterface;
import interfaces.TravellerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import messages.Flight;
import messages.FlightBooking;
import messages.FlightSearch;
import messages.FlightSubscription;
import messages.Hotel;
import messages.HotelBooking;
import messages.HotelSearch;
import messages.HotelSubscription;

/**
 *
 * @author Diogo
 */
public class SkyscannerServant extends UnicastRemoteObject implements SkyscannerInterface {

    private AdminGui adminGui;
    private Database database;
    
    public SkyscannerServant() throws RemoteException {
        this.database = new Database();
        adminGui = new AdminGui(this);
        adminGui.setLocationRelativeTo(null);
        adminGui.setVisible(true);
        
        // Dummy data
        database.getFlights().add(new Flight("JJ2020", "TAM", "Curitiba", "São Paulo", "01/01/2016", "11:30am", "12:30pm", 61.50, 1));
        database.getFlights().add(new Flight("JJ2021", "TAM", "Curitiba", "Rio de Janeiro", "01/01/2016", "12:00pm", "13:20pm", 76.25, 100));
        database.getFlights().add(new Flight("JJ2022", "TAM", "Rio de Janeiro", "Curitiba", "07/01/2016", "12:00pm", "13:20pm", 76.25, 100));
        database.getFlights().add(new Flight("JJ2023", "TAM", "São Paulo", "Curitiba", "07/01/2016", "11:30am", "12:30pm", 61.50, 1));
        database.getFlights().add(new Flight("JJ2024", "TAM", "São Paulo", "Curitiba", "07/01/2016", "15:30am", "16:30pm", 61.50, 100));
        
        database.getHotels().add(new Hotel("Ibis", "Curitiba", 10, 150.00));
        database.getHotels().add(new Hotel("Ibis", "São Paulo", 1, 200.00));
        database.getHotels().add(new Hotel("Ibis", "Rio de Janeiro", 10, 250.00));
        database.getHotels().add(new Hotel("Hilton", "São Paulo", 10, 500.00));
        database.getHotels().add(new Hotel("Sheraton", "São Paulo", 150, 400.00));
        database.getHotels().add(new Hotel("Sheraton", "Rio de Janeiro", 150, 400.00));
    }

    @Override
    public void bookFlight(FlightBooking booking, TravellerInterface travellerInterface) throws RemoteException {
        database.getFlightBookings().add(booking);
        int index = 0;
        
        for (Flight flight : database.getFlights()) {
            if (flight.getFlightNumber().equals(booking.getDepartingFlightNumber())) {
                flight.setAvailableSeats(flight.getAvailableSeats() - booking.getPassengers().size());
                database.getFlights().set(index, flight);
            }
                
            if (booking.isRoundTrip() && flight.getFlightNumber().equals(booking.getReturningFlightNumber())) {
                flight.setAvailableSeats(flight.getAvailableSeats() - booking.getPassengers().size());
                database.getFlights().set(index, flight);
            }
            index++;
        }
        
        travellerInterface.displayFlightBookingConfirmation(booking);
    }

    @Override
    public void bookHotel(HotelBooking booking, TravellerInterface travellerInterface) throws RemoteException {
        database.getHotelBookings().add(booking);
        int index = 0;
        
        for (Hotel hotel : database.getHotels()) {
            if (hotel.getHotelId().equals(booking.getHotelId())) {
                hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getGuests().size());
                database.getHotels().set(index, hotel);
            }
            index++;
        }
        
        travellerInterface.displayHotelBookingConfirmation(booking);
    }

    @Override
    public void searchFlights(FlightSearch flightSearch, TravellerInterface travellerInterface) throws RemoteException {
        ArrayList<Flight> departingFlights = new ArrayList();
        ArrayList<Flight> returningFlights = new ArrayList();
        
        for (Flight flight : database.getFlights()) {
            if (flightSearch.getOrigin().equals(flight.getOrigin()) &&
                flightSearch.getDestination().equals(flight.getDestination()) &&
                flightSearch.getDepartureDate().equals(flight.getDepartureDate()) &&
                flightSearch.getNumberOfPassengers() <= flight.getAvailableSeats()
                ) {
                departingFlights.add(flight);
            }
            else if (flightSearch.isRoundTrip() &&
                     flightSearch.getDestination().equals(flight.getOrigin()) &&
                     flightSearch.getOrigin().equals(flight.getDestination()) &&
                     flightSearch.getReturnDate().equals(flight.getDepartureDate()) &&
                     flightSearch.getNumberOfPassengers() <= flight.getAvailableSeats()
                     ) {
                returningFlights.add(flight);
            }
        }
        
        travellerInterface.getQueriedFlights(departingFlights, returningFlights);
    }

    @Override
    public void searchHotels(HotelSearch hotelSearch, TravellerInterface travellerInterface) throws RemoteException {
        ArrayList<Hotel> hotels = new ArrayList();
        
        for (Hotel hotel : database.getHotels()) {
            if (hotelSearch.getCity().equals(hotel.getCity()) &&
                hotelSearch.getNumberOfRooms() <= hotel.getAvailableRooms()
                ) {
                hotels.add(hotel);
            }
        }
        
        travellerInterface.getQueriedHotels(hotels);
    }

//    @Override
//    public void subscribe(String subscribeTo, TravellerInterface travellerInterface) throws RemoteException {
//        travellerInterface.publish(subscribeTo);
//    }
    
    //        for (FlightSubscription subscriptionRecord : database.getFlightSubscriptions()) {
//            if (subscriptionRecord.) {
//                
//            }
//        }

    @Override
    public void subscribeToFlight(FlightSubscription subscription, TravellerInterface travellerInterface) throws RemoteException {
        database.getFlightSubscriptions().add(subscription);
    }

    @Override
    public void subscribeToHotel(HotelSubscription subscription, TravellerInterface travellerInterface) throws RemoteException {
        database.getHotelSubscriptions().add(subscription);
    }
    
    public Database getDatabase() {
        return database;
    }
    
    public void publishFlightChange(Flight flight) throws RemoteException {
        for (FlightSubscription subscriptionRecord : database.getFlightSubscriptions()) {
            if (subscriptionRecord.getOrigin().equals(flight.getOrigin()) &&
                subscriptionRecord.getDestination().equals(flight.getDestination())
                ) {
                subscriptionRecord.getSubscriber().displayFlightNotification(flight);
            }
        }
    }
    
    public void publishHotelChange(Hotel hotel) throws RemoteException {
        for (HotelSubscription subscriptionRecord : database.getHotelSubscriptions()) {
            if (subscriptionRecord.getCity().equals(hotel.getCity()) &&
                subscriptionRecord.getNumberOfRooms() <= hotel.getAvailableRooms()
                ) {
                subscriptionRecord.getSubscriber().displayHotelNotification(hotel);
            }
        }
    }

}
