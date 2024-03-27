package boro.assignment.test;

import org.junit.jupiter.api.BeforeEach;

import boro.assignment.LinkTraverser;

public class LinkTraverserTest {
	//Not sure how to verify LinkTraverser (would take some time to figure that out)
	// Ideas:
	// 1. Mock FileDownloader and LocalDiskUtil
	// 2. Introduce some testing data as resource

	private LinkTraverser traverser;

	@BeforeEach
	void setup() {
		traverser = new LinkTraverser();
	}
}
