import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

public class GetInfo {
    public static void main(String[] args) {
    Airport vnukovo = Airport.getInstance();
    Date now = new Date();
    long hour = 3600000L;
    vnukovo.getTerminals().stream()
                                    .flatMap(terminal -> terminal.getFlights().stream()
                                    .filter(flight -> flight.getDate().getTime() - now.getTime() < 2 * hour & flight.getDate().getTime() - now.getTime() > 0))
                                    .collect(Collectors.toList())
                                    .forEach(flight -> System.out.println(new SimpleDateFormat("HH:mm").format(flight.getDate()) + " / " + flight.getCode()));
    }
}
