# Person Provider

An example application showing how to write Pact Provider tests with JUnit and Maven and publish the verification results back to a Pact Broker.

## Setup

You need to setup a Pact Broker and export the username and password (if it's authenticated):

    export PACTBROKER_USERNAME=xxxxxxxxx
    export PACTBROKER_PASSWORD=xxxxxxxxx
    
You can register a free hosted Pact Broker here: https://pact.dius.com.au/ or you can spin up you own instance with Docker Compose: https://github.com/dervism/pact-jvmio/tree/master/docker-app (the instance will be available at http://localhost:80) without authentication.

### To switch Java version while testing:

1. /usr/libexec/java_home -V
2. Depending on what you have installed:

    export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
    
    For JDK10:
    export JAVA_HOME=`/usr/libexec/java_home -v 10`

3. Update the java.version property in the pom.xml file accordingly.
