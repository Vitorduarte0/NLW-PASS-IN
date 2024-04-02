CREATE UNIQUE INDEX events_slug_key ON events(slug);
CREATE UNIQUE INDEX attendees_events_id_email_key ON attendees (id);
CREATE UNIQUE INDEX check_ins_attendee_id_key ON checkin (attendee_id);