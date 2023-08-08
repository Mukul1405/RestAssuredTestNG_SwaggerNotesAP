FROM maven:3.6.3-jdk-11

RUN mkdir AutomationFramework
RUN cd AutomationFramework
WORKDIR AutomationFramework
COPY  .	.
RUN mvn clean install -DsuiteXmlFile=testng.xml
RUN apt-get update 
RUN apt-get install -y nano
ENV file testng.xml
CMD ["sh","-c","mvn test -DsuiteXmlFile=$file"]
