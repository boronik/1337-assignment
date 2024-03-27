package boro.assignment;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LinkTraverser {
	private final Set<URI> visited = Collections.synchronizedSet(new HashSet<>());
	private final FileDownloader fileDownloader;
	private final LocalDiskUtil fileUtil;

	private ExecutorService executorService;

	public LinkTraverser(ExecutorService executorService) {
		this.executorService = executorService;
		this.fileDownloader = new NioFileDownloader();
		this.fileUtil = new LocalDiskUtil();
	}

	public void traverse(URI uri, Path root) throws IOException, InterruptedException {
		synchronized (visited) {
			if (visited.contains(uri)) // The same file should not be traversed more than once
			{
				return;
			}
			visited.add(uri);
		}

		// Download and parse the contents of the uri
		Document doc = Jsoup.connect(uri.toString()).get();

		System.out.println(format("%s - Download file success: %s", Thread.currentThread().getName(), uri));

		// Save the content to disk
		fileUtil.save(doc.html(), root.resolve(uri.getPath().substring(1)));

		// Download styles
		doc.select("link[href]").stream().map(s -> uri.resolve(s.attr("href")))
				.forEach(source -> downloadAndLogErrors(root, source));

		// Download scripts
		doc.select("script[src]").stream().map(e -> uri.resolve(e.attr("src")))
				.forEach(source -> downloadAndLogErrors(root, source));

		// Download images
		doc.select("img[src]").stream().map(e -> uri.resolve(e.attr("src")))
				.forEach(source -> downloadAndLogErrors(root, source));

		// Traverse sub-links recursively
		doc.select("a[href]").stream().map(l -> uri.resolve(l.attr("href"))).forEach(elementUri -> {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						traverse(elementUri, root);
					} catch (Exception ex) {
						// Log and continue traversing the rest of the links
						System.err.println(format("Could not traverse file: %s", ex.getMessage()));
					}
				}
			});
		});
	}

	private void downloadAndLogErrors(Path root, URI source) {
		Path destination = root.resolve(source.getPath().substring(1));
		if (Files.exists(destination)) {
			return;
		}

		fileDownloader.download(executorService, source, destination, new IDownloaderCallback() {
			public void start(URI source) {
			}
		});
	}
}
