FROM ${docker.registry}/ah-jdk-base:${docker.image.base.tag}

RUN apt-get update && apt-get install supervisor -y

ENV APP_HOME /runtime
ENV LOG_DIR $APP_HOME/log
ENV CONFIG_DIR $APP_HOME/config

RUN mkdir -p $APP_HOME \
    && mkdir -p $LOG_DIR \
    && mkdir -p $CONFIG_DIR

VOLUME $APP_HOME/log
VOLUME $APP_HOME/config

WORKDIR $APP_HOME

RUN chmod -R a+rwx $APP_HOME

# JVM
ENV JVM_MEMORY '-Xms1g -Xmx8g'
ENV JAVA_OPTS=""

ENV DB_HOST ${DB_HOST:-localhost}
ENV DB_PORT ${DB_PORT:-3306}
ENV DB_NAME ${DB_NAME:-db_ah_dev}
ENV DB_USER ${DB_USER:-ah_dev}
ENV DB_PASSWORD ${DB_PASSWORD:-ah_dev}
ENV DDL_STRATEGY ${DDL_STRATEGY:-validate}

ENV LOG_FILE $LOG_DIR/autohouse.log

COPY assets/supervisord.conf /etc/supervisor/conf.d/autohouse.conf

COPY scripts /scripts
RUN chmod a+x /scripts/*.sh \
    && cp /scripts/startautohouse.sh $APP_HOME/startautohouse.sh

COPY autohouse-server.jar app.jar

ENV SPRING_PROFILES_ACTIVE=${ACTIVE_PROFILES:-default}

EXPOSE 8080

CMD ["supervisord", "-n"]

