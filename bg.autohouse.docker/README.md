# Build instructions

## Build on local docker

```bash
mvn clean install -Ddocker.nocache=true
```

> **NOTE** The `mvn` command must be executed in the `bg.autohouse.docker` module folder

## Build specific image

```bash
mvn -f ${target-pom-dir} clean install -Ddocker.nocache=true
```

> **NOTE** that all dependent images must exit in local docker in the remote registry.
