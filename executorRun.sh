#!/bin/sh

# This ..
#

java -jar ./dist/sqlExecutor/sqlExecutor.jar -h

java -jar ./dist/sqlExecutor/sqlExecutor.jar -d jdbc:mysql://localhost:3306/lenkodbtr -u lenkotrApp -p lenko \
    -s $1

echo That_s it.

exit 0
