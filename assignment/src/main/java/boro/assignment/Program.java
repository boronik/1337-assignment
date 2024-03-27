package boro.assignment;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Program
{
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException
	{
		long start = System.currentTimeMillis();

		URI uri = new URI("https://books.toscrape.com/index.html");
		Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("assignment-1337").resolve(UUID.randomUUID().toString());

		Files.createDirectories(destination);

		System.out.println(format("Traversing uri: %s", uri));

		ThreadPoolExecutor executorService = new ThreadPoolExecutor(100, 100, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		executorService.allowCoreThreadTimeOut(true); //Enable program to exit

		LinkTraverser traverser = new LinkTraverser(executorService);
		traverser.traverse(uri, destination);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run()
			{
				long end = System.currentTimeMillis();
				long seconds = ((end -start)/1000)%60;
				System.out.println(format("\nDONE in [%d sec] - Output: %s", seconds, destination));
			}
		});
	}
}
