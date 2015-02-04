package fileformats.gif;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.GifModule;

public class GifChecker {

	public static String giffolder;
	static App gifjhoveapp;
	static JhoveBase jhoveBaseGif;
	static Module gifmodule;
	static OutputHandler handler;
	public static String folder;

	public static void main(String args[]) throws Exception {
	
		try {
			boolean gifisvalid = false;
			// JOptionPane.showMessageDialog(null,
			// "Please choose a Folder with files", "Gif File Examination",
			// JOptionPane.QUESTION_MESSAGE);
			giffolder = validatorUtilities.BrowserDialogs.chooseFolder();
			if (giffolder != null) {
				ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(giffolder), new ArrayList<File>());
				outputs.GifXmlOutput.createXmlGifOutput();
				createJhoveChecker();
				/*
				 * TODO: This is a nice way to print every file in the folder in
				 * hex files, but there is always missing so much at the end
				 */
				/*
				 * String tempwriter; PrintWriter tempHexWriter; String
				 * filename; for (int i = 0; i < files.size(); i++) { filename =
				 * FilenameUtils.getBaseName(files.get(i).toString());
				 * tempwriter = (folder + "//" + filename + "GifinHex.hex");
				 * tempHexWriter = new PrintWriter(new FileWriter(tempwriter));
				 * utilities.HexReader.convertToHex(tempHexWriter,
				 * files.get(i)); }
				 */
				for (int i = 0; i < files.size(); i++) {
					String extension = validatorUtilities.fileStringUtilities.getExtension(files.get(i).toString());
					extension = extension.toLowerCase();

					if (extension.equals("gif")) {
						if (validatorUtilities.GenericFileAnalysis.testFileHeaderGif(files.get(i).toString()) == false) {
							JOptionPane.showMessageDialog(null, (files.get(i).toString()), "gif-Extension has no correct Gif Header", JOptionPane.QUESTION_MESSAGE);
						}
						checkGifWithJhove(files.get(i));

						// gifisvalid anders als mit Jhove testen und gifisvalid
						// auf true setzen, jetzt sind alle false
						boolean hasEofTag = checkIfGifHasEof(files.get(i));
						if (!gifisvalid) {
							GifReparator.repairgif(files.get(i));

							if (gifisvalid) {
								// Some output that it has worked
							} else {
								// Some output of failure
							}
						}
					}
				}
			}
			outputs.GifXmlOutput.closeXmlGifOutput();
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Exception", JOptionPane.WARNING_MESSAGE);
		}
	}

	private static boolean checkIfGifHasEof(File giffile) throws IOException {
		// TODO
		if (validatorUtilities.GenericFileAnalysis.testFileHeaderGif(giffile) == true) {
			// System.out.println(giffile.toString());
			// System.out.println(utilities.HexReader.readEofTag(giffile));
			return true;
		} else {
			return false;
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
		jhoveBaseGif.process(gifjhoveapp, gifmodule, handler, giffile.toString());
		outputs.GifXmlOutput.xmlgifwriter.println("</item>");
	}

	public static void createJhoveChecker() throws Exception {
		jhoveBaseGif = new JhoveBase();

		String configFilePath = JhoveBase.getConfigFileFromProperties();
		jhoveBaseGif.init(configFilePath, null);

		jhoveBaseGif.setEncoding("UTF-8");
		jhoveBaseGif.setBufferSize(131072);
		jhoveBaseGif.setChecksumFlag(false);
		jhoveBaseGif.setShowRawFlag(false);
		jhoveBaseGif.setSignatureFlag(false);

		String appName = "Customized JHOVE";
		String version = "1.0";
		int[] date = { 2014, 12, 03 };
		String usage = "Call JHOVE via own Java";
		String rights = "Copyright nestor Format Working Group";
		gifjhoveapp = new App(appName, version, date, usage, rights);

		gifmodule = new GifModule(); // JHOVE GifModule only
		handler = new XmlHandler();

		handler.setWriter(outputs.GifXmlOutput.xmlgifwriter);
		handler.setBase(jhoveBaseGif);
		gifmodule.init("");
		gifmodule.setDefaultParams(new ArrayList<String>());
	}
}
