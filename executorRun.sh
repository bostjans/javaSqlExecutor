#!/bin/sh

# This ..
#

java -jar sqlExecutor.jar -s $1 -d jdbc:mysql://localhost:3306/lenkodb -u lenkoApp -p lenkoApp

echo That_s it.

exit 0
