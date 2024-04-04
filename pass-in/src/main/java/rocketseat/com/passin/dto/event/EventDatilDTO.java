package rocketseat.com.passin.dto.event;

public record EventDatilDTO(
        String id,
        String title,
        String detail,
        String slug,
        Integer maximumAttendees,
        Integer attendeesAmount
) {
}
