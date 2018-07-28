package examples2;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class NewDirManager {
	
	public static void copy(final Path from, final Path to, final boolean deleteAfterCopy) throws IOException {
		if (Files.exists(from)) {		// <1>
			if (!Files.isDirectory(from)) {	// <2>
				Files.copy(from,to,StandardCopyOption.REPLACE_EXISTING);
			}
			else {
				Files.createDirectories(to);	// <3>
				try(final DirectoryStream<Path>	stream = Files.newDirectoryStream(from)) { // <4>
					for (Path item : stream) {	// <5>
						copy(Paths.get(from.toString(),item.getFileName().toString()),Paths.get(to.toString(),item.getFileName().toString()),deleteAfterCopy);
					}
				}
			}
			if (deleteAfterCopy) {	// <6>
				Files.delete(from);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		copy(new File("C:/tmp/from").toPath(),new File("C:/tmp/to").toPath(),true);	// <7>
	}
}
