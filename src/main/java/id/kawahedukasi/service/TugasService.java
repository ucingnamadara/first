package id.kawahedukasi.service;

import id.kawahedukasi.dto.TugasDTO;
import id.kawahedukasi.model.Peserta;
import id.kawahedukasi.model.Tugas;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TugasService {

    @Inject
    EntityManager entityManager;

    public Response getV1(Long id){
        Peserta peserta = Peserta.findById(id);
        if(peserta == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Tugas> tugasList = Tugas.find("peserta_id = ?1", peserta.id).list();
        List<Map<String, Object>> result = new ArrayList<>();

        for(Tugas tugas : tugasList){
            Map<String, Object> map = new HashMap<>();
            map.put("id", tugas.id);
            map.put("name", tugas.name);
            map.put("pesertaId", id);
            map.put("score", tugas.score);

            result.add(map);
        }

        return Response.status(Response.Status.OK).entity(result).build();
    }

    public Response getV2(Long id){
        Query query = entityManager.createNativeQuery(
                " SELECT id, name, peserta_id, score FROM public.tugas\n" +
                " WHERE peserta_id = :id ");
        query.setParameter("id", id);
        List<Object[]> execList = query.getResultList();
        return Response.status(Response.Status.OK).entity(TugasDTO.response(execList)).build();
    }

    @Transactional
    public Response post(Long id, Map<String, Object> request){
        Peserta peserta = Peserta.findById(id);
        if(peserta == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Tugas tugas = new Tugas();
        tugas.name = request.get("name").toString();
        tugas.score = Integer.parseInt(request.get("score").toString());
        tugas.peserta = peserta;

        //save to database
        tugas.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
}
