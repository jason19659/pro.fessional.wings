package pro.fessional.wings.faceless.spring.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import pro.fessional.wings.faceless.database.FacelessDataSources;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author trydofor
 * @since 2019-06-25
 */
@Configuration
@ConditionalOnProperty(name = "spring.wings.jdbctemplate.enabled", havingValue = "true")
public class JdbcTemplateConfiguration {

    @Bean
    @Order
    @ConditionalOnMissingBean(JdbcTemplate.class)
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("plainJdbcTemplate")
    @ConditionalOnBean(FacelessDataSources.class)
    public Map<String, JdbcTemplate> plainJdbcTemplate(FacelessDataSources dataSources) {
        LinkedHashMap<String, DataSource> plains = dataSources.plains();
        LinkedHashMap<String, JdbcTemplate> templates = new LinkedHashMap<>(plains.size());
        for (Map.Entry<String, DataSource> entry : plains.entrySet()) {
            templates.put(entry.getKey(), new JdbcTemplate(entry.getValue()));
        }
        return templates;
    }
}
