package rocketseat.com.passin.dto.event;

import lombok.Getter;
import lombok.Setter;
import rocketseat.com.passin.domain.event.Event;
@Getter
public class EventResponseDTO {
    EventDatilDTO eventDatilDTO;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.eventDatilDTO = new EventDatilDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), numberOfAttendees);
    }
}
