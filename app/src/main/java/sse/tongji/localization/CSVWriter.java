package sse.tongji.localization;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 13987 on 2016/4/21.
 */
public class CSVWriter {

    private String dirPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    private File file;
    private FileWriter writer;

    private void writeCsv(){
        try {
            writer = new FileWriter(file);
            writeCsvData("FirstParam","SecondParam","ThirdParam");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(String fileName){
        File gpxfile = new File(dirPath, fileName + ".csv");
        this.file = gpxfile;
    }

    public CSVWriter(String fileName) {
        createFile(fileName);
    }

    private void writeCsvData(String h1, String h2, String h3) throws IOException {
        String line = String.format("%s,%s,%s\n", h1,h2,h3);
        writer.write(line);
    }

    void appendTextToFile( String text){

        File f = new File(dirPath, "mydata.csv");

        if(!f.exists()){
            createFile(f);
        }
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            out.println(text);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void createFile(File f){
        File parentDir = f.getParentFile();
        try{
            parentDir.mkdirs();
            f.createNewFile();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
