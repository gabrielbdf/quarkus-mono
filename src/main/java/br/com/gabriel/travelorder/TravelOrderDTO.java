package br.com.gabriel.travelorder;

import br.com.gabriel.flight.Flight;
import br.com.gabriel.hotel.Hotel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelOrderDTO {

    private Long travelOrderId;
    private String fromAirport;
    private String toAirport;
    private Integer nights;

    public TravelOrderDTO() {

    }

    private TravelOrderDTO(Long travelOrderId, String fromAirport, String toAirport, Integer nights) {
        this.travelOrderId = travelOrderId;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.nights = nights;
    }

    public static TravelOrderDTO of(TravelOrder travelOrder, Flight flight, Hotel hotel) {
        return new TravelOrderDTO(travelOrder.id, flight.fromAirport, flight.toAirport, hotel.nights);
    }

    public static TravelOrderDTO of(Long travelOrderId, String fromAirport, String toAirport, Integer nights) {
        return new TravelOrderDTO(travelOrderId, fromAirport, toAirport, nights);
    }
}
