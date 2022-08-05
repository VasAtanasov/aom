#!/usr/bin/env bash

echo "$(date +"%T"): ========AutoHouse Service Environment Variables========="
echo "$(date +"%T"): PROFILE=${SPRING_PROFILES_ACTIVE}"
echo "$(date +"%T"): DB_HOST=${DB_HOST}"
echo "$(date +"%T"): DB_PORT=${DB_PORT}"
echo "$(date +"%T"): DB_NAME=${DB_NAME}"
echo "$(date +"%T"): DB_USER=${DB_USER}"
echo "$(date +"%T"): JVM_MEMORY=${JVM_MEMORY}"
echo "$(date +"%T"): APP_HOME=${APP_HOME}"
echo "$(date +"%T"): ========================================================"

COMMAND="java $JAVA_OPTS  \
  -jar $JVM_MEMORY app.jar > log/autohouse.log  2>&1 &"

$COMMAND