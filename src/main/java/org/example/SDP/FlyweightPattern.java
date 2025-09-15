package org.example.SDP;

/*
UseFull when we have to share same object to say huge no of another object
Ex : Google Map using Tree/Road same Photos to all possible locations
 */

import java.util.HashMap;
import java.util.Map;

class TreeType{
    private String name ;
    private String color;
    private  String texture;

    public TreeType(String name , String color , String texture){
        this.name = name;
        this.color=color;
        this.texture=texture;
    }

    public  void draw (int x){
        System.out.println("loc is "+x +" for tree" + this.toString());
    }

    @Override
    public String toString() {
        return "TreeType{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", texture='" + texture + '\'' +
                '}';
    }
}

class Tree{
    private  final  int x;
    private final TreeType treeType;

    public Tree(int x , TreeType treeType){
        this.treeType=treeType;
        this.x=x;
    }

    public void draw(){
        treeType.draw(x);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "x=" + x +
                ", treeType=" + treeType +
                '}';
    }
}

class TreeFactory{

    public static  Map<String , TreeType > mp = new HashMap<>();

    static {
        mp.put("mango" , new TreeType("Mango","Green", "Yellow"));
    }

    public static TreeType getTreeType(String name){
        return mp.get(name);
    }
}
public class FlyweightPattern {

    public static void main(String[] args){

        // if we have to get location for 100 Trees
        // TreeType Object will not be created again  and again

        for(int i=0;i<100;i++){
            System.out.println(new Tree(i,TreeFactory.getTreeType("mango")));
        }
    }
}
