ALTER TABLE users RENAME COLUMN creationtimestamp TO creationdatetime;

ALTER TABLE todos RENAME COLUMN duedatetimestamp TO duedatedatetime;
ALTER TABLE todos RENAME COLUMN effectivedatetimestamp TO effectivedatetime;
ALTER TABLE todos RENAME COLUMN streakstarttimestamp TO streakstartdatetime;
ALTER TABLE todos RENAME COLUMN creationtimestamp TO creationdatetime;

ALTER TABLE adventureevents RENAME COLUMN creationtimestamp TO creationdatetime;

ALTER TABLE todoevents RENAME COLUMN creationtimestamp TO creationdatetime;

ALTER TABLE actionevents RENAME COLUMN creationtimestamp TO creationdatetime;