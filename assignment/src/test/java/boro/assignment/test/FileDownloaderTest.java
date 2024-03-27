package boro.assignment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boro.assignment.FileDownloader;
import boro.assignment.IDownloaderCallback;
import boro.assignment.NioFileDownloader;

public class FileDownloaderTest {
	private FileDownloader downloader;
	private CountDownLatch lock = new CountDownLatch(1);

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

	@Test
	void testDownloaderAsync() throws URISyntaxException, IOException, InterruptedException {
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		executorService.allowCoreThreadTimeOut(true);

	    URI uri = new URI("https://books.toscrape.com/media/cache/39/82/39826643a2e100f9e763079d7d5867f4.jpg");
	    Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("assgnment-download-async.jpg");

	    Files.deleteIfExists(destination);

	    downloader.download(executorService, uri, destination, new IDownloaderCallback() {
			@Override
			public void success(URI source) {
				lock.countDown();
			}

			@Override
			public void error(URI source, Throwable t)
			{
				lock.countDown();
			}
	    });

	    lock.await(5000, TimeUnit.MILLISECONDS);

	    assertTrue(Files.exists(destination));
	    assertEquals(Files.size(destination), 63115);
	}
}
