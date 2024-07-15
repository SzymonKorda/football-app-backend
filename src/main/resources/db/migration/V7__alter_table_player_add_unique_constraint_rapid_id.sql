ALTER TABLE player
    ADD CONSTRAINT uc_player_rapidid UNIQUE (rapid_id);