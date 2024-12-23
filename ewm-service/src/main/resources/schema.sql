CREATE TABLE IF NOT EXISTS categories (
    CATEGORY_ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    NAME VARCHAR(128) NOT NULL,
    CONSTRAINT CATEGORIES_PK PRIMARY KEY (CATEGORY_ID),
    CONSTRAINT CATEGORY_NAME_UNIQUE UNIQUE (NAME)
    );

CREATE TABLE IF NOT EXISTS users (
 email VARCHAR(255) UNIQUE NOT NULL,
 user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
 name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations (
    compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS LOCATIONS
(
    LOCATION_ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    LAT   NUMERIC(10,6) NOT NULL,
    LON   NUMERIC(10,6) NOT NULL,
    CONSTRAINT LOCATIONS_PK
    PRIMARY KEY (LOCATION_ID)
    );

CREATE TABLE IF NOT EXISTS events (
    annotation VARCHAR(5000) NOT NULL ,
    category_id BIGINT NOT NULL,
    description VARCHAR(7000) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    paid BOOLEAN NOT NULL,
    title VARCHAR(255) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    initiator_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    participant_limit BIGINT NOT NULL,
    published_on TIMESTAMP,
    request_moderation BOOLEAN NOT NULL,
    state VARCHAR(64) NOT NULL,

    CONSTRAINT EVENTS_TITLE_UNIQUE
        UNIQUE (title),
    CONSTRAINT EVENTS_CATEGORY_ID_FK_CATEGORY_CATEGORY_ID
        FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORIES (CATEGORY_ID) ON DELETE RESTRICT,
    CONSTRAINT EVENTS_LOCATION_ID_FK_LOCATIONS_LOCATION_ID
        FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS (LOCATION_ID) ON DELETE CASCADE,
    CONSTRAINT EVENTS_INITIATOR_ID_FK_USERS_USER_ID
        FOREIGN KEY (INITIATOR_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS COMPILATIONS_EVENTS (
    COMPILATION_ID     BIGINT   NOT NULL,
    EVENT_ID  BIGINT   NOT NULL,
    PRIMARY KEY (COMPILATION_ID, EVENT_ID),
    FOREIGN KEY (COMPILATION_ID) REFERENCES COMPILATIONS (COMPILATION_ID) ON DELETE CASCADE,
    FOREIGN KEY (EVENT_ID) REFERENCES EVENTS (EVENT_ID) ON DELETE CASCADE

    );

CREATE TABLE IF NOT EXISTS requests (
                                        created timestamp not null,
                                        event_id BIGINT NOT NUll,
                                        request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                        requester_id BIGINT NOT NULL,
                                        status VARCHAR(100) not null,
    CONSTRAINT REQUESTS_UNIQUE_EVENT_ID_REQUESTER_ID
    UNIQUE (EVENT_ID, REQUESTER_ID),
    CONSTRAINT REQUESTS_EVENT_ID_FK_EVENTS_EVENT_ID
    FOREIGN KEY (EVENT_ID) REFERENCES EVENTS (EVENT_ID) ON DELETE CASCADE,
    CONSTRAINT REQUESTS_REQUESTER_ID_FK_USERS_USER_ID
    FOREIGN KEY (REQUESTER_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS LIKES_EVENTS
(
    EVENT_ID BIGINT NOT NULL,
    USER_ID BIGINT NOT NULL,
    CONSTRAINT LIKES_EVENTS_PK
    PRIMARY KEY (EVENT_ID, USER_ID),
    CONSTRAINT LIKES_EVENTS_EVENT_ID_FK_EVENTS_EVENT_ID
    FOREIGN KEY (EVENT_ID) REFERENCES EVENTS (EVENT_ID) ON DELETE CASCADE,
    CONSTRAINT LIKES_EVENTS_USER_ID_FK_USERS_USER_ID
    FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS LIKES_LOCATIONS
(
    LOCATION_ID BIGINT NOT NULL,
    USER_ID BIGINT NOT NULL,
    CONSTRAINT LIKES_PK
    PRIMARY KEY (LOCATION_ID, USER_ID),
    CONSTRAINT LIKES_EVENT_ID_FK_EVENTS_EVENT_ID
    FOREIGN KEY (LOCATION_ID) REFERENCES EVENTS (EVENT_ID) ON DELETE CASCADE,
    CONSTRAINT LIKES_EVENT_USER_ID_FK_USERS_USER_ID
    FOREIGN KEY (USER_ID) REFERENCES EVENTS (EVENT_ID) ON DELETE CASCADE
    );






