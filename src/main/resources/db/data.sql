DELETE FROM review_photo;
DELETE FROM item_photo;
DELETE FROM file;
ALTER TABLE file ALTER COLUMN file_id RESTART WITH 1;
DELETE FROM favourite_item;
ALTER TABLE favourite_item ALTER COLUMN favourite_item_id RESTART WITH 1;
DELETE FROM review;
ALTER TABLE review ALTER COLUMN review_id RESTART WITH 1;
DELETE FROM item;
ALTER TABLE item ALTER COLUMN item_id RESTART WITH 1;
-- INSERT INTO item (created, modified, city, street1, street2, zipcode, categories, price, sold, spec, title, views, member_id)
-- VALUES ('2021-04-12T18:18:31.830821', '2021-04-12T18:18:31.830821', '대전광역시', '동구', '', '', 'PLANT', 3000, 'FALSE', '관리하기가 힘들어서요', '장미 팝니다', 0, 1);