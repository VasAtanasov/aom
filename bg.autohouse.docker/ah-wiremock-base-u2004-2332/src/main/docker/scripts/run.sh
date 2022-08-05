#!/bin/bash

COMMAND="java $JAVA_OPTS -jar $WIREMOCK_SERVER_JAR_FILE"

# Set `java` command if needed
if [ "$1" = "" -o "${1:0:1}" = "-" ]; then
  set -- $COMMAND "$@"
fi

exec "$@"