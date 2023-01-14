package bnbnac.ytdlserver.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DeleteService {

    @Async
    public void deleteAsync(String dirName) throws InterruptedException, IOException {
        Thread.sleep(60000);
        ClassPathResource cpr = new ClassPathResource("temp/hereIsTemp");
        String path = cpr.getFile().getParent().toString();
        path = path.concat("/").concat(dirName);
        File directory = new File(path);
        try {
            while(directory.exists()) {
                File[] fileList = directory.listFiles();

                assert fileList != null;
                for (File file : fileList) {
                    file.delete();
                    System.out.println("file deleted");
                }

                if(fileList.length == 0 && directory.isDirectory()){
                    directory.delete();
                    System.out.println("directory deleted");
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
