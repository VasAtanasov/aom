# Ubuntu with JDK 11 base image

FROM ${docker.registry}/ah-ubuntu-base:${docker.image.base.tag}

## Install OpenJDK Java
RUN apt-get update \
    && apt-get -y install software-properties-common \
    && apt-get -y install apt-transport-https ca-certificates \
    && wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | apt-key add - \
    && add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/ \
    && apt-get -y purge software-properties-common \
    && apt-get update \
    && apt-get -y install --no-install-recommends adoptopenjdk-11-hotspot \
    && apt-get -y autoremove \
    && apt-get -y autoclean \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/lib/jvm/adoptopenjdk-11-hotspot-amd64
ENV PATH "$JAVA_HOME/bin/:$PATH"