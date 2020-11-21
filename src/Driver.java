import java.io.IOException;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		System.out.println("Welcome to the Covid-19 Tracker!");
		HttpClient http = new HttpClient();
		String[] dates = getDateRangeFromUser();
		String jsonData = http.requestApi(dates);

		try {
			FilePrinter.printToCSV(jsonData);

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

	public static String[] getDateRangeFromUser() {
		@SuppressWarnings("resource")
		Scanner myObj = new Scanner(System.in);
		String[] dates = new String[2];

		System.out.println("Enter the start date in YYYY-MM-DD format:");
		String fromDate = myObj.nextLine().trim();
		dates[0] = fromDate;

		if (fromDate.length() < 10) {
			return new String[0];
		}

		System.out.println("Enter the end date in YYYY-MM-DD format:");
		String toDate = myObj.nextLine().trim();
		dates[1] = toDate;

		if (toDate.length() < 10) {
			return new String[0];
		}

		return dates;
	}
}
