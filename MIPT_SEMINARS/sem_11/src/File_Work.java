import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class File_Work {
    public static void main(String[] args) throws IOException{
        /*
        File folder = new File("C:\\Users\\George");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File f : listOfFiles) {
                if (f.isFile()) {
                    System.out.println("File " + f.getName());
                }
                if (f.isDirectory()) {
                    System.out.println("Directory " + f.getName());
                }
            }
        }
        else {
            System.out.println("Opening directory problem!");
        }*/

        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream("./src/xanadu.txt");
            out = new FileOutputStream("./src/outagain.txt");
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
