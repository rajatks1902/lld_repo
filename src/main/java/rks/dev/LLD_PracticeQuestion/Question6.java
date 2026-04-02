package rks.dev.LLD_PracticeQuestion;

import java.util.ArrayList;
import java.util.List;

/*

Problem Statement:

Design a File System where you have:

1. Files
2. Directories

-----------------------------------------------
Requirements:

1. Both File and Directory should support:
   - showDetails()

2. A Directory can contain:
   - Files
   - Other Directories

3. You should be able to do:

   Directory root = new Directory("root");

   root.add(new File("file1.txt"));

   Directory subDir = new Directory("sub");
   subDir.add(new File("file2.txt"));

   root.add(subDir);

   root.showDetails();

-----------------------------------------------
Expected Output:

root
  file1.txt
  sub
    file2.txt

-----------------------------------------------
Constraints:

- Treat File and Directory uniformly
- Follow Open/Closed Principle
- Avoid type-checking (instanceof)

-----------------------------------------------
Follow-up Questions:

1. What pattern is this?
2. Why not use simple List<File>?
3. Difference between Composite and Decorator?

=========================================================
*/
/*
-----------------------------------------------
Component Interface
-----------------------------------------------
*/
interface FileSystemComponent {
    void showDetails(int level);
}

/*
-----------------------------------------------
Leaf Node (File)
-----------------------------------------------
*/
class FileLeaf implements FileSystemComponent {

    private final String name;

    public FileLeaf(String name) {
        this.name = name;
    }

    @Override
    public void showDetails(int level) {
        System.out.println("  ".repeat(level) + name);
    }
}

/*
-----------------------------------------------
Composite Node (Directory)
-----------------------------------------------
*/
class Directory implements FileSystemComponent {

    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    @Override
    public void showDetails(int level) {
        System.out.println("  ".repeat(level) + name);

        for (FileSystemComponent component : children) {
            component.showDetails(level + 1);
        }
    }
}

/*
-----------------------------------------------
Client
-----------------------------------------------
*/
public class Question6 {

    public static void main(String[] args) {

        Directory root = new Directory("root");

        root.add(new FileLeaf("file1.txt"));

        Directory subDir = new Directory("sub");
        subDir.add(new FileLeaf("file2.txt"));

        root.add(subDir);

        root.showDetails(0);
    }
}
