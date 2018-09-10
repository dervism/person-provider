package no.dervis.pact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;

public class PersonController {

    private List<Person> personList;

    public PersonController() {
        personList = new ArrayList<>();
        //addPerson(new Person(0, "Test", 50));

        port(9999);

        path("/api/person", () ->  {
            get("/:id", (req, res) -> {
                int id = parseInt(req.params("id"));
                res.type("application/json;charset=utf-8");
                return new ObjectMapper().writeValueAsString(personList.get(id));
            });

            exception(IndexOutOfBoundsException.class, (exception, request, response) -> {
                exception.printStackTrace();
                response.status(HttpStatus.SC_BAD_REQUEST);
                response.body(exception.getLocalizedMessage());
            });

            exception(NumberFormatException.class, (exception, request, response) -> {
                exception.printStackTrace();
                response.status(HttpStatus.SC_BAD_REQUEST);
                response.body(exception.getLocalizedMessage());
            });
        });
    }

    public void stop() {
        Spark.stop();
    }

    public boolean addPerson(Person person) {
        return personList.add(person);
    }
}
