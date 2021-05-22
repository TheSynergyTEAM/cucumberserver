DELETE FROM item;
ALTER TABLE item ALTER COLUMN item_id RESTART WITH 1;
INSERT INTO item (created, modified, city, street1, street2, zipcode, categories, price, sold, spec, title, views, member_id)
VALUES ('2021-04-12T18:18:31.830821', '2021-04-12T18:18:31.830821', '대전광역시', '동구', '', '', 'PLANT', 3000, 'FALSE', '관리하기가 힘들어서요', '장미 팝니다', 0, 1);
INSERT INTO item (created, modified, city, street1, street2, zipcode, categories, price, sold, spec, title, views, member_id)
VALUES ('2021-04-13T18:18:31.830821', '2021-04-13T18:18:31.830821', '서울특별시', '강남구', '', '', 'PLANT', 2000, 'FALSE', '예쁘게 키워주실 분', '채송화 팝니다', 0, 2);
INSERT INTO item (created, modified, city, street1, street2, zipcode, categories, price, sold, spec, title, views, member_id)
VALUES ('2021-04-14T18:18:31.830821', '2021-04-14T18:18:31.830821', '대구광역시', '달서구', '', '', 'KID', 5000, 'FALSE', '많이 커서 팔아요', '애기옷 팝니다', 0, 3);
INSERT INTO item (created, modified, city, street1, street2, zipcode, categories, price, sold, spec, title, views, member_id)
VALUES ('2021-04-15T18:18:31.830821', '2021-04-15T18:18:31.830821', '부산광역시', '서구', '', '', 'KID', 4000, 'FALSE', '쪽쪽이 싸게 처분해요', '쪽쪽이 팝니다', 0, 4);