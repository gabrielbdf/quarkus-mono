package br.com.gabriel.travelorder;

import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import br.com.gabriel.flight.FlightResource;
import br.com.gabriel.hotel.HotelResource;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
public class TravelOrderResource {

    @Channel("quote-requests")
    Emitter<TravelOrderDTO> quoteRequestEmitter;

    @Inject
    FlightResource flightResource;

    @Inject
    HotelResource hotelResource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TravelOrderDTO> orders() {
        return TravelOrder
                .<TravelOrder>listAll()
                .stream()
                .map(item -> TravelOrderDTO
                        .of(item, flightResource.findByTravelOrderId(item.id),
                                hotelResource.findHotelByTravelOrderId(item.id)))
                .toList();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TravelOrder findById(@PathParam("id") Long id) {
        return TravelOrder.findById(id);
    }

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    public TravelOrder findByIdQuery(@QueryParam("id") Long id) {
        return TravelOrder.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public TravelOrder createOrder(TravelOrderDTO orderDTO) {
        TravelOrder order = new TravelOrder();
        order.id = null;
        order.persist();

        orderDTO.setTravelOrderId(order.id);

        this.quoteRequestEmitter.send(orderDTO).toCompletableFuture().join();

        return order;
    }

}
