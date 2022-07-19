#!/bin/bash -eu

STARTED_MARKER_FILE=/var/run/mysqld/server-started.txt
rm -f $STARTED_MARKER_FILE

rm -f /var/run/mysqld/mysqld.sock
rm -f /var/run/mysqld/mysqld.sock.lock
chown -R mysql:mysql /var/lib/mysql/

/scripts/mysql/setup-root.sh
/scripts/mysql/setup-prepare-db.sh

echo "$(date +"%T"): Base server started!"

touch $STARTED_MARKER_FILE

# And block
tail -f /dev/null