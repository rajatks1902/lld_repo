package org.example.BDP;

/*
 --> When we want to have snapshot at different stage of a lifeCycle of the Object
        --> Example Undo/Redo in the Text Editor
 */

import java.util.Stack;

class Resume {

    private  String name;
    private  String exp;

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "name='" + name + '\'' +
                ", exp='" + exp + '\'' +
                '}';
    }

    public  Memento save(){
        return  new Memento(name,exp);
    }

    public  void restore(Memento memento){
        this.name = memento.getName();
        this.exp = memento.getExp();
    }

    public  static class Memento{
        private final String name;

        private  final String exp;

        private  Memento(String name , String exp){
            this.exp=exp;
            this.name=name;
        }

        public String getName() {
            return name;
        }

        public String getExp() {
            return exp;
        }
    }
}

class ResumeHistory{

    private Stack<Resume.Memento> his = new Stack<>();

    public  void save(Resume resume){
        his.push(resume.save());
    }

    public  void undo(Resume resume){
        if(!his.empty()){
            resume.restore(his.pop());
        }
    }
}
public class MementoPattern {

    public static  void main(String[] args){
        Resume resume = new Resume();
        ResumeHistory resumeHistory = new ResumeHistory();

        resume.setExp("2");
        resume.setName("Rajat");
        resumeHistory.save(resume);
        System.out.println(resume.toString());
        resume.setExp("3");
        System.out.println(resume.toString());
        resumeHistory.undo(resume);
        System.out.println(resume.toString());

    }
}
