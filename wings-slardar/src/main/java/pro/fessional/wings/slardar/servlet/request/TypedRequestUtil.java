package pro.fessional.wings.slardar.servlet.request;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.cast.TypedCastUtil;
import pro.fessional.mirana.text.Wildcard;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型安全的获得request中的值。
 *
 * @author trydofor
 * @since 2019-07-03
 */
public class TypedRequestUtil {

    @Nullable
    public static <T> T getAttribute(HttpServletRequest request, String name, Class<T> claz) {
        Object obj = request.getAttribute(name);
        return TypedCastUtil.castObject(obj, claz);
    }

    @Nullable
    public static <T> T getAttribute(HttpServletRequest request, String name) {
        Object obj = request.getAttribute(name);
        return TypedCastUtil.castObject(obj, null);
    }

    @Nullable
    public static <T> T getAttributeIgnoreCase(HttpServletRequest request, String name) {
        return getAttributeIgnoreCase(request, name, null);
    }

    @Nullable
    public static <T> T getAttributeIgnoreCase(HttpServletRequest request, String name, Class<T> claz) {
        if (request == null || name == null) return null;

        Enumeration<String> names = request.getAttributeNames();
        while (names != null && names.hasMoreElements()) {
            String s = names.nextElement();
            if (name.equalsIgnoreCase(s)) {
                name = s;
                break;
            }
        }

        Object obj = request.getAttribute(name);
        return TypedCastUtil.castObject(obj, claz);
    }

    @NotNull
    public static String getRemoteIp(HttpServletRequest request, String... header) {
        if (header != null) {
            for (String h : header) {
                String ip = request.getHeader(h);
                if (ip != null) return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 带有contextpath进行URI的忽略大小写的全匹配，支持wildcard
     *
     * @param req  请求
     * @param path 路径，null或空为false
     * @return 是否匹配
     */
    public static boolean matchIgnoreCase(HttpServletRequest req, String path) {
        if (path == null || path.isEmpty()) return false;

        String uri = req.getRequestURI();
        int idx = uri.indexOf(';');
        if (idx > 0) {
            uri = uri.substring(0, idx);
        }

        return Wildcard.match(true, uri, req.getContextPath(), path);
    }

    /**
     * 带有contextpath进行URI的忽略大小写的全匹配，支持wildcard
     *
     * @param req  请求
     * @param path 路径，null或空为false
     * @return 是否匹配
     */
    public static boolean matchIgnoreCase(HttpServletRequest req, String... path) {
        if (path == null) return false;

        String uri = req.getRequestURI();
        int idx = uri.indexOf(';');
        if (idx > 0) {
            uri = uri.substring(0, idx);
        }

        for (String s : path) {
            if (s == null) continue;
            if (Wildcard.match(true, uri, req.getContextPath(), s)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public static String getParameter(Map<String, String[]> param, String name) {
        String[] vals = param.get(name);
        return (vals == null || vals.length == 0) ? null : vals[0];
    }

    @NotNull
    public static Map<String, String> getParameter(Map<String, String[]> param) {

        if (param == null) return Collections.emptyMap();

        HashMap<String, String> rst = new HashMap<>(param.size());
        for (Map.Entry<String, String[]> entry : param.entrySet()) {
            String[] vs = entry.getValue();
            if (vs != null && vs.length > 0) {
                rst.put(entry.getKey(), vs[0]);
            }
        }

        return rst;
    }

    /**
     * 有些获得 headers中的Bearer然后 Parameter的access_token
     * `Bearer`和`access_token` 不区分大小写，如果有多个token，取最后一个
     */
    @Nullable
    public static String getAccessToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = null;
        if (auth != null) {
            String bearer = "bearer";
            int p = StringUtils.indexOfIgnoreCase(auth, bearer);
            if (p >= 0) {
                token = auth.substring(p + bearer.length()).trim();
            }
        }
        if (token == null) {
            token = request.getParameter("access_token");
        }

        if (token != null) {
            int pos = token.indexOf(",");
            if (pos > 0) {
                String[] tks = token.split(",");
                token = tks[tks.length - 1];
            }
            token = token.trim();
        }

        return token;
    }
}
