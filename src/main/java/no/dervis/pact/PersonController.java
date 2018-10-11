package no.dervis.pact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import spark.Response;

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
                response.type(contentTypeJson);
                return new ObjectMapper().writeValueAsString(personList);
            });

            get("/:id", (request, response) -> {
                int id = parseInt(request.params("id"));
                response.type(contentTypeJson);

                if (!personExist(id)) {
                    return handlePersonDoesNotExist(response);
                }

                return new ObjectMapper().writeValueAsString(personList.get(id));
            });

            post("", (request, response) -> {
                Person person = new ObjectMapper().readValue(request.body(), Person.class);
                int id = personList.size();
                addPerson(new Person(id, person.getName(), person.getAge()));

                response.status(200);
                return id;
            });

            put("", (request, response) -> {
                Person updatedPerson = new ObjectMapper().readValue(request.body(), Person.class);

                if (!personExist(updatedPerson.getId())) {
                    return handlePersonDoesNotExist(response);
                }

                Person existingPerson = personList.get(updatedPerson.getId());
                existingPerson.setName(updatedPerson.getName());
                existingPerson.setAge(updatedPerson.getAge());

                response.status(200);
                return "Ok";
            });

            delete("/:id", (request, response) -> {

                int id = parseInt(request.params("id"));

                if (!personExist(id)) {
                    return handlePersonDoesNotExist(response);
                }

                personList.remove(id);

                response.status(200);
                return "Ok";
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

    @NotNull
    private Object handlePersonDoesNotExist(Response response) {
        response.status(404);
        return "Person does not exist.";
    }


    public boolean addPerson(Person person) {
        return personList.add(person);
    }


    public boolean personExist(int id) {
        return !(id > personList.size() || id < 0 || personList.isEmpty());
    }

}
