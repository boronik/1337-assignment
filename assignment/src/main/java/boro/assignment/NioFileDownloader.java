package boro.assignment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * The Java NIO package offers the possibility to transfer bytes between two
 * Channels without buffering them into the application memory.
 */
public class NioFileDownloader implements FileDownloader {
	@Override
	public void download(URI source, Path destination) throws IOException {
		Files.createDirectories(destination.getParent());

		try (ReadableByteChannel sourceChannel = Channels.newChannel(source.toURL().openStream())) {
			try (FileOutputStream fileOutputStream = new FileOutputStream(destination.toFile())) {
				FileChannel destinationChannel = fileOutputStream.getChannel();
				destinationChannel.transferFrom(sourceChannel, 0, Long.MAX_VALUE);
			}
		}
	}

	@Override
	public void download(ExecutorService executorService, URI source, Path destination, IDownloaderCallback... callback) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Arrays.asList(callback).forEach(c->c.start(source));
					download(source, destination);
					Arrays.asList(callback).forEach(c->c.success(source));
				} catch (IOException ex) {
					Arrays.asList(callback).forEach(c->c.error(source, ex));
				}
			}
		});
	}
}
