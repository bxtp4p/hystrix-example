# INSTRUCTIONS

A sample application to show Hystrix circuit-breaking functionality.

## SETUP

Clone this repository and navigate to its directory.

```
$ git clone git@github.com:bxtp4p/hystrix-example.git
$ cd hystrix-example
```

## BUILD

Build the Dockerfiles

```
$ docker build --rm -f producer/Dockerfile -t hystrix-example-producer:1.0 producer
$ docker build --rm -f consumer/Dockerfile -t hystrix-example-consumer:1.0 consumer
```

## RUN

A couple of options for running:

### Docker for Mac

```
$ docker run -d -p 9090:9090 hystrix-example-producer:1.0
$ docker run -e PRODUCER_ENDPOINT=http://docker.for.mac.localhost -d -p 8080:8080 hystrix-example-consumer:1.0
```

### Docker Compose

```
$ docker-compose up -d
$ docker-compose down
```

### Kubernetes (Build and Run on Minikube)

```
$ eval $(minikube docker-env)
$ docker build --rm -f producer/Dockerfile -t hystrix-example-producer:1.0 producer
$ docker build --rm -f consumer/Dockerfile -t hystrix-example-consumer:1.0 consumer
$ kubectl apply -f deployment.minikube.yaml
$ minikube service hystrix-consumer-svc
```

The browser should open to `http://<minikube-ip>:31800`. To test, append `/get-greeting/<any name>` to this address. For example, `http://<minikube-ip>:31800/get-greeting/bart`.

## TEST

To simulate the circuit-breaker functionality, shut down the `greeting-producer` service. For example, for Minikube:

```
$ kubectl delete deployment greeting-producer
```

If you have `hystrix-dashboard` deployed somewhere, you can also watch this application's stream at `http://<minikube-ip>:31800/hystrix.stream` and see the circuit change from open/close (depending on the availability of the `greeting-producer` service) by hitting the service continuously (for example `$ repeat 100 curl http://<minikube-ip>:31800/get-greeting/bart`).