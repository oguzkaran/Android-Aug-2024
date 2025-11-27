package org.csystem.app.fileupload.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfo {
    private String fileName;
    private long size;
    private long lastModified;
}
