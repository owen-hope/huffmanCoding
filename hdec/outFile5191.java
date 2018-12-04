//OWEN HOPE cs610 5191 prp
import java.io.RandomAccessFile;


public class outFile5191 {
    private boolean inputFile;
    private RandomAccessFile file;
    private long spotInFile;
    private char buffer;

    public outFile5191(String fn) {
        try {
            file = new RandomAccessFile(fn, "rw");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        spotInFile = 0;
    }

    public void charOut(char c) {
        try {
            file.write((byte) c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void close(){
        try {
            file.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
