FROM ${docker.registry}/ah-jdk-base:${docker.image.base.tag}

ARG WIREMOCK_VERSION=2.33.2
ARG WIREMOK_JAR=wiremock-jre8-standalone-$WIREMOCK_VERSION.jar

ENV WIREMOCK_SERVER_LIB_LOCATION "/var/wiremock/lib"
ENV WIREMOCK_SERVER_EXTENSIONS_LOCATION "/var/wiremock/extensions"
ENV WIREMOCK_SERVER_JAR_FILE "$WIREMOCK_SERVER_LIB_LOCATION/$WIREMOK_JAR"
ENV WIREMOCK_HTTP_PORT ${WIREMOCK_HTTP_PORT:-8080}
ENV WIREMOCK_HTTPS_PORT ${WIREMOCK_HTTPS_PORT:-8443}
ENV WIREMOKE_HOME /home/wiremock

COPY scripts /scripts
COPY assets /assets

RUN mkdir -p $WIREMOCK_SERVER_LIB_LOCATION/ \
        && cp /assets/$WIREMOK_JAR $WIREMOCK_SERVER_LIB_LOCATION/

RUN chmod -R a+x /scripts/*.sh \
    && mkdir -p /runtime/logs \
    && chmod a+rw /runtime/logs

WORKDIR $WIREMOKE_HOME

VOLUME $WIREMOKE_HOME

EXPOSE $WIREMOCK_HTTP_PORT $WIREMOCK_HTTPS_PORT

ENTRYPOINT ["/scripts/run.sh"]
