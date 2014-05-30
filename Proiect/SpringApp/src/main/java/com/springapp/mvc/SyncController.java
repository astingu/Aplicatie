package com.springapp.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapp.model.MoviesDetails;
import com.springapp.model.SyncServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by astingu on 5/29/2014.
 */
public class SyncController implements Runnable {

    String server_url;
    List<MoviesDetails> moviesDetails = new ArrayList<MoviesDetails>();

    public SyncController(String server_url){
        this.server_url = server_url;
    }

    @Override
    public void run() {

        /* se parseaza json-ul luat de pe server, modific caracterele cu UTF-8, dupa care salvez doar anumite date
         * care mi s-au parut mai importante despre filmul respectiv */
        RestTemplate restTemplate = new RestTemplate();

        String jsonString = restTemplate.getForObject(server_url, String.class);

        byte jsonBytes[] = jsonString.getBytes();

        String dataString = null;

        try {
            dataString = new String(jsonBytes, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        SyncServer syncServer = null;
        try {
            syncServer = mapper.readValue(dataString,  SyncServer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < syncServer.getNoEntries(); i++){

            MoviesDetails newObject = new MoviesDetails();

            newObject.setTitle(syncServer.getContent().get(i).getTitle());
            newObject.setUrl(syncServer.getContent().get(i).getUrl());
            newObject.setPoster(syncServer.getContent().get(i).getPoster());
            newObject.setYear(syncServer.getContent().get(i).getYear());

            moviesDetails.add(newObject);
        }
    }
}
