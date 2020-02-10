package tomida.log;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KGPLogParser {

  public OptionalDouble getExecTime(final List<String> log) {
    final Pattern pattern = Pattern.compile("^real\\s+(\\d+)m(\\d{1,2}\\.\\d+)s");

    return log.stream()
        .map(pattern::matcher)
        .filter(Matcher::find)
        .mapToDouble(e -> {
          final String minute = e.group(1);
          final String second = e.group(2);

          return Double.parseDouble(minute) * 60 + Double.parseDouble(second);
        })
        .findFirst();
  }

  public Optional<Integer> getMaxGeneration(final List<String> log) {
    final Pattern pattern = Pattern.compile("the era of (\\d+)(th|rd|nd|st) generation\\.");
    return log.stream()
        .map(pattern::matcher)
        .filter(Matcher::find)
        .map(e -> e.group(1))
        .map(Integer::parseInt)
        .max(Comparator.naturalOrder());
  }
}
