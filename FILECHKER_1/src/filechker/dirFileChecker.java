package filechker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class dirFileChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream inputStream;
		Properties configuration = null;
		String checkDir;
		String toDir;

		try {
			configuration = new Properties();
			inputStream = new FileInputStream(new File("dir.properties"));
			configuration.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		checkDir = configuration.getProperty("from_dir");
		toDir = configuration.getProperty("to_dir");
		System.out.println(checkDir+"フォルダを監視開始・・・");

		FileDirChecker check = new FileDirChecker(checkDir,toDir);
		check.start();
		try {
			// 1分間プログラムを走らせる
			Thread.sleep(60 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		check.stop();

	}

}
