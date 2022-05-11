FROM	amazoncorretto
EXPOSE	80
ADD		./build/libs/AuthenticateServer-1.0-SNAPSHOT.jar /opt/AuthenticateServer/
CMD		["java", "-jar", "/opt/AuthenticateServer/AuthenticateServer-1.0-SNAPSHOT.jar"]
