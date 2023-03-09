package id.kawahedukasi.service;

import com.opencsv.CSVWriter;
import id.kawahedukasi.model.Peserta;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ExportService {
    public Response exportPdfPeserta() throws JRException {

        //load template jasper
        File file = new File("src/main/resources/TemplatePeserta6.jrxml");

        //build object jasper report dari load template
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        //create datasource jasper for all data peserta
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Peserta.listAll());

//        Map<String, Object> param = new HashMap<>();
//        param.put("DATASOURCE", jrBeanCollectionDataSource);

        //create object jasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>() , jrBeanCollectionDataSource);

        //export jasperPrint to byte array
        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        //return response dengan content type pdf
        return Response.ok().type("application/pdf").entity(jasperResult).build();

    }
    public Response exportExcelPeserta() throws IOException {

        ByteArrayOutputStream outputStream = excelPeserta();

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=\"peserta_list_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();

    }

    public ByteArrayOutputStream excelPeserta() throws IOException {
        //get all data peserta
        List<Peserta> pesertaList = Peserta.listAll();

        //create new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //create sheet
        XSSFSheet sheet = workbook.createSheet("data");

        //set header
        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("email");
        row.createCell(3).setCellValue("phoneNumber");
        row.createCell(4).setCellValue("createdAt");
        row.createCell(5).setCellValue("updatedAt");

        //set data
        for(Peserta peserta : pesertaList){
            row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(peserta.id);
            row.createCell(1).setCellValue(peserta.name);
            row.createCell(2).setCellValue(peserta.email);
            row.createCell(3).setCellValue(peserta.phoneNumber);
            row.createCell(4).setCellValue(peserta.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
            row.createCell(5).setCellValue(peserta.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

    public Response exportCsvPeserta() throws IOException {
        //get all data peserta
        List<Peserta> pesertaList = Peserta.listAll();

        File file = File.createTempFile("temp", "");

        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        String[] headers = {"id", "name", "email", "phoneNumber", "createdAt", "updateAt"};
        writer.writeNext(headers);
        for(Peserta peserta : pesertaList){
            String[] data = {
                    peserta.id.toString(),
                    peserta.name,
                    peserta.email,
                    peserta.phoneNumber,
                    peserta.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                    peserta.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            };
            writer.writeNext(data);
        }

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment; filename=\"peserta_list_excel.csv\"")
                .entity(file).build();

    }

}
