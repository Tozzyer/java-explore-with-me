DROP TABLE IF EXISTS compilation_events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS participation_requests CASCADE;

CREATE TABLE users (
                       id              BIGSERIAL PRIMARY KEY,
                       username        VARCHAR(255) NOT NULL UNIQUE,
                       email           VARCHAR(255) NOT NULL UNIQUE,
                       password_hash   VARCHAR(255) NOT NULL,
                       created_at      TIMESTAMP DEFAULT now(),
                       updated_at      TIMESTAMP DEFAULT now()
);

CREATE TABLE participation_requests (
                                        id              BIGSERIAL PRIMARY KEY,
                                        event_id        BIGINT NOT NULL,
                                        requester_id    BIGINT NOT NULL,
                                        status          VARCHAR(50) DEFAULT 'PENDING',
                                        created_at      TIMESTAMP DEFAULT now(),

                                        CONSTRAINT fk_participation_event FOREIGN KEY (event_id) REFERENCES events(id),
                                        CONSTRAINT fk_participation_user FOREIGN KEY (requester_id) REFERENCES users(id)
);
