package de.goeuro.test.services;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TransformationService {
	public void transform(String cityName) {
		String baseURL = "http://api.goeuro.com/api/v2/position/suggest/en/";
		String url = baseURL + cityName;
		try {
			JSONArray locations = getJSONArray(url);
			writeCSV(locations);
			System.out.println("Transformation successful!");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static JSONArray getJSONArray(String url) throws Exception {
		try {
			URL urlObject = new URL(url);
			URLConnection urlConnection = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			StringBuilder output = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				output.append(inputLine);

			in.close();

			return new JSONArray(output.toString());
		} catch (IOException e) {
			throw new Exception("Wrong input! Please check your input and try again.");
		} catch (JSONException e) {
			throw new Exception("Wrong Data!");
		}
	}

	private static void writeCSV(JSONArray locations) throws Exception {

		try {
			FileWriter fileWriter = new FileWriter("output.csv");

			for (int i = 0; i < locations.length(); i++) {
				JSONObject location = locations.getJSONObject(i);
				Long _id = location.getLong("_id");
				String name = location.getString("name");
				String type = location.getString("type");
				JSONObject geoPosition = location.getJSONObject("geo_position");
				Double latitude = geoPosition.getDouble("latitude");
				Double longitude = geoPosition.getDouble("longitude");
				fileWriter.append(_id.toString() + ',' + name + ',' + type + ',' + latitude.toString() + ','
						+ longitude.toString() + "\n");
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			throw new Exception("Output CSV file could not be created!");
		} catch (JSONException e) {
			throw new Exception("Wrong Data!");
		}
	}
}
