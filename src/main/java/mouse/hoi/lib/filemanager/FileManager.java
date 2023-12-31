package mouse.hoi.lib.filemanager;

import java.util.List;

public interface FileManager {
    String read(String filename);
    void write(String filename, String content);
    void append(String filename, String content);
    List<String> readLines(String filename);
    void clear(String filename);
}
