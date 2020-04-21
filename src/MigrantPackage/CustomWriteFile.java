package MigrantPackage;

import java.io.*;

public class CustomWriteFile{
	
	FileOutputStream fos = null;
	File file;
	
	public void write(String content) {
		
		try {
			file = new File("C:\\Users\\SARTIRANO\\eclipse-workspace\\MigrationProject\\log\\MigrantLog.txt");
			fos = new FileOutputStream(file,true);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			//System.out.println(content);
			byte[] bytesArray = content.getBytes();
			fos.write(bytesArray);
			fos.write('\n');
			fos.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
