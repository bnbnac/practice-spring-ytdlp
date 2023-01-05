package bnbnac.ytdlserver.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadUtil {
    private Path foundFile;

    public Resource getFileAsResource(String dirName) throws IOException {
        Path temp = Paths.get("temp");
        Path cur = temp.resolve(dirName);

        Files.list(cur).forEach(f -> {
            Path fname = f.getFileName();
            foundFile = cur.resolve(fname);

        });

        if (foundFile != null) {
            System.out.println(new UrlResource(foundFile.toUri()));
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
