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

import boro.assignment.traverse.LinkTraverser;

public class Program
{
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException
	{
		URI uri = new URI("https://books.toscrape.com/index.html");
		Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("assignment-1337").resolve(UUID.randomUUID().toString());

		System.out.println(format("OUTPUT: %s", destination));
		System.out.print("  Traversing...");

		Files.createDirectories(destination);

		ThreadPoolExecutor executorService = new ThreadPoolExecutor(100, 100, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		executorService.allowCoreThreadTimeOut(true); //Enable program to exit

		LinkTraverser traverser = new LinkTraverser(executorService);
		traverser.traverse(uri, destination);
	}
}
