import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LZ77 {
    private static final int WINDOW_SIZE = 10;
    private static final int LOOKAHEAD_BUFFER_SIZE = 5;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choice;
        String sourceFile, resultFile, firstFile, secondFile;

        loop:
        while (true) {

            choice = sc.next();

            switch (choice) {
                case "comp":
                    System.out.print("source file name: ");
                    sourceFile = sc.next();
                    System.out.print("archive name: ");
                    resultFile = sc.next();
                    comp(sourceFile, resultFile);
                    break;
                case "decomp":
                    System.out.print("archive name: ");
                    sourceFile = sc.next();
                    System.out.print("file name: ");
                    resultFile = sc.next();
                    decomp(sourceFile, resultFile);
                    break;
                case "size":
                    System.out.print("file name: ");
                    sourceFile = sc.next();
                    size(sourceFile);
                    break;
                case "equal":
                    System.out.print("first file name: ");
                    firstFile = sc.next();
                    System.out.print("second file name: ");
                    secondFile = sc.next();
                    System.out.println(equal(firstFile, secondFile));
                    break;
                case "about":
                    about();
                    break;
                case "exit":
                    break loop;
            }
        }

        sc.close();

    }

    public static String readFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    public static void comp(String filename, String result) {
        try {
            String text = readFile(filename);
            ArrayList<Token> compressed = compressText(text);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(result))) {
                oos.writeObject(compressed);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decomp(String filename, String result) {
        try {
            ArrayList<Token> compressed = null;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                compressed = (ArrayList<Token>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String decompressed = decompressText(compressed);

            try (PrintWriter writer = new PrintWriter(result)) {
                writer.println(decompressed);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Token> compressText(String text) {
        ArrayList<Token> compressed = new ArrayList<>();
        int textSize = text.length();
        int currentIndex = 0;

        while (currentIndex < textSize) {
            int matchLength = 0;
            int matchIndex = 0;

            for (int i = Math.max(0, currentIndex - WINDOW_SIZE); i < currentIndex; i++) {
                int len = 0;
                while (currentIndex + len < textSize && text.charAt(i + len) == text.charAt(currentIndex + len) && len < LOOKAHEAD_BUFFER_SIZE) {
                    len++;
                }
                if (len > matchLength) {
                    matchLength = len;
                    matchIndex = i;
                }
            }

            if (matchLength > 0) {
                compressed.add(new Token(currentIndex - matchIndex, matchLength, text.charAt(currentIndex + matchLength)));
                currentIndex += matchLength + 1;
            } else {
                compressed.add(new Token(0, 0, text.charAt(currentIndex)));
                currentIndex++;
            }
        }

        return compressed;
    }

    public static String decompressText(ArrayList<Token> compressed) {
        StringBuilder decompressed = new StringBuilder();

        for (Token token : compressed) {
            if (token.getOffset() == 0 && token.getLength() == 0) {
                decompressed.append(token.getLiteral());
            } else {
                int start = decompressed.length() - token.getOffset();
                for (int i = 0; i < token.getLength(); i++) {
                    decompressed.append(decompressed.charAt(start + i));
                }
                decompressed.append(token.getLiteral());
            }
        }

        return decompressed.toString();
    }

    public static void size(String sourceFile) {
        try {
            FileInputStream f = new FileInputStream(sourceFile);
            System.out.println("size: " + f.available());
            f.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static boolean equal(String firstFile, String secondFile) {
        try {
            FileInputStream f1 = new FileInputStream(firstFile);
            FileInputStream f2 = new FileInputStream(secondFile);
            int k1, k2;
            byte[] buf1 = new byte[1000];
            byte[] buf2 = new byte[1000];
            do {
                k1 = f1.read(buf1);
                k2 = f2.read(buf2);
                if (k1 != k2) {
                    f1.close();
                    f2.close();
                    return false;
                }
                for (int i = 0; i < k1; i++) {
                    if (buf1[i] != buf2[i]) {
                        f1.close();
                        f2.close();
                        return false;
                    }

                }
            } while (!(k1 == -1 && k2 == -1));
            f1.close();
            f2.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static void about() {
        System.out.println("231RDB305 Ņikita Berdnikovs");
        System.out.println("231RDB203 Andrejs Načiss");
    }

}

class Token implements Serializable {
    private int offset;
    private int length;
    private char literal;

    public Token(int offset, int length, char literal) {
        this.offset = offset;
        this.length = length;
        this.literal = literal;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public char getLiteral() {
        return literal;
    }
}
