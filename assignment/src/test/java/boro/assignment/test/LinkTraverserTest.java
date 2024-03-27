package boro.assignment.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;

import boro.assignment.traverse.LinkTraverser;

public class LinkTraverserTest {
	//Not sure how to verify LinkTraverser (would take some time to figure that out)
	// Ideas:
	// 1. Mock FileDownloader and LocalDiskUtil
	// 2. Introduce some testing data as resource

	private LinkTraverser traverser;

	@BeforeEach
	void setup() {
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		executorService.allowCoreThreadTimeOut(true);
		traverser = new LinkTraverser(executorService);
	}
}
