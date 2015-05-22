package jhoveobjects;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class JhoveFileObject {
	
	public String path;	
	public String fileName;
	long creationYear;
	public String creationSoftware;
	public String jhoveModul;
	public String status;
	public String extension;
	public int jhovemessagesNo;
	public ArrayList<String> jhovemessagestext = new ArrayList<String>();
	
	
	
	public String getName(String path) {
		String[] parts = path.toString().split(Pattern.quote("\\"));
		fileName = parts[parts.length - 1]; // filename including extension
		String[] segs = fileName.split(Pattern.quote(".")); //das haut aber auch nicht immer hin, oft ist ein Punkt im Dateinamen
		fileName = segs[segs.length - 2];
		return fileName;
	}
	
	public String getExtension(String path) {
		String[] parts = path.toString().split(Pattern.quote("\\"));
		extension = parts[parts.length - 1]; // filename including extension
		String[] segs = extension.split(Pattern.quote(".")); //das haut aber auch nicht immer hin, oft ist ein Punkt im Dateinamen
		extension = segs[segs.length - 1];
		extension = extension.toLowerCase();
		return extension;
	}

}
