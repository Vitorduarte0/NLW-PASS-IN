package rocketseat.com.passin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.com.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String> {
}
