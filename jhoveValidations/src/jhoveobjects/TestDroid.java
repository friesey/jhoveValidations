package jhoveobjects;

import java.io.File;

import uk.gov.nationalarchives.droid.core.signature.droid4.Droid;
import uk.gov.nationalarchives.droid.core.signature.droid4.FileFormatHit;
import uk.gov.nationalarchives.droid.core.signature.droid4.IdentificationFile;
import uk.gov.nationalarchives.droid.core.signature.droid4.signaturefile.FileFormat;

public class TestDroid {

	public static void main(String args[]) throws Exception {

		String test = "D://test.pdf";
		File testfile = new File(test);

		String signaturefileV81 = "C://Users//Friese Yvonne//.m2//DROID_SignatureFile_V81.xml";

		Droid droid = null;
		try {

			droid = new Droid();
			droid.readSignatureFile(signaturefileV81);

			System.out.println(testfile.getAbsolutePath());

			String puid = "";
			IdentificationFile ifile = droid.identify(testfile.getAbsolutePath());
			for (int x = 0; x < ifile.getNumHits(); x++) {
				FileFormatHit ffh = ifile.getHit(x);
				FileFormat ff = ffh.getFileFormat();
				puid = ff.getPUID();
			}

			System.out.println(puid);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
