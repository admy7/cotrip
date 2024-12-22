FROM maven:3.9.9-amazoncorretto-21 AS build

WORKDIR /build
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


FROM amazoncorretto:21

ARG DB_URL=jdbc:postgresql://postgres:5432/cotrip
ARG PROFILE=dev

WORKDIR /app
COPY --from=build /build/target/cotrip-backend-*.jar /app

RUN JAR_FILE=$(ls /app/cotrip-backend-*.jar) && \
    ln -s $JAR_FILE /app/app.jar

ENV _DB_URL=${DB_URL}
ENV _PROFILE=${PROFILE}

EXPOSE 8080

CMD java -jar -Dspring.profiles.active=${_PROFILE} -Dspring.datasource.url=${_DB_URL} /app/app.jar