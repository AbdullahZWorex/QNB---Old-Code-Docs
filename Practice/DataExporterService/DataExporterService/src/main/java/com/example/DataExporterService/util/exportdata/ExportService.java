package com.example.DataExporterService.util.exportdata;

import java.util.List;

public interface ExportService<T> {
    byte[] export(List<T> data) throws Exception;
    String getContentType();
    String getFileExtension();
}
