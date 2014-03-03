package mayte;

import java.lang.StringBuilder;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GoalServlet
 */
public class GoalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoalServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log("doGet",request);
		
		HttpSession session = request.getSession();
		log("Session.id = "+session.getId());
		
		String goal = (String)session.getAttribute("user");

		Cookie cookie = new Cookie("user", goal);
		response.addCookie(cookie);
		response.sendRedirect("goal.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log("doPost",request);
		
		HttpSession session = request.getSession();
		log("  Session.id = "+session.getId());

		String goal = request.getParameter("user");
		session.setAttribute("user", goal);
		log("  user="+goal);
		
		Cookie cookie = new Cookie("user", goal);
		response.addCookie(cookie);
		response.sendRedirect("goal.html");
	}

	void log(String where, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder(where+":\n");
		sb.append("ContextPath="+request.getContextPath()+"\n");
		
//		sb.append("Headers::\n");
//		Enumeration<String> headers = request.getHeaderNames();
//		while (headers.hasMoreElements()) {
//			String name  = headers.nextElement();
//			String value = request.getHeader(name);
//			sb.append("  "+name+"="+value+"\n");
//		}
//		
		sb.append("Parameters::\n");
		@SuppressWarnings("unchecked")
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String name  = params.nextElement();
			String value = request.getParameter(name);
			sb.append("  "+name+"="+value+"\n");
		}
		
		sb.append("Cookies::\n");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				sb.append("  "+cookie.getName()+"="+cookie.getValue()+"\n");
			}
		}
		log(sb.toString());
	}

}
