package org.csystem.app.fileupload.service;

import org.csystem.app.fileupload.model.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    private final Path root;

    public FileStorageService(@Value("${file.storage.location:uploads}") String storageLocation)
    {
        this.root = Paths.get(storageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage at: " + root, e);
        }
    }

    public String save(MultipartFile file)
    {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        String safeName = System.currentTimeMillis() + "_" + original.replace("..", "").replace("/", "_").replace("\\", "_");
        Path destination = resolveSafe(safeName);
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file: " + safeName, e);
        }
        return destination.getFileName().toString();
    }

    public List<String> saveAll(List<MultipartFile> files)
    {
        List<String> names = new ArrayList<>();
        for (MultipartFile f : files) {
            names.add(save(f));
        }
        return names;
    }

    public List<FileInfo> list()
    {
        try (Stream<Path> stream = Files.list(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(p -> {
                        try {
                            return new FileInfo(
                                    p.getFileName().toString(),
                                    Files.size(p),
                                    Files.getLastModifiedTime(p).toMillis()
                            );
                        } catch (IOException e) {
                            return new FileInfo(p.getFileName().toString(), -1L, -1L);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files", e);
        }
    }

    public Resource loadAsResource(String fileName)
    {
        Path path = resolveSafe(fileName);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new RuntimeException("File not found: " + fileName);
        }
        return new FileSystemResource(path.toFile());
    }

    public boolean delete(String fileName) {
        Path path = resolveSafe(fileName);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + fileName, e);
        }
    }

    private Path resolveSafe(String fileName)
    {
        Path normalized = root.resolve(fileName).normalize();
        if (!normalized.startsWith(root)) {
            throw new RuntimeException("Invalid file path");
        }
        return normalized;
    }
}
