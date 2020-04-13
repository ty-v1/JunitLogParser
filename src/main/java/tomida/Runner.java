package tomida;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import tomida.data.TestSuite;
import tomida.log.KGPLogParser;
import tomida.log.KGPLogReader;

public class Runner {

  private final KGPLogReader kgpLogReader;
  private final KGPLogParser kgpLogParser = new KGPLogParser();

  public Runner(final Path rootDir) {
    kgpLogReader = new KGPLogReader(rootDir);
  }

  public void writeToFile(final Path outDir, final String contest,
      final TestSuite testSuite, final String date) throws IOException {
    if (Files.notExists(outDir)) {
      Files.createDirectories(outDir);
    }
    //
    final Path outFile = outDir.resolve(contest + ".csv");
    final BufferedWriter writer = Files.newBufferedWriter(outFile, StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
    //
    final List<String> log = kgpLogReader.readLog(testSuite, date);
    final double time = kgpLogParser.getExecTime(log)
        .orElse(0.0d);
    final int maxGeneration = kgpLogParser.getMaxGeneration(log)
        .orElse(0);
    writer.append(testSuite.toString())
        .append(",")
        .append(String.valueOf(time))
        .append(",")
        .append("")
        .append(String.valueOf(maxGeneration));
    writer.newLine();
    writer.close();
  }
}
