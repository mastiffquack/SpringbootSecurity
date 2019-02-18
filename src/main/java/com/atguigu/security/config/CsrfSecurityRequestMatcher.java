package com.atguigu.security.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CsrfSecurityRequestMatcher implements RequestMatcher{
    public Log log = LogFactory.getLog(getClass());
    /**
     * 需要排除的url列表
     */
    private List<String> execludeUrls;
    /**
     * 需求csrf判断的url列表
     */
    private List<String>includeUrls;

    public List<String> getExecludeUrls() {
        return execludeUrls;
    }

    public void setExecludeUrls(List<String> execludeUrls) {
        this.execludeUrls = execludeUrls;
    }

    public List<String> getIncludeUrls() {
        return includeUrls;
    }

    public void setIncludeUrls(List<String> includeUrls) {
        this.includeUrls = includeUrls;
    }

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        /*if(execludeUrls!=null && execludeUrls.size() >0){
            String servletPath = httpServletRequest.getServletPath();
            for (String url :execludeUrls){
                if(servletPath.contains(url)){
                    log.info("csrf no contain"+servletPath);
                    //false不走csrf判断
                    return false;
                }
            }
        }*/
        if(includeUrls!=null && includeUrls.size() >0){
            String servletPath = httpServletRequest.getServletPath();
            for (String url :includeUrls){
                if(servletPath.contains(url)){
                    System.out.println("csrf contain"+servletPath);
                    //true走csrf判断
                    return true;
                }
            }
        }
        return false;
    }
}
