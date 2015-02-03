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
			break;
		case "Feb":
			month = 2;
			break;
		case "Mar":
			month = 3;
			break;
		case "Apr":
			month = 4;
			break;
		case "May":
			month = 5;
			break;
		case "Jun":
			month = 6;
			break;
		case "Jul":
			month = 7;
			break;
		case "Aug":
			month = 8;
			break;
		case "Sep":
			month = 9;
			break;
		case "Oct":
			month = 10;
			break;
		case "Nov":
			month = 11;
			break;
		case "Dec":
			month = 12;
			break;
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
