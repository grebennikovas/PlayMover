package ru.redate.playmover.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redate.playmover.ConnectionProperties;
import ru.redate.playmover.Consumers.Connections.HttpSender;
import ru.redate.playmover.Consumers.Consumer;
import ru.redate.playmover.Consumers.SpotifyConsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.PropertyResourceBundle;

@RestController
@RequestMapping("/api")
public class MainController {

    Consumer spotify = new SpotifyConsumer();

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public ResponseEntity<String> test(){
        return new ResponseEntity<>(ConnectionProperties.getProperty("spotify.client_id"), HttpStatus.OK);
    }

    @RequestMapping(value="/auth", method = RequestMethod.GET)
    public ResponseEntity<String> auth() throws IOException {

        String response = spotify.authorize();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value="/testget", method = RequestMethod.GET)
    public ResponseEntity<String> testGet(){
        try {
            return new ResponseEntity<>(HttpSender.Get("http://httpbin.org/get").get(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Something Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/testpost", method = RequestMethod.GET)
    public ResponseEntity<String> testPost() {
        try {
            return new ResponseEntity<>(HttpSender.Post("http://httpbin.org/post","Application/json","").get(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Something Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/hello_spotify", method = RequestMethod.GET)
    public ResponseEntity<String> spotifyCallback(@RequestParam(required = false) String code,
                                                  @RequestParam String state,
                                                  @RequestParam(required = false) String error) {
        System.out.println("code: " + code + ", state: " + state + ", error: " + error);
        return new ResponseEntity<>("code: " + code + ", state: " + state + ", error: " + error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/*")
    public String index() {
        return "index";
    }
}
