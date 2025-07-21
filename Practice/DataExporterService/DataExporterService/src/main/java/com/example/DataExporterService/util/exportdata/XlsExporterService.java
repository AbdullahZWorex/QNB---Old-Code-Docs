package com.example.DataExporterService.util.exportdata;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;

@Component("xlsExporter")
public class XlsExporterService<T> implements ExportService<T> {

    private final String FILE_EXTENSION ="xls";

    @Override
    public byte[] export(List<T> data) throws Exception {
        if (data == null || data.isEmpty()) {
            return new byte[0];
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");
        Field[] fields = data.get(0).getClass().getDeclaredFields();

        // Header Row
        Row header = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            header.createCell(i).setCellValue(fields[i].getName());
        }

        // Data Rows
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T item = data.get(i);
            for (int j = 0; j < fields.length; j++) {
                Object value = fields[j].get(item);
                row.createCell(j).setCellValue(value != null ? value.toString() : "");
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
