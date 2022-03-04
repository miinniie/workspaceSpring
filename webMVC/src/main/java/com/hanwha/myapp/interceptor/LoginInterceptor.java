package com.hanwha.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	//��Ʈ�ѷ��� ȣ��Ǳ� ���� ���ͼ��� Ŭ������ ����ȴ�.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler )
			throws Exception {
			//�α��� ���θ� Ȯ���ϰ� ��� ������ ��� true, ���� ���߰� �α������� ������ ��� false�� �����Ѵ�.
		
		HttpSession session= request.getSession();
		//session���� �α��� ���θ� �����س��� ���� logStatus�� ������ ���´�.
		String logStatus = (String)session.getAttribute("logStatus"); //Y null Y�� �ƴ� �ٸ� ����
		
		if(logStatus != null && logStatus.equals("Y")) {//�α��� �����϶�
			return true;//������ �����
		}else {//�α����� �ȵȰ�� �α��� �������� �̵�
						//			myapp�� �������°� �տ� request~ path����..
			response.sendRedirect(request.getContextPath()+"/member/loginForm");
			return false;//�α��� ������ �̵��� ���
		}
		
		
	}
	//��Ʈ�ѷ� ���� �� ȣ��Ǵ� ���ͼ���Ŭ����
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex)throws Exception{
		
	}
	//��� �̵��ϱ� �� ȣ��Ǵ� ���ͼ���
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
		
	}
}
