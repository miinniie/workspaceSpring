package com.hanwha.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	//컨트롤러가 호출되기 전에 인터셉터 클래스가 실행된다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler )
			throws Exception {
			//로그인 여부를 확인하고 계속 진행할 경우 true, 진행 멈추고 로그인으로 변경할 경우 false를 리턴한다.
		
		HttpSession session= request.getSession();
		//session에서 로그인 여부를 저장해놓은 변수 logStatus의 정보를 얻어온다.
		String logStatus = (String)session.getAttribute("logStatus"); //Y null Y가 아닌 다른 문자
		
		if(logStatus != null && logStatus.equals("Y")) {//로그인 상태일때
			return true;//가던길 갈경우
		}else {//로그인이 안된경우 로그인 페이지로 이동
						//			myapp이 구해지는게 앞에 request~ path까지..
			response.sendRedirect(request.getContextPath()+"/member/loginForm");
			return false;//로그인 폼으로 이동할 경우
		}
		
		
	}
	//컨트롤러 실행 후 호출되는 인터셉터클래스
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex)throws Exception{
		
	}
	//뷰로 이동하기 전 호출되는 인터셉터
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
		
	}
}
