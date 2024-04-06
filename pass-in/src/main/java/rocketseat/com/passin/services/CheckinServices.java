package rocketseat.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.Checkin;
import rocketseat.com.passin.domain.checkin.exceptions.CheckinAlreadyExistsException;
import rocketseat.com.passin.repository.CheckinRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckinServices {
    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckinExist(attendee.getId());

        Checkin newCheckin = new Checkin();
        newCheckin.setAttendee(attendee);
        newCheckin.setCreatedAt(LocalDateTime.now());

        this.checkinRepository.save(newCheckin);
    }

    private void verifyCheckinExist(String attendId) {
        Optional<Checkin> isCheckdIn = getCheckin(attendId);
        if (isCheckdIn.isPresent()) throw new CheckinAlreadyExistsException("Ateendee already checked in");
    }

    public Optional<Checkin> getCheckin(String attendId) {
        return this.checkinRepository.findByAttendeeId(attendId);
    }
}
