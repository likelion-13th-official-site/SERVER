# 1. JDK 17 환경 사용
FROM openjdk:17-jdk

# 2. 빌드된 JAR 파일을 컨테이너에 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 애플리케이션 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
