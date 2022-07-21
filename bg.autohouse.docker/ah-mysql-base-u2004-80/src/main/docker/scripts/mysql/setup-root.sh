#!/bin/bash -eu

FIRST_RUN_MARKER=/var/lib/mysql/first-run.txt

if [[ ! -f "$FIRST_RUN_MARKER" ]]; then
    echo "$(date +"%T"): first run setup"

    # prepare the folders
    mkdir -p /var/lib/mysql/
    chown -R mysql:mysql /var/lib/mysql/

    # just to make sure init works
    rm -rf /var/lib/mysql/*
    #
    mkdir -p /var/run/mysqld
    chown -R mysql:mysql /var/run/mysqld

    echo "$(date +"%T"): Initializing the empty data folder"
    # init the empty folder and make sure there is no root password
    mysqld --initialize-insecure --user=mysql
    service mysql stop
fi

echo "$(date +"%T") >>> MySQL: START"
service mysql start

function checkMysqlRunning() {
  if mysqladmin ping -h"localhost" --silent; then
    echo "success"
  else
    echo "failure"
  fi
}

function waitForMysql() {
  local totalAttempts=10;
  local attempts=0;
  while [ $attempts -lt $totalAttempts ]; do
    if mysqladmin ping -h"localhost" --silent; then
      return 0
    fi
    attempts=$((attempts+1))
    echo "Waiting for MySql to start! Attempt: ${attempts}/${totalAttempts}"
    sleep 1
  done
  echo "ERROR: MySQL service FAILED to start within ${totalAttempts} seconds"
  return 1
}

waitForMysql
waitForMysqlRes=$?
if [ $waitForMysqlRes -ne 0 ]; then
  echo "$(date +"%T") <<< MySQL: FAILED to start"
  exit 1
fi

echo "$(date +"%T") <<< MySQL: STARTED"
netstat -tulpn

if [[ ! -f "$FIRST_RUN_MARKER" ]]; then
    echo "$(date +"%T") >>> Setup root user: START"
    # prepare the root user access
    ROOT_USER=root
    # Set root password
    mysql -hlocalhost --user=$ROOT_USER -e "SET PASSWORD = '$DB_ROOT_PASSWORD'"
    mysql -hlocalhost --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e  "CREATE USER '$ROOT_USER'@'%' IDENTIFIED BY '$DB_ROOT_PASSWORD'"
    mysql -hlocalhost --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "grant all PRIVILEGES on *.* to '$ROOT_USER'@'%' WITH GRANT OPTION"
    echo "$(date +"%T") <<< Setup root user: END"

    touch $FIRST_RUN_MARKER
fi

echo "$(date +"%T"): ROOT setup finished!"

