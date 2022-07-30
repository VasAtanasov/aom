FROM ${docker.registry}/ah-jdk-base:${docker.image.base.tag}

ENV WIREMOCK_VERSION 2.33.2
ENV WIREMOCK_SERVER_FILE_LOCATION "/var/wiremock/lib/wiremock-jre8-standalone.jar"
# grab wiremock standalone jar
RUN mkdir -p /var/wiremock/lib/ \
  && wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-jre8-standalone/$WIREMOCK_VERSION/wiremock-jre8-standalone-$WIREMOCK_VERSION.jar \
    -O $WIREMOCK_SERVER_FILE_LOCATION

COPY scripts /scripts

RUN chmod -R a+x /scripts/*.sh \
    && mkdir -p /runtime/logs \
    && chmod a+rw /runtime/logs

WORKDIR /home/wiremock

VOLUME /home/wiremock
EXPOSE 8080 8443

ENTRYPOINT [ "/bin/bash", "/scripts/run.sh" ]
