package boro.assignment;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Program
{
	public static void main(String[] args) throws URISyntaxException
	{
		URI uri = new URI("https://books.toscrape.com/index.html");
		Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("aassignment-1337");
	}
}
