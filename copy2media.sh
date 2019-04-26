#!/bin/sh

# This batch cp all required files from project
# ... to folder from which the actual distrubution can be made.
#

rm -f -r dist
mkdir dist
cd dist

mkdir sqlExecutor
#mkdir sqlExecutor/lib
cp -r ../target/*.jar ./sqlExecutor/
cp -r ../executorRun.sh ./sqlExecutor/
#cp -r ../target/lib/*.jar ./sqlExecutor/lib/
rm ./sqlExecutor/lib/junit*.*
rm ./sqlExecutor/lib/hamcrest*.*


# .. zip ..
rm -f dist.zip
zip -9 -o -r dist.zip sqlExecutor

cd ..

#scp media/dist.zip lenko11@sweb01:/home/lenko11/install/
#scp -P 10122 media/dist.zip lenko11@stupica.com:/home/lenko11/install/

echo That_s it.

exit 0
