package pro.fessional.wings.slardar.spring.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.fessional.wings.slardar.servlet.WingsServletConst;
import pro.fessional.wings.slardar.servlet.filter.WingsTerminalFilter;
import pro.fessional.wings.slardar.servlet.resolver.WingsLocaleResolver;
import pro.fessional.wings.slardar.servlet.resolver.WingsRemoteResolver;

/**
 * @author trydofor
 * @since 2019-06-29
 */
@Configuration
@ConditionalOnProperty(name = "spring.wings.slardar.terminal.enabled", havingValue = "true")
public class WingsFilterTerminalConfiguration {

    private final Log logger = LogFactory.getLog(WingsFilterTerminalConfiguration.class);

    @Bean
    @ConditionalOnBean({WingsLocaleResolver.class, WingsRemoteResolver.class})
    public WingsTerminalFilter wingsTerminalFilter(WingsLocaleResolver localeResolver, WingsRemoteResolver remoteResolver) {
        logger.info("Wings conf Terminal filter");
        WingsTerminalFilter filter = new WingsTerminalFilter(localeResolver, remoteResolver);
        filter.setOrder(WingsServletConst.ORDER_FILTER_TERMINAL);
        return filter;
    }
}
