package cn.haiwai.newsnotification.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

public class EncodingServletFilter  implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 字符编码
     */
    private final String ENCODING = "UTF-8";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        //System.out.println("uri:"+uri);
        String ch = URLDecoder.decode(uri, ENCODING);
        //System.out.println("解码后的uri："+ch);
        if (uri.equals(ch)) {
            chain.doFilter(request,response);
            return;
        }
        ch = ch.substring(req.getContextPath().length());
        request.getRequestDispatcher(ch).forward(request,response);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
