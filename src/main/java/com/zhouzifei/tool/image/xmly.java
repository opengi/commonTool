package com.zhouzifei.tool.image;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.zhouzifei.tool.html.Randoms;
import com.zhouzifei.tool.media.file.FileUtil;
import com.zhouzifei.tool.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.data.redis.core.script.ReactiveScriptExecutor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 周子斐
 * @date 2021/2/7
 * @Description
 */
@Slf4j
public class xmly {
    //@Test
    public static String pic(String tsUrl){
        final InputStream inputStream = FileUtil.getInputStreamByUrl(tsUrl, "");
        if(null == inputStream){
            throw new RuntimeException();
        }
        String result = post("https://upload.ximalaya.com/dtres/headerThumb/upload"
                , "_xmLog=h5&2872be80-f5d5-4747-9b75-cb9e34503cf0&2.2.17; Hm_lvt_4a7d8ec50cfd6af753c4f8aee3425070=1612664140; trackType=web; fds_otp=8172383124598533914; 1&remember_me=y; 1&_token=296993092&6B62AF70140N62AD80A28DA7DB1479A11D32B64352BEB4ECBC37887968F72543742F4606E28F13M5F3014F4AE8BE20_; 1_l_flag=296993092&6B62AF70140N62AD80A28DA7DB1479A11D32B64352BEB4ECBC37887968F72543742F4606E28F13M5F3014F4AE8BE20__2021-02-0710:16:26; x_xmly_traffic=utm_source%253A%2526utm_medium%253A%2526utm_campaign%253A%2526utm_content%253A%2526utm_term%253A%2526utm_from%253A; Hm_lpvt_4a7d8ec50cfd6af753c4f8aee3425070=1612664187"
                , inputStream);
        final JSONObject parse = JSONObject.parseObject(result);
        if(null == parse){
            throw new RuntimeException();
        }
        final Object data = parse.get("data");
        if(null == data){
            throw new RuntimeException();
        }
        final Object url = ((JSONArray) data).get(0);
        if(null == url){
            throw new RuntimeException();
        }
        final Object url1 = ((JSONObject) url).get("url");
        return (String) url1;
    }

    public static  String post(String url, String cookie, InputStream inputStream){
        final String fileName = Randoms.alpha(6) + "/" + Randoms.alpha(20) + ".jpg";
        //flush输出流的缓冲
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httppost = new HttpPost(url);
            InputStreamBody bin = new InputStreamBody(inputStream, fileName);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", bin);
            httppost.setEntity(reqEntity);
            httppost.setHeader("cookie", cookie);
            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                //EntityUtils.consume(resEntity);
                return EntityUtils.toString(resEntity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    public static void main(String[] args){
        final File file = new File("/Users/Dabao/Downloads/123.jpg");
//        final FileInputStream inputStream = new FileInputStream(file);
//        String result = post("https://upload.ximalaya.com/dtres/headerThumb/upload"
//                , "_xmLog=h5&2872be80-f5d5-4747-9b75-cb9e34503cf0&2.2.17; Hm_lvt_4a7d8ec50cfd6af753c4f8aee3425070=1612664140; trackType=web; fds_otp=8172383124598533914; 1&remember_me=y; 1&_token=296993092&6B62AF70140N62AD80A28DA7DB1479A11D32B64352BEB4ECBC37887968F72543742F4606E28F13M5F3014F4AE8BE20_; 1_l_flag=296993092&6B62AF70140N62AD80A28DA7DB1479A11D32B64352BEB4ECBC37887968F72543742F4606E28F13M5F3014F4AE8BE20__2021-02-0710:16:26; x_xmly_traffic=utm_source%253A%2526utm_medium%253A%2526utm_campaign%253A%2526utm_content%253A%2526utm_term%253A%2526utm_from%253A; Hm_lpvt_4a7d8ec50cfd6af753c4f8aee3425070=1612664187"
//                , inputStream);
        final String result = pic("https://tva2.sinaimg.cn/large/0072Vf1pgy1fodqipz6i7j31kw0vk7wh.jpg");
        System.out.println(result);
    }
}
