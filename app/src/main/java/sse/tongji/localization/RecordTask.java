package sse.tongji.localization;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by 13987 on 2016/4/21.
 */
public class RecordTask extends TimerTask {

    private String dirPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    public void run() {
        //InfomationManager.setRecord();
        String outputFile = "users.csv";

        File file = new File(dirPath, outputFile);

        // before we open the file check to see if it already exists
        boolean alreadyExists = file.exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
                csvOutput.write("id");
                csvOutput.write("name");
                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            // write out a few records
            csvOutput.write("1");
            csvOutput.write("Bruce");
            csvOutput.endRecord();

            csvOutput.write("2");
            csvOutput.write("John");
            csvOutput.endRecord();

            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
