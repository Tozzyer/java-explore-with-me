DROP TABLE IF EXISTS compilation_events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS participation_requests CASCADE;

CREATE TABLE IF NOT EXISTS participation_requests (
                                                      id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                                      event_id     BIGINT NOT NULL,
                                                      requester_id BIGINT NOT NULL,
                                                      status       VARCHAR(100) NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fk_participation_event FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_participation_user FOREIGN KEY (requester_id) REFERENCES users(id)
    );
