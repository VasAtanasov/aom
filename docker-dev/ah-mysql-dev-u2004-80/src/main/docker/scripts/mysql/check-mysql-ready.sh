#!/bin/bash

STARTED_MARKER_FILE=/var/run/mysqld/server-started.txt

if test -f "$STARTED_MARKER_FILE"; then
  if mysqladmin ping -h"localhost" --silent; then
      exit 0
  fi
fi

# leave with docker unhealthy code
exit 1
