#!/usr/bin/env sh
set -e
DIR="$(dirname "$0")"
java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
