package pl.pr0gramista;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.pr0gramista.user.User;

/**
 * This argument resolver creates gives fake user so we don't have to
 * mock whole OAuth behaviour.
 */
public class FakeUserArgumentResolver implements HandlerMethodArgumentResolver {
    private User fakeUser;

    public FakeUserArgumentResolver() {
        this.fakeUser = new User();

        //Arbitrary values
        fakeUser.setId(424L);
        fakeUser.setUid("0284024804");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return fakeUser;
    }
}
