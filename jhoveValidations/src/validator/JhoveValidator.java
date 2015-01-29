package validator;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.GifModule;
import edu.harvard.hul.ois.jhove.module.PdfModule;
import edu.harvard.hul.ois.jhove.module.TiffModule;


public class JhoveValidator {

	static App gifjhoveapp;
	static JhoveBase jhoveBaseGif;
	static Module gifmodule;
	static OutputHandler handler; 
	public static String folder;

	 public static void JhovePdfValidator() {

		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with PDF files", "JHOVE PDF-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = validatorUtilities.BrowserDialogs.chooseFolder();
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

				PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));
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
					if (validatorUtilities.GenericFileAnalysis.testFileHeaderPdf(files.get(i)) == true) { //tests only PDF with Header %PDF
						writer.println("<item>");
						if (files.get(i).toString().contains("&")) {
							String substitute = validatorUtilities.genericUtilities.normaliseToUtf8(files.get(i).toString());
							writer.println("<filename>" + substitute + "</filename>");
						} else {
							writer.println("<filename>" + files.get(i).toString() + "</filename>");
						}
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
	
	

	public static void JhoveGifValidator() {
		
		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with Gif files", "JHOVE Gif-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = validatorUtilities.BrowserDialogs.chooseFolder();
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

				Module module = new GifModule();

				OutputHandler handler = new XmlHandler();
				ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination.xml");

				PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));
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
				if (validatorUtilities.GenericFileAnalysis.testFileHeaderGif(files.get(i).toString()) == true) {
						writer.println("<item>");
						if (files.get(i).toString().contains("&")) {
							String substitute = validatorUtilities.genericUtilities.normaliseToUtf8(files.get(i).toString());
							writer.println("<filename>" + substitute + "</filename>");
						} else {
							writer.println("<filename>" + files.get(i).toString() + "</filename>");
						}
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
	
	public static void JhoveTiffValidator() {
		
		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with Tiff files", "JHOVE Tiff-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = validatorUtilities.BrowserDialogs.chooseFolder();
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

				Module module = new TiffModule();

				OutputHandler handler = new XmlHandler();
				ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination.xml");

				PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));
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
					if (validatorUtilities.GenericFileAnalysis.testFileHeaderTiff(files.get(i)) == true) {
						writer.println("<item>");
						if (files.get(i).toString().contains("&")) {
							String substitute = validatorUtilities.genericUtilities.normaliseToUtf8(files.get(i).toString());
							writer.println("<filename>" + substitute + "</filename>");
						} else {
							writer.println("<filename>" + files.get(i).toString() + "</filename>");
						}
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



	 public void init(String init) throws Exception {

	}

	public void reset() {
	}

	public static void checkGifWithJhove(File giffile) throws Exception {
		outputs.GifXmlOutput.xmlgifwriter.println("<item>");
		if (giffile.toString().contains("&")) {
			String substitute = validatorUtilities.genericUtilities.normaliseToUtf8(giffile.toString());
			outputs.GifXmlOutput.xmlgifwriter.println("<filename>" + substitute + "</filename>");
		} else {
			outputs.GifXmlOutput.xmlgifwriter.println("<filename>" + giffile.toString() + "</filename>");
		}
		JhoveValidator.jhoveBaseGif.process(JhoveValidator.gifjhoveapp, JhoveValidator.gifmodule, JhoveValidator.handler, giffile.toString());
		outputs.GifXmlOutput.xmlgifwriter.println("</item>");
	}	
	

	public static void createJhoveChecker() throws Exception {
		JhoveValidator.jhoveBaseGif = new JhoveBase();

		String configFilePath = JhoveBase.getConfigFileFromProperties();
		JhoveValidator.jhoveBaseGif.init(configFilePath, null);

		JhoveValidator.jhoveBaseGif.setEncoding("UTF-8");
		JhoveValidator.jhoveBaseGif.setBufferSize(131072);
		JhoveValidator.jhoveBaseGif.setChecksumFlag(false);
		JhoveValidator.jhoveBaseGif.setShowRawFlag(false);
		JhoveValidator.jhoveBaseGif.setSignatureFlag(false);

		String appName = "Customized JHOVE";
		String version = "1.0";
		int[] date = { 2014, 12, 03 };
		String usage = "Call JHOVE via own Java";
		String rights = "Copyright nestor Format Working Group";
		JhoveValidator.gifjhoveapp = new App(appName, version, date, usage, rights);

		JhoveValidator.gifmodule = new GifModule(); // JHOVE GifModule only
		JhoveValidator.handler = new XmlHandler();

		JhoveValidator.handler.setWriter(outputs.GifXmlOutput.xmlgifwriter);
		JhoveValidator.handler.setBase(JhoveValidator.jhoveBaseGif);
		JhoveValidator.gifmodule.init("");
		JhoveValidator.gifmodule.setDefaultParams(new ArrayList<String>());

	}
}