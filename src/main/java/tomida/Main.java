package tomida;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import tomida.data.TestSuite;

public class Main {

  public static void main(final String[] args) {
    try {
      final Path inDir = Paths.get(args[0]);
      final Path outDir = Paths.get(args[1]);
      //
      final List<Path> logs = Files.walk(inDir, FileVisitOption.FOLLOW_LINKS)
          .filter(e -> !Files.isDirectory(e))
          .filter(e -> e.toString()
              .endsWith(".xml"))
          .collect(Collectors.toList());
      final Multimap<String, TestSuite> testSuites = ArrayListMultimap.create();
      for (final Path logFile : logs) {
        final String log = String.join("\n", Files.readAllLines(logFile));
        final TestSuite testSuite = new XmlMapper().readValue(log, TestSuite.class);
        final String contest = testSuite.getContest();
        testSuites.put(contest, testSuite);
      }
      //
      final Runner runner = new Runner(inDir);
      testSuites.forEach((k, v) -> {
        try {
          runner.writeToFile(outDir, k, v);
        } catch (final IOException e) {
          e.printStackTrace();
        }
      });
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
