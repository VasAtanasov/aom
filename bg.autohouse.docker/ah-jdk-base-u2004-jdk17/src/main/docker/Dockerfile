# Ubuntu with JDK base image

FROM ${ID_DOCKER_REGISTRY}/ah-ubuntu-base:${BASE_IMAGE_TAG}

# Install OpenJDK Java
RUN apt-get update \
    && apt-get install --no-install-recommends -y openjdk-17-jdk \
    && apt-get -y autoremove \
    && apt-get -y autoclean \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64
ENV PATH "$JAVA_HOME/bin/:$PATH"