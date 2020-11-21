import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {

	public String requestApi(String[] dates) {
		OkHttpClient client = new OkHttpClient();
		String fromDate = null;
		String toDate = null;

		if (dates.length < 2) {
			LocalDate date = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			toDate = date.format(formatter);
			fromDate = date.minusDays(7).format(formatter);
			System.out.println("Requesting the Covid cases from the past week.");

		} else {
			fromDate = dates[0];
			toDate = dates[1];
			System.out.println("Requesting the Covid cases from " + fromDate + " to " + toDate + ".");
		}

		Request request = new Request.Builder()
				.url("https://api.covid19api.com/country/canada?from=" + fromDate + "T00:00:00Z&to=" + toDate + "T00:00:00Z").build();
		Response response = null;

		try {
			response = client.newCall(request).execute();
			return response.body().string();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
