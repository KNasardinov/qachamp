FROM maven:3.6.3-jdk-8-slim

ARG RELEASE=2.8.0
ARG ALLURE_REPO=https://dl.bintray.com/qameta/maven/io/qameta/allure/allure-commandline

RUN apt-get update
RUN apt-get install unzip -y
RUN apt-get install wget -y

RUN wget --no-verbose -O /tmp/allure-$RELEASE.zip $ALLURE_REPO/$RELEASE/allure-commandline-$RELEASE.zip \
      && unzip /tmp/allure-$RELEASE.zip -d / \
      && rm -rf /tmp/*
ENV ALLURE_HOME=/allure-$RELEASE
ENV PATH=$PATH:$ALLURE_HOME/bin
RUN chmod -R +x /allure-$RELEASE/bin
RUN mkdir -p /usr/src/app
RUN mkdir -p /usr/src/download
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN ["chmod", "+x", "/usr/src/app/entrypoint.sh"]
ENTRYPOINT ["sh", "entrypoint.sh"]
