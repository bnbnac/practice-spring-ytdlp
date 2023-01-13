package bnbnac.ytdlserver.controller;

import bnbnac.ytdlserver.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class AddressController {

    @Autowired
    private final DeleteService deleteService = new DeleteService();
    String fileName;

    @PostMapping("/address")
    public String create(Model model, @RequestParam String address) throws IOException, InterruptedException {

        Date date = new Date();
        ProcessBuilder pb = new ProcessBuilder();

        String dirName = String.valueOf(date.hashCode());
        Map<String, String> env = pb.environment();
        env.put("dirName", dirName);

        pb.command(
                "yt-dlp",
                "-P",
                "./src/main/resources/temp/$dirName",
                "-o",
                "%(title)s.%(ext)s",
                "-f",
                "m4a",
                address
        );

        try {
            Process p = pb.start();
            if (!p.waitFor(20, TimeUnit.SECONDS)) {
                p.destroy();
                if (p.isAlive()) {
                    p.destroyForcibly();
                }
                deleteService.deleteAsync(dirName);
                return "large";
            }
            System.out.println("Process ended...(종료코드) ::: " + p.exitValue());
        } catch(Throwable t) {
            t.printStackTrace();
        }

        model.addAttribute("dir", dirName);

        deleteService.deleteAsync(dirName);
        return "download";
    }

    public String getFileName(String dirName) throws IOException {
        Path temp = Paths.get("src", "main", "resources", "temp", dirName);
        Files.list(temp).forEach(f -> {
            Path p = f.getFileName().normalize();
            fileName = p.toString();
        });
        return fileName;
    }
}
