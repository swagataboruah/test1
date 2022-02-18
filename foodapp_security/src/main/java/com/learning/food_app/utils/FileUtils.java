package com.learning.food_app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class FileUtils {

public byte[] readFile(File file) throws IOException {
		
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] allBytes = new byte[(int) file.length()];
		fileInputStream.read(allBytes);
		return allBytes;
	}
	
	public String writeFile(byte[] allBytes, String fileName)  {
		
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(allBytes);
			return "success";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
}
