package bnbnac.ytdlserver.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadUtil {
    private Path foundFile;

    public Resource getFileAsResource(String dir, String name) throws IOException {
        Path temp = Paths.get("src");
        temp = temp.resolve("main").resolve("resources").resolve("temp");
        Path cur = temp.resolve(dir);
        Files.list(cur).forEach(f -> {
            if (f.getFileName().toString().startsWith(name)) {
                foundFile = f;
            }
        });
        if (foundFile != null) {
            System.out.println(foundFile);
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
