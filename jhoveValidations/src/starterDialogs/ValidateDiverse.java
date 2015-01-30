package starterDialogs;

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
import edu.harvard.hul.ois.jhove.module.AiffModule;
import edu.harvard.hul.ois.jhove.module.AsciiModule;
import edu.harvard.hul.ois.jhove.module.BytestreamModule;
import edu.harvard.hul.ois.jhove.module.GifModule;
import edu.harvard.hul.ois.jhove.module.HtmlModule;
import edu.harvard.hul.ois.jhove.module.Jpeg2000Module;
import edu.harvard.hul.ois.jhove.module.JpegModule;
import edu.harvard.hul.ois.jhove.module.PdfModule;
import edu.harvard.hul.ois.jhove.module.TiffModule;
import edu.harvard.hul.ois.jhove.module.Utf8Block;
import edu.harvard.hul.ois.jhove.module.Utf8Module;
import edu.harvard.hul.ois.jhove.module.WaveModule;
import edu.harvard.hul.ois.jhove.module.XmlModule;
import fileformats.pdf.ValidatePdf;

public class ValidateDiverse {

	static App jhoveapp;
	static JhoveBase jhoveBase;
	static Module jpegmodule;
	static OutputHandler handler;
	public static String folder;

	public static void JhoveDiverseValidator() {
		
		//depending on the file extension, the module is chosen
		
		
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

				Module jpeg2000Module = new Jpeg2000Module();
				Module pdfModule = new PdfModule();
				Module jpegModule = new JpegModule();
				Module tiffModule =new TiffModule();
				Module gifModule = new GifModule();
				Module xmlModule = new XmlModule();
				Module aiffModule = new AiffModule();
				Module waveModule = new WaveModule();
				Module asciiModule = new AsciiModule();
				Module utf8Module = new Utf8Module();
				Module htmlModule = new HtmlModule();				
				Module bytestreamModule = new BytestreamModule();
																					

				OutputHandler handler = new XmlHandler();
				ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination.xml");

				PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));
				handler.setWriter(writer);
				handler.setBase(jb);
				
				jpeg2000Module.init("");
				jpeg2000Module.setDefaultParams(new ArrayList<String>());
				
				pdfModule.init("");
				pdfModule.setDefaultParams(new ArrayList<String>());
				
				jpegModule.init("");
				jpegModule.setDefaultParams(new ArrayList<String>());
				
				tiffModule.init("");
				tiffModule.setDefaultParams(new ArrayList<String>());
				
				gifModule.init("");
				gifModule.setDefaultParams(new ArrayList<String>());
				
				xmlModule.init("");
				xmlModule.setDefaultParams(new ArrayList<String>());
								
				aiffModule.init("");
				aiffModule.setDefaultParams(new ArrayList<String>());
				
				utf8Module.init("");
				utf8Module.setDefaultParams(new ArrayList<String>());
				
				htmlModule.init("");
				htmlModule.setDefaultParams(new ArrayList<String>());				
				
				bytestreamModule.init("");
				bytestreamModule.setDefaultParams(new ArrayList<String>());
				

				String xmlVersion = "xml version='1.0'";
				String xmlEncoding = "encoding='ISO-8859-1'";

				writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
				writer.println("<JhoveFindings>");

				// To handle one file after the other
				for (int i = 0; i < files.size(); i++) {					
						writer.println("<item>");
						if (files.get(i).toString().contains("&")) {
							String substitute = validatorUtilities.genericUtilities.normaliseToUtf8(files.get(i).toString());
							writer.println("<filename>" + substitute + "</filename>");
						} else {
							writer.println("<filename>" + files.get(i).toString() + "</filename>");
						}
						
						String extension = validatorUtilities.fileStringUtilities.getExtension(files.get(i).toString());
						
						extension = extension.toLowerCase();
						
						switch (extension){						
						case "jpg":		
							jb.process(app, jpegModule, handler, files.get(i).toString());				
							break;
						case "pdf":		
							jb.process(app, pdfModule, handler, files.get(i).toString());				
							break;
						case "tif":		
							jb.process(app, tiffModule, handler, files.get(i).toString());				
							break;
						case "gif":		
							jb.process(app, gifModule, handler, files.get(i).toString());		
							break;
						case "xml":		
							jb.process(app, xmlModule, handler, files.get(i).toString());		
							break;
						default:
							JOptionPane.showMessageDialog(null, "This file extension has not been developed yet or is not covered by JHOVE.", "Under construction", JOptionPane.WARNING_MESSAGE);
							//TODO: use bytestream module or ask user if he wants to skip or use bytestream
							break;			
						}												
						
						writer.println("</item>");		
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
}