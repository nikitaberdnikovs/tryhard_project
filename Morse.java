import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Morse {
    private static final Map<Character, String> morseCodeMap = new HashMap<>();
    private static final Map<String, Character> reverseMorseCodeMap = new HashMap<>();
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
        morseCodeMap.put(' ', " ");
        morseCodeMap.put(' ', " ");
        morseCodeMap.put('<', "-.--.");
        morseCodeMap.put('>', ".-.-.");
        morseCodeMap.put('/', "-..-.");
        morseCodeMap.put('\n', ".-.-");
        morseCodeMap.put('\t', "-...-");
    }
    static {
        for (Map.Entry<Character, String> entry : morseCodeMap.entrySet()) {
            reverseMorseCodeMap.put(entry.getValue(), entry.getKey());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		String choiseStr;
		String sourceFile, resultFile, firstFile, secondFile;
		
		loop: while (true) {
			
			choiseStr = sc.next();
								
			switch (choiseStr) {
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

    public static void comp(String filename, String result) {
        try {
            String text = extractTextFromHtml(filename);
            StringBuilder sb = new StringBuilder();
    
            for (char c : text.toCharArray()) {
                String morseCode = morseCodeMap.get(c);
                if (morseCode != null) {
                    sb.append(morseCode).append(" ");
                } else {
                    sb.append(c).append(" ");
                }
            }

            try (PrintWriter writer = new PrintWriter(result)) {
                writer.println(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decomp(String filename, String result) {
        try {
            String compressedText = extractTextFromHtml(filename);
            StringBuilder sb = new StringBuilder();
    
            String[] codes = compressedText.trim().split(" {2,}");
            
            for (String code : codes) {
                String[] characters = code.trim().split(" ");
                
                for (String character : characters) {
                    if (character.equals("")) {
                        sb.append(' ');
                    } else {
                        Character decodedCharacter = reverseMorseCodeMap.get(character);
                        if (decodedCharacter != null) {
                            sb.append(decodedCharacter);
                        } else {
                            sb.append(character);
                        }
                    }
                }
                
                sb.append(' ');
            }

            try (PrintWriter writer = new PrintWriter(result)) {
                writer.println(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void size(String sourceFile) {
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		}
		catch (IOException ex) {
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
				for (int i=0; i<k1; i++) {
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
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public static void about() {
		System.out.println("231RDB305 Ņikita Berdnikovs");
		System.out.println("231RDB203 Andrejs Načiss");
	}
    
}