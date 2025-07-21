package com.example.DataExporterService;

import com.example.DataExporterService.util.exportdata.ExportService;
import com.example.DataExporterService.util.exportdata.ExportServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    @Autowired
    private ExportServiceFactory factory;

    @GetMapping("/{format}")
    public ResponseEntity<byte[]> exportData(@PathVariable String format) throws Exception {
        List<UserDTO> users = List.of(
                new UserDTO(1, "John", "john@example.com"),
                new UserDTO(2, "Alice", "alice@example.com")
        );

        ExportService<UserDTO
                > exporter = factory.getExporter(format);
        byte[] file = exporter.export(users);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(exporter.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users." + exporter.getFileExtension())
                .body(file);
    }
}
