package starterDialogs;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.io.PrintWriter;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import jhoveobjects.JhoveFileObject;

public class JhoveGuiStarterDialogObjects {

	public static String jhoveExaminationFolder;

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

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			ObjectValidation.pathwriter = (starterDialogs.JhoveGuiStarterDialogObjects.jhoveExaminationFolder + "//" + "JhovetemporaryFile.xml");

			PrintWriter xmlsummary = new PrintWriter(new FileWriter((jhoveExaminationFolder + "//" + "JhoveExaminationSummary" + ".xml")));

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";
			String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"JhoveCustomized.xsl\"?>";

			xmlsummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			xmlsummary.println(xmlxslStyleSheet);
			xmlsummary.println("<JhoveFindingsSummary>");
			outputs.XslStyleSheets.JhoveObjectsCustomizedXsl(); 
	

			ArrayList<String> errormessages = new ArrayList<String>();

			// TODO: neue arraylist aus jhovefileobjekten

			// To handle one file after the other
			for (int i = 0; i < files.size(); i++) {							

				// the outputfiles files should not be examined
				if ((!files.get(i).toString().contains("JhoveExamination")) && (!files.get(i).toString().contains("JhoveCustomized")) && (!files.get(i).toString().contains("JhovetemporaryFile"))) {

					JhoveFileObject temp = new JhoveFileObject();
					temp.path = files.get(i).toString();

					temp.fileName = temp.getName(temp.path);
					temp.extension = temp.getExtension(temp.path);
					// TODO: Falls Droid eines Tages einbettbar ist,
					// Dateiformatidentifikation via DROID durchfuehren

					ObjectValidation.jhoveValidation(files.get(i));

					Document doc = dBuilder.parse(ObjectValidation.pathwriter);
					doc.getDocumentElement().normalize();

					NodeList nList = doc.getElementsByTagName("repInfo");

					for (int n = 0; n < nList.getLength(); n++) {

						Node nNode = nList.item(n);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;

							temp.status = eElement.getElementsByTagName("status").item(0).getTextContent();
					

							temp.jhoveModul = eElement.getElementsByTagName("reportingModule").item(0).getTextContent();
					

						}
					}

					findings.add(temp);
				}

				// im naechsten Schritt wird die temp.xml Datei mit dem
				// JHOVE Output der naechsten zu untersuchenden Datei
				// ueberschrieben

			}

	

			for (int l = 0; l < findings.size(); l++) {
				System.out.println("File Name: " + findings.get(l).fileName);
				System.out.println("File extension: " + findings.get(l).extension);
				System.out.println("File status: " + findings.get(l).status);
				System.out.println("Reporting Module: " + findings.get(l).jhoveModul);
				
				xmlsummary.println("<File>");
				xmlsummary.println("<FileName>" + findings.get(l).fileName + "</FileName>");
				xmlsummary.println("<FileExtension>" + findings.get(l).extension + "</FileExtension>");
				xmlsummary.println("<Status>" + findings.get(l).status + "</Status>");
				xmlsummary.println("<Module>" + findings.get(l).jhoveModul + "</Module>");
				
				xmlsummary.println("</File>");
			}
			
			xmlsummary.println("</JhoveFindingsSummary>");
			xmlsummary.close();

		}
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}

}
