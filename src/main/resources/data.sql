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