package no.dervis.pact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class PersonController {

    private List<Person> personList;

    public PersonController() {
        this(new ArrayList<>());
    }

    public PersonController(List<Person> personList) {
        this.personList = personList;

        String contentTypeJson = "application/json;charset=utf-8";

        port(9999);

        path("/api/person", () ->  {

            get("",  (request, response) -> {
                int id = parseInt(request.queryParams("fnr"));
                response.type(contentTypeJson);
                return new ObjectMapper().writeValueAsString(personList.get(id));
            });

            get("/:id", (req, res) -> {
                int id = parseInt(req.params("id"));
                res.type(contentTypeJson);
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

    public boolean addPerson(Person person) {
        return personList.add(person);
    }
}
