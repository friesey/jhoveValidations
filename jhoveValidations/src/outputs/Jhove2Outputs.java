package outputs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Jhove2Outputs {

	public static void main(String args[]) throws IOException {

		// String jhove2OutputFile =
		// validatorUtilities.BrowserDialogs.chooseFile();

		String jhove2Output = "C://jhove2-2.1.0//outnew.xml";
		
		FileInputStream inputStream = new FileInputStream(jhove2Output);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));		
		String line;
		
		ArrayList<String> lines = new ArrayList<String>();

		StringBuilder responseData = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			responseData.append(line);
			lines.add(line);
		}
		
		int tagerror = 0;
		
		int len = lines.size();
		for (int i = 0; i < len; i++) {
			if (lines.get(i).contains("[WARNING/OBJECT]")) {
				tagerror++;
				String temp = lines.get(i);
				temp = temp.replace("<j2:value>[WARNING/OBJECT]","");			
				temp = temp.replace("</j2:value>","");
				temp = temp.replace("   ","");	
				temp = temp.replace("  ","");		
				temp = temp.replace(" Missing","Missing");		
		
				System.out.println(temp);
			}
		}
		
		System.out.println(tagerror);
		
		reader.close();
	}
}