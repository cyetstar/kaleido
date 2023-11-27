package cc.onelooker.kaleido.support;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.*;

/**
 * @Author cyetstar
 * @Date 2023-06-08 17:45:00
 * @Description TODO
 */
public class LogFileAlterationListener extends FileAlterationListenerAdaptor {

    private StringBuffer contentStringBuffer = new StringBuffer();

    @Override
    public void onFileChange(File file) {
        try {
            FileReader in = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(in);
            String s = null;
            while ((s = reader.readLine()) != null) {
                contentStringBuffer.append(s);
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onFileChange(file);
    }

    public String takeFileContent(int start) {
        return contentStringBuffer.substring(start);
    }
}
