package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*") // 拦截所有请求
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();
        HttpSession session = req.getSession(false); // 如果没有session就返回null

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdmin = (loggedIn && Boolean.TRUE.equals(session.getAttribute("isAdmin")));

        // 登录/注册页面、静态资源不拦截
        if (path.equals("/login.jsp") || path.equals("/register.jsp") || path.startsWith("/static")) {
            chain.doFilter(request, response);
            return;
        }

        // 未登录用户尝试访问任何页面（除login和register），跳转登录页
        if (!loggedIn && !path.startsWith("/public") && !path.equals("/contact.jsp")) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // 普通用户尝试访问管理员路径（以 /admin 开头）
        if (path.startsWith("/admin") && !isAdmin) {
            res.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}

