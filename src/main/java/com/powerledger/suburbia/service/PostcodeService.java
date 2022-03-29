package com.powerledger.suburbia.service;

import com.powerledger.suburbia.models.Postcode;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface PostcodeService {

    ArrayList<Postcode> postPostCode(ArrayList<Postcode> codes);

    String getNames(int fromPostCode, int toPostCode);

    ArrayList<Postcode> init(String state) throws IOException, ParseException;

}
