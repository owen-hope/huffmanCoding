//OWEN HOPE cs610 5191 prp
public class hdec5191 {
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

    public static void preorder(huffmanNode no) {
        if (no.leaf()) {
            System.out.print(no.c);
            return;
        }

        preorder(no.left);
        preorder(no.right);

    }

    //Build the Huff Tree
    public static huffmanNode tree(huffFileRead5191 file) {
        minHeap5191<huffmanNode> mh = new minHeap5191<huffmanNode>();
        while (!file.EoF()) {
            if (!file.bitIn()) {
                char c = file.charIn();
                System.out.println("This is char Decoded: " + c);
                if (c == '\u0000') {
                    break;
                } else {
                    mh.insert(new huffmanNode(c, 0, null, null));
                }
            } //else
                //mh.insert(new huffmanNode('\0', 0, null, null));
        }


        //TODO HANDLE only one char with frequency

        //merge trees
        while (mh.size() > 1) {
            huffmanNode left = mh.removeMin();
            huffmanNode right = mh.removeMin();
            huffmanNode parent = new huffmanNode('\0', 0, left, right);
            mh.insert(parent);
        }
        return mh.removeMin();
    }

    public static huffmanNode buildTree(huffFileRead5191 file) {
        if (!file.bitIn()) {
            return new huffmanNode(file.charIn(), 0, null, null);
        } else {
            return new huffmanNode('\0', 0, buildTree(file), buildTree(file));
        }
    }

    public static void toFile(huffmanNode node, huffFileRead5191 file) {

            if (node.leaf()) {
                System.out.println(node.c);
                return;
            } else if (!file.bitIn()) {
                toFile(node.left, file);
            } else {
                toFile(node.right, file);
            }


    }

    public static void main(String[] args) {
        String fileDecode = args[0];
        huffFileRead5191 file = new huffFileRead5191(fileDecode);

        huffmanNode root = buildTree(file);


        //preorder(root);

        String normFile = fileDecode.substring(0, fileDecode.length() - 4);
        outFile5191 ofile = new outFile5191(normFile);

        while (!file.EoF()){
            huffmanNode temp = root;
            while (!temp.leaf()) {
                if (!file.bitIn()) {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
            }
            //System.out.println(temp.c);
            ofile.charOut(temp.c);
        }
        ofile.close();
        file.close();
    }
}
