#!/bin/bash -eu

DB_NAME=$1
DB_USER=$2
DB_USER_PASS=$3

ROOT_USER=root
echo "$(date +"%T"): >>> Create DB: START, User: $DB_USER, DB: $DB_NAME"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "DROP DATABASE IF EXISTS \`$DB_NAME\`"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "CREATE DATABASE \`$DB_NAME\`"
echo "$(date +"%T"): << Create DB: END"
#
echo "$(date +"%T"): >>> Create USER: START"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "drop user if exists '$DB_USER'@'%'"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "flush privileges"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "CREATE USER '$DB_USER'@'%' IDENTIFIED BY '$DB_USER_PASS'"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "grant all on \`$DB_NAME\`.* to '$DB_USER'@'%'"
mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD -e "flush privileges"
echo "$(date +"%T"): <<< Create USER: END"

