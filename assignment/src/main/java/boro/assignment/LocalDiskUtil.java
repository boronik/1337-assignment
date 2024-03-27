package boro.assignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalDiskUtil {
	public void save(String content, Path destination) throws IOException, FileNotFoundException {
		Files.createDirectories(destination.getParent());
		try (FileOutputStream os = new FileOutputStream(destination.toFile())) {
			os.write(content.getBytes());
		}
	}
}
