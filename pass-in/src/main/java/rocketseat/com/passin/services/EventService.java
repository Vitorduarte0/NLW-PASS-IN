package rocketseat.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.dto.event.EventDatilDTO;
import rocketseat.com.passin.dto.event.EventIdDTO;
import rocketseat.com.passin.dto.event.EventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.repository.AttendeeRepository;
import rocketseat.com.passin.repository.EventRepository;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String id) {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(id);
        return  new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventRequestDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(eventRequestDTO.title());
        newEvent.setDetails(eventRequestDTO.details());
        newEvent.setMaximumAttendees(eventRequestDTO.maximumAttendees());
        newEvent.setSlug(createSlug(eventRequestDTO.title()));
        System.out.println(newEvent.getSlug());

        this.eventRepository.save(newEvent);
        EventIdDTO eventIdDTO = new EventIdDTO(newEvent.getId());
        return eventIdDTO;
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized
                .replaceAll("[\\p{ASCII}]", "")  //TODO VER UMA FORMA DE RETIRAR ACENTUAÇÃO, VIDEO DA KEPPER FICOU NO MINUTO 53,49
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "")
                .toLowerCase();
    }

}