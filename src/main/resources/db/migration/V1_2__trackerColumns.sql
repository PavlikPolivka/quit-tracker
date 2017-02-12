ALTER TABLE tracker ADD COLUMN tracker_item VARCHAR(255) NOT NULL;
ALTER TABLE tracker ADD COLUMN tracker_owner VARCHAR(255) NOT NULL;
ALTER TABLE tracker ADD COLUMN tracker_stop_date DATE NOT NULL;
ALTER TABLE tracker ADD COLUMN tracker_created_date DATE NOT NULL;