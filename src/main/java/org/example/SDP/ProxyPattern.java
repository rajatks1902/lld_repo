package org.example.SDP;

import java.util.HashMap;
import java.util.Map;

interface Video{

    void url(String url);
}

class  RealVideo implements Video{

    @Override
    public void url(String url) {
        System.out.println("Real URL");
    }
}

class CacheVideo implements  Video{

    private  RealVideo realVideo;
    private Map<String, String> cache;

    public  CacheVideo(){
        this.realVideo = new RealVideo();
        this.cache = new HashMap<>();
    }

    @Override
    public void url(String url) {
        if(cache.containsKey(url)){
            System.out.println("from Cache");
            return;
        }
        realVideo.url(url);
        cache.put(url,url);

    }
}

/*
  Similar to Filter Chain
  Ex : if a file se already Downloaded --> should be picked from cache 1st if present
 */
public class ProxyPattern {

    public static void main(String[] args){

        Video video = new CacheVideo();
        video.url("rajat");
        video.url("rajat");
        video.url("rajat");
        video.url("rajat");
        video.url("rajat");
    }
}
