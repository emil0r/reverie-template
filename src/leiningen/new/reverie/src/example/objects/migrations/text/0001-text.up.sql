CREATE TABLE objects_text (
       id BIGSERIAL PRIMARY KEY,
       object_id BIGINT NOT NULL REFERENCES reverie_object(id),
       text TEXT NOT NULL DEFAULT '',
       css TEXT NOT NULL DEFAULT ''
);
