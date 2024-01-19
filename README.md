# javaSqlExecutor

Sql Executor

Build status: [![CI](https://github.com/bostjans/javaSqlExecutor/actions/workflows/ci.yml/badge.svg)](https://github.com/bostjans/javaSqlExecutor/actions/workflows/ci.yml) .. in GitHub TBD;


## How to build Artifact

### Local

```
mvn clean package -P mariadb
mvn package -P oracle
mvn depgraph:graph -DcreateImage=true
```

SCM: commit & push

To upload artifact to GitHub:
`mvn github-release:release`

`./deploy.sh`


# Related

* https://sourceforge.net/projects/sqlline/
  * https://sqlline.sourceforge.net/
* https://github.com/julianhyde/sqlline
* https://github.com/xo/usql


# Reference

* /