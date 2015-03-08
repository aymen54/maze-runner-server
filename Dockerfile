FROM dockerfile/java:oracle-java8

ADD target/maze-runner-server.jar /maze-runner-server.jar

CMD java -jar /maze-runner-server.jar

