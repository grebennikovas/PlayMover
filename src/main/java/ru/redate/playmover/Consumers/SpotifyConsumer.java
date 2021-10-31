package ru.redate.playmover.Consumers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.redate.playmover.ConnectionProperties;
import ru.redate.playmover.Consumers.Connections.HttpSender;
import ru.redate.playmover.DTO.Song;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

public class SpotifyConsumer extends ConsumerProps implements Consumer {

    String authURI;

    public SpotifyConsumer(){
        super();
        authURI = ConnectionProperties.getProperty("spotify.uri.auth");
        login = ConnectionProperties.getProperty("spotify.client_id");
        password = ConnectionProperties.getProperty("spotify.secret");
    }

    @Override
    public boolean setLogin(String login) {
        super.login = login;
        return true;
    }

    @Override
    public boolean setPassword(String password) {
        super.password = password;
        return true;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String authorize() throws IOException {

        /*

     response_type: 'code',
      client_id: client_id,
      scope: scope,
      redirect_uri: redirect_uri,
      state: state

     */
        String scope = "user-read-private user-read-email";
        String state = UUID.randomUUID().toString();
        state = state.replace('-','a');
        state = state.substring(15);

        HashMap<String,String> parameters = new HashMap<>();

        parameters.put("response_type","code");
        parameters.put("client_id",login);
        parameters.put("scopes",scope);
        parameters.put("redirect_uri","http://localhost:8080/api/hello_spotify");
        parameters.put("state",state);

        return HttpSender.Get(authURI,parameters).get();
    }

    @Override
    public boolean createPlaylist(String name) {
        return false;
    }

    @Override
    public List<Song> getSongsFromPlaylist(String playlistName) {
        return null;
    }

    @Override
    public List<Song> getSongsFromMyMusic() {
        //String a = PropertyResourceBundle.getBundle("connection.propperties").getString()
        return null;
    }

    @ConfigurationProperties(prefix="spotify")
    @Configuration
    class SpotifyProperties {
        String client_id;
        String secret;
        String uri_authorization;
    }
}
