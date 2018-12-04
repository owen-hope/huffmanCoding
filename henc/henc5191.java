//OWEN HOPE cs610 5191 prp
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.PriorityQueue;


public class henc5191 {
    private static int asciiSize = 256;
    static class huffmanNode implements Comparable<huffmanNode> {
        char c;
        private int freq;

        huffmanNode left;
        huffmanNode right;

        huffmanNode(char c, int freq, huffmanNode left, huffmanNode right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean leaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return ((left == null) && (right == null));
        }

        public int compareTo(huffmanNode hn) {
            return this.freq - hn.freq;
        }
    }

    //Build the Huff Tree
    public static huffmanNode tree(int[] freq) {
        minHeap5191<huffmanNode> mh = new minHeap5191<huffmanNode>();
        for (char i = 0; i < asciiSize; i++) {
            if (freq[i] > 0) {
                mh.insert(new huffmanNode(i, freq[i], null, null));
            }
        }

        //TODO HANDLE only one char with frequency

        //merge trees
        while (mh.size() > 1) {
            huffmanNode left = mh.removeMin();
            huffmanNode right = mh.removeMin();
            huffmanNode parent = new huffmanNode('\0', left.freq + right.freq, left, right);
            mh.insert(parent);
        }
        return mh.removeMin();
    }

    public static void treeOut(huffmanNode x) {
        if (x.leaf()) {
            System.out.println(x.c);
        }
        treeOut(x.left);
        treeOut(x.right);
    }

    public static void huffCode(String[] str, huffmanNode no, String s){
        if (!no.leaf()) {
            huffCode(str, no.left, s + '0');
            huffCode(str, no.right, s + '1');
        }
        else {
            //System.out.println(s);
            str[no.c] = s;
        }
    }

    public static void preorder(huffmanNode no, fileWrite5191 file) {
        if (no.leaf()) {
            //System.out.print("0" + " ");
            //System.out.print(no.c + " ");
            file.bitToFile(false);
            file.charToB(no.c);
            return;
        }
        //System.out.print("1" + " ");
        file.bitToFile(true);
        preorder(no.left, file);
        preorder(no.right, file);
    }

    public static void main(String[] args) throws IOException {
        int asciiSize = 257;

        
        int[] frequency = new int[asciiSize];
        String fileFromArg = args[0];

        //This was for reading in text files only
        /*
        String temp = "";
        while ((line = reader.readLine()) != null) {
            temp += line;
        }


        char[] fi = temp.toCharArray();
        for (char i = 0; i < fi.length; i++) {
            System.out.println(fi[i]);
            frequency[fi[i]]++;
        }

        System.out.println(Arrays.toString(frequency));
        */




        //"C:\\Users\\Owen\\Documents\\School Semesters\\Fall Semester 2018\\CS610\\huffman\\OwenHopeResumeFall18-v2SE.pdf";
        //String fileToByteFile = "testText.txt";

        try {
            byte[] bfile = Files.readAllBytes((Paths.get(fileFromArg)));


            for (byte by : bfile) {
                frequency[by&0xff]++;
            }


            huffmanNode root = tree(frequency);
            String[] st = new String[asciiSize];
            huffCode(st, root, "");
            //treeOut(root);


            /*
            for (int  i = 0; i < bfile.length; i++) {
                String letCode = st[bfile[i]&0xff];
                for (int j = 0; j < letCode.length(); j++) {
                    if (letCode.charAt(j) == '0') {
                        System.out.println("THIS IS A 0");
                    } else if (letCode.charAt(j) == '1') {
                        System.out.println("THIS IS A 1");
                    }
                }
            }
            */
            
            String fileEncoded = fileFromArg + ".huf";
            fileWrite5191 bFile = new fileWrite5191(fileEncoded);
            //bFile.bitToFile(false);
            //bFile.charToB('\u0000');
            preorder(root, bFile);
            for (int  i = 0; i < bfile.length; i++) {
                String letCode = st[bfile[i]&0xff];
                for (int j = 0; j < letCode.length(); j++) {
                    if (letCode.charAt(j) == '0') {
                        bFile.bitToFile(false);
                    } else if (letCode.charAt(j) == '1') {
                        bFile.bitToFile(true);
                    }
                }

            }

            bFile.closeFile();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
