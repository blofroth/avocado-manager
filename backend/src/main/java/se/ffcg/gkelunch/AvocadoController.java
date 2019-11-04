package se.ffcg.gkelunch;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/avocados")
public class AvocadoController {

    private Set<Avocado> avocados = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public AvocadoController() {
        avocados.add(new Avocado(UUID.randomUUID().toString(),1, LocalDate.of(2019,11,4), "Hass"));
    }

    @GET
    public Set<Avocado> hello() {
        return avocados;
    }
}