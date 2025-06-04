package com.logger.enhancer.LoggerEnhancer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class JavaParserUtil {
    public static List<Path> findJavaFiles(Path root) throws IOException {
        return Files.walk(root)
                .filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());
    }
}

