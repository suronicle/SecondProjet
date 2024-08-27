package kr.co.book.interceptors;

import java.io.IOException;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.book.ecom.dto.loginsessionDTO;


public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 예: 인증이 필요하고 사용자가 인증되지 않은 경우
    	loginsessionDTO loginDto = (loginsessionDTO) request.getSession().getAttribute("login");
        if (loginDto == null) {
        	response.sendRedirect("/elogin"); // 로그인 페이지 URL
            return false; // 요청 처리 중단
        }
        return true; // 요청 계속 진행
    }

}