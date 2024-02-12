------Insert Member Data------
INSERT INTO dia_member
    (pk, nickname, image_url, github_id)
VALUES (1, 'jay','https://avatars.githubusercontent.com/u/30148662?v=4', 'jaeykweon');

INSERT INTO oauth_accounts
    (pk, member_pk, access_token)
VALUES (1, 1,'1');

------Insert Interview Question Data------
INSERT INTO interview_question_category
    (pk, kor_title, eng_title)
VALUES (1, '백엔드', 'backend');

INSERT INTO interview_question_category
    (pk, kor_title, eng_title)
VALUES (2, '프론트엔드', 'frontend');

INSERT INTO interview_question
    (pk, kor_title)
VALUES (1, 'HTTP와 HTTPS에 대해 설명해보세요');

INSERT INTO interview_question_category_mapping
    (pk, interview_question_category_pk, interview_question_pk)
VALUES (1, 1, 1);

INSERT INTO interview_question_voice
    (pk, file_path, gender, question_pk, subtitle)
VALUES (1, 'http와%20https.mp3', 'MALE', 1, 'HTTP와 HTTPS에 대해 설명해보세요');


