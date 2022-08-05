FROM ${docker.registry}/ah-jdk-base:${docker.image.base.tag}

ENV APP_HOME /runtime

COPY spider.jar $APP_HOME/app.jar

CMD java -jar $APP_HOME/app.jar