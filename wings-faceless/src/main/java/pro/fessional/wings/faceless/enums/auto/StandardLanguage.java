package pro.fessional.wings.faceless.enums.auto;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.i18n.LocaleResolver;
import pro.fessional.wings.faceless.enums.StandardLanguageEnum;

import java.util.Locale;

/**
 * language + "_" + country，使用`_`分隔，zh_CN，在解析中，也支持zh-CN
 *
 * @author trydofor
 * @see Locale#toString()
 * @since 2020-11-12
 */
public enum StandardLanguage implements StandardLanguageEnum {

    SUPER(1020100, "standard_language", "标准语言", "classpath:/wings-tmpl/StandardLanguageTemplate.java"),
    AR_AE(1020101, "ar_AE", "阿拉伯联合酋长国", ""),
    DE_DE(1020102, "de_DE", "德语", ""),
    EN_US(1020103, "en_US", "美国英语", ""),
    ES_ES(1020104, "es_ES", "西班牙语", ""),
    FR_FR(1020105, "fr_FR", "法语", ""),
    IT_IT(1020106, "it_IT", "意大利语", ""),
    JA_JP(1020107, "ja_JP", "日语", ""),
    KO_KR(1020108, "ko_KR", "韩语", ""),
    RU_RU(1020109, "ru_RU", "俄语", ""),
    TH_TH(1020110, "th_TH", "泰国语", ""),
    ZH_CN(1020111, "zh_CN", "简体中文", ""),
    ZH_HK(1020112, "zh_HK", "繁体中文", ""),
    ;
    public static final String $SUPER = SUPER.code;
    public static final String $AR_AE = AR_AE.code;
    public static final String $DE_DE = DE_DE.code;
    public static final String $EN_US = EN_US.code;
    public static final String $ES_ES = ES_ES.code;
    public static final String $FR_FR = FR_FR.code;
    public static final String $IT_IT = IT_IT.code;
    public static final String $JA_JP = JA_JP.code;
    public static final String $KO_KR = KO_KR.code;
    public static final String $RU_RU = RU_RU.code;
    public static final String $TH_TH = TH_TH.code;
    public static final String $ZH_CN = ZH_CN.code;
    public static final String $ZH_HK = ZH_HK.code;

    public static final boolean useIdAsKey = false;
    private final int id;
    private final String code;
    private final String desc;
    private final String info;

    private final String ukey;
    private final String rkey;
    private final Locale locl;

    StandardLanguage(int id, String code, String desc, String info) {
        this.id = id;
        this.code = code;
        this.desc = desc;
        this.info = info;
        this.ukey = useIdAsKey ? "id" + id : code;
        this.rkey = "sys_constant_enum.desc." + ukey;
        this.locl = LocaleResolver.locale(code);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getType() {
        return "standard_language";
    }

    @Override
    public @NotNull String getInfo() {
        return info;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Locale toLocale() {
        return locl;
    }

    @Override
    public @NotNull String getBase() {
        return "sys_constant_enum";
    }

    @Override
    public @NotNull String getKind() {
        return "desc";
    }

    @Override
    public @NotNull String getUkey() {
        return ukey;
    }

    @Override
    public @NotNull String getCode() {
        return code;
    }

    @Override
    public @NotNull String getHint() {
        return desc;
    }

    @Override
    public @NotNull String getI18nCode() {
        return rkey;
    }
}
