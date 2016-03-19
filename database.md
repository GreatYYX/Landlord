# player表

    -- Table: public.player

    -- DROP TABLE public.player;

    CREATE TABLE public.player
    (
      id integer NOT NULL DEFAULT nextval('player_id_seq'::regclass),
      username character varying(30) NOT NULL,
      password character varying(50) NOT NULL,
      salt character varying(20),
      nickname character varying(30) NOT NULL,
      photo character varying(30),
      lastloginip character varying(20),
      lastlogintime timestamp with time zone,
      gamecount integer DEFAULT 0,
      wincount integer DEFAULT 0,
      losecount integer DEFAULT 0,
      landlordcount integer DEFAULT 0,
      score integer DEFAULT 0,
      token character varying(40),
      role integer DEFAULT 10,
      CONSTRAINT player_pkey PRIMARY KEY (id),
      CONSTRAINT player_user_key UNIQUE (username)
    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE public.player
      OWNER TO landlord;