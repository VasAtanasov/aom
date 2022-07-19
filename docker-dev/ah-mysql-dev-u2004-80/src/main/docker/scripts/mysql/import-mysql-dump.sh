#!/bin/bash -eu

DB_FILE=$1
DB_NAME=$2
DB_USER=$3
DB_USER_PASS=$4

ROOT_USER=root

source /scripts/mysql/create-mysql-db.sh ${DB_NAME} ${DB_USER} ${DB_USER_PASS}

echo "$(date +"%T"): >>> Import DUMP: START"

if [[ ! -f $DB_FILE ]]; then
  echo "ERROR: DB dump file not found: $DB_FILE"
  exit 1
fi

# unzip > clean up definer > import
unzip -p $DB_FILE                                       |
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/'                | # replace with '*/' anything starting with 'DEFINER' and ending with '*/'
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*PROCEDURE/PROCEDURE/'  | # replace with 'PROCEDURE' anything starting with 'DEFINER' and ending with 'PROCEDURE'
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*FUNCTION/FUNCTION/'    | # replace with 'FUNCTION' anything starting with 'DEFINER' and ending with 'FUNCTION'
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*TRIGGER/TRIGGER/'      | # replace with 'TRIGGER' anything starting with 'DEFINER' and ending with 'TRIGGER'
  mysql --host=$DB_HOST --port=$DB_PORT --user=$ROOT_USER --password=$DB_ROOT_PASSWORD $DB_NAME

echo "$(date +"%T"): <<< Import DUMP: END"
