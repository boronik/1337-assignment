package boro.assignment.download;

import static java.lang.String.format;

import java.net.URI;

public interface IDownloaderCallback {
	default void start(URI source) {
		System.out.println(format("Download file: %s", source));
	}

	default void success(URI source) {
		System.out.println(format("%s - Download file success: %s", Thread.currentThread().getName(), source));
	}

	default void error(URI source, Throwable t){
		System.err.println(format("%s - Download file error: %s", Thread.currentThread().getName(), t.getMessage()));
	}
}
