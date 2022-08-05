#!/bin/bash

COMMAND="java $JAVA_OPTS -jar $WIREMOCK_SERVER_JAR_FILE"

# Set `java` command if needed
if [ "$1" = "" -o "${1:0:1}" = "-" ]; then
  set -- $COMMAND "$@"
fi

# allow the container to be started with `-e uid=`
if [ "$UID" != "" ]; then
  # Change the ownership of /home/wiremock to $uid
  chown -R $UID:$UID $WIREMOKE_HOME

  set -- gosu $UID:$UID "$@"
fi

exec "$@"