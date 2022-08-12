INSERT INTO member (create_date,update_date,bio,email,image,password,username) VALUES ('2022-08-03 09:20:17.96225','2022-08-03 09:20:17.96225',NULL,'test01@realworld.com',NULL,'$2a$10$CaJDIhvBaDXBYnMXff2fIOEfI.zycN83WudeZz94tYWuuZUMBuGvS','test01');
INSERT INTO member (create_date,update_date,bio,email,image,password,username) VALUES ('2022-08-03 09:20:17.96225','2022-08-03 09:20:17.96225',NULL,'test02@realworld.com',NULL,'$2a$10$CaJDIhvBaDXBYnMXff2fIOEfI.zycN83WudeZz94tYWuuZUMBuGvS','test02');
INSERT INTO member (create_date,update_date,bio,email,image,password,username) VALUES ('2022-08-03 09:20:17.96225','2022-08-03 09:20:17.96225',NULL,'test03@realworld.com',NULL,'$2a$10$CaJDIhvBaDXBYnMXff2fIOEfI.zycN83WudeZz94tYWuuZUMBuGvS','test03');
INSERT INTO member (create_date,update_date,bio,email,image,password,username) VALUES ('2022-08-03 09:20:17.96225','2022-08-03 09:20:17.96225',NULL,'test04@realworld.com',NULL,'$2a$10$CaJDIhvBaDXBYnMXff2fIOEfI.zycN83WudeZz94tYWuuZUMBuGvS','test04');
INSERT INTO member (create_date,update_date,bio,email,image,password,username) VALUES ('2022-08-03 09:20:17.96225','2022-08-03 09:20:17.96225',NULL,'test05@realworld.com',NULL,'$2a$10$CaJDIhvBaDXBYnMXff2fIOEfI.zycN83WudeZz94tYWuuZUMBuGvS','test05');
INSERT INTO member (create_date,update_date,bio,email,image,password,username) VALUES ('2022-08-03 09:20:17.96225','2022-08-03 09:20:17.96225',NULL,'test06@realworld.com',NULL,'$2a$10$CaJDIhvBaDXBYnMXff2fIOEfI.zycN83WudeZz94tYWuuZUMBuGvS','test06');

INSERT INTO FOLLOW (follow_member_id,member_id) VALUES (2,1); -- 01이 02 follow
INSERT INTO FOLLOW (follow_member_id,member_id) VALUES (2,3); -- 03이 02 follow
INSERT INTO FOLLOW (follow_member_id,member_id) VALUES (2,4); -- 04이 02 follow
INSERT INTO FOLLOW (follow_member_id,member_id) VALUES (2,5); -- 05이 02 follow
INSERT INTO FOLLOW (follow_member_id,member_id) VALUES (2,6); -- 06이 02 follow

INSERT INTO FOLLOW (follow_member_id,member_id) VALUES (1,5); -- 05가 01 follow


INSERT INTO ARTICLE (create_date,update_date,body,description,slug,title,member_id)
VALUES ('2022-08-11 11:28:27.035704','2022-08-11 11:28:27.035704','You have to believe','Ever wonder how?','how-to-train-your-dragon','How to train your dragon',1);

INSERT INTO article_favorite (article_id,member_id) VALUES (1,1);
INSERT INTO article_favorite (article_id,member_id) VALUES (1,2);

INSERT INTO TAG (name) VALUES ('reactjs');
INSERT INTO TAG (name) VALUES ('angularjs');
INSERT INTO TAG (name) VALUES ('dragons');

INSERT INTO article_tag (article_id,tag_id) VALUES (1,1);
INSERT INTO article_tag (article_id,tag_id) VALUES (1,2);
INSERT INTO article_tag (article_id,tag_id) VALUES (1,3);

