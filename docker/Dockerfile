FROM openjdk:jre-alpine

ADD idp-thorntail.jar /opt/thorntail.jar
ADD keystore.jks /opt/keystore.jks

WORKDIR "/opt"
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar"]