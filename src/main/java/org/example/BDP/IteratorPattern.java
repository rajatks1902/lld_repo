package org.example.BDP;

/*
Traversing through the elements without exposing the underlying operations
--> Ex : Vending Machine :  Do not know how are they stored internally
            --> As per the requested item the next iteration happens
    Ex : All Collection FrameWork implement IteratorPattern
 */

import java.util.ArrayList;
import java.util.List;

class Video{
    private  String title;

    public Video(String title) {
        this.title = title;
    }

    public String getTitle(){
        return  this.title;
    }
}

class YoutubeVideo {

    private List<Video> videoList = new ArrayList<>();

    public void addVideo(Video video) {
        videoList.add(video);
    }

    public List<Video> getVideoList() {
        return videoList;
    }
}

interface  Playlist {
    boolean hasNext();
    Video next();
}

// Concrete Iterator class

class Iterator implements Playlist{

    private List<Video> videos;
    private int position;

    public Iterator(List<Video> videos){
        this.videos=videos;
        this.position=0;
    }

    @Override
    public boolean hasNext() {
        return position < videos.size();
    }

    @Override
    public Video next() {
        // Main Business Logic
        return hasNext() ? videos.get(position++) : null;
    }
}

// We only see .next() -->the actual logic of filtration is hidden
public class IteratorPattern {

    public static void main(String[] args){
        YoutubeVideo yt = new YoutubeVideo();
        yt.addVideo(new Video("today"));
        yt.addVideo(new Video("LLD"));

        Playlist itr = new Iterator(yt.getVideoList());
        while(itr.hasNext()){
            System.out.println(itr.next().getTitle());
        }

    }
}
