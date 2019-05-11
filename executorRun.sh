#!/bin/sh

# This ..
#

java -jar ./dist/sqlExecutor/sqlExecutor.jar -h

#java -jar ./dist/sqlExecutor/sqlExecutor.jar -d jdbc:mysql://localhost:3306/lenkodbtr -u lenkotrApp -p lenkoTrApp \
#    -s $1

java -jar ./dist/sqlExecutor/sqlExecutor.jar -d jdbc:mysql://localhost:3306/lenkoTrDb -u lenkoTrApp -p lenkoTrApp \
    -s "insert into CURR_TICKER_HIST (ID_CTI, ID_CPA, TS_VALID_UNIX, TS_VALID, HIGH, LOW, RATE, MA1H, MA3H, WMA1H, WMA3H, BBELTH, BBELTL, RSI)  select ID_CTI, ID_CPA, TS_VALID_UNIX, TS_VALID, HIGH, LOW, RATE, MA1H, MA3H, WMA1H, WMA3H, BBELTH, BBELTL, RSI from CURR_TICKER where ((TS_VALID_UNIX > UNIX_TIMESTAMP('2018-02-01 00:00')) and (TS_VALID_UNIX < UNIX_TIMESTAMP('2019-01-01 00:00')))  order by ID_CTI asc;"

echo That_s it.

exit 0
