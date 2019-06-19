Insert into user (username,password,email,name,created_at) values ('test', '$2a$10$MYvuSObIrY5bEdsfzr6Qi.66mZAfXPiVFvcWqCweC93l2lLLoOGSe', 'test@mum.edu','test','2019-06-17');
Insert into user (username,password,email,name,created_at) values ('test1', '$2a$10$MYvuSObIrY5bEdsfzr6Qi.66mZAfXPiVFvcWqCweC93l2lLLoOGSe', 'test1@mum.edu','test1','2019-06-17');
Insert into user (username,password,email,name,created_at) values ('test2', '$2a$10$MYvuSObIrY5bEdsfzr6Qi.66mZAfXPiVFvcWqCweC93l2lLLoOGSe', 'test2@mum.edu','test2','2019-06-17');
Insert into user (username,password,email,name,created_at) values ('test3', '$2a$10$MYvuSObIrY5bEdsfzr6Qi.66mZAfXPiVFvcWqCweC93l2lLLoOGSe', 'test3@mum.edu','test3','2019-06-17');
Insert into user (username,password,email,name,created_at) values ('test4', '$2a$10$MYvuSObIrY5bEdsfzr6Qi.66mZAfXPiVFvcWqCweC93l2lLLoOGSe', 'test4@mum.edu','test4','2019-06-17');

ALTER TABLE user AUTO_INCREMENT = 1;


INSERT INTO `user_friends` (`user_id`, `friends_id`) VALUES ('2', '1');
INSERT INTO `user_friends` (`user_id`, `friends_id`) VALUES ('3', '1');
INSERT INTO `user_friends` (`user_id`, `friends_id`) VALUES ('4', '1');
INSERT INTO `user_friends` (`user_id`, `friends_id`) VALUES ('5', '1');
