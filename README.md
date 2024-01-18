# javaSqlExecutor

Sql Executor

Build status: [![CI](https://github.com/bostjans/javaSqlExecutor/actions/workflows/ci.yml/badge.svg)](https://github.com/bostjans/javaSqlExecutor/actions/workflows/ci.yml) .. in GitHub TBD;


## How to build Artifact

### Local

`mvn clean package -P mariadb`

`mvn clean package -P oracle`

`mvn depgraph:graph -DcreateImage=true`

SCM: commit & push

`mvn github-release:release`

`./deploy.sh`


# Related

* https://sourceforge.net/projects/sqlline/
  * https://sqlline.sourceforge.net/
* https://github.com/julianhyde/sqlline
* https://github.com/xo/usql


# Reference

* /