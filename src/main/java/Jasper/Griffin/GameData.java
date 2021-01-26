package Jasper.Griffin;

import java.util.HashMap;

public class GameData {

    // stores each country by the server id
    HashMap<String, String> countryData = new HashMap<String, String>();

    // stores each canvas by server id
    HashMap<String, String[]> canvasData = new HashMap<String, String[]>();

    // stores each number by server id
    HashMap<String, Integer> numberData = new HashMap<String, Integer>();

    // stores each counter by server id
    HashMap<String, Integer> counterData = new HashMap<String, Integer>();

    // stores each boolean by server id
    HashMap<String, Boolean> booleanData = new HashMap<String, Boolean>();

    // stores each set by server id
    HashMap<String, Integer[]> setData = new HashMap<String, Integer[]>();
}
