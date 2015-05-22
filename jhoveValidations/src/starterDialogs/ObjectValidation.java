package starterDialogs;

import java.io.File;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.JhoveException;
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
import edu.harvard.hul.ois.jhove.module.Utf8Module;
import edu.harvard.hul.ois.jhove.module.WaveModule;
import edu.harvard.hul.ois.jhove.module.XmlModule;

public class ObjectValidation {
	
	static OutputHandler handler;
	public static String folder;
	static PrintWriter writer;
	public static String pathwriter;

	public static void jhoveValidation(File file) throws Exception {
		JFrame f = new JFrame();
		JButton but = new JButton("Examinating Gif: " + file.toString());
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
		Module tiffModule = new TiffModule();
		Module gifModule = new GifModule();
		Module xmlModule = new XmlModule();
		Module aiffModule = new AiffModule();
		Module waveModule = new WaveModule();
		Module asciiModule = new AsciiModule();
		Module utf8Module = new Utf8Module();
		Module htmlModule = new HtmlModule();
		Module bytestreamModule = new BytestreamModule();

		OutputHandler handler = new XmlHandler();
	
			writer = new PrintWriter(new FileWriter(pathwriter));
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

		waveModule.init("");
		waveModule.setDefaultParams(new ArrayList<String>());
		
		asciiModule.init("");
		asciiModule.setDefaultParams(new ArrayList<String>());

		htmlModule.init("");
		htmlModule.setDefaultParams(new ArrayList<String>());

		bytestreamModule.init("");
		bytestreamModule.setDefaultParams(new ArrayList<String>());


		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";

		writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		
		String extension = validatorUtilities.fileStringUtilities.getExtension(file.toString());
		
		extension = extension.toLowerCase();

		switch (extension) {
		case "jpg":
			jb.process(app, jpegModule, handler, file.toString());
			break;
		case "pdf":
			jb.process(app, pdfModule, handler, file.toString());
			break;
		case "tif":
			jb.process(app, tiffModule, handler, file.toString());
			break;
		case "gif":
			jb.process(app, gifModule, handler, file.toString());
			break;
		case "xml":
			jb.process(app, xmlModule, handler, file.toString());
			break;
		case "htm":
			jb.process(app, htmlModule, handler, file.toString());
			break;
		case "html":
			jb.process(app, htmlModule, handler, file.toString());
			break;
		case "wav":
			jb.process(app, waveModule, handler, file.toString());
			break;
		default:
			jb.process(app, bytestreamModule, handler, file.toString());
			// TODO: use bytestream module or ask user if he
			// wants to skip or use bytestream
			break;
		}
		
		

		writer.close();				
		f.dispose();


	}

	public static void jhovePdfValidation(File file) throws Exception {
		JFrame f = new JFrame();
		JButton but = new JButton("Examinating PDF: " + file.toString());
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
	

		writer = new PrintWriter(new FileWriter(pathwriter));
		handler.setWriter(writer);
		handler.setBase(jb);
		module.init("");
		module.setDefaultParams(new ArrayList<String>());

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";

		writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		
		jb.process(app, module, handler, file.toString());
		writer.close();				
		f.dispose();
	}

	public static void jhoveGifValidation(File file) throws Exception {
		JFrame f = new JFrame();
		JButton but = new JButton("Examinating Gif: " + file.toString());
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

		Module module = new GifModule(); // JHOVE PdfModule only

		OutputHandler handler = new XmlHandler();
	
			writer = new PrintWriter(new FileWriter(pathwriter));
		handler.setWriter(writer);
		handler.setBase(jb);
		module.init("");
		module.setDefaultParams(new ArrayList<String>());

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";

		writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		
		jb.process(app, module, handler, file.toString());
		writer.close();				
		f.dispose();

	}

	public static void jhoveTiffValidation(File file) throws Exception {
		JFrame f = new JFrame();
		JButton but = new JButton("Examinating Tiff: " + file.toString());
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

		Module module = new TiffModule(); // JHOVE PdfModule only

		OutputHandler handler = new XmlHandler();
	
		writer = new PrintWriter(new FileWriter(pathwriter));
		handler.setWriter(writer);
		handler.setBase(jb);
		module.init("");
		module.setDefaultParams(new ArrayList<String>());

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";

		writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		
		jb.process(app, module, handler, file.toString());
		writer.close();				
		f.dispose();

	}

}