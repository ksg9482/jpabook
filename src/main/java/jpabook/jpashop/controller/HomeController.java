package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    //home -> home 타임리프 파일을 찾아감
    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
