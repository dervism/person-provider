package no.dervis.pact;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(PactRunner.class)
@Provider("person-provider")
@PactBroker(
        protocol = "https",
        host = "norwegianlga.pact.dius.com.au",
        authentication = @PactBrokerAuth(username = "${PACTBROKER_USERNAME}", password = "${PACTBROKER_PASSWORD}"),
        port = "443")
@IgnoreNoPactsToVerify
public class PactPersonProviderTest {

    private static PersonController person;

    @TestTarget
    public static Target target;

    @BeforeClass
    public static void beforeAll() {
        person = new PersonController();
        target = new HttpTarget("localhost", 9999);
    }

    @AfterClass
    public static void after() {
        person.stop();
    }

    @State("a person exists")
    public void verifyPersonExists() {
        System.out.println("Single person must exist");
        person.addPerson(new Person(0, "Test", 50));
    }

}