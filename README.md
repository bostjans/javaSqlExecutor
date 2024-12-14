# javaSqlExecutor

SqlExecutor

.. it is just a simple Java program to run SQL command via JDBC.

Build status: [![CI](https://github.com/bostjans/javaSqlExecutor/actions/workflows/ci.yml/badge.svg)](https://github.com/bostjans/javaSqlExecutor/actions/workflows/ci.yml) .. in GitHub TBD;


## How to build Artifact

### Local

DEVelop in "snapshot" version.

Then:
`mvn release:prepare`

Then:
`mvn release:perform`
`mvn release:perform -P mysql` .. for specific DB type;

Then > make build(-s) for GitHub Release:
* +
```
cd target/checkout/
mvn clean package -P mysql
mvn clean package -P oracle
mvn clean package -P mariadb
```
  * files will reside in: `target/checkout/build/`

To upload artifact to GitHub: `mvn github-release:release`

To increment version(s) - if required (usually note required):

* `mvn release:update-versions`

To build some documentation:

* `mvn depgraph:graph -DcreateImage=true`

SCM: commit & push

.. and (local) deploy:
`mvn clean package -P mysql,mariadb,oracle && ./deploy.sh`


# Related

* https://sourceforge.net/projects/sqlline/
  * https://sqlline.sourceforge.net/
* https://github.com/julianhyde/sqlline
* https://github.com/SergeyLebidko/SQLExecutor
* https://github.com/moliniao999/sqlexecutor
* https://github.com/pmintz/sqlexecutor
* https://github.com/xo/usql


# Reference

* /