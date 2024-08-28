-- CREATE SEQUENCE interview_practice_history_pk_seq;
--
-- ALTER TABLE interview_practice_history
-- ALTER COLUMN pk SET DEFAULT NEXTVAL('interview_practice_history_pk_seq');

------Member Data------
INSERT INTO dia_members
    (pk, nickname, image_url, oauth_id, oauth_provider)
VALUES (1001, 'jay','https://avatars.githubusercontent.com/u/30148662?v=4', 'jaeykweon', 'GITHUB');

INSERT INTO member_tokens
    (pk, access_token, user_agent, owner_pk, created_time)
VALUES (1001, 'ACCESS@jaeykweon@2021-08-01 00:00:00', 'chrome', 1001, '2021-08-01 00:00:00');

------면접 질문 Interview Question Data------
INSERT INTO interview_questions
    (pk, title)
VALUES (1001, '공통 문제 제목');

INSERT INTO interview_questions
    (pk, title)
VALUES (1002, '백엔드 문제 제목');

INSERT INTO interview_questions
(pk, title)
VALUES (1003, '프론트엔드 문제 제목');

------면접 질문 카테고리 Interview Question Category Data------
INSERT INTO interview_question_categories
    (pk, kor_title, eng_title)
VALUES (1001, '백엔드', 'backend');

INSERT INTO interview_question_categories
    (pk, kor_title, eng_title)
VALUES (1002, '프론트엔드', 'frontend');

------면접 질문 - 카테고리 매핑 Interview Question Category Mapping Data------
INSERT INTO interview_question_category_mappings
    (pk, category_pk, question_pk, created_time)
VALUES (1001, 1001, 1001, '2021-08-01 00:00:00');

INSERT INTO interview_question_category_mappings
(pk, category_pk, question_pk, created_time)
VALUES (1002, 1002, 1001, '2021-08-01 00:00:00');

INSERT INTO interview_question_category_mappings
(pk, category_pk, question_pk, created_time)
VALUES (1003, 1001, 1002, '2021-08-01 00:00:00');

INSERT INTO interview_question_category_mappings
(pk, category_pk, question_pk, created_time)
VALUES (1004, 1002, 1003, '2021-08-01 00:00:00');

-------면접 질문 음성 / Interview Question Voice Data-------
INSERT INTO interview_question_voices
    (pk, file_path, gender, question_pk, subtitle)
VALUES (1001, '공통 문제 음성 파일.mp3', 'MALE', 1001, '공통 문제 음성 자막');

INSERT INTO interview_question_voices
(pk, file_path, gender, question_pk, subtitle)
VALUES (1002, '백엔드 문제 음성 파일.mp3', 'MALE', 1002, '백엔드 문제 음성 자막');

INSERT INTO interview_question_voices
(pk, file_path, gender, question_pk, subtitle)
VALUES (1003, '프론트엔드 문제 음성 파일.mp3', 'MALE', 1003, '프론트엔드 문제 음성 자막');

-------면접 대본 / Interview Script Data-------
INSERT INTO interview_scripts
    (pk, owner_pk, question_pk, content)
VALUES
    (1001, 1001, 1001, 'HTTP는 HyperText Transfer Protocol의 약자로, 웹 서버와 클라이언트 간에 데이터를 주고받기 위한 통신 규약입니다. HTTPS는 HTTP에 데이터 암호화가 추가된 것으로, 보안이 강화된 프로토콜입니다.');

------Interview Practice History Data------
INSERT INTO interview_practice_histories
    (pk, owner_pk, question_pk, content, type, elapsed_time, file_path, star, created_time)
VALUES
    (1001, 1001, 1001, '첫번째 연습입니다. HTTP는 HyperText Transfer Protocol의 약자로, 웹 서버와 클라이언트 간에 데이터를 주고받기 위한 통신 규약입니다. HTTPS는 HTTP에 데이터 암호화가 추가된 것으로, 보안이 강화된 프로토콜입니다.', 'SINGLE', 60, null, false,'2021-08-01 00:00:00');

INSERT INTO interview_practice_histories
    (pk, owner_pk, question_pk, content, type, elapsed_time, file_path, star, created_time)
VALUES
    (1002, 1001, 1001, '두번째 연습입니다. HTTP는 HyperText Transfer Protocol의 약자로, 웹 서버와 클라이언트 간에 데이터를 주고받기 위한 통신 규약입니다. HTTPS는 HTTP에 데이터 암호화가 추가된 것으로, 보안이 강화된 프로토콜입니다.', 'SINGLE', 60, null, true,'2021-08-01 00:00:00');

INSERT INTO interview_question_bookmark_mappings
    (pk, owner_pk, question_pk, created_time)
VALUES
    (1001, 1001, 1001, '2021-08-01 00:00:00');

-- SELECT setval('interview_question_pk_seq', (SELECT MAX(id) FROM interview_practice_history));

-- ALTER SEQUENCE interview_practice_history_pk_seq START WITH (SELECT MAX(pk) + 1 FROM interview_practice_history);

