package id.kawahedukasi.dto;

import javax.ws.rs.FormParam;

public class FileFormDTO {
    @FormParam("file")
    public byte[] file;
}
