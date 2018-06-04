package Interceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * 登录拦截器：
 *
 * @author: @我没有三颗心脏
 * @create: 2018-05-02-下午 19:28
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static String PREFIX = "/fore";
    private static String SERVLET_PATH = "/foreServlet";
    private static String BACK ="/admin";  //后台管理员验证
	/**
	 * 在业务处理器处理请求之前被调用
	 * - 如果返回false
	 * 从当前的拦截器往回执行所有拦截器的afterCompletion()方法，再退出拦截器链
	 * - 如果返回true
	 * 执行下一个拦截器，知道素有的拦截器都执行完毕
	 * 再执行被拦截的Controller
	 * 然后进入拦截器链，
	 * 从最后一个拦截器往回执行所有的postHandel()方法
	 * 接着再从最后一个拦截器往回执行所有的afterCompletion()方法
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

          request.setCharacterEncoding("UTF-8"); //同时把所有请求的字符编码设置为UTF-8
		
		
		

	      String[] noNeedAuthPage = new String[]{
	                "home",
	                "checkLogin",
	                "register",
	                "loginAjax",
	                "login",
	                "product",
	                "category",
	                "search"
	    };
	    
	    String contextPath = request.getServletContext().getContextPath();
	    String uri = request.getRequestURI();
	    uri = StringUtils.remove(uri, contextPath);
	    if (uri.startsWith(PREFIX) && !uri.startsWith(SERVLET_PATH)) {
	    	String method = StringUtils.substringAfterLast(uri, PREFIX);
	        if (!Arrays.asList(noNeedAuthPage).contains(method)) {
	        	User user = (User) request.getSession().getAttribute("user");
	            if (null == user) {
	            	response.sendRedirect("login.jsp");
	                return false;
	            }
	        }
	    }
	    
	    //用于后台的验证	   
	    /*
	    if(uri.startsWith(BACK)){
	    	Administrator admin = (Administrator) request.getSession().getAttribute("admin");
	    	if(null == admin){
	    		response.sendRedirect("login_admin.jsp");
	    		return false;
	    	}
	    }*/
		
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后，生成视图之前执行的动作
	 * 可在 modelAndView 中加入数据，比如当前的时间
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等
	 * 当有拦截器抛出异常时，会从当前拦截器往回执行所有的拦截器的afterCompletion()方法
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
