FROM 110.30.182.30:8082/dependency-docker/tomcat:8.5-jdk11-openjdk-slim
COPY build/libs/$MODULE-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/$MODULE.war

COPY scripts/trust_certs.sh /tmp/trust_certs.sh
RUN chmod 750 /tmp/trust_certs.sh
RUN chmod 660 /usr/local/openjdk-11/lib/security/cacerts

RUN chgrp -R 0 /usr/local/tomcat && chmod -R g=u /usr/local/tomcat

#CMD ["catalina.sh", "run"]
ENTRYPOINT ["java", "-jar", "/usr/local/tomcat/webapps/$MODULE.war"]
