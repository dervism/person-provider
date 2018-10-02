package no.dervis.pact;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import spark.Spark;

@Provider("person-provider")
@PactBroker(
        protocol = "https",
        host = "${PACTBROKER_URL:norwegianlga.pact.dius.com.au}",
        authentication = @PactBrokerAuth(username = "${PACTBROKER_USERNAME}", password = "${PACTBROKER_PASSWORD}"),
        port = "443")
@IgnoreNoPactsToVerify
class PactPersonProviderTest {

    private static PersonController person;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeAll
    public static void beforeAll() {
        person = new PersonController();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 9999, "/"));
    }

    @AfterAll
    public static void after() {
        Spark.stop();
    }

    @State("a person exists")
    public void verifyPersonExists() {
        System.out.println("Single person must exist");
        person.addPerson(new Person(0, "Test", 50));
    }


}