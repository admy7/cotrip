FROM maven:3.9.9-amazoncorretto-21 AS build

WORKDIR /build
COPY ./pom.xml ./

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean package -DskipTests


FROM amazoncorretto:21

WORKDIR /app
COPY --from=build /build/target/cotrip-backend-*.jar /app

RUN JAR_FILE=$(ls /app/cotrip-backend-*.jar) && \
    ln -s $JAR_FILE /app/app.jar

EXPOSE 8081

CMD java -jar /app/app.jar