#!/bin/sh

# This ..
#

java -jar ./dist/sqlExecutor/sqlExecutor.jar -h

java -jar ./dist/sqlExecutor/sqlExecutor.jar -d jdbc:mysql://localhost:3306/lenkodbtr -u lenkotrApp -p lenko \
    -s $1
exitStatus=$?
if [ $exitStatus -eq 0 ]
then
  echo ".. process done;"
else
  echo ".. process failed! -> Continue > to > Terminate .."
  exit $?
fi

exit 0
