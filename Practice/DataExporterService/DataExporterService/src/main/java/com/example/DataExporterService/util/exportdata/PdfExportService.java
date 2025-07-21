package com.example.DataExporterService.util.exportdata;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;

@Component("pdfExporter")
public class PdfExportService<T> implements ExportService<T>{

    private final String FILE_EXTENSION ="pdf";

    @Override
    public byte[] export(List<T> data) throws Exception {
        if (data == null || data.isEmpty()) return new byte[0];

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Field[] fields = data.get(0).getClass().getDeclaredFields();

        PdfPTable table = new PdfPTable(fields.length);

        // Header
        for (Field field : fields) {
            table.addCell(new PdfPCell(new Phrase(field.getName())));
        }

        // Data
        for (T item : data) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(item);
                table.addCell(new PdfPCell(new Phrase(value != null ? value.toString() : "")));
            }
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }

    @Override
    public String getContentType() {
        return "application/"+FILE_EXTENSION;
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
