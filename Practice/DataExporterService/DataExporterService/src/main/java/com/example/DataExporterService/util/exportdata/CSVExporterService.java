package com.example.DataExporterService.util.exportdata;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component("csvExporter")
public class CSVExporterService<T> implements ExportService<T> {

    private final String FILE_EXTENSION ="csv";

    @Override
    public byte[] export(List<T> data) throws Exception {
        if(data == null || data.isEmpty()) {
            return new byte[0];
        }
        StringWriter writer = new StringWriter();
        StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
        beanToCsv.write(data);
        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return "text/"+ FILE_EXTENSION;
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
