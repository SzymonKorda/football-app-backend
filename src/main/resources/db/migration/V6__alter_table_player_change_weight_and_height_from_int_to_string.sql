ALTER TABLE player
DROP
COLUMN height;

ALTER TABLE player
DROP
COLUMN weight;

ALTER TABLE player
    ADD height VARCHAR(255);

ALTER TABLE player
    ADD weight VARCHAR(255);