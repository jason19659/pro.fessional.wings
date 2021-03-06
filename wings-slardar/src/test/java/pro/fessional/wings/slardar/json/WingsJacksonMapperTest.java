package pro.fessional.wings.slardar.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.fessional.mirana.data.R;
import pro.fessional.mirana.i18n.I18nString;
import pro.fessional.wings.silencer.datetime.DateTimePattern;
import pro.fessional.wings.slardar.jackson.JsonI18nString;
import pro.fessional.wings.slardar.jackson.StringMapGenerator;
import pro.fessional.wings.slardar.jackson.StringMapHelper;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2019-06-26
 */
@SpringBootTest(properties = {"debug = true"})
public class WingsJacksonMapperTest {

    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper objectMapper;

    @Setter(onMethod = @__({@Autowired}))
    private XmlMapper xmlMapper;

    @Autowired
    private void init() {
        System.out.println("=== set locale to us ===");
        Locale.setDefault(Locale.US);
    }

    @Test
    public void testEquals() throws IOException {
        System.out.println("=== ZoneId= " + ZoneId.systemDefault());
        JsonIt it = new JsonIt();
        System.out.println("===== to string ======");
        System.out.println(it);
        String json = objectMapper.writeValueAsString(it);
        System.out.println("===== write json ======");
        System.out.println(json);
        JsonIt obj = objectMapper.readValue(json, JsonIt.class);
        System.out.println("===== read json ======");
        System.out.println(obj);

        String json2 = objectMapper.writeValueAsString(obj);
        JsonIt obj2 = objectMapper.readValue(json2, JsonIt.class);

        assertEquals(json, json2);
        assertEquals(obj, obj2);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement
    public static class JsonIt {
        @JsonProperty("bool-val")
        private boolean boolVal = false;
        private int intVal = Integer.MAX_VALUE - 1;
        private long longVal = Long.MAX_VALUE - 1;
        private float floatVal = 1.1F;
        private double doubleVal = 2.2D;
        private BigDecimal decimalVal = new BigDecimal("3.3");
        private Integer intNull = null;
        private Long longNull = null;
        private LocalDateTime localDateTimeVal = LocalDateTime.parse("2020-06-01T12:34:46");
        private LocalDate localDateVal = localDateTimeVal.toLocalDate();
        private LocalTime localTimeVal = localDateTimeVal.toLocalTime();
        private ZonedDateTime zonedDateTimeVal = localDateTimeVal.atZone(ZoneId.of("Asia/Shanghai"));
        @JsonFormat(pattern = DateTimePattern.PTN_FULL_23V)
        private ZonedDateTime zonedDateTimeValV = zonedDateTimeVal.withZoneSameInstant(ZoneId.of("America/New_York"));
        @JsonFormat(pattern = DateTimePattern.PTN_FULL_23Z)
        private ZonedDateTime zonedDateTimeValZ = zonedDateTimeVal.withZoneSameInstant(ZoneId.of("America/New_York"));
        private Instant instantVal = Instant.parse("2020-06-01T12:34:46.000Z");
        private Date utilDateVal = new Date(zonedDateTimeValV.toEpochSecond() * 1000);
        private List<String> listVal = Arrays.asList("字符串", "列表");
        private Map<String, Long> mapVal = new HashMap<String, Long>() {{put("Map", 1L);}};
    }

    @Test
    public void testI18nString() throws IOException {
        I18nJson obj = new I18nJson();
        ObjectWriter jackson = objectMapper.writerWithDefaultPrettyPrinter();
        String json = jackson.writeValueAsString(obj);
        assertEquals("{\n" +
                "  \"codeManual\" : \"{0} can not be empty\",\n" +
                "  \"codeIgnore\" : \"base.not-empty\",\n" +
                "  \"textAuto\" : \"textAuto can not be empty\",\n" +
                "  \"textDisabled\" : {\n" +
                "    \"code\" : \"base.not-empty\",\n" +
                "    \"hint\" : \"\",\n" +
                "    \"args\" : [ \"textDisabled\" ]\n" +
                "  },\n" +
                "  \"longIgnore\" : \"0\",\n" +
                "  \"mapIgnore\" : {\n" +
                "    \"ikey\" : \"ival\"\n" +
                "  },\n" +
                "  \"mapDisabled\" : {\n" +
                "    \"i18n\" : {\n" +
                "      \"code\" : \"base.not-empty\",\n" +
                "      \"hint\" : \"\",\n" +
                "      \"args\" : [ \"textDisabled\" ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"mapAuto\" : {\n" +
                "    \"i18n\" : \"textAuto can not be empty\"\n" +
                "  }\n" +
                "}", json.trim());
    }

    @Data
    @XmlRootElement
    public static class I18nJson {
        @JsonI18nString // 有效
        private String codeManual = "base.not-empty";
        private String codeIgnore = "base.not-empty";
        // 自动
        private I18nString textAuto = new I18nString("base.not-empty", "", "textAuto");
        @JsonI18nString(false) //禁用
        private I18nString textDisabled = new I18nString("base.not-empty", "", "textDisabled");
        @JsonI18nString // 无效
        private Long longIgnore = 0L;
        @JsonI18nString // 无效
        private Map<String, String> mapIgnore = Collections.singletonMap("ikey", "ival");
        @JsonI18nString(false) //禁用
        private Map<String, I18nString> mapDisabled = Collections.singletonMap("i18n", textDisabled);
        // 自动
        private Map<String, I18nString> mapAuto = Collections.singletonMap("i18n", textAuto);
    }


    @Test
    public void testI18nResult() throws IOException {
        ObjectWriter jackson = objectMapper.writerWithDefaultPrettyPrinter();

        R<I18nJson> r1 = R.ok("这是一个消息", new I18nJson());
        String j1 = jackson.writeValueAsString(r1);
        assertEquals("{\n" +
                "  \"success\" : true,\n" +
                "  \"message\" : \"这是一个消息\",\n" +
                "  \"data\" : {\n" +
                "    \"codeManual\" : \"{0} can not be empty\",\n" +
                "    \"codeIgnore\" : \"base.not-empty\",\n" +
                "    \"textAuto\" : \"textAuto can not be empty\",\n" +
                "    \"textDisabled\" : {\n" +
                "      \"code\" : \"base.not-empty\",\n" +
                "      \"hint\" : \"\",\n" +
                "      \"args\" : [ \"textDisabled\" ]\n" +
                "    },\n" +
                "    \"longIgnore\" : \"0\",\n" +
                "    \"mapIgnore\" : {\n" +
                "      \"ikey\" : \"ival\"\n" +
                "    },\n" +
                "    \"mapDisabled\" : {\n" +
                "      \"i18n\" : {\n" +
                "        \"code\" : \"base.not-empty\",\n" +
                "        \"hint\" : \"\",\n" +
                "        \"args\" : [ \"textDisabled\" ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"mapAuto\" : {\n" +
                "      \"i18n\" : \"textAuto can not be empty\"\n" +
                "    }\n" +
                "  }\n" +
                "}", j1);

        R<I18nJson> r2 = r1.toI18n("base.not-empty", "第一个参数");
        String j2 = jackson.writeValueAsString(r2);
        assertEquals("{\n" +
                "  \"success\" : true,\n" +
                "  \"message\" : \"第一个参数 can not be empty\",\n" +
                "  \"data\" : {\n" +
                "    \"codeManual\" : \"{0} can not be empty\",\n" +
                "    \"codeIgnore\" : \"base.not-empty\",\n" +
                "    \"textAuto\" : \"textAuto can not be empty\",\n" +
                "    \"textDisabled\" : {\n" +
                "      \"code\" : \"base.not-empty\",\n" +
                "      \"hint\" : \"\",\n" +
                "      \"args\" : [ \"textDisabled\" ]\n" +
                "    },\n" +
                "    \"longIgnore\" : \"0\",\n" +
                "    \"mapIgnore\" : {\n" +
                "      \"ikey\" : \"ival\"\n" +
                "    },\n" +
                "    \"mapDisabled\" : {\n" +
                "      \"i18n\" : {\n" +
                "        \"code\" : \"base.not-empty\",\n" +
                "        \"hint\" : \"\",\n" +
                "        \"args\" : [ \"textDisabled\" ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"mapAuto\" : {\n" +
                "      \"i18n\" : \"textAuto can not be empty\"\n" +
                "    }\n" +
                "  }\n" +
                "}", j2);
    }

    @Test
    public void testXml() throws IOException {
        ObjectWriter jackson = xmlMapper.writerWithDefaultPrettyPrinter();
        I18nJson i18nJson = new I18nJson();
        JsonIt jsonIt = new JsonIt();
        String i18n = jackson.writeValueAsString(i18nJson);
        String json = jackson.writeValueAsString(jsonIt);
        assertEquals("<I18nJson>\n" +
                "  <codeManual>{0} can not be empty</codeManual>\n" +
                "  <codeIgnore>base.not-empty</codeIgnore>\n" +
                "  <textAuto>textAuto can not be empty</textAuto>\n" +
                "  <textDisabled>\n" +
                "    <code>base.not-empty</code>\n" +
                "    <hint></hint>\n" +
                "    <args>\n" +
                "      <item>textDisabled</item>\n" +
                "    </args>\n" +
                "  </textDisabled>\n" +
                "  <longIgnore>0</longIgnore>\n" +
                "  <mapIgnore>\n" +
                "    <ikey>ival</ikey>\n" +
                "  </mapIgnore>\n" +
                "  <mapDisabled>\n" +
                "    <i18n>\n" +
                "      <code>base.not-empty</code>\n" +
                "      <hint></hint>\n" +
                "      <args>\n" +
                "        <item>textDisabled</item>\n" +
                "      </args>\n" +
                "    </i18n>\n" +
                "  </mapDisabled>\n" +
                "  <mapAuto>\n" +
                "    <i18n>textAuto can not be empty</i18n>\n" +
                "  </mapAuto>\n" +
                "</I18nJson>", i18n.trim());
        assertEquals("<JsonIt>\n" +
                "  <intVal>2147483646</intVal>\n" +
                "  <longVal>9223372036854775806</longVal>\n" +
                "  <floatVal>1.1</floatVal>\n" +
                "  <doubleVal>2.2</doubleVal>\n" +
                "  <decimalVal>3.3</decimalVal>\n" +
                "  <localDateTimeVal>2020-06-01 12:34:46</localDateTimeVal>\n" +
                "  <localDateVal>2020-06-01</localDateVal>\n" +
                "  <localTimeVal>12:34:46</localTimeVal>\n" +
                "  <zonedDateTimeVal>2020-06-01 12:34:46</zonedDateTimeVal>\n" +
                "  <zonedDateTimeValV>2020-06-01 00:34:46.000 America/New_York</zonedDateTimeValV>\n" +
                "  <zonedDateTimeValZ>2020-06-01 00:34:46.000 -0400</zonedDateTimeValZ>\n" +
                "  <instantVal>2020-06-01T12:34:46Z</instantVal>\n" +
                "  <utilDateVal>2020-06-01 12:34:46</utilDateVal>\n" +
                "  <listVal>\n" +
                "    <listVal>字符串</listVal>\n" +
                "    <listVal>列表</listVal>\n" +
                "  </listVal>\n" +
                "  <mapVal>\n" +
                "    <Map>1</Map>\n" +
                "  </mapVal>\n" +
                "  <bool-val>false</bool-val>\n" +
                "</JsonIt>", json.trim());
    }

    @Test
    public void testTreeMapGenerator() throws IOException {
        I18nJson i18nJson = new I18nJson();
        JsonIt jsonIt = new JsonIt();
        StringMapGenerator t1 = StringMapGenerator.treeMap();
        StringMapGenerator t2 = StringMapGenerator.linkMap();
        objectMapper.writeValue(t1, i18nJson);
        objectMapper.writeValue(t2, jsonIt);
        assertEquals("{code=base.not-empty, codeIgnore=base.not-empty, codeManual={0} can not be empty, hint=, i18n=textAuto can not be empty, ikey=ival, longIgnore=0, textAuto=textAuto can not be empty}", t1.getResultTree().toString().trim());
        assertEquals("{intVal=2147483646, longVal=9223372036854775806, floatVal=1.1, doubleVal=2.2, decimalVal=3.3, localDateTimeVal=2020-06-01 12:34:46, localDateVal=2020-06-01, localTimeVal=12:34:46, zonedDateTimeVal=2020-06-01 12:34:46, zonedDateTimeValV=2020-06-01 00:34:46.000 America/New_York, zonedDateTimeValZ=2020-06-01 00:34:46.000 -0400, instantVal=2020-06-01T12:34:46Z, utilDateVal=2020-06-01 12:34:46, listVal=列表, Map=1, bool-val=false}", t2.getResultTree().toString().trim());
    }

    @Test
    public void testHelper() {
        I18nJson i18nJson = new I18nJson();
        JsonIt jsonIt = new JsonIt();
        Map<String, String> j1 = StringMapHelper.json(i18nJson, objectMapper);
        Map<String, String> j2 = StringMapHelper.json(jsonIt, objectMapper);

        Map<String, String> x1 = StringMapHelper.jaxb(i18nJson);
        Map<String, String> x2 = StringMapHelper.jaxb(jsonIt);

        assertEquals("{code=base.not-empty, codeIgnore=base.not-empty, codeManual={0} can not be empty, hint=, i18n=textAuto can not be empty, ikey=ival, longIgnore=0, textAuto=textAuto can not be empty}", j1.toString());
        assertEquals("{Map=1, bool-val=false, decimalVal=3.3, doubleVal=2.2, floatVal=1.1, instantVal=2020-06-01T12:34:46Z, intVal=2147483646, listVal=列表, localDateTimeVal=2020-06-01 12:34:46, localDateVal=2020-06-01, localTimeVal=12:34:46, longVal=9223372036854775806, utilDateVal=2020-06-01 12:34:46, zonedDateTimeVal=2020-06-01 12:34:46, zonedDateTimeValV=2020-06-01 00:34:46.000 America/New_York, zonedDateTimeValZ=2020-06-01 00:34:46.000 -0400}", j2.toString());
        assertEquals("{args=textDisabled, codeIgnore=base.not-empty, codeManual=base.not-empty, hint=, key=ikey, longIgnore=0, value=ival}", x1.toString());
        assertEquals("{boolVal=false, decimalVal=3.3, doubleVal=2.2, floatVal=1.1, intVal=2147483646, key=Map, listVal=列表, longVal=9223372036854775806, utilDateVal=2020-06-01T12:34:46+08:00, value=1}", x2.toString());
    }

    @Data
    public static class NumberAsString {
        private long numLong = 10L;
        private int numInt = 10;
        private double numDouble = 3.14159;
        private BigDecimal numDecimal = new BigDecimal("2.71828");
    }

    @Data
    public static class NumberAsNumber {
        @JsonRawValue()
        private long numLong = 10L;
        @JsonRawValue()
        private int numInt = 10;
        @JsonRawValue()
        private double numDouble = 3.14159;
        @JsonRawValue()
        private BigDecimal numDecimal = new BigDecimal("2.71828");
    }

    @Test
    public void testNumber() throws JsonProcessingException {
        NumberAsString nas = new NumberAsString();
        NumberAsNumber nan = new NumberAsNumber();
        String s1 = objectMapper.writeValueAsString(nas);
        String s2 = objectMapper.writeValueAsString(nan);
        //
        assertEquals("{\"numLong\":\"10\",\"numInt\":\"10\",\"numDouble\":\"3.14159\",\"numDecimal\":\"2.71828\"}",s1);
        assertEquals("{\"numLong\":10,\"numInt\":10,\"numDouble\":3.14159,\"numDecimal\":2.71828}",s2);
    }
}
