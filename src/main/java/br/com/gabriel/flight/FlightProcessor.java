package br.com.gabriel.flight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.gabriel.hotel.Hotel;
import br.com.gabriel.hotel.HotelResource;
import br.com.gabriel.travelorder.TravelOrderDTO;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FlightProcessor {

    @Inject
    FlightResource flightResource;

    @Inject
    HotelResource hotelResource;

    JsonMapper mapper = new JsonMapper();

    @Incoming("requests")
    @Outgoing("quotes")
    @Blocking
    @Transactional
    public TravelOrderDTO process(TravelOrderDTO orderDTO) throws InterruptedException {
        // simulate some hard working task

        this.request();

        Flight flight = new Flight();
        flight.travelOrderId = orderDTO.getTravelOrderId();
        flight.fromAirport = orderDTO.getFromAirport();
        flight.toAirport = orderDTO.getToAirport();
        flightResource.createFlight(flight);

        Hotel hotel = new Hotel();
        hotel.travelOrderId = orderDTO.getTravelOrderId();
        hotel.nights = orderDTO.getNights();
        hotelResource.createHotel(hotel);

        return orderDTO;
    }

    private void request() {

        try {
            // Create a URL object with the target URL
            URL url = new URL("https://api.agify.io/?name=gomes");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the HTTP request method to GET
            connection.setRequestMethod("GET");

            // Get the response code (e.g., 200 for a successful request)
            int responseCode = connection.getResponseCode();

            // Read the response data
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                TreeMap<String, String> map = mapper.readValue(response.toString(), TreeMap.class);

                // Print the response
                System.out.println(map);
            } else {
                System.out.println("HTTP GET request failed with response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
