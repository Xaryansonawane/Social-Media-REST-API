SELECT
  (SELECT COUNT(*) FROM posts)    AS total_posts,
  (SELECT COUNT(*) FROM comments) AS total_comments,
  (SELECT COUNT(*) FROM likes)    AS total_likes,
  (SELECT COUNT(*) FROM follows)  AS total_follows,
  (SELECT COUNT(*) FROM messages) AS total_messages;