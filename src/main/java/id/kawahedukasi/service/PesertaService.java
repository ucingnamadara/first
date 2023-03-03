package id.kawahedukasi.service;

import id.kawahedukasi.model.Peserta;
import id.kawahedukasi.util.FormatUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PesertaService {

    @Inject
    TugasService tugasService;

    public Response get(){
        return Response.status(Response.Status.OK).entity(Peserta.findAll().list()).build();
    }

    @Transactional
    public Response post(Map<String, Object> request){

        if(!FormatUtil.isEmail(request.get("email").toString())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Peserta peserta = new Peserta();
        peserta.name = request.get("name").toString();
        peserta.email = request.get("email").toString();
        peserta.phoneNumber = request.get("phoneNumber").toString();

        //save to database
        peserta.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public Response put(Long id, Map<String, Object> request){
        Peserta peserta = Peserta.findById(id);
        if(peserta == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        peserta.name = request.get("name").toString();
        peserta.email = request.get("email").toString();
        peserta.phoneNumber = request.get("phoneNumber").toString();

        //save to database
        peserta.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public Response delete(Long id){
        Peserta peserta = Peserta.findById(id);
        if(peserta == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //save to database
        peserta.delete();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
