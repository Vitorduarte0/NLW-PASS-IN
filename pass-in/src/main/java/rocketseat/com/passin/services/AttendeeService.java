package rocketseat.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.Checkin;
import rocketseat.com.passin.dto.attendee.AttendBadgeResponseDTO;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeDTO;
import rocketseat.com.passin.dto.attendee.AttendeeDetails;
import rocketseat.com.passin.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.passin.repository.AttendeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckinServices checkinServices;

    public List<Attendee> getAllAttendesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendesFromEvent(eventId);
        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<Checkin> checkin = this.checkinServices.getCheckin(attendee.getId());
            LocalDateTime checkedInAt = checkin.<LocalDateTime>map(Checkin::getCreatedAt).orElse(null);

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), null);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void registerAttendee(Attendee attendee) {
        this.attendeeRepository.save(attendee);
    }
    public void checInAttendee(String attendeeId) {
        Attendee attendee =  this.getAttendeeById(attendeeId);
        this.checkinServices.registerCheckIn(attendee);
    }
    public Attendee getAttendeeById(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).
                orElseThrow(() -> new AttendeeNotFoundException("Attend not found with ID" + attendeeId));
    }

    public AttendBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uri) {
        Attendee attendee = this.getAttendeeById(attendeeId);
        String url = uri.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();
        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), url, attendee.getEvent().getId());
        return new AttendBadgeResponseDTO(attendeeBadgeDTO);
    }

    public void verifyAttendSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }
}
