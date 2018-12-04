//OWEN HOPE cs610 5191 prp
import java.io.RandomAccessFile;


public class huffFileRead5191 {
    byte buffer;
    int bufferLen;
    int bitTotal;
    int readBits;
    int bitsLeft;
    RandomAccessFile file;

    public huffFileRead5191(String fn)  {
        buffer = (byte) 0;
        bufferLen = 0;
        bitTotal = 0;
        readBits = 0;
        bitsLeft = 0;
        try {
            file = new RandomAccessFile(fn, "r");
            bitTotal = file.readInt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean EoF() {
        return readBits == bitTotal;
    }

    public char charIn() {
        int charBuff = 0;
        int reversCharBuff = 0;

        for (int i = 0; i < 8; i++) {
            charBuff = charBuff << 1;
            if (bitIn()) {
                charBuff++;
            }
        }

        for (int i = 0; i < 8; i++) {
            reversCharBuff = reversCharBuff << 1;
            reversCharBuff += charBuff % 2;
            charBuff = charBuff >> 1;
        }

        return (char) reversCharBuff;
    }

    public boolean bitIn() {
        try {
            if (bitsLeft == 0) {
                buffer = file.readByte();
                bitsLeft = 8;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        readBits++;
        return (((buffer >> --bitsLeft) & 0x01) > 0);
    }

    public void close() {
        try {
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
