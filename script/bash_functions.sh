#!/bin/bash

check_file_exists() {
    local file_path=$1
    if [ ! -f "$file_path" ]; then
        echo "파일을 찾을 수 없습니다: $file_path"
        exit 1
    fi
}
