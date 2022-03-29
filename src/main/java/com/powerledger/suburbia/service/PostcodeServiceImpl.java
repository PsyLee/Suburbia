package com.powerledger.suburbia.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerledger.suburbia.models.Postcode;
import com.powerledger.suburbia.service.repository.PostcodeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class PostcodeServiceImpl implements PostcodeService {

    @Autowired
    protected PostcodeRepository repository;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ArrayList<Postcode> postPostCode(ArrayList<Postcode> codes) {
        codes.stream().forEach(postcode -> repository.save(postcode));

        ArrayList<Postcode> returnAll = new ArrayList<>();
        repository.findAll().forEach(postcode -> returnAll.add(postcode));

        return returnAll;
    }

    @Override
    public String getNames(int fromPostCode, int toPostCode) {
        ArrayList<String> suburbnames = new ArrayList<>();
        int total = 0;
        for(int pc=fromPostCode; pc<=toPostCode; pc++) {
            if (repository.findById(pc).isPresent()) {
                for (String suburb:repository.findById(pc).get().getSuburbNames()) {
                    if (!suburbnames.contains(suburb)) {
                        suburbnames.add(suburb);
                        int count = suburb.replace(" ", "").length();
                        total+=count;
                    }
                }
            }
        }
        Collections.sort(suburbnames);
        String jsonString = new JSONObject()
                .put("suburb names:", suburbnames)
                .put("total chars", total)
                .toString();
        return jsonString;
    }

    @Override
    public ArrayList<Postcode> init(String state) throws IOException {

        ArrayList<Postcode> postcodeList = objectMapper.readValue(new File("src/main/resources/"+state+".json"), new TypeReference<ArrayList<Postcode>>(){});
        postcodeList.stream().forEach(postcode -> repository.save(postcode));
        ArrayList<Postcode> returnAll = new ArrayList<>();
        repository.findAll().forEach(postcode -> returnAll.add(postcode));

        return returnAll;
    }

}

