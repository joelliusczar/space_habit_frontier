ALTER TABLE users ALTER COLUMN creationtimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + creationtimestamp * interval '1 second';

ALTER TABLE todos ALTER COLUMN duedatetimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + duedatetimestamp * interval '1 second';
ALTER TABLE todos ALTER COLUMN effectivedatetimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + effectivedatetimestamp * interval '1 second';
ALTER TABLE todos ALTER COLUMN streakstarttimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + streakstarttimestamp * interval '1 second';
ALTER TABLE todos ALTER COLUMN creationtimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + creationtimestamp * interval '1 second';

ALTER TABLE adventureevents ALTER COLUMN creationtimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + creationtimestamp * interval '1 second';

ALTER TABLE todoevents ALTER COLUMN creationtimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + creationtimestamp * interval '1 second';

ALTER TABLE actionevents ALTER COLUMN creationtimestamp TYPE TIMESTAMPTZ USING TIMESTAMPTZ 'epoch' + creationtimestamp * interval '1 second';
