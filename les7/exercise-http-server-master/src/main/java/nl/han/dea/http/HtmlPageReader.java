package nl.han.dea.http;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HtmlPageReader {
    public String readFile(String filename) {
        var fullFileName = "pages/".concat(filename);
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            var file = new File(classLoader.getResource(fullFileName).getFile()).toPath();

            var fileAsString = new String(Files.readAllBytes(file));

            return fileAsString;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLength(String filename) {
        if(filename.isEmpty()) {
            return "0";
        }

        var fullFileName = "pages/".concat(filename);
        var classLoader = getClass().getClassLoader();
        var path = new File(classLoader.getResource(fullFileName).getFile()).toPath();
        var length = path.toFile().length();

        return Long.toString(length);
    }
}