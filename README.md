# Person Provider

An example SparkJava-application showing how to write Pact Provider tests with JUnit and Maven and publish the verification results back to a Pact Broker.

The PactPersonProviderTest class will start a local instance of the provider before running the Pact-verification. The provider is automatically stopped after the test has completed (regardless of the test result).

## Setup

You need to setup a Pact Broker and export the username and password (if it's authenticated):

    $ export PACTBROKER_URL="http://localhost"
    $ export PACTBROKER_USERNAME=xxxxxxxxx
    $ export PACTBROKER_PASSWORD=xxxxxxxxx
    
You can register a free hosted Pact Broker here: https://pact.dius.com.au/ or you can spin up you own instance with Docker Compose: https://github.com/dervism/pact-jvmio/tree/master/docker-app (the instance will be available at http://localhost:80) without authentication.

In order to automatically publish verification results, set the following environment variable to true:

    $ export pact.verifier.publishResults=true

Leave it unset or set it to false, to disable automatic publishing.

### To switch Java version while testing:

1. /usr/libexec/java_home -V
2. Depending on what you have installed:

    export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
    
    For JDK10:
    export JAVA_HOME=`/usr/libexec/java_home -v 10`

3. Update the java.version property in the pom.xml file accordingly.