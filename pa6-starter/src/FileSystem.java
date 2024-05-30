import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    // TODO
    public FileSystem() {
        nameMap = new MyHashMap<>();
        dateMap = new MyHashMap<>();
    }

    // TODO
    public FileSystem(String inputFile) {
        // Add your code here
        this();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Add your code here
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // TODO
    public boolean add(String fileName, String directory, String modifiedDate) {
        FileData fileData = new FileData(fileName, directory, modifiedDate);

        if(!nameMap.containsKey(fileName)){
            nameMap.put(fileName, new ArrayList<>());
        }

        nameMap.get(fileName).add(fileData);

        if(!dateMap.containsKey(modifiedDate)){
            dateMap.put(modifiedDate, new ArrayList<>());
        }
        dateMap.get(modifiedDate).add(fileData);

        return true;
    }

    // TODO
    public FileData findFile(String name, String directory) {
        if(nameMap.containsKey(name)){
            for(FileData fileData : nameMap.get(name)){
                if(fileData.dir.equals(directory)){
                    return fileData;
                }
            }
        }
    return null;

    }

    // TODO
    public ArrayList<String> findAllFilesName() {
        return new ArrayList<>(nameMap.keys());

    }

    // TODO
    public ArrayList<FileData> findFilesByName(String name) {
        if(nameMap.containsKey(name)){
            return nameMap.get(name);
        }
        return new ArrayList<>();

    }

    // TODO
    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
        if(dateMap.containsKey(modifiedDate)){
            return dateMap.get(modifiedDate);
        }
        return new ArrayList<>();
    }

    // TODO
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
        ArrayList<FileData> result = new ArrayList<>();

        if (dateMap.containsKey(modifiedDate)) {
            for (FileData fileData : dateMap.get(modifiedDate)) {
                boolean foundInOtherDir = false;
                for (FileData otherFile : dateMap.get(modifiedDate)) {
                    if (!fileData.dir.equals(otherFile.dir) && fileData.name.equals(otherFile.name)) {
                        foundInOtherDir = true;
                        break;
                    }
                }

                if (foundInOtherDir) {
                    result.add(fileData);
                }
            }
        }

        return result;

    }

    // TODO
    public boolean removeByName(String name) {
        if (nameMap.containsKey(name)) {
            ArrayList<FileData> filesToRemove = nameMap.get(name);
            
            for (FileData fileData : filesToRemove) {
                dateMap.get(fileData.lastModifiedDate).remove(fileData);
                if (dateMap.get(fileData.lastModifiedDate).isEmpty()) {
                    dateMap.remove(fileData.lastModifiedDate);
                }
            }
            nameMap.remove(name);
            return true;
        }

        return false;

    }

    // TODO
    public boolean removeFile(String name, String directory) {
        if (nameMap.containsKey(name)) {
            ArrayList<FileData> files = nameMap.get(name);
            FileData fileToRemove = null;
            for (FileData fileData : files) {
                if (fileData.dir.equals(directory)) {
                    fileToRemove = fileData;
                    break;
                }
            }
            if (fileToRemove != null) {
                files.remove(fileToRemove);
                if (files.isEmpty()) {
                    nameMap.remove(name);
                }
                ArrayList<FileData> dateFiles = dateMap.get(fileToRemove.lastModifiedDate);
                dateFiles.remove(fileToRemove);
                if (dateFiles.isEmpty()) {
                    dateMap.remove(fileToRemove.lastModifiedDate);
                }
                return true;
            }
        }
        return false;
    }


    }

