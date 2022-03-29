package com.powerledger.suburbia.controller;

import com.powerledger.suburbia.models.Postcode;
import com.powerledger.suburbia.service.PostcodeService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class PostcodesController {

    @Autowired
    protected PostcodeService postcodeService;

    @PostMapping(value="/postcodes")
    public ArrayList<Postcode> postAnArrayOfPostcodes(@RequestBody ArrayList<Postcode> postcode) {
        return postcodeService.postPostCode(postcode);
    }

    @GetMapping("/{first}/{second}")
    public String getSuburbNamesForGivenRange(@PathVariable("first") int first, @PathVariable("second") int second) {
            return postcodeService.getNames(first, second);
    }

    /**
     * Set Postcodes And Suburb Names For A Given State
     * Example: a given parameter on a get method can be WA for West Australia
     * **/
    @GetMapping("/{state}")
    public ArrayList<Postcode> setPostcodesAndSuburbsForAGivenState(@PathVariable("state") String state) throws IOException, ParseException {
        return postcodeService.init(state);
    }
}