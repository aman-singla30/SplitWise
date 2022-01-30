# define base docker image
FROM openjdk:11
LABEL maintainer="aman.singla30@gmail.com"
ADD target/SplitWise-1.0.jar SplitWise-1.0.jar
ENTRYPOINT ["java", "-jar", "SplitWise-1.0.jar"]