import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

public class ApiHandler {

    public String getWeek(int offset) throws IOException {

        LocalDate currentdate = LocalDate.now();

        int station = 76517002;
        int year = currentdate.getYear();
        int week = currentdate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + offset;

        URL url = new URL(String.format("https://skolmaten.se/api/4/menu/?station=%d&year=%d&weekOfYear=%d&count=1", station, year, week));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("USER_AGENT", "skolmaten-java");
        con.setRequestProperty("api-version", "4.0");
        con.setRequestProperty("client-token", "web");
        con.setRequestProperty("client-version-token", "web");
        con.setRequestProperty("locale", "sv_SE");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        JSONArray days = new JSONObject(String.valueOf(content)).getJSONObject("menu").getJSONArray("weeks").getJSONObject(0).getJSONArray("days");

        String[] dayNames = {
                "Mån",
                "Tis",
                "Ons",
                "Tor",
                "Fre"
        };

        StringBuilder result = new StringBuilder();
        result.append("<html>");
        result.append("Vecka: ");
        result.append(week);
        result.append("<br><br>");
        for (int i = 0; i < days.length(); i++) {
            result.append(dayNames[i]);
            result.append("<br>");
            try {
                JSONArray meals = days.getJSONObject(i).getJSONArray("meals");
                for (int j = 0; j < meals.length(); j++) {
                    JSONObject meal = meals.getJSONObject(j);
                    String mealValue = String.valueOf(meal.get("value"));
                    result.append(mealValue);
                    result.append("<br>");
                }
            } catch (Exception e) {
                try {
                    String reason = (String) days.getJSONObject(i).get("reason");
                    result.append(reason);
                    result.append("<br>");
                } catch (Exception f) {
                    result.append("Finns ingen matsedel<br>");
                }
            }
            result.append("<br>");
        }
        result.append("</html>");
        String resultString = String.valueOf(result);
        resultString = resultString.replaceAll("\\.", ".<br>");

        return resultString;
    }
}
