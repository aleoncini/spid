# spid
A SAML2 IDP Service to test a SPID authentication environment

## Packaging your application

You can simply package and run your IDP in the same way you develop, package and run a Thorntail service.
Just run `mvn clean package`. An `idp-thorntail.jar` file will be built in your target folder.

### Package a container
Run `mvn clean package docker:build`. An `example-docker-jaxrs-dockerfile` docker image will be built.

This example is using Spotifys docker-maven-plugin: https://github.com/spotify/docker-maven-plugin/


## Running Docker with a Thorntail microservice inside

You run the Docker container and start the Thorntail microservice (a simple JAX-RS application) with the following commands:

`docker run -p 8080:8080 example-docker-jaxrs-dockerfile`

## Inspect your running Docker containers

From a terminal run `docker ps` and you should see something like:

    CONTAINER ID        IMAGE                                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
    a840d7990c15        example-docker-jaxrs-dockerfile           "/bin/sh -c 'java -ja"   43 seconds ago      Up 42 seconds       0.0.0.0:8080->8080/tcp   admiring_brattain

Now try to `curl` the resource running inside Docker:

    curl localhost:8080/resource

Result should be the following:

    bar

## Special notes if you are using DockerMachine/Docker Toolbox:

Your `curl`
command should look like

    curl $(docker-machine ip default):8080/resource
