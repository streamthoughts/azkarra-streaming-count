# Hello Azkarra Streams - Word Count

This project demonstrates how to build and containerize a [Kafka Streams](https://kafka.apache.org/documentation/streams/) application using :
 * [Azkarra Streams](https://www.azkarrastreams.io/) 
 * [Jib](https://github.com/GoogleContainerTools/jib).

The project contains a simple WordCount application : [KStreamsWordCountTopology](./tree/master/src/main/java/demo/KStreamsWordCountTopology)

## Quickstart

* **Build and package application**

on Linux

```bash
$ ./mvnw compile jib:dockerBuild
```

or on Windows

```bash
$ mvnw.cmd compile jib:dockerBuild
```

Note: This Maven project uses the [jib-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#quickstart) to build a Docker image named `streaming-word-count:latest`.
In addition, we are using [Azkarra-Worker](https://www.azkarrastreams.io/docs/azkarra-worker/) image as parent which is available on [DockerHub](https://hub.docker.com/r/streamthoughts/azkarra-streams-worker).


* **Run Docker containers**

```bash
$ docker-compose up -d
```

* **Start a kafka-console-producer**

```bash
$ docker-compose exec kafka /usr/bin/kafka-console-producer \
--topic streams-plaintext-input \
--broker-list kafka:9092
```
* **Then, produce some messages**
```bash
> Azkarra Streams rocks!
> WordCount
> I Heart Logs   
> Kafka Streams
> Making Sense of Stream Processing
```

`Ctrl+c` to exit producer

* **Run Interactive Query**

```bash
$ curl -sX POST 'http://localhost:8080/api/v1/applications/k-streams-word-count-topology-1-0/stores/count' \
--data '{ "type":"key_value", "query" : {"all":{} } }' | jq
```

* **Access to Azkarra Dashboard**

Open your favorite browser and go to : [http://localhost:8080/ui](http://localhost:8080/ui)


## Join the Azkarra Streams community

Join the Azkarra Streams community on [Slack](https://communityinviter.com/apps/azkarra-streams/azkarra-streams-community)










