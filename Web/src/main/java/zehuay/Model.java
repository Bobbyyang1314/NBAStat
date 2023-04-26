package zehuay;

/**
 * Author: Bobby Yang
 * Email: zehuay@andrew.cmu.edu
 * Name: Model
 */
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Model {

    long miniStartTime;
    long miniEndTime;
    long totalTime;
    String startTime;
    String endTime;
    String searchInput;
    String responseMessage;

    // Base api url from: https://www.balldontlie.io/home.html#getting-started
    String base_url = "https://www.balldontlie.io/api/v1/players?search=";
    String fullUrl;

    /**
     * Get request from android
     * @param request
     * @return String
     * @throws IOException
     */
    public String playerInfo(String request) throws IOException {

        totalTime = 0;

        searchInput = request;

        fullUrl = base_url + request;

        URL url = new URL(fullUrl);

        miniStartTime = System.currentTimeMillis();

        // Get the time stamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startTime = now.format(formatter);

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);

        JSONObject jsonObject = new JSONObject(response);
        List<Player> playerList = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject info = jsonArray.getJSONObject(i);
            Player player = new Player();
            // Format data into database
            player.height = info.isNull("height_feet") ? "N/A" : String.format("%d'%d\"", info.getInt("height_feet"), info.getInt("height_inches"));
            player.weight = info.isNull("weight_pounds") ? "N/A" : String.valueOf(info.getInt("weight_pounds")) + " lbs";
            player.position = info.isNull("position") ? "N/A" : info.getString("position");
            player.name = info.isNull("first_name") || info.isNull("last_name") ? "N/A" : info.getString("first_name") + " " + info.getString("last_name");
            player.team = info.isNull("team") || info.getJSONObject("team").isNull("full_name") ? "N/A" : info.getJSONObject("team").getString("full_name");
            playerList.add(player);
        }

        Gson gson = new Gson();
        responseMessage = gson.toJson(playerList);

        miniEndTime = System.currentTimeMillis();
        // Get the time stamp
        LocalDateTime end_now = LocalDateTime.now();
        DateTimeFormatter end_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        endTime = end_now.format(end_formatter);
        // total time need
        totalTime = miniEndTime - miniStartTime;
        return responseMessage;
    }
}
