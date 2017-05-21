package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.VerifySource;

public class BasicFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String nickname = "";
		int userId = 0;
		String verify = "";
		
		boolean isLogin = false;
		
		if(request.getCookies()!=null) {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie:cookies) {
				
				if(cookie.getName().equals("nickname")) {
					String[] sss = cookie.getValue().split("&");  //解密
					StringBuilder bs = new StringBuilder();
					for(String str:sss) {
						int x = Integer.valueOf(str);
						char c = (char)x;
						bs.append(c);
					}
					nickname = bs.toString();
				}
				
				if(cookie.getName().equals("user_id")) {
					try {
						userId = Integer.valueOf(cookie.getValue());
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				if(cookie.getName().equals("verify")) {
					verify = cookie.getValue();
				}
			}
			
			if(verify.equals(VerifySource.getMD5(userId))) {
				isLogin = true;
			}
			
		}
	
		request.setAttribute("requestURI", request.getRequestURI());
		request.setAttribute("isLogin", isLogin);
		request.setAttribute("nickname", nickname);
		request.setAttribute("userId", userId);
		request.setAttribute("verify", verify);
		
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
