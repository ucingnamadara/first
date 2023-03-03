package id.kawahedukasi.controller;

import id.kawahedukasi.service.PesertaService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/peserta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PesertaController {

    @Inject
    PesertaService pesertaService;

    @GET
    public Response get() {
        return pesertaService.get();
    }

    @POST
    public Response post(Map<String, Object> request){
        return pesertaService.post(request);
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request){
        return pesertaService.put(id, request);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id){
        return pesertaService.delete(id);
    }
}
