package validatorUtilities;

public class genericUtilities {
	
	public static int[] getDate() {
		String date = new java.util.Date().toString();

		String[] parts = date.split(" ");

/*		for (int i = 0; i < parts.length; i++) {
			System.out.println(i + " " + parts[i]);
		}*/

		int year = Integer.parseInt(parts[5]);
		int day = Integer.parseInt(parts[2]);

		String monthstr = (parts[1]);
		int month = 0;

		switch (monthstr) {
		case "Jan":
			month = 1;
		case "Feb":
			month = 2;
		case "Mar":
			month = 3;
		case "Apr":
			month = 4;
		case "May":
			month = 5;
		case "Jun":
			month = 6;
		case "Jul":
			month = 7;
		case "Aug":
			month = 8;
		case "Sep":
			month = 9;
		case "Oct":
			month = 10;
		case "Nov":
			month = 11;
		case "Dec":
			month = 12;
		}

		int[] dateInt = { year, month, day };

/*		for (int j = 0; j < dateInt.length; j++) {		
		}	*/	
		return dateInt;
	}
	
	public static String normaliseToUtf8(String string) {
		String[] splitstring = string.split("&");
		String substitute = splitstring[0] + "&#38;" + splitstring[1];
		return substitute;
	}

}
