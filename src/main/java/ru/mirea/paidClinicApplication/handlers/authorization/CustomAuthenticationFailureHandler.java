package ru.mirea.paidClinicApplication.handlers.authorization;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write("вы ввели неверные данные для входа");
//        response.getWriter().flush();
//        response.getWriter().close();

//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write("<h1>ВЫ ВВЕЛИ НЕВЕРНЫЕ ДАННЫЕ</h1>");
//        response.getWriter().write("<a href=\"/login\">Назад</a>");
//        response.getWriter().flush();
//        response.getWriter().close();

        response.sendRedirect("/authentication_failure");
    }
}
