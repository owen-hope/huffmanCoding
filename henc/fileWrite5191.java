//OWEN HOPE cs610 5191 prp
import java.io.RandomAccessFile;


public class fileWrite5191 {
    byte buffer;
    int bufferLen;
    int bitTotal;
    int readBits;
    RandomAccessFile file;
    public fileWrite5191(String fn)  {
        buffer = (byte) 0;
        bufferLen = 0;
        bitTotal = 0;
        readBits = 0;
        try {
            file = new RandomAccessFile(fn, "rw");
            file.writeInt(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void charToB(char c) {
        int charBuffer = (int) c;
        for (int i = 0; i < 8; i++) {
            bitToFile(charBuffer % 2 > 0);
            charBuffer = charBuffer >> 1;
        }
    }

    public void bitToFile(boolean bit) {
        byte madeOfBits;
        bitTotal++;
        if (bit) {
            madeOfBits = 1;
        }else {
            madeOfBits = 0;
        }

        buffer |= (madeOfBits << (7 - bufferLen++));

        try {
            if (bufferLen == 8) {
                file.writeByte(buffer);
                bufferLen = 0;
                buffer = 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeFile() {
        try {
            if (bufferLen != 0) {
                while (bufferLen < 8) {
                    buffer |= (0 << (7 - bufferLen++));
                }
                file.writeByte(buffer);
            }
            file.seek(0);
            file.writeInt(bitTotal);
            file.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
