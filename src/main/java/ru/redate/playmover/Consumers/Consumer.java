package ru.redate.playmover.Consumers;

import ru.redate.playmover.DTO.Song;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

public interface Consumer {

    boolean setLogin(String login);

    boolean setPassword(String Password);

    String getLogin();

    String getPassword();

    String authorize() throws IOException;

    boolean createPlaylist(String name);

    List<Song> getSongsFromPlaylist(String playlistName);

    List<Song> getSongsFromMyMusic();


}
