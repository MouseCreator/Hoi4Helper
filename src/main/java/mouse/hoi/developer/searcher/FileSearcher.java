package mouse.hoi.developer.searcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileSearcher {
    public List<String> findTxtFiles(String directoryPath) {
        List<String> filePaths = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            filePaths = paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(string -> string.endsWith(".txt")).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePaths;
    }
}
