-- Drop tables if they exist
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS ideas;
DROP TABLE IF EXISTS voting_sessions;
DROP TABLE IF EXISTS users;

-- Create extension for sequential UUID generation (if required)
-- REPLACE 'pgcrypto' with the appropriate extension for your PostgreSQL version
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create users table
CREATE TABLE users (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);

-- Create voting_sessions table
CREATE TABLE voting_sessions (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL
);

-- Create ideas table
CREATE TABLE ideas (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  title VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  creator_id UUID NOT NULL,
  voting_session_id UUID NULL,
  FOREIGN KEY (creator_id) REFERENCES users(id),
  FOREIGN KEY (voting_session_id) REFERENCES voting_sessions(id)
);

-- Create votes table
CREATE TABLE votes (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  user_id UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  idea_id UUID NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (idea_id) REFERENCES ideas(id)
);

-- Indexes for efficient access
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_votes_user_id ON votes (user_id);
CREATE INDEX idx_ideas_creator_id ON ideas (creator_id);
CREATE INDEX idx_ideas_voting_session_id ON ideas (voting_session_id);
