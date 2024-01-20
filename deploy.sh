#!/bin/sh

# This batch cp all required files from project
# .. to (some) local dir.
#

# If local install exists ..
if [ -d "$HOME/bin/java/sqlExecutor" ]; then
  echo "Dir. exists: $HOME/bin/java/sqlExecutor"
  rm -fr $HOME/bin/java/sqlExecutor/lib
  cp target/*.jar $HOME/bin/java/sqlExecutor/
  cp -r target/lib $HOME/bin/java/sqlExecutor/
  cp executorRun.sh $HOME/bin/java/sqlExecutor/
fi

echo "That_s it."

exit 0
