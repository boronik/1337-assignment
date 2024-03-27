package boro.assignment;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

/**
 * File download could be implemented using: 1. Using Java IO 2. Using NIO 3.
 * Using Libraries (e.g. AsyncHttpClient or Apache Commons IO) 4. ...
 */
public interface FileDownloader {
	void download(URI source, Path root)throws IOException;
}
