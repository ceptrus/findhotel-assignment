FROM openjdk:8u171-jre
ADD target/find-hotel-api.jar /find-hotel-api.jar
ADD data_dump.csv /data_dump.csv

ENV SPRING_PROFILES_ACTIVE=""
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

EXPOSE 8080
EXPOSE 8000

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Xmx3G -jar /find-hotel-api.jar --file.location=/data_dump.csv" ]

# --spring.data.mongodb.uri=mongodb://192.168.1.44:27017/findhotel