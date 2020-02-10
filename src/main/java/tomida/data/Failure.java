package tomida.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Failure {

  @JacksonXmlProperty(localName = "message", isAttribute = true)
  private String message;

  @JacksonXmlProperty(localName = "type", isAttribute = true)
  private String type;
}
