package id.kawahedukasi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TugasDTO {
    public Long id;

    public String name;

    @JsonProperty(namespace = "peserta_id")
    public Long pesertaId;

    public Integer score;

    public static List<TugasDTO> response(List<Object[]> input){
        List<TugasDTO> result = new ArrayList<>();
        for(Object[] objects: input){
            TugasDTO tugasDTO = new TugasDTO();
            tugasDTO.id = Long.valueOf(Objects.toString(objects[0]));
            tugasDTO.name = Objects.toString(objects[1]);
            tugasDTO.pesertaId = Long.valueOf(Objects.toString(objects[2]));
            tugasDTO.score = Integer.valueOf(Objects.toString(objects[3]));
            result.add(tugasDTO);
        }
        return result;
    }
}
