package com.goeuroapi;

/**
 * Created by nautilus on 7/10/16.
 */
import java.io.*;
import java.net.URL;
import javax.json.*;
import java.util.*;

public class myJsonStreaming {




    public static void main(String[] args) throws Exception{

        //Read City name from console
        System.out.println("enter City Name");
        Scanner sc = new Scanner(System.in);
        String cName = sc.nextLine().toString().trim();

        StringBuilder sb = new StringBuilder();
        sb.append("http://api.goeuro.com/api/v2/position/suggest/en/");
        sb.append(cName);



        URL url = new URL(sb.toString());
        //Read CSV location from console

        System.out.println(" enter csv Location:");
        Scanner _sc = new Scanner(System.in);
        String csvLocation = _sc.nextLine().toString().trim();

        System.out.println("searching for city: " + cName);

        try (InputStream is = url.openStream();
             JsonReader rdr = Json.createReader(is)) {
            //write the csv header
            FileWriter writer = new FileWriter(csvLocation);
            writer.append("_id");
            writer.append(',');
            writer.append("name");
            writer.append(',');
            writer.append("type");
            writer.append(',');
            writer.append("geo_position");
            writer.append('\n');

            //fill csv with results
            JsonArray results = rdr.readArray();
            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                writer.append((result.getJsonNumber("_id")).toString());
                writer.append(',');
                writer.append((result.getJsonString("name")).toString());
                writer.append(',');
                writer.append((result.getJsonString("type")).toString());
                writer.append(',');
                writer.append((result.getJsonObject("geo_position")).toString());
                writer.append('\n');

                //write results to console
                System.out.println((result.getJsonNumber("_id")));
                System.out.println((result.getJsonString("name")).toString());
                System.out.println((result.getJsonString("type")).toString());
                System.out.println((result.getJsonObject("geo_position")));


            }
            writer.flush();
            writer.close();
        }catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
}
