package starterDialogs;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jhoveobjects.JhoveFileObject;

public class JhoveGuiStarterDialogObjects {

	public static String jhoveExaminationFolder;
	String pathwriter;

	public static void main(String args[]) throws Exception {
		changecolor();
		String path = "D://Eclipse__Jython__Py_dev//jhove-logo_small.gif";
		String description = "JHOVE Logo";
		ImageIcon icon = new ImageIcon(path, description);

		JOptionPane.showMessageDialog(null, "Please choose the folder with files to validate.", "JHOVE Examination", JOptionPane.QUESTION_MESSAGE, icon);
		jhoveExaminationFolder = validatorUtilities.BrowserDialogs.chooseFolder();

		if (jhoveExaminationFolder != null) {
			ArrayList<File> files = validatorUtilities.ListsFiles.getPaths(new File(jhoveExaminationFolder), new ArrayList<File>());

			ArrayList<JhoveFileObject> findings = new ArrayList<JhoveFileObject>();

			// TODO: neue arraylist aus jhovefileobjekten

			// To handle one file after the other
			for (int i = 0; i < files.size(); i++) {

				// the outputfiles files should not be examined
				if ((files.get(i).toString().contains("JhoveExamination")) || (files.get(i).toString().contains("JhoveCustomized"))) {
				}

				else {

					JhoveFileObject temp = new JhoveFileObject();
					temp.path = files.get(i).toString();

					temp.fileName = temp.getName(temp.path);

					// TODO: die jhoveobjekte mit infos fuellen

					// Problem: Jhove erstellt eine XML File. Ich koennte die
					// natuerlich für jede Datei ertstellen, schließen und dann
					// auslesen, aber das erscheint mir etwas bescheuert.

					findings.add(temp);
				}
			}

			for (int i = 0; i < findings.size(); i++) {
				System.out.println(findings.get(i).fileName);
			}

		}
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}

}
