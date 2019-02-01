# SPID integration validator
A SAML2 IDP Service to test a SPID authentication environment

## Running as standalone

You can simply package and run your IDP in the same way you develop, package and run a Thorntail service.
Just run

`> mvn clean package`
 
 An `idp-thorntail.jar` file will be built in your target folder.

To test that everything is working execute the application with 

`> java -jar target/idp-thorntail.jar`

and from another shell connect to a test ReST API called ping with curl.

`> curl -k https://localhost:8443/ping`

If everything is properly configured you should get a JSON payload like `{"ping":"pong"}`

## Running in a container

The same service can be packaged as a docker image (for further deployment in PaaS environment like OpenShift)

#### Packaging the container

In this case Run
`> mvn clean package docker:build`

An `idp-test-suite` docker image will be built.
check it running:

`> docker images`

you should see an output like this:

`REPOSITORY                                                  TAG                 IMAGE ID            CREATED             SIZE
 idp-test-suite                                              latest              bc79d1b1a1cc        2 minutes ago       152MB
 <none>                                                      <none>              95024076bb4e        35 minutes ago      152MB
  ...                                                         ...                    ...             .. minutes ago       ... `

#### Running the container with a Thorntail microservice inside

Now you can run the container (and start the Thorntail microservice) using the following command:

`> docker run -p 8443:8443 -d idp-test-suite`

as well as for local packaging you can test that the service is running using the same curl command:

`> curl -k https://localhost:8443/ping`

and you should receive the same response.

This example is using Spotifys docker-maven-plugin: https://github.com/spotify/docker-maven-plugin/

## Deploy into OpenShift
Let's do it very simple with a complete container platform for your development!
If you don't have an openshift enterprise cluster, just begin with minishift on your workstation: 
  - download the "minishift" executable
  - run "minishift start"

After that you will be able to run this IDP and all your component on a consistent and portable environment

ref. to the guide: https://docs.okd.io/latest/minishift/index.html

#### Create and expose the app using the image centos/wildfly

`> oc new-app --image-stream=wildfly --name=spid-test https://github.com/aleoncini/spid.git \

oc expose service spid-test`

That's it! now make the ping test
`> curl -k http://$(oc get route | grep spid-test | awk '{ print $2}')/idp/ping`

