package bnbnac.ytdlserver.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeleteService {

    @Async
    public void deleteAsync(String dirName) throws InterruptedException {
        Thread.sleep(60000);
        String path = "./src/main/resources/temp/";
        path = path.concat(dirName);
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
