package bnbnac.ytdlserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/hello")
    public String create(Model model, @RequestParam String addr) throws IOException, InterruptedException {

        Date date = new Date();
        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> env = pb.environment();
        String dirName = String.valueOf(date.hashCode());
        env.put("dirName", dirName);

        pb.command(
                "yt-dlp",
                "-P",
                "./temp/$dirName",
                "-f",
                "ba",
                addr
        );

        try {
            Process p = pb.start();
            p.waitFor(10, TimeUnit.SECONDS);
            System.out.println("Process ended...(종료코드) ::: " + p.exitValue());
        } catch(Throwable t) {
            t.printStackTrace();
        }

        // 파일네임 익스 추출
        model.addAttribute("dirName", dirName + ".webm");

        return "download";
    }
}
