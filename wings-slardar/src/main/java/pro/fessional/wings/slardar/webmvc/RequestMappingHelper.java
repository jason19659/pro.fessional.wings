package pro.fessional.wings.slardar.webmvc;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 列出所有RequestMapping标记的URL，
 * 注意，不是容器内所有 mapping
 * @see DispatcherServlet#getHandlerMappings()
 *
 */
public class RequestMappingHelper {

    public static Map<RequestMappingInfo, HandlerMethod> listAllMapping(ApplicationContext context) {
//        Map<String, HandlerMapping> matchingBeans =
//                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);

        RequestMappingHandlerMapping mapping = context.getBean(RequestMappingHandlerMapping.class);
        return mapping.getHandlerMethods();
    }

    public static void dealAllMapping(ApplicationContext context, BiConsumer<RequestMappingInfo, HandlerMethod> consumer) {
        Map<RequestMappingInfo, HandlerMethod> mapping = listAllMapping(context);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mapping.entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @NotNull
    public static List<Info> infoAllMapping(ApplicationContext context) {
        Map<RequestMappingInfo, HandlerMethod> mappings = listAllMapping(context);
        List<Info> result = new ArrayList<>(mappings.size() * 3 / 2);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mappings.entrySet()) {
            RequestMappingInfo key = entry.getKey();
            HandlerMethod value = entry.getValue();
            Method method = value.getMethod();
            String javaClass = method.getDeclaringClass().getName();
            String javaMethod = method.getName();
            String httpMethod = key.getMethodsCondition().getMethods().stream().map(Enum::name).collect(Collectors.joining(","));

            for (String url : key.getPatternsCondition().getPatterns()) {
                result.add(new Info(url, httpMethod, javaClass, javaMethod));
            }
        }
        return result;
    }

    @Data
    public static class Info {
        private final String url;
        private final String httpMethod;
        private final String javaClass;
        private final String javaMethod;

        /**
         * 是否包含以下 http method，空表示包含。
         *
         * @param method 指定
         * @return 包含true，否则false
         */
        public boolean hasMethod(RequestMethod method) {
            if (method == null || httpMethod.isEmpty()) return true;
            return httpMethod.contains(method.name());
        }

        public String toJson() {
            return "{url:\"" + url.replaceAll("\"", "\\\"") + "\",method=\"" + httpMethod + "\",java=\"" + javaClass + "#" + javaMethod + "\"}";
        }
    }
}
