package pl.pr0gramista;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles typical web requests like index, 404
 */
@Controller
public class MainController {
    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("/test-login")
    public ModelAndView testLogin(ModelAndView modelAndView, OAuth2Authentication authentication) {
        modelAndView.setViewName("test-login");
        modelAndView.addObject("user", authentication.getUserAuthentication().getDetails());
        return modelAndView;
    }
}
