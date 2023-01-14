package bnbnac.ytdlserver.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DownloadController {

    String fileName;

    @GetMapping("/download/{dirName}")
    public ResponseEntity<Resource> download(@PathVariable("dirName") String dirName) throws IOException {
        ClassPathResource cpr = new ClassPathResource("temp/hereIsTemp");
        Path temp = Paths.get(cpr.getFile().getParent().toString(), dirName);

        Files.list(temp).forEach(f -> {
            Path p = f.getFileName();
            fileName = p.toString();
        });

        File downloadFile = new File((temp.resolve(fileName)).toUri());

        fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
