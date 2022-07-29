FROM ${docker.registry}/ah-ubuntu-base:${docker.image.base.tag}

# install MySql and disable autostart. The run script will start it later.
ARG MYSQL_VERSION=8.0.30*
RUN apt-get update \
    && apt-get --no-install-recommends --allow-downgrades -y install \
       mysql-client=${MYSQL_VERSION} mysql-server=${MYSQL_VERSION} \
       perl perl-base \
       net-tools \
       sudo \
    && apt-get -y autoremove \
    && apt-get -y autoclean \
    && rm -rf /var/lib/apt/lists/*

ENV DB_HOST ${DB_HOST:-localhost}
ENV DB_PORT ${DB_PORT:-3306}
ENV DB_ROOT_PASSWORD ${DB_ROOT_PASSWORD:-root}

COPY assets/mysql/config/mysqld.cnf /etc/mysql/mysql.conf.d/
COPY scripts /scripts
COPY assets /assets

RUN mkdir -p /var/run/mysqld \
    && chown -R mysql:mysql /var/run/mysqld \
    && chown -R mysql:mysql /var/lib/mysql \
    && chmod -R 655 /etc/mysql/mysql.conf.d/ \
    && chmod a+x /scripts/mysql/*.sh \
    && echo "mysql user home (before): " && grep mysql /etc/passwd \
    && usermod -d /var/lib/mysql/ mysql \
    && echo "mysql user home (after): " && grep mysql /etc/passwd

EXPOSE 3306

HEALTHCHECK --interval=15s --retries=40 CMD /scripts/mysql/check-mysql-ready.sh
CMD /scripts/mysql/start-mysql.sh

