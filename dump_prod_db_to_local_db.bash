#!/bin/bash

#프로덕션 DB를 로컬 DB로 덮어씌우기 위한 스크립트입니다.
#현재 로컬 DB를 삭제하고 프로덕션 DB를 덤프하여 로컬 DB로 복원합니다.

echo "프로덕션 데이터베이스를 로컬 데이터베이스로 덮어 씌웁니다."

echo "명령어 실행 오류 발생시 즉시 종료합니다."
set -e

DUMP_SOURCE_FILE="dump.sql"
ENV_FILE="./.env"

echo "프로덕션 환경의 데이터베이스를 로컬 환경으로 덮어 씌웁니다."
source $ENV_FILE

echo "프로덕션 데이터베이스를 덤프합니다."
PGPASSWORD=$PROD_PG_PASSWORD pg_dump \
-h "$PROD_PG_HOST" \
-p 5432 \
-U "$PROD_PG_USER" \
-d "$PROD_PG_DB_NAME" \
> $DUMP_SOURCE_FILE

echo "로컬 데이터베이스($LOCAL_PG_DB_NAME)를 삭제하기 위해 기존 세션을 종료합니다."
PGPASSWORD=$LOCAL_PG_PASSWORD psql \
-U "$LOCAL_PG_USER" \
-c "SELECT pg_terminate_backend(pg_stat_activity.pid) \
FROM pg_stat_activity \
WHERE pg_stat_activity.datname = '$LOCAL_PG_DB_NAME';"

echo "로컬 데이터베이스($LOCAL_PG_DB_NAME)를 삭제합니다."
PGPASSWORD=$LOCAL_PG_PASSWORD psql \
-U "$LOCAL_PG_USER" \
-c "DROP DATABASE $LOCAL_PG_DB_NAME;"

echo "로컬 데이터베이스($LOCAL_PG_DB_NAME)를 생성합니다."
PGPASSWORD=$LOCAL_PG_PASSWORD psql \
-U "$LOCAL_PG_USER" \
-c "CREATE DATABASE $LOCAL_PG_DB_NAME;"

echo "로컬 데이터베이스($LOCAL_PG_DB_NAME)를 복원합니다."
PGPASSWORD=$LOCAL_PG_PASSWORD psql \
-U "$LOCAL_PG_USER" \
"$LOCAL_PG_DB_NAME" < $DUMP_SOURCE_FILE

echo "$LOCAL_PG_USER 계정에 권한을 부여합니다."
PGPASSWORD=$LOCAL_PG_PASSWORD psql \
-U "$LOCAL_PG_USER" \
-c "ALTER DATABASE $LOCAL_PG_DB_NAME OWNER TO $LOCAL_PG_USER;"

echo "프로덕션 데이터베이스 -> 로컬 데이터베이스($LOCAL_PG_DB_NAME) 복원이 완료되었습니다."
