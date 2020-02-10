package tomida.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tomida.data.TestSuite;

@RequiredArgsConstructor
public class KGPLogReader {

  private final Path rootDir;

  public List<String> readLog(final TestSuite testSuite) {
    final String contest = testSuite.getContest();
    final int seed = testSuite.getSeed();
    final String depth = testSuite.getDepth();
    final String logFileName = String.format("20200114-%s-%d-%s.log", contest, seed, depth);
    final Path logFile = rootDir.resolve(logFileName);

    try {
      return Files.readAllLines(logFile);
    } catch (final IOException e) {
      e.printStackTrace();
      System.exit(1);
      return Collections.emptyList();
    }
  }
}
