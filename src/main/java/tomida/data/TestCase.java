package tomida.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {

  @JacksonXmlProperty(localName = "name", isAttribute = true)
  private String name;

  @JacksonXmlProperty(localName = "classname", isAttribute = true)
  private String className;

  @JacksonXmlProperty(localName = "failure", isAttribute = true)
  private Failure failure;

  public boolean isSucceeded() {
    return failure != null;
  }
}
