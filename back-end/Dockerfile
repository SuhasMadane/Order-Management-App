FROM openjdk:17
WORKDIR /order
ENV PORT 8181
EXPOSE 8181
COPY target/*.war /order/app.war
#ENTRYPOINT exec java $JAVA_OPTS -jar app.war
ENTRYPOINT ["java","-jar","/order/app.war"]