#!/bin/sh

# This is sample script for ..
#
#PATH_PROG=~/bin/sqlExecutor
PATH_PROG=.
if [ -f "target/sqlExecutor.jar" ]; then
  PATH_PROG=./target
fi
PATH_LOG=$PATH_PROG/log

if [ -f "$PATH_PROG/../src/main/resources/logging.properties" ]; then
  echo "File exists: $PATH_PROG/../src/main/resources/logging.properties"
  LOG_CONF=$PATH_PROG/../src/main/resources/logging.properties
fi
if [ -f "$PATH_PROG/properties/logging.properties" ]; then
  echo "File exists: $PATH_PROG/properties/logging.properties"
  LOG_CONF=$PATH_PROG/properties/logging.properties
fi

VMparam="-Xms264m -Xmx286m"
VMparam="-Dfile.encoding=UTF-8 $VMparam"
if [ -z "$LOG_CONF" ]; then
  echo "LOG_CONF is set to the empty string!"
else
  echo "LOG_CONF > value: $LOG_CONF"
  VMparam="-Djava.util.logging.config.file=$LOG_CONF $VMparam"
fi

#java -jar $PATH_PROG/sqlExecutor.jar -h

#java $VMparam -jar $PATH_PROG/sqlExecutor.jar \
#    -d jdbc:mysql://fqdn_db:3306/lenkoDb \
#    -u lenkoTrApp -p somePsw \
#    -s "select 1;"
java $VMparam -jar $PATH_PROG/sqlExecutor.jar \
    -d $1 \
    -u $2 -p $3 \
    -s $4
exitStatus=$?
if [ $exitStatus -eq 0 ]
then
  echo ".. process done;"
else
  echo ".. process failed! -> Continue > to > Terminate .."
  exit $?
fi

exit 0
