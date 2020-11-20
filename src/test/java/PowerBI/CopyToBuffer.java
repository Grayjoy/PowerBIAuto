package PowerBI;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;

public class CopyToBuffer {
    public void CallClipboard(String file) {
        System.out.println("Copying text from system clipboard.");
        String grabbed = ReadClipboard(file);
        System.out.println(grabbed);

    }

    public String ReadClipboard(String file) {
        File testFile = new File(file);

        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable clipboardContents = systemClipboard.getContents(null);

        if (clipboardContents.equals(null)) {

            return ("Clipboard is empty!!!");
        } else

            try {


                if (clipboardContents.isDataFlavorSupported(DataFlavor.stringFlavor)) {

                    String returnText = (String) clipboardContents.getTransferData(DataFlavor.stringFlavor);

                    try {
                        setContents(testFile, returnText);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return returnText;


                }
            } catch (UnsupportedFlavorException ufe) {
                ufe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        return null;
    }


    static public void setContents(File aFile, String aContents) throws FileNotFoundException, IOException {
        if (aFile == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        if (!aFile.exists()) {
            throw new FileNotFoundException("File does not exist: " + aFile);
        }
        if (!aFile.isFile()) {
            throw new IllegalArgumentException("Should not be a directory: " + aFile);
        }
        if (!aFile.canWrite()) {
            throw new IllegalArgumentException("File cannot be written: " + aFile);
        }

        Writer output = new BufferedWriter(new FileWriter(aFile));
        try {

            output.write(aContents);
        } finally {
            output.close();
        }
    }
}
