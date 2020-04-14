import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

public class GetInfo {
    public static void main(String[] args) {
    Airport vnukovo = Airport.getInstance();
    LocalDateTime now = LocalDateTime.now();

    vnukovo.getTerminals().stream()
                                    .flatMap(terminal -> terminal.getFlights().stream()
                                    .filter(flight -> convertToLocalDateTime(flight.getDate()).isAfter(now) && convertToLocalDateTime(flight.getDate()).isBefore(now.plusHours(2)) && flight.getType().equals(Flight.Type.DEPARTURE)))
                                    .sorted(Comparator.comparing(flight -> convertToLocalDateTime(flight.getDate())))
                                    .collect(Collectors.toList())
                                    .forEach(flight -> System.out.println(new SimpleDateFormat("HH:mm").format(flight.getDate()) + " / " + flight.getCode()));
    }

    private static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
