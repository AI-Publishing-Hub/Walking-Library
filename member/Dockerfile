FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY member/build/libs/member-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=docker","-Xmx400M","-Djava.security.egd=file:/dev/./urandom"]
