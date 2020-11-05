FROM gradle:6.7.0-jdk11 AS builder
LABEL stage=builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon


FROM markhobson/maven-chrome:jdk-11
RUN mkdir /app
#COPY /build/libs/settlerManager-all.jar /app/application.jar
COPY --from=builder /home/gradle/src/build/libs/IngDibaSniffer-all.jar /app/application.jar
ENTRYPOINT java -XX:+UseContainerSupport -jar /app/application.jar
