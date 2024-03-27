package boro.assignment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boro.assignment.FileDownloader;
import boro.assignment.NioFileDownloader;

public class FileDownloaderTest {
	private FileDownloader downloader;

	@BeforeEach
	void setup() {
		downloader = new NioFileDownloader();
	}

	@Test
	void testDownloaderSync() throws URISyntaxException, IOException {
	    URI uri = new URI("https://books.toscrape.com/media/cache/39/82/39826643a2e100f9e763079d7d5867f4.jpg");
	    Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("assgnment-download-sync.jpg");

	    Files.deleteIfExists(destination);

	    downloader.download(uri, destination);

	    assertTrue(Files.exists(destination));
	    assertEquals(Files.size(destination), 63115);
	}
}
