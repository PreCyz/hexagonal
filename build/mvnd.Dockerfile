FROM alpine:latest AS fetcher
RUN apk add --no-cache curl
ARG MVND_VERSION=1.0.5
RUN curl -L https://github.com/apache/maven-mvnd/releases/download/${MVND_VERSION}/maven-mvnd-${MVND_VERSION}-linux-amd64.tar.gz -o mvnd.tar.gz \
    && tar -xzf mvnd.tar.gz

#FROM alpine:latest AS skeleton
#WORKDIR /src
#COPY . .
#RUN find . -type f ! -name 'pom.xml' -delete

FROM eclipse-temurin:25-jdk-noble
COPY --from=fetcher /maven-mvnd-* /opt/mvnd
ENV PATH="/opt/mvnd/bin:${PATH}"
ENV JAVA_HOME="/opt/java/openjdk"
ENV MAVEN_OPTS="-Xmx4g -XX:+UseG1GC"

WORKDIR /app
#COPY --from=skeleton /src ./
#COPY .mvn ./.mvn
COPY . .

RUN --mount=type=cache,target=/root/.m2 \
    mvnd dependency:go-offline -B

RUN --mount=type=cache,target=/root/.m2 \
    mvnd clean package -Dmaven.build.cache.enabled=true

#RUN mvnd --version

CMD ["mvnd", "clean", "package", "-Dmaven.build.cache.enabled=true"]
#CMD ["mvnd", "--version"]