FROM gradle:8.10.2-focal AS build

WORKDIR /workspace

COPY src ./src
COPY build.gradle ./build.gradle
COPY settings.gradle ./settings.gradle

RUN gradle clean build --no-daemon --exclude-task test

FROM bellsoft/liberica-openjdk-alpine:17

RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot

WORKDIR /app

COPY --from=build /workspace/build/libs/spring-aop-*.jar ./spring-aop.jar

CMD ["java", "-jar", "spring-aop.jar"]