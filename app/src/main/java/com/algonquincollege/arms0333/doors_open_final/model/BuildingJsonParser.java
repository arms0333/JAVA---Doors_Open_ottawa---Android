package com.algonquincollege.arms0333.doors_open_final.model;

/**
 * Created by codyarmstrong on 2016-12-06.
 */


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuildingJsonParser {

    public static List<Building> parseFeed(String content) {

        try {
            JSONObject jsonResponse = new JSONObject(content);
            JSONArray buildingArray = jsonResponse.getJSONArray("buildings");
            List<Building> buildingList = new ArrayList<>();

            for (int i = 0; i < buildingArray.length(); i++) {

                JSONObject obj = buildingArray.getJSONObject(i);
                Building building = new Building();

                building.setBuildingId(obj.getInt("buildingId"));
                building.setName(obj.getString("name"));
                building.setAddress(obj.getString("address"));
                building.setDescription(obj.getString("description"));
                building.setImage(obj.getString("image"));

                JSONArray openDoors = obj.getJSONArray("open_hours");
                ArrayList<String> doorsDates=new ArrayList<>();
                for(int j = 0; j < openDoors.length(); j++){
                    doorsDates.add(openDoors.getJSONObject(j).getString("date"));
                }
                building.setOpen_doors(doorsDates);
                buildingList.add(building);
            }

            return buildingList;
        } catch (JSONException e) {
            Log.e("TAG", "its", e);
            e.printStackTrace();
            return null;
        }
    }
}
