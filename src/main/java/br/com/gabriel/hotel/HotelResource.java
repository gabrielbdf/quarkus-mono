package br.com.gabriel.hotel;

import java.util.List;

import javax.print.attribute.standard.Media;

import jakarta.enterprise.inject.spi.ProducerFactory;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("hotel")
public class HotelResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hotel> hotels() {
        return Hotel.listAll();
    }

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findById(@QueryParam("id") Long id) {
        return Hotel.findById(id);
    }

    @GET
    @Path("findHotelByTravelOrderId")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findHotelByTravelOrderId(@QueryParam("id") Long travelOrderId) {
        return Hotel.findHotelByTravelOrderId(travelOrderId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Hotel createHotel(Hotel hotel) {
        hotel.id = null;
        hotel.persist();
        return hotel;
    }

}
