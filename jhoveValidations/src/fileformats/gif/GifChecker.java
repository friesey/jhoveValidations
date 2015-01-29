package fileformats.gif;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GifChecker {

	public static String giffolder;

	// TODO: This will error on Travis as the JHOVE Library is not in the maven
	// repository and had to be added externally

	public static void main(String args[]) throws Exception {
		// test
		try {
			boolean gifisvalid = false;
			// JOptionPane.showMessageDialog(null,
			// "Please choose a Folder with files", "Gif File Examination",
			// JOptionPane.QUESTION_MESSAGE);
			giffolder = validatorUtilities.BrowserDialogs.chooseFolder();
			if (giffolder != null) {
				ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(giffolder), new ArrayList<File>());
				outputs.GifXmlOutput.createXmlGifOutput();
				validator.JhoveValidator.createJhoveChecker();
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
						validator.JhoveValidator.checkGifWithJhove(files.get(i));

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
}
