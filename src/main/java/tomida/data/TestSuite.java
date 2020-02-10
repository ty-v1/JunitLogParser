package tomida.data;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "testsuite")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {

  private static final Pattern PATTERN = Pattern.compile("(Seed[0-9]+)");

  @JacksonXmlProperty(localName = "name", isAttribute = true)
  private String name;

  @JacksonXmlProperty(localName = "tests", isAttribute = true)
  private int tests;

  @JacksonXmlProperty(localName = "skipped", isAttribute = true)
  private int skipped;

  @JacksonXmlProperty(localName = "failures", isAttribute = true)
  private int failures;

  @JacksonXmlProperty(localName = "errors", isAttribute = true)
  private int errors;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "testcase")
  private List<TestCase> testCases;

  public String getContest() {
    return name.split("\\.")[1];
  }

  public int getSeed() {
    final Matcher matcher = Pattern.compile("Seed[0-9]+")
        .matcher(name);
    matcher.find();
    final String matched = matcher.group(0);

    return Integer.parseInt(matched.replace("Seed", ""));
  }

  public String getDepth() {
    final Matcher matcher = Pattern.compile("Depth(shallow|normal|deep)")
        .matcher(name);
    matcher.find();
    return matcher.group(1);
  }

  public int getMutationGeneratingCount() {
    if (name.contains("deep")) {
      return 40;
    } else if (name.contains("normal")) {
      return 100;
    } else {
      return 400;
    }
  }

  public boolean isSucceeded() {
    return failures == 0 && errors == 0 && skipped == 0;
  }

  @Override
  public String toString() {
    return String.format("%s,%d,%d,%b", getContest(), getSeed(), getMutationGeneratingCount(),
        isSucceeded());
  }
}
