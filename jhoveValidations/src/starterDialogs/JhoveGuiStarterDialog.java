package starterDialogs;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import fileformats.gif.ValidateGif;
import fileformats.jpeg.ValidateJpeg;
import fileformats.jpeg.ValidateJpeg2000;
import fileformats.pdf.ValidatePdf;
import fileformats.tiff.ValidateTiff;
import fileformats.xml.ValidateXml;

public class JhoveGuiStarterDialog {
	
	public static String jhoveExaminationFolder;

	public static void main(String args[]) throws Exception {
		changecolor();
		String path = "D://Eclipse New//jhove-logo_small.gif";
		String description = "JHOVE Logo";
		ImageIcon icon = new ImageIcon(path, description);

		Object[] options = { "PDF", "GIF", "XML", "TIFF", "JPEG", "JPEG2000", "diverse" };
		int inteingabe = JOptionPane.showOptionDialog(null, "Which file format do you want to validate?", "Jhove Validation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[1]);

		JOptionPane.showMessageDialog(null, "Please choose the folder with files to validate.", "JHOVE Examination", JOptionPane.QUESTION_MESSAGE);
		 jhoveExaminationFolder = validatorUtilities.BrowserDialogs.chooseFolder();
		 
		 //TODO: add used Module
				
		switch (inteingabe) {
		case 0:		
			ValidatePdf.JhovePdfValidator();
			break;
		case 1:		
			ValidateGif.JhoveGifValidator();
			//TODO: XSLT Summary has to be developed further
			break;
		case 2:			
			ValidateXml.JhoveXmlValidator();
			break;
		case 3:	
			ValidateTiff.JhoveTiffValidator();
			break;
		case 4:
			ValidateJpeg.JhoveJpegValidator();
		
			break;
		case 5:
			ValidateJpeg2000.JhoveJpeg2000Validator();
			break;
		case 6:
			starterDialogs.ValidateDiverse.JhoveDiverseValidator();
			break;
		case 7:
		// TODO: develop all the other JHOVE modules if necessary
		break;
		default:
			JOptionPane.showMessageDialog(null, "Nothing will be done. Please choose properly.", "Misbehaviour", JOptionPane.WARNING_MESSAGE);
			
			break;
		}
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}
}
