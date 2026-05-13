import java.util.Base64;

public class StringEncoderTool {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java StringEncoderTool <string>");
            return;
        }

        String input = args[0];
        char key = 'K'; // XOR key (kannst du ändern)

        // XOR encode
        char[] xored = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            xored[i] = (char)(input.charAt(i) ^ key);
        }

        String xoredStr = new String(xored);

        // Base64 encode
        String encoded = Base64.getEncoder().encodeToString(xoredStr.getBytes());

        System.out.println("Encoded string:");
        System.out.println(encoded);
    }
}