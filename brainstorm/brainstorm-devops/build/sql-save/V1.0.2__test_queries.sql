-- ******************************************************
-- PostgreSQL database migration script
-- Version: 3.0.0
-- Description: Test queries for verifying sample data
-- ******************************************************
-- Change Log: V3.0.0
-- Date: $(date +'%Y-%m-%d %H:%M:%S')
-- Author: Your Name
-- ******************************************************

-- Query 1: Retrieve all users
SELECT * FROM users;

-- Query 2: Retrieve all ideas
SELECT * FROM ideas;

-- Query 3: Retrieve all votes
SELECT * FROM votes;

-- Query 4: Retrieve all voting sessions
SELECT * FROM voting_sessions;

-- Query 5: Retrieve ideas with their corresponding votes
SELECT i.title, v.user_id
FROM ideas i
JOIN idea_votes iv ON i.id = iv.idea_id
JOIN votes v ON iv.vote_id = v.id;

-- Query 6: Retrieve the creator of each idea
SELECT i.title, u.first_name, u.last_name
FROM ideas i
JOIN users u ON i.creator_id = u.id;
