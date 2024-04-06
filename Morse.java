import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<Character, String> morseCodeMap = new HashMap<>();
    static {
        morseCodeMap.put('A', "+.-");
        morseCodeMap.put('B', "+-...");
        morseCodeMap.put('C', "+-.-.");
        morseCodeMap.put('D', "+-..");
        morseCodeMap.put('E', "+.");
        morseCodeMap.put('F', "+..-.");
        morseCodeMap.put('G', "+--.");
        morseCodeMap.put('H', "+....");
        morseCodeMap.put('I', "+..");
        morseCodeMap.put('J', "+.---");
        morseCodeMap.put('K', "+-.-");
        morseCodeMap.put('L', "+.-..");
        morseCodeMap.put('M', "+--");
        morseCodeMap.put('N', "+-.");
        morseCodeMap.put('O', "+---");
        morseCodeMap.put('P', "+.--.");
        morseCodeMap.put('Q', "+--.-");
        morseCodeMap.put('R', "+.-.");
        morseCodeMap.put('S', "+...");
        morseCodeMap.put('T', "+-");
        morseCodeMap.put('U', "+..-");
        morseCodeMap.put('V', "+...-");
        morseCodeMap.put('W', "+.--");
        morseCodeMap.put('X', "+-..-");
        morseCodeMap.put('Y', "+-.--");
        morseCodeMap.put('Z', "+--..");
        morseCodeMap.put('a', ".-");
        morseCodeMap.put('b', "-...");
        morseCodeMap.put('c', "-.-.");
        morseCodeMap.put('d', "-..");
        morseCodeMap.put('e', ".");
        morseCodeMap.put('f', "..-.");
        morseCodeMap.put('g', "--.");
        morseCodeMap.put('h', "....");
        morseCodeMap.put('i', "..");
        morseCodeMap.put('j', ".---");
        morseCodeMap.put('k', "-.-");
        morseCodeMap.put('l', ".-..");
        morseCodeMap.put('m', "--");
        morseCodeMap.put('n', "-.");
        morseCodeMap.put('o', "---");
        morseCodeMap.put('p', ".--.");
        morseCodeMap.put('q', "--.-");
        morseCodeMap.put('r', ".-.");
        morseCodeMap.put('s', "...");
        morseCodeMap.put('t', "-");
        morseCodeMap.put('u', "..-");
        morseCodeMap.put('v', "...-");
        morseCodeMap.put('w', ".--");
        morseCodeMap.put('x', "-..-");
        morseCodeMap.put('y', "-.--");
        morseCodeMap.put('z', "--..");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put(',', "--..--");
        morseCodeMap.put('!', "-.-.--");
        morseCodeMap.put('-', "-....-");
    }

    public static void main(String[] args) {
        String htmlFile = "C:\\Users\\andre\\Downloads\\piemeri\\6.html";
        try {
            String text = extractTextFromHtml(htmlFile);
            String compressedText = compress(text);
            System.out.println(compressedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractTextFromHtml(String htmlFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(htmlFile))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    public static String compress(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            String morseCode = morseCodeMap.get(c);
            if (morseCode != null) {
                sb.append(morseCode).append(" ");
            }
        }
        return sb.toString();
    }
}