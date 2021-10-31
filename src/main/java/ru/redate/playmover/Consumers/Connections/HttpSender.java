package ru.redate.playmover.Consumers.Connections;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpSender {

    public static Optional<String> Post(String url, String contentType, String requestBody)
            throws MalformedURLException, IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", contentType);
        connection.setConnectTimeout(5000);
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        try(OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(requestBody);
        }

        if (connection.getResponseCode() != 200) {
            System.err.println("connection failed");
            return Optional.empty();
        }

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            return Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
        }
    }

    public static Optional<String> Get(String url, Map<String,String> parameters) throws IOException {
        url = url.concat(getParamsString(parameters));
        //return Get(url);
        return Optional.of(url);
    }

    public static Optional<String> Get(String url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(5000);

        System.out.println(url);

        if (connection.getResponseCode() != 200) {
            System.out.println("connection failed");
            return Optional.empty();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        return Optional.of(response.toString());
    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
