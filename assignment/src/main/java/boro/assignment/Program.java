package boro.assignment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Program
{
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException
	{
		URI uri = new URI("https://books.toscrape.com/index.html");
		Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("aassignment-1337-1");

		Files.createDirectories(destination);

		LinkTraverser traverser = new LinkTraverser();
		traverser.traverse(uri, destination);
	}
}
