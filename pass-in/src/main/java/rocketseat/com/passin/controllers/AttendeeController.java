package rocketseat.com.passin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rocketseat.com.passin.dto.attendee.AttendBadgeResponseDTO;
import rocketseat.com.passin.services.AttendeeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendees")
public class AttendeeController {
    private final AttendeeService attendeeService;
    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uri) {
        AttendBadgeResponseDTO attendeeBadgeDTO = this.attendeeService.getAttendeeBadge(attendeeId, uri);
        return ResponseEntity.ok(attendeeBadgeDTO);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckinIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        this.attendeeService.checInAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();
        return ResponseEntity.created(uri).build();
    }
}
