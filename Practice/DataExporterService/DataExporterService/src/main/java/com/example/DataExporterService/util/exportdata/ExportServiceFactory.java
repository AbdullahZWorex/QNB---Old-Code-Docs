package com.example.DataExporterService.util.exportdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExportServiceFactory {

    @Autowired
    private Map<String, ExportService<?>> exporters;

    public <T> ExportService<T> getExporter(String type) {
        return switch (type.toLowerCase()) {
            case "pdf" -> (ExportService<T>) exporters.get("pdfExporter");
            case "xls", "xlsx" -> (ExportService<T>) exporters.get("xlsExporter");
            case "csv" -> (ExportService<T>) exporters.get("csvExporter");
            default -> throw new IllegalArgumentException("Unsupported export type: " + type);
        };
    }
}
