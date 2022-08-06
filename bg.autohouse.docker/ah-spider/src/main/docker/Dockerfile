FROM ${docker.registry}/ah-jdk-base:${docker.image.base.tag}

ENV APP_HOME /runtime
ENV SPIDER_CACHE_DIR $APP_HOME/storage

RUN mkdir -p $SPIDER_CACHE_DIR

COPY spider.jar $APP_HOME/app.jar

CMD java -jar $APP_HOME/app.jar