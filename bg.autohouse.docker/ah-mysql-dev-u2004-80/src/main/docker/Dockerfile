FROM ${docker.registry}/ah-mysql-base:${docker.image.base.tag}

# MySql
ENV MYSQL_HOST ''
ENV MYSQL_PORT ''
ENV MYSQL_DB ''
ENV MYSQL_USER ''
ENV MYSQL_PASSWORD ''
ENV MYSQL_ROOT_PASSWORD ''
ENV MYSQL_CONNECTION_OPTS ''

ENV DB_HOST ${DB_HOST:-localhost}
ENV DB_PORT ${DB_PORT:-3306}
ENV DB_NAME ${DB_NAME:-db_ah_dev}
ENV DB_USER ${DB_USER:-ah_dev}
ENV DB_PASSWORD ${DB_PASSWORD:-ah_dev}
ENV DB_ROOT_PASSWORD ${DB_ROOT_PASSWORD:-root}
ENV DB_CONNECTION_OPTS ''

COPY scripts /scripts
COPY assets /assets

RUN chmod a+rx /scripts/mysql/*.sh && chmod -R a+r /assets/mysql/

EXPOSE 3306

HEALTHCHECK --interval=15s --retries=40 CMD /scripts/mysql/check-mysql-ready.sh
CMD /scripts/mysql/start-mysql.sh

