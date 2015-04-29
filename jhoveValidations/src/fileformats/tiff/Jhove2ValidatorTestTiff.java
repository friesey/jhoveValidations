package fileformats.tiff;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jhove2.core.Document;
import org.jhove2.core.JHOVE2;
import org.jhove2.core.io.Input;
import org.jhove2.core.source.Source;
import org.jhove2.core.source.SourceFactory;

public class Jhove2ValidatorTestTiff {

	static String folder;

	public static void main(String args[]) throws Exception {
		String pathwriter;

		folder = validatorUtilities.BrowserDialogs.chooseFolder();
		if (folder != null) {

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";

			pathwriter = (folder + "//" + "Jhove2Exam.xml");
			PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));

			writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			writer.println("<JhoveFindings>");

			ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

			JHOVE2 jhove2 = new JHOVE2();

	

				for (int i = 0; i < files.size(); i++) {
					if (validatorUtilities.GenericFileAnalysis.testFileHeaderTiff(files.get(i)) == true) {

						// Source source = SourceFactory.getSource(JHOVE2,
						// files.get(i).toString());

						System.out.println(jhove2.toString());
						System.out.println(files.get(i).toString());

						Source source = jhove2.getSourceFactory().getSource(jhove2, files.get(i).toString()); // Nullpointerexception
						source.addModule(jhove2);
						Input input = source.getInput(jhove2);
						jhove2.characterize(source, input);

						/*
						 * String title = "title"; Document.Type type =
						 * Document.Type.getType(); Document.Intention intention
						 * = "int";
						 * 
						 * Document tiffdoc = new Document(title, type,
						 * intention);
						 */

					}
				}
				writer.close();
		
		}

	}
}
