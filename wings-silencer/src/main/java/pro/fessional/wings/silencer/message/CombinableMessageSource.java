package pro.fessional.wings.silencer.message;


import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自己先处理，然后[兄弟连]处理，然后父类
 *
 * @author trydofor
 * @since 2019-09-15
 */
public class CombinableMessageSource extends AbstractMessageSource {

    private final ArrayList<OrderedMessageSource> orderedBrotherSources = new ArrayList<>(16);
    private final ConcurrentHashMap<String, ConcurrentHashMap<Locale, String>> codeLocaleString = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<Locale, MessageFormat>> codeLocaleFormat = new ConcurrentHashMap<>();

    @Override
    @Nullable
    protected String resolveCodeWithoutArguments(@NonNull String code, @NonNull Locale locale) {
        ConcurrentHashMap<Locale, String> cache = codeLocaleString.get(code);
        return cache == null ? null : cache.get(locale);
    }

    @Override
    @Nullable
    protected MessageFormat resolveCode(@NonNull String code, @NonNull Locale locale) {
        ConcurrentHashMap<Locale, MessageFormat> cache = codeLocaleFormat.get(code);
        return cache == null ? null : cache.get(locale);
    }

    public void removeMessage(String code) {
        removeMessage(code, null);
    }

    public void removeMessage(String code, Locale locale) {
        if (code == null) return;

        if (locale == null) {
            codeLocaleString.remove(code);
            codeLocaleFormat.remove(code);
        } else {
            remove(codeLocaleString, code, locale);
            remove(codeLocaleFormat, code, locale);
        }
    }

    private <T extends Map<Locale, ?>> void remove(Map<String, T> map, String code, Locale locale) {
        Map<Locale, ?> mapStr = map.get(code);
        if (mapStr != null) {
            mapStr.remove(locale);
            if (mapStr.isEmpty()) {
                map.remove(code);
            }
        }
    }

    public void addMessage(String code, Locale locale, String msg) {
        Assert.notNull(code, "Code must not be null");
        Assert.notNull(locale, "Locale must not be null");
        Assert.notNull(msg, "Message must not be null");

        codeLocaleString.computeIfAbsent(code, key -> new ConcurrentHashMap<>())
                        .putIfAbsent(locale, msg);

        codeLocaleFormat.computeIfAbsent(code, key -> new ConcurrentHashMap<>())
                        .computeIfAbsent(locale, key -> createMessageFormat(msg, key));

        if (logger.isDebugEnabled()) {
            logger.debug("Added message [" + msg + "] for code [" + code + "] and Locale [" + locale + "]");
        }
    }

    public void addMessages(Map<String, String> messages, Locale locale) {
        Assert.notNull(messages, "Messages Map must not be null");
        messages.forEach((code, msg) -> addMessage(code, locale, msg));
    }

    public void addMessages(Properties messages, Locale locale) {
        Assert.notNull(messages, "Properties must not be null");
        messages.forEach((code, msg) -> addMessage(code.toString(), locale, msg.toString()));
    }

    @Override
    public void setParentMessageSource(@Nullable MessageSource parent) {
        synchronized (orderedBrotherSources) {
            final int size = orderedBrotherSources.size();
            if (size == 0) {
                super.setParentMessageSource(parent);
            } else {
                orderedBrotherSources.get(size - 1).source.setParentMessageSource(parent);
            }
        }
    }

    /**
     * 按order顺序，组合其他messageSource，序号小的优先解析message。
     * 当order==Integer.MIN_VALUE时，表示移除该message
     *
     * @param messageSource 其他messageSource
     * @param order         顺序，#Integer.MIN_VALUE时表示移除
     */
    public void addMessages(HierarchicalMessageSource messageSource, int order) {
        Assert.notNull(messageSource, "messageSource must not be null");
        synchronized (orderedBrotherSources) {
            final int size = orderedBrotherSources.size();
            MessageSource thatParent;
            if (size == 0) {
                thatParent = getParentMessageSource();
            } else {
                thatParent = orderedBrotherSources.get(size - 1).source.getParentMessageSource();
            }

            orderedBrotherSources.removeIf(o -> o.source == messageSource);
            if (order != Integer.MIN_VALUE) {
                orderedBrotherSources.add(new OrderedMessageSource(messageSource, order));
                orderedBrotherSources.sort(Comparator.comparingInt(o -> o.order));
            }

            for (int i = orderedBrotherSources.size() - 1; i >= 0; i--) {
                HierarchicalMessageSource source = orderedBrotherSources.get(i).source;
                source.setParentMessageSource(thatParent);
                thatParent = source;
            }

            super.setParentMessageSource(thatParent);
        }
    }

    /**
     * 移除组合的messageSource
     *
     * @param messageSource 被移除对象
     */
    public void removeMessages(HierarchicalMessageSource messageSource) {
        addMessages(messageSource, Integer.MIN_VALUE);
    }


    private static class OrderedMessageSource {
        private OrderedMessageSource(HierarchicalMessageSource source, int order) {
            this.order = order;
            this.source = source;
        }

        private final int order;
        private final HierarchicalMessageSource source;
    }
}
