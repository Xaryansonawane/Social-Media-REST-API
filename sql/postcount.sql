SET SQL_SAFE_UPDATES = 0;

-- UPDATE like_count and comment_count on posts to stay in sync
UPDATE posts SET like_count = (SELECT COUNT(*) FROM likes WHERE likes.post_id = posts.id);
UPDATE posts SET comment_count = (SELECT COUNT(*) FROM comments WHERE comments.post_id = posts.id);

-- UPDATE message_count on users
UPDATE users SET message_count = (
    SELECT COUNT(*) FROM messages
    WHERE messages.sender_id = users.id OR messages.receiver_id = users.id
);

SET SQL_SAFE_UPDATES = 1;