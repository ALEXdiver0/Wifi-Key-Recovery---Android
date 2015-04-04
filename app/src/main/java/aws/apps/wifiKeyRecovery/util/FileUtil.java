package aws.apps.wifiKeyRecovery.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by alex on 04/04/15.
 */
public class FileUtil {
    private final String TAG = this.getClass().getName();
    private Context mContext;

    public FileUtil(final Context cntx) {
        mContext = cntx.getApplicationContext();
    }

    public String readAssetsFileAsText(final String fileName) {
        return readAssetsFileAsText(fileName, "UTF-8");
    }

    public String readAssetsFileAsText(final String fileName, final String encoding) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            final InputStream is = mContext.getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is, encoding));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }


    public boolean saveToFile(final String fileName, final File directory, final String contents) {
        Log.d(TAG, "^ Saving file.");

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            try {

                if (directory.canWrite()) {
                    File gpxfile = new File(directory, fileName);
                    FileWriter gpxwriter = new FileWriter(gpxfile);
                    BufferedWriter out = new BufferedWriter(gpxwriter);
                    out.write(contents);
                    out.close();
                    Log.d(TAG, "^ Saved to SD as '" + directory.getAbsolutePath() + "/" + fileName + "'");
                    //showToast("Saved to SD as '" + directory.getAbsolutePath() + "/" + fileName + "'");
                    return true;
                }

            } catch (Exception e) {
                //showToast("Could not write file:\n+ e.getMessage()", Toast.LENGTH_SHORT);
                Log.e(TAG, "^ Could not write file " + e.getMessage());
            }

        } else {
            //showToast("No SD card is mounted...", Toast.LENGTH_SHORT);
            Log.e(TAG, "^ No SD card is mounted.");
        }
        return false;
    }


}
