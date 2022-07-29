FROM ${docker.registry}/ah-jdk-base:${docker.image.base.tag}

EXPOSE 8080

#Set app home folder
ENV APP_HOME /app

#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
ENV JAVA_OPTS=""

#Create base app folder
RUN mkdir $APP_HOME

#Create folder to save configuration files
RUN mkdir $APP_HOME/config

#Create folder with application logs
RUN mkdir $APP_HOME/log

VOLUME $APP_HOME/log
VOLUME $APP_HOME/config

WORKDIR $APP_HOME

RUN chmod -R a+rwx $APP_HOME

# JVM
ENV JVM_MEMORY '-Xms1g -Xmx8g'

ENV DB_HOST ${DB_HOST:-localhost}
ENV DB_PORT ''
ENV DB_NAME ${DB_NAME:-db_ah_dev}
ENV DB_USER ${DB_USER:-ah_dev}
ENV DB_PASSWORD ${DB_PASSWORD:-ah_dev}
ENV DDL_STRATEGY ${DDL_STRATEGY:-validate}

#Copy executable jar file from the builder image
COPY autohouse-server.jar app.jar

ENV SPRING_PROFILES_ACTIVE=${ACTIVE_PROFILES:-default}

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
