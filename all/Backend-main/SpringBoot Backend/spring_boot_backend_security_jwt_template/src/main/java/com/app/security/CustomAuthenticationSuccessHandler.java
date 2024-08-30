package com.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("Successful authentication for user: " + authentication.getName());
        System.out.println("Roles: " + roles);
//        if (roles.contains("ROLE_ADMIN")) {
//            response.sendRedirect("/moviestest");
//       } 
// //           else if (roles.contains("ROLE_USER")) {
////            response.sendRedirect("/home");
////        } 
//            else {
//            response.sendRedirect("/home");
//        }
        
        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                // Redirect admin users to /moviestest
                response.sendRedirect("/moviestest");
            } else {
                // Redirect non-admin users to a default page
                response.sendRedirect("/home");
            }
        response.setContentType("application/json");
        response.getWriter().write("{ \"redirectUrl\": \"/moviestest\" }");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
