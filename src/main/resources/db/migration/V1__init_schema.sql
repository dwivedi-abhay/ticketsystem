CREATE TABLE booking (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         show_id BIGINT NOT NULL,
                         status VARCHAR(32) NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         expires_at TIMESTAMP NOT NULL
);

CREATE TABLE booking_seat (
                              id BIGSERIAL PRIMARY KEY,
                              booking_id BIGINT NOT NULL,
                              seat_id BIGINT NOT NULL
);

CREATE TABLE payment (
                         id BIGSERIAL PRIMARY KEY,
                         booking_id BIGINT NOT NULL,
                         status VARCHAR(32) NOT NULL,
                         provider VARCHAR(64) NOT NULL,
                         provider_ref VARCHAR(128) NOT NULL UNIQUE,
                         amount BIGINT NOT NULL,
                         created_at TIMESTAMP NOT NULL
);

CREATE TABLE confirmed_booked_seat (
                                       id BIGSERIAL PRIMARY KEY,
                                       show_id BIGINT NOT NULL,
                                       seat_id BIGINT NOT NULL,
                                       booking_id BIGINT NOT NULL,
                                       CONSTRAINT uk_show_seat UNIQUE (show_id, seat_id)
);
