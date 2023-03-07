package id.kawahedukasi.controller;

import com.opencsv.exceptions.CsvValidationException;
import id.kawahedukasi.dto.FileFormDTO;
import id.kawahedukasi.service.ExportService;
import id.kawahedukasi.service.ImportService;
import id.kawahedukasi.service.PesertaService;
import net.sf.jasperreports.engine.JRException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/peserta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PesertaController {

    @Inject
    PesertaService pesertaService;

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    @GET
    public Response get() {
        return pesertaService.get();
    }

    @GET
    @Path("/export/pdf")
    @Produces("application/pdf")
    public Response exportPdf() throws JRException {
        return exportService.exportPdfPeserta();
    }

    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws IOException {
        return exportService.exportExcelPeserta();
    }

    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCsv() throws IOException {
        return exportService.exportCsvPeserta();
    }

    @POST
    public Response post(Map<String, Object> request){
        return pesertaService.post(request);
    }

    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm FileFormDTO request) {
        try{
            return importService.importExcel(request);
        } catch (IOException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm FileFormDTO request) {
        try{
            return importService.importCSV(request);
        } catch (IOException | CsvValidationException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
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
