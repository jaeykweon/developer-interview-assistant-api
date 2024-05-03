#!/bin/bash

set -e

check_env() {
    source "./.env"
    server_env=$ENV
    local env=$1

    if [ "$server_env" != "${env^^}" ] && [ "$server_env" != "${env,,}" ]; then
        echo "입력한 환경과 서버의 환경이 다릅니다. 스크립트를 종료합니다."
        exit 1
    fi
}

# 비어있는 포트들 중 랜덤한 포트를 반환합니다
get_random_idle_port() {
    # todo : return "$port" 시 에러 발생
    local port
    while :
    do
        port=$(shuf -i 1024-65535 -n 1)
        if ! lsof -Pi :"$port" -sTCP:LISTEN -t >/dev/null; then
            echo "$port"
            break
        fi
    done
}

get_process_pid() {
    local input_port=$1
    # shellcheck disable=SC2046
    # shellcheck disable=SC2005
    echo $(lsof -nP -iTCP -sTCP:LISTEN | grep ":$input_port" | awk '{print $2}')
}

# 포트 번호와 함께 호출하면 해당 포트로 health check를 시도합니다
# 1초마다 한번씩 최대 10번
process_health_check() {
    # set +e 설정을 하지않으면 아래 curl 명령어 실패시 스크립트가 종료됌.
    set +e
    local port=$1
    local max_attempts=10
    local attempt=1

    while [ $attempt -le $max_attempts ]; do
        echo "$port 포트로 $attempt 회 health check 시도 중.."
        response=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:"$port"/api/v0/health-check)
        echo "response: $response"
        if [ "$response" -eq 200 ]; then
            echo "$port 포트로 health check 성공"
            set -e
            return 0  # 함수 종료
        fi
        attempt=$((attempt + 1))
        sleep 1
    done

    echo "$port 포트로 health check $max_attempts 번 시도했으나 200 응답을 받지 못했습니다. 스크립트를 종료합니다."
    exit 1
}

# java 프로세스를 찾아서 PID를 반환합니다. 여러개의 경우 여러개 모두 출력합니다
get_running_java_process() {
    echo $(ps aux | grep java | grep -v grep | awk '{print $2}')
}



# 1. 환경을 입력받되, env 파일의 환경과 다르면 종료합니다
environment="$1"
check_env "$environment"
echo "실행 환경: $environment"


# 2. 기존 프로세스 포트를 확인합니다
port_file="/etc/nginx/conf.d/host-urls.inc"
old_port=$(awk '/set \$dia_backend_port/ {print $3}' $port_file | awk '{sub(/;/,"")}1')
echo "기존 프로세스 포트: $old_port"
old_pid=$(get_process_pid "$old_port")
echo "기존 프로세스 pid: $old_pid"
process_health_check "$old_port"


# 3. 새 프로세스를 실행합니다
new_port=$(get_random_idle_port)
echo "새 프로세스 포트: $new_port"

nohup java -jar build/libs/dia-0.0.1-SNAPSHOT.jar \
--server.port="$new_port" \
--spring.profiles.active="$environment" \
> output.log 2>&1 &

echo "새 프로세스를 실행했습니다. 20초 후 부터 health check를 시작합니다."
sleep 20
process_health_check "$new_port"

new_pid=$(get_process_pid "$new_port")
echo "새 프로세스 pid: $new_pid"


# 4. nginx config를 변경하고 재시작합니다.
sudo sed -i "/set \$dia_backend_port /c\set \$dia_backend_port $new_port;" $port_file
sudo service nginx reload
# 이전 명령어의 종료 상태 확인
if [ $? -ne 0 ]; then
    echo "nginx 서비스를 재시작할 수 없습니다. 수동 확인이 필요합니다."
    exit 1
fi

sleep 1
echo "nginx로 요청을 보내 새 프로세스가 정상적으로 동작하는지 확인합니다."
process_health_check 80

# 5. 기존 프로세스 종료 및 java 프로세스 전체 확인
echo "기존 프로세스를 종료합니다."
kill -15 "$old_pid"

echo "모든 java 프로세스를 확인합니다."
echo $(get_running_java_process)

echo "스크립트가 종료되었습니다"

