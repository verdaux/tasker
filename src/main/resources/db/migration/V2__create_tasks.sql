CREATE TABLE tasks ( 

    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(), 

    title       VARCHAR(255) NOT NULL, 

    description TEXT, 

    status      VARCHAR(20)  NOT NULL DEFAULT 'TODO', 

    priority    VARCHAR(10)  NOT NULL DEFAULT 'MEDIUM', 

    due_date    DATE, 

    user_id     UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE, 

    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(), 

    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now() 

); 

 

CREATE INDEX idx_tasks_user_id ON tasks(user_id); 

CREATE INDEX idx_tasks_status  ON tasks(status); 

 

-- Automatically bump updated_at on any row update 

CREATE OR REPLACE FUNCTION set_updated_at() 

RETURNS TRIGGER LANGUAGE plpgsql AS $ 

BEGIN 

    NEW.updated_at = now(); 

    RETURN NEW; 

END; 

$; 

 

CREATE TRIGGER trg_tasks_updated_at 

    BEFORE UPDATE ON tasks 

    FOR EACH ROW EXECUTE FUNCTION set_updated_at(); 