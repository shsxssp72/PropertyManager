package com.Property.Utility;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class WeatherRequest {

    public String getWeather() {
        //参数字符串，如果拼接在请求链接之后，需要对中文进行 URLEncode   字符集 UTF-8
        String param = "key=99b27af8e18d4dda8ca7084333777f78&location=CN101020100";
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        try {
            //接口地址
            String url = "https://free-api.heweather.com/s6/weather/forecast";
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("accept", "*/*");
            //发送参数
            connection.setDoOutput(true);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();
            //接收结果
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            //缓冲逐行读取
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            /*System.out.println(sb.toString());*/
        } catch (Exception ignored) {
        } finally {
            //关闭流
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception ignored) {
            }
        }

        return sb.toString();
    }

    public String getLifeStyle(){
        //参数字符串，如果拼接在请求链接之后，需要对中文进行 URLEncode   字符集 UTF-8
        String param = "key=99b27af8e18d4dda8ca7084333777f78&location=CN101020100";
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        try {
            //接口地址
            String url = "https://free-api.heweather.com/s6/weather/lifestyle";
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("accept", "*/*");
            //发送参数
            connection.setDoOutput(true);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();
            //接收结果
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            //缓冲逐行读取
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            /*System.out.println(sb.toString());*/
        } catch (Exception ignored) {
        } finally {
            //关闭流
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception ignored) {
            }
        }

        return sb.toString();
    }

    public Map<String, Object> parseJSONToWeather(String jsonData) {
        Map<String, Object> contentValues = new HashMap<>();

        try {
            JSONObject object = new JSONObject(jsonData);
            JSONArray heweather = object.getJSONArray("HeWeather6");
            JSONObject weatherObject = heweather.getJSONObject(0);
            JSONArray daily_forecast = weatherObject.getJSONArray("daily_forecast");
            JSONObject forecast = daily_forecast.getJSONObject(0);


            if (forecast != null) {

                contentValues.put("data", forecast.getString("date"));
                contentValues.put("tmp_max", forecast.getString("tmp_max"));
                contentValues.put("tmp_min", forecast.getString("tmp_min"));
                contentValues.put("cond_txt_d", forecast.getString("cond_txt_d"));
                contentValues.put("cond_txt_n", forecast.getString("cond_txt_n"));
                contentValues.put("wind_dir", forecast.getString("wind_dir"));
                contentValues.put("wind_sc", forecast.getString("wind_sc"));
                contentValues.put("hum", forecast.getString("hum"));
                contentValues.put("pcpn", forecast.getString("pcpn"));
                contentValues.put("pop", forecast.getString("pop"));
                contentValues.put("uv_index", forecast.getString("uv_index"));
                contentValues.put("vis", forecast.getString("vis"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentValues;     //包含了所有的数据库信息
    }

    public Map<String, Object> parseJSONToLifeStyle(String jsonData){
        Map<String, Object> contentValues = new HashMap<>();

        try {
            JSONObject object = new JSONObject(jsonData);
            JSONArray heweather = object.getJSONArray("HeWeather6");
            JSONObject weatherObject = heweather.getJSONObject(0);
            JSONArray lifestyles = weatherObject.getJSONArray("lifestyle");

            contentValues.put("comf", lifestyles.getJSONObject(0).getString("txt"));
            contentValues.put("drsg", lifestyles.getJSONObject(1).getString("txt"));
            contentValues.put("flu", lifestyles.getJSONObject(2).getString("txt"));
            contentValues.put("sport", lifestyles.getJSONObject(3).getString("txt"));
            contentValues.put("air", lifestyles.getJSONObject(7).getString("txt"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentValues;
    }
}
