package org.csystem.app.fileupload.controller;

import org.csystem.app.fileupload.model.FileInfo;
import org.csystem.app.fileupload.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService storage;

    public FileController(FileStorageService storage)
    {
        this.storage = storage;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file)
    {
        String name = storage.save(file);
        return ResponseEntity.ok(Map.of("message", "uploaded", "fileName", name));
    }

    @PostMapping("/uploads")
    public ResponseEntity<Map<String, Object>> uploadMany(@RequestParam("files") List<MultipartFile> files)
    {
        var names = storage.saveAll(files);
        return ResponseEntity.ok(Map.of("message", "uploaded", "count", names.size(), "files", names));
    }

    @GetMapping
    public List<FileInfo> list()
    {
        return storage.list();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName)
    {
        Resource resource = storage.loadAsResource(fileName);
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        try {
            contentType = Files.probeContentType(resource.getFile().toPath());
        } catch (Exception ignored) {}
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String fileName)
    {
        boolean deleted = storage.delete(fileName);
        return ResponseEntity.ok(Map.of("deleted", deleted, "fileName", fileName));
    }
}
