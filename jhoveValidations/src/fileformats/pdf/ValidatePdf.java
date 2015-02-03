package fileformats.pdf;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lowagie.text.pdf.PdfReader;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.PdfModule;

public class ValidatePdf {

	static OutputHandler handler;
	public static String folder;
	static PrintWriter writer;

	public static void JhovePdfValidator() {

		String pathwriter;

		try {
			folder = starterDialogs.JhoveGuiStarterDialog.jhoveExaminationFolder;
			if (folder != null) {

				JFrame f = new JFrame();
				JButton but = new JButton("... Program is running ... ");
				f.add(but, BorderLayout.PAGE_END);
				f.pack();
				f.setVisible(true);

				JhoveBase jb = new JhoveBase();

				String configFilePath = JhoveBase.getConfigFileFromProperties();
				jb.init(configFilePath, null);

				jb.setEncoding("UTF-8");// UTF-8 does not calculate checksums,
										// which saves time
				jb.setBufferSize(131072);
				jb.setChecksumFlag(false);
				jb.setShowRawFlag(false);
				jb.setSignatureFlag(false);

				String appName = "Customized JHOVE";
				String version = "1.0";

				int[] date = validatorUtilities.genericUtilities.getDate();
				String usage = "Call JHOVE via own Java";
				String rights = "Copyright nestor Format Working Group";
				App app = new App(appName, version, date, usage, rights);

				Module module = new PdfModule(); // JHOVE PdfModule only

				OutputHandler handler = new XmlHandler();
				ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination.xml");

				writer = new PrintWriter(new FileWriter(pathwriter));
				handler.setWriter(writer);
				handler.setBase(jb);
				module.init("");
				module.setDefaultParams(new ArrayList<String>());

				String xmlVersion = "xml version='1.0'";
				String xmlEncoding = "encoding='ISO-8859-1'";

				writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
				writer.println("<JhoveFindings>");

				// To handle one file after the other
				for (int i = 0; i < files.size(); i++) {
					if (validatorUtilities.GenericFileAnalysis.testFileHeaderPdf(files.get(i)) == true) { // tests
																											// only
																											// PDF
																											// with
																											// Header
																											// %PDF
						writer.println("<item>");

						String substitute = validatorUtilities.fileStringUtilities.getFileName(files.get(i).toString());
						substitute = validatorUtilities.fileStringUtilities.reduceXmlEscapors(substitute);
						writer.println("<filename>" + substitute + "</filename>");

						addSomeMetadata(files.get(i).toString());

						jb.process(app, module, handler, files.get(i).toString());
						writer.println("</item>");
					}
				}
				writer.println("</JhoveFindings>");
				writer.close();
				outputs.XmlParserJhove.parseXmlFile(pathwriter);

				f.dispose();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);

		}
	}

	private static void addSomeMetadata(String pdfFile) throws IOException {

		PdfReader reader = new PdfReader(pdfFile);

		if (reader != null) {
			Map<String, String> metadata = reader.getInfo();

			if (metadata.get("CreationDate") != null) {
				if (metadata.get("CreationDate").length() > 10) {
					String creationYear = getYear(metadata.get("CreationDate"));
					int creationYearInt = Integer.parseInt(creationYear);
					if (creationYearInt > 1992) {
						writer.println("<creationyear>" + creationYear + "</creationyear>");
					}
				}
			}
		}
	}

	private static String getYear(String creationYear) {
		creationYear = creationYear.replace("D:", "");
		String year = creationYear.substring(0, 4);
		return year;
	}

}
