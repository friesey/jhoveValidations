package outputs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Jhove2Outputs {

	public static void main(String args[]) throws IOException {

		// String jhove2OutputFile =
		// validatorUtilities.BrowserDialogs.chooseFile();

		String jhove2Output = "C://jhove2-2.1.0//outtrimtrim.xml";
		
		FileInputStream inputStream = new FileInputStream(jhove2Output);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));		
		String line;
		
		ArrayList<String> lines = new ArrayList<String>();

		StringBuilder responseData = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			responseData.append(line);
			lines.add(line);
		}
		
		
		PrintWriter xmlsummary = new PrintWriter(new FileWriter(("C://jhove2-2.1.0//Jhove2Trim.xml")));		

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"Jhove2Trim.xsl\"?>";

		xmlsummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		xmlsummary.println(xmlxslStyleSheet);
		xmlsummary.println("<Jhove2Trim>");

		outputs.XslStyleSheets.Jhove2TrimXsl();
		
		int tagerror = 0;
		
	
		int len = lines.size();
		xmlsummary.println("<Tags>");
		for (int i = 0; i < len; i++) {			
			if (lines.get(i).contains("[WARNING/OBJECT]")) {
				tagerror++;
				String temp = lines.get(i);
				temp = temp.replace("<j2:value>[WARNING/OBJECT]","");			
				temp = temp.replace("</j2:value>","");
				temp = temp.replace("   ","");	
				temp = temp.replace("  ","");		
				temp = temp.replace(" Missing","Missing");		
				
				xmlsummary.println("<Error>");
				xmlsummary.println("<TagInformation>" + temp + "</TagInformation>");
				xmlsummary.println("<TagNumber>" + tagerror + "</TagNumber>");	
				xmlsummary.println("</Error>");
			}
		}
		xmlsummary.println("</Tags>");		

		
		reader.close();
		xmlsummary.println("</Jhove2Trim>");
		xmlsummary.close();
	}
}