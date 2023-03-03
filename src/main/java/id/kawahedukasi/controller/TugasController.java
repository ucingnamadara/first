package id.kawahedukasi.controller;

import id.kawahedukasi.model.Peserta;
import id.kawahedukasi.model.Tugas;
import id.kawahedukasi.service.TugasService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/tugas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TugasController {

    @Inject
    TugasService tugasService;

    @GET
    @Path("/{pesertaId}")
    public Response get(@PathParam("pesertaId") Long id){
        return tugasService.getV2(id);
    }

    @POST
    @Path("/{pesertaId}")
    @Transactional
    public Response post(@PathParam("pesertaId") Long id, Map<String, Object> request){
        return tugasService.post(id, request);
    }
}
