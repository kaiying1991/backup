/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Backup {

	private static final String VERSION = "1.1";
	private static final String CLASS_NAME = Backup.class.getSimpleName();
	private static final DateTimeFormatter FOLDER_FORMAT = DateTimeFormatter.ofPattern("yyyyMMMdd hhmmss");

	public static void main(String[] args) {
		print("##### Starting ", CLASS_NAME, " v", VERSION, " #####\n");

		try (Scanner scanner = new Scanner(new File("config.csv"));) {
			scanner.nextLine();
			String now = LocalDateTime.now().format(FOLDER_FORMAT);

			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split(",");
				Path source = Paths.get(line[0]);
				Path target = Paths.get(line[1]).resolve(now);

				Files.createDirectories(target);
				Files.copy(source, target.resolve(source.getFileName()));
				print("Backing up '" + source + "' to '" + target + "'");
			}
		} catch (IOException e) {
			while (true) {
				print("Backup failed! ", e.getMessage());
				sleep(5000);
			}
		}

		print("\n##### " + CLASS_NAME + " completed #####");
	}

	private static void print(Object... objs) {
		System.out.println(Stream.of(objs).map(Object::toString).collect(Collectors.joining()));
	}

	private static void sleep(long count) {
		try {
			Thread.sleep(count);
		} catch (Exception e) {
			print("Failed to delay ", count, " milli-seconds");
		}
	}
}
