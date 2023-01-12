package bnbnac.ytdlserver.controller;

import bnbnac.ytdlserver.util.DownloadUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class DownloadController {
    @GetMapping("/download/{name}")
    public ResponseEntity<?> download(@RequestParam("dir") String dir, @PathVariable("name") String name) throws UnsupportedEncodingException {

        DownloadUtil downloadUtil = new DownloadUtil();
        Resource resource = null;
        String outName;
        System.out.println(name);
        try {
            resource = downloadUtil.getFileAsResource(dir, name);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        outName = new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1");

        String contentType = "application/octet-stream";
        String headerValue = "attachment; file=\"" + outName + "\"";

        System.out.println(outName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
