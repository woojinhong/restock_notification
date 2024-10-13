FROM openjdk:17-jdk
# 베이스 이미지로 OpenJDK가 포함된 이미지 사용


# 애플리케이션의 JAR 파일을 컨테이너에 app.jar라는 이름으로 복사
# 실제 build된 jar 파일 경로와 build.gradle을 참고하여 파일 이름을 작성해 주어야 한다
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
# 애플리케이션 실행