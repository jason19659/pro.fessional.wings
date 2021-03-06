package pro.fessional.wings.example.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.fessional.mirana.data.R;
import pro.fessional.wings.silencer.context.WingsI18nContext;
import pro.fessional.wings.silencer.datetime.DateTimePattern;
import pro.fessional.wings.slardar.servlet.resolver.WingsLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author trydofor
 * @since 2019-06-30
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class TestJsonController {

    private final MessageSource messageSource;
    private final WingsLocaleResolver wingsLocaleResolver;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JsonIt {
        private boolean boolVal = false;
        private int intVal = Integer.MAX_VALUE - 1;
        private long longVal = Long.MAX_VALUE - 1;
        private float floatVal = 1.1F;
        private double doubleVal = 2.2D;
        private BigDecimal decimalVal = new BigDecimal("3.3");
        private Integer intNull = null;
        private Long longNull = null;
        private LocalDate localDateVal = LocalDate.now();
        private LocalTime localTimeVal = LocalTime.now();
        private LocalDateTime localDateTimeVal = LocalDateTime.now();
        private ZonedDateTime zonedDateTimeVal = ZonedDateTime.now();
        @JsonFormat(pattern = DateTimePattern.PTN_FULL_19V)
        private ZonedDateTime zonedDateTimePtn = ZonedDateTime.now();
        private Instant instantVal = Instant.now();
        private Date utilDateVal = new Date();
        private Calendar calendarVal = Calendar.getInstance();
        private List<String> listVal = Arrays.asList("字符串", "列表");
        private Map<String, Long> mapVal = new HashMap<String, Long>() {{put("Map", 1L);}};
        private String hello;
        private ZoneId systemZoneId;
        private ZoneId userZoneId;
    }

    @RequestMapping("/test/test.json")
    @ResponseBody
    public R<JsonIt> jsonIt(HttpServletRequest request) {
        JsonIt json = new JsonIt();
        ZonedDateTime now = ZonedDateTime.now();
        @NotNull WingsI18nContext ctx = wingsLocaleResolver.resolveI18nContext(request);
        ZonedDateTime userDate = now.withZoneSameInstant(ctx.getZoneIdOrDefault());
        json.setZonedDateTimeVal(userDate);
        Locale locale = ctx.getLocaleOrDefault();
        json.setHello(messageSource.getMessage("user.hello", new Object[]{}, locale));
        json.setUserZoneId(ctx.getZoneId());
        json.setSystemZoneId(ZoneId.systemDefault());
        return R.okData(json);
    }
}
