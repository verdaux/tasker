CREATE TABLE users ( 

    id         UUID        PRIMARY KEY DEFAULT gen_random_uuid(), 

    username   VARCHAR(50) NOT NULL UNIQUE, 

    email      VARCHAR(150) NOT NULL UNIQUE, 

    password   VARCHAR(255) NOT NULL, 

    role       VARCHAR(20)  NOT NULL DEFAULT 'USER', 

    created_at TIMESTAMPTZ  NOT NULL DEFAULT now() 

); 
 

CREATE INDEX idx_users_email ON users(email); 