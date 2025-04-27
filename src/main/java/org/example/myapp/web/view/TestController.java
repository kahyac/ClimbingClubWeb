package org.example.myapp.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String testPage() {
        return "test"; // correspond à WEB-INF/jsp/test.jsp
    }
}
