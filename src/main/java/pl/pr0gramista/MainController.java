package pl.pr0gramista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles typical web requests like index, 404
 */
@Controller
public class MainController {
    @RequestMapping("")
    public String index() {
        return "index";
    }
}
