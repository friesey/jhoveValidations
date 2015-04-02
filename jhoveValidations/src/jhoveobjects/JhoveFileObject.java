package jhoveobjects;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class JhoveFileObject {
	
	public String path;	
	public String fileName;
	long creationYear;
	String creationSoftware;
	String jhoveModul;
	String status;
	int jhovemessagesNo;
	ArrayList<String> jhovemessagestext = new ArrayList<String>();
	
	
	public String getName(String path) {
		String[] parts = path.toString().split(Pattern.quote("\\"));
		fileName = parts[parts.length - 1]; // filename including extension
		String[] segs = fileName.split(Pattern.quote("."));
		fileName = segs[segs.length - 2];
		return fileName;
	}

}
