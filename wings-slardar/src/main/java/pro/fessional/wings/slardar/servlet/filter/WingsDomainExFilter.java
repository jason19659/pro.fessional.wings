package pro.fessional.wings.slardar.servlet.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import pro.fessional.mirana.text.Wildcard;
import pro.fessional.wings.slardar.servlet.WingsServletConst;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static pro.fessional.wings.slardar.servlet.WingsServletConst.ATTR_DOMAIN_EXTEND;

/**
 * @author trydofor
 * @since 2019-11-16
 */
@RequiredArgsConstructor
public class WingsDomainExFilter implements OrderedFilter {

    private final String pathPrefix;
    private final Map<String, List<String[]>> hostWildcard;
    private final BiFunction<HttpServletRequest, String, HttpServletRequest> domainMapping;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String host = request.getServerName();
        String domain = null;

        out:
        for (Map.Entry<String, List<String[]>> entry : hostWildcard.entrySet()) {
            for (String[] ptn : entry.getValue()) {
                if (Wildcard.match(true, host, ptn)) {
                    domain = entry.getKey();
                    break out;
                }
            }
        }

        HttpServletRequest wrap = request;
        if (domain != null) {
            request.setAttribute(ATTR_DOMAIN_EXTEND, domain);
            String domainUrl = pathPrefix + domain + request.getRequestURI();
            wrap = domainMapping.apply(request, domainUrl);
        }

        chain.doFilter(wrap, res);
    }

    @Data
    public static class Config {
        private String prefix;
        private Map<String, List<String>> host;
        private Set<String> urlMapping;
    }

    //
    private int order = WingsServletConst.ORDER_FILTER_DOMAINEX;

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
