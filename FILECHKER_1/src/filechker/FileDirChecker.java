package filechker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class  FileDirChecker {
	private String checkDir;
	private String toDir;
	private File targetDir;
	private File copyDir;
	protected boolean fStop;
	protected ArrayList<String> 		fRegistereds;
	protected HashMap<String,Long>		fLastModifieds;

	public FileDirChecker(String checkDir,String toDir) {
		this.checkDir = checkDir;
		this.toDir = toDir;
		targetDir = new File(checkDir);
		copyDir = new File(toDir);
	}
	

	
	public void start() {
		fStop = false;
		fRegistereds = new ArrayList<String>();
		fLastModifieds = new HashMap<String,Long>();
		Thread thread = new Thread(new AutoChecker());
		thread.setDaemon(true);
		thread.start();
	}

	public void stop() {
		fStop = true;
	}
	protected void check() {

		checkRemoved();
		checkNew();
		checkModified();
	}
	protected void checkRemoved() {
		Iterator <String>it = fRegistereds.iterator();
		while(it.hasNext()) {
			String filename = it.next();
			File file  = new File(checkDir,filename);
			if (!file.exists()){
				it.remove();
				System.out.println(filename +"が削除されました");
				File delFile  = new File(toDir,filename);
				delFile.delete();
			}
		}
	}
	protected void checkNew() {
		String [] files = targetDir.list();
		File copyFile;
		File toFile;
		for( int i=0;i<files.length;i++) {
			if ( !fRegistereds.contains(files[i])){
				fRegistereds.add(files[i]);
				System.out.println(files[i]+"が追加されました");
				copyFile  = new File(checkDir,files[i]);
				toFile = new File(toDir,files[i]);
				try {
					CopyTransfer.CopyTransfer(copyFile,toFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected void checkModified() {
		File copyFile;
		File toFile;

		Iterator <String>it = fRegistereds.iterator();
		while(it.hasNext()) {
			String filename = it.next();
			File file  = new File(checkDir,filename);
			Long lastModified = (Long)fLastModifieds.get(file);
			long newLastModified = file.lastModified();
			if ( lastModified == null ) {
				fLastModifieds.put(filename, new Long(newLastModified));
			}
			else {
				if ( lastModified.longValue() > file.lastModified() ) {
					fLastModifieds.put(filename, new Long(newLastModified));
					System.out.println(filename+"が更新されました");
				}
				toFile = new File(toDir,filename);
				try {
					CopyTransfer.CopyTransfer(file,toFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	protected class AutoChecker implements Runnable {
		public void run() {
			while(!fStop) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
				}
				check();
			}
		}
	}

}
