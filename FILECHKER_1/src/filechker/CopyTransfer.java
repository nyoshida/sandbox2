package filechker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class CopyTransfer {
	/**
	 * コピー元のパス[srcPath]から、コピー先のパス[destPath]へ
	 * ファイルのコピーを行います。
	 * コピー処理にはFileChannel#transferToメソッドを利用します。
	 * 尚、コピー処理終了後、入力・出力のチャネルをクローズします。
	 * @param srcPath    コピー元のパス
	 * @param destPath    コピー先のパス
	 * @throws IOException    何らかの入出力処理例外が発生した場合
	 */
	public static void CopyTransfer(File srcPath, File destPath) 
	    throws IOException {
	    
	    FileChannel srcChannel = new
	        FileInputStream(srcPath).getChannel();
	    FileChannel destChannel = new
	        FileOutputStream(destPath).getChannel();
	    try {
	        srcChannel.transferTo(0, srcChannel.size(), destChannel);
	    } finally {
	        srcChannel.close();
	        destChannel.close();
	    }

	}
	public static void CopyTransfer(String srcPath, String destPath) 
		    throws IOException {
		    
		    FileChannel srcChannel = new
		        FileInputStream(srcPath).getChannel();
		    FileChannel destChannel = new
		        FileOutputStream(destPath).getChannel();
		    try {
		        srcChannel.transferTo(0, srcChannel.size(), destChannel);
		    } finally {
		        srcChannel.close();
		        destChannel.close();
		    }

		}

}
