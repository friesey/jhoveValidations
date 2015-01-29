package starterDialogs;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class JhoveGuiStarterDialog {

	public static void main(String args[]) throws Exception {
		changecolor();
		String path = "D://Eclipse New//jhove-logo_small.gif";
		String description = "JHOVE Logo";
		ImageIcon icon = new ImageIcon(path, description);

		Object[] options = { "PDF", "GIF", "XML", "TIFF" };
		int inteingabe = JOptionPane.showOptionDialog(null, "Which file format do you want to validate?", "Jhove Validation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[1]);

		switch (inteingabe) {
		case 0:		
			fileformats.pdf.validatePdf.JhovePdfValidator();
			break;
		case 1:		
			validator.JhoveValidator.JhoveGifValidator();
			break;
		case 2:
			JOptionPane.showMessageDialog(null, "JHOVE will be used to validate XML files from a chosen folder", "XML Validation", JOptionPane.INFORMATION_MESSAGE);
			// TODO: develop xml validator
			break;
		case 3:	
			validator.JhoveValidator.JhoveTiffValidator();
			break;
		case 4:
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
