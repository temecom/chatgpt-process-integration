-- Insert sample data into the users table
INSERT INTO users (id, created_at, updated_at, first_name, last_name, email, password)
VALUES
  ('11111111-1111-1111-1111-111111111111', '2023-05-01 09:00:00', '2023-05-01 09:00:00', 'John', 'Doe', 'john.doe@example.com', 'password123'),
  ('22222222-2222-2222-2222-222222222222', '2023-05-02 10:00:00', '2023-05-02 10:00:00', 'Jane', 'Smith', 'jane.smith@example.com', 'password456');
  -- Add more user records as needed

-- Insert sample data into the voting_sessions table
INSERT INTO voting_sessions (id, created_at, updated_at, name, description, start_time, end_time)
VALUES
  ('77777777-7777-7777-7777-777777777777', '2023-05-07 20:00:00', '2023-05-07 20:00:00', 'Session 1', 'This is the first voting session', '2023-05-10 10:00:00', '2023-05-11 10:00:00'),
  ('88888888-8888-8888-8888-888888888888', '2023-05-08 22:00:00', '2023-05-08 22:00:00', 'Session 2', 'This is the second voting session', '2023-05-12 10:00:00', '2023-05-13 10:00:00');
  -- Add more voting session records as needed

-- Insert sample data into the ideas table
INSERT INTO ideas (id, created_at, updated_at, title, description, creator_id, voting_session_id)
VALUES
  ('55555555-5555-5555-5555-555555555555', '2023-05-05 16:00:00', '2023-05-05 16:00:00', 'Idea 1', 'This is the first idea', '11111111-1111-1111-1111-111111111111', '77777777-7777-7777-7777-777777777777'),
  ('66666666-6666-6666-6666-666666666666', '2023-05-06 18:00:00', '2023-05-06 18:00:00', 'Idea 2', 'This is the second idea', '22222222-2222-2222-2222-222222222222', '88888888-8888-8888-8888-888888888888');
  -- Add more idea records as needed

-- Insert sample data into the votes table
INSERT INTO votes (id, user_id, created_at, updated_at, idea_id)
VALUES
  ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', '2023-05-03 12:00:00', '2023-05-03 12:00:00', '55555555-5555-5555-5555-555555555555'),
  ('44444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222', '2023-05-04 14:00:00', '2023-05-04 14:00:00', '66666666-6666-6666-6666-666666666666');
  -- Add more vote records as needed
