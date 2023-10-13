package br.com.gabriel.travelorder;

import br.com.gabriel.flight.Flight;
import br.com.gabriel.hotel.Hotel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelOrderDTO {

    private String fromAirport;
    private String toAirport;
    private Integer nights;

    public TravelOrderDTO() {

    }

    private TravelOrderDTO(String fromAirport, String toAirport, Integer nights) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.nights = nights;
    }

    public static TravelOrderDTO of(Flight flight, Hotel hotel) {
        return new TravelOrderDTO(flight.fromAirport, flight.toAirport, hotel.nights);
    }

    public static TravelOrderDTO of(String fromAirport, String toAirport, Integer nights) {
        return new TravelOrderDTO(fromAirport, toAirport, nights);
    }
}
