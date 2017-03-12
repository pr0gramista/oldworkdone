package pl.pr0gramista.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (this.supportsParameter(parameter)) {
            if (webRequest.getUserPrincipal() instanceof OAuth2Authentication) {
                OAuth2Authentication auth2Authentication = (OAuth2Authentication) webRequest.getUserPrincipal();
                User user = userService.getUserForOAuthentication(auth2Authentication);
                return user;
            }
        }
        return WebArgumentResolver.UNRESOLVED;
    }
}
