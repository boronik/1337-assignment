package boro.assignment.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boro.assignment.util.LocalDiskUtil;

public class LocalDiskUtilTest {
	private LocalDiskUtil util;

	@BeforeEach
	void setup() {
		util = new LocalDiskUtil();
	}

	@Test
	void testSave() throws FileNotFoundException, IOException
	{
		Path destination = Path.of(System.getProperty("java.io.tmpdir")).resolve("test.txt");
		util.save("test", destination);

		assertTrue(Files.exists(destination));
		assertEquals(Files.size(destination), 4);
	}
}
