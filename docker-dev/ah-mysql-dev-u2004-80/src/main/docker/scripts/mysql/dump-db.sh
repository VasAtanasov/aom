#!/bin/bash

EXPORT_PATH=$1
DB_NAME=$2

FILE_NAME_SQL=${EXPORT_PATH}/${DB_NAME}.sql
FILE_NAME_ZIP=${EXPORT_PATH}/${DB_NAME}.sql.zip

echo "$(date +"%T"): >>> DUMP ${DB_NAME}: START"

mkdir -p ${EXPORT_PATH}
rm -f ${FILE_NAME_SQL}
rm -f ${FILE_NAME_ZIP}

mysqldump --host=${DB_HOST} --port=${DB_PORT} -u root --password=$DB_ROOT_PASSWORD --triggers --routines ${DB_NAME} |
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/'                |
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*PROCEDURE/PROCEDURE/'  |
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*FUNCTION/FUNCTION/'    |
  sed -e 's/DEFINER[ ]*=[ ]*[^*]*TRIGGER/TRIGGER/'      > ${FILE_NAME_SQL}

cd ${EXPORT_PATH}
zip ${FILE_NAME_ZIP} ${DB_NAME}.sql
rm -f ${FILE_NAME_SQL}

echo "$(date +"%T"): Created file: "
ls -la ${FILE_NAME_ZIP}

echo "$(date +"%T"): <<< DUMP ${DB_NAME}: END"
