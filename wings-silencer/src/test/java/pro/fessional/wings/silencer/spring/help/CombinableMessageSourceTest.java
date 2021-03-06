package pro.fessional.wings.silencer.spring.help;

import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import pro.fessional.wings.silencer.message.CombinableMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-09-16
 */
@SpringBootTest
public class CombinableMessageSourceTest {

    @Setter(onMethod = @__({@Autowired}))
    private MessageSource messageSource;
    @Setter(onMethod = @__({@Autowired}))
    private CombinableMessageSource combinableMessageSource;

    @Test
    public void combine() {

        Object[] args = {};
        String m1 = messageSource.getMessage("test.我的测试", args, Locale.CHINA);
        combinableMessageSource.addMessage("test.我的测试", Locale.CHINA, "啥都好用");
        String m2 = messageSource.getMessage("test.我的测试", args, Locale.CHINA);

        StaticMessageSource sms = new StaticMessageSource();
        sms.addMessage("test.mytest", Locale.CHINA, "又一个测试");
        combinableMessageSource.addMessages(sms, 1);
        String m3 = messageSource.getMessage("test.mytest", args, Locale.CHINA);

        assertEquals("test.我的测试", m1);// code
        assertEquals("啥都好用", m2);
        assertEquals("又一个测试", m3);
    }
}