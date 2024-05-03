#!/bin/bash

set -e

echo "git pull"
git pull

echo "./gradlew clean bootjar"
./gradlew clean bootjar
