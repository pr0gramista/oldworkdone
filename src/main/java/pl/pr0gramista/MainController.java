package pl.pr0gramista;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles typical web requests like index, 404
 */
@Controller
public class MainController {
    @RequestMapping("")
    public String index(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "index";
    }

    @RequestMapping("/test-login")
    public ModelAndView testLogin(ModelAndView modelAndView, OAuth2Authentication authentication) {
        modelAndView.setViewName("test-login");
        modelAndView.addObject("user", authentication.getUserAuthentication().getDetails());
        return modelAndView;
    }

    @RequestMapping("/test-oauth")
    @ResponseBody
    public Object testOAuth(OAuth2Authentication auth) {
        return auth;
    }

    @RequestMapping("/test-auth")
    @ResponseBody
    public Object testAuth(Authentication auth) {
        return auth;
    }

    @RequestMapping("/dashboard")
    public String dashboard(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "dashboard";
        }
        return "redirect:/";
    }
}
