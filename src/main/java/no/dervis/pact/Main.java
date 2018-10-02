package no.dervis.pact;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new PersonController(
                Arrays.asList(new Person(0, "Test", 50))
        );
    }

}
