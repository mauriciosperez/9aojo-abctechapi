FROM tomcat:8.5-jdk11-corretto-al2

RUN yum install -y unzip wget
RUN wget https\://services.gradle.org/distributions/gradle-7.5.1-bin.zip -P /tmp
RUN unzip -d /opt /tmp/gradle-7.5.1-bin.zip

ENV GRADLE_HOME=/opt/gradle-7.5.1
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /usr/app

COPY . .

RUN gradle generateArtifact

RUN cp build/libs/*.war /usr/local/tomcat/webapps/ROOT.war