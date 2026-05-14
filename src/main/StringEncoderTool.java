import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class StringEncoderTool {

    private static JTextField inputField;
    private static JTextField keyField;
    private static JTextField outputField;
    private static JButton generateButton;
    private static JButton copyButton;

    public static void main(String[] args) {
        if (args.length >= 2) {
            runCli(args);
        } else {
            SwingUtilities.invokeLater(StringEncoderTool::createAndShowGui);
        }
    }

    private static void runCli(String[] args) {
        String input = args[0];
        char key = parseXorKey(args[1]);
        String encoded = encodeString(input, key);

        System.out.println("Encoded string:");
        System.out.println(encoded);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Simple Encoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(createInputPanel(), BorderLayout.NORTH);
        frame.add(createOutputPanel(), BorderLayout.CENTER);
        frame.add(createButtonPanel(), BorderLayout.SOUTH);
        frame.getRootPane().setDefaultButton(generateButton);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Input"));

        JPanel labels = new JPanel(new BorderLayout(5, 5));
        labels.add(new JLabel("String:"), BorderLayout.NORTH);
        labels.add(new JLabel("XOR key:"), BorderLayout.SOUTH);

        JPanel fields = new JPanel(new BorderLayout(5, 5));
        inputField = new JTextField(30);
        keyField = new JTextField(8);
        fields.add(inputField, BorderLayout.NORTH);
        fields.add(keyField, BorderLayout.SOUTH);

        panel.add(labels, BorderLayout.WEST);
        panel.add(fields, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Generated"));

        outputField = new JTextField(30);
        outputField.setEditable(false);

        panel.add(outputField, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        generateButton = new JButton("Generate");
        copyButton = new JButton("Copy Generated String");
        copyButton.setEnabled(false);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGenerate();
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCopy();
            }
        });

        JPanel buttons = new JPanel();
        buttons.add(generateButton);
        buttons.add(copyButton);
        panel.add(buttons, BorderLayout.CENTER);
        return panel;
    }

    private static void onGenerate() {
        String input = inputField.getText();
        if (input == null || input.isEmpty()) {
            showError("Please enter a string to encode.");
            return;
        }

        String xorKeyText = keyField.getText();
        if (xorKeyText == null || xorKeyText.isEmpty()) {
            showError("Please enter an XOR key.");
            return;
        }

        char key = parseXorKey(xorKeyText);
        String encoded = encodeString(input, key);
        outputField.setText(encoded);
        copyButton.setEnabled(true);
    }

    private static void onCopy() {
        String text = outputField.getText();
        if (text == null || text.isEmpty()) {
            showError("There is no encoded string to copy.");
            return;
        }

        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        JOptionPane.showMessageDialog(null, "Generated string copied to clipboard.", "Copied", JOptionPane.INFORMATION_MESSAGE);
    }

    private static char parseXorKey(String keyText) {
        try {
            return (char) Integer.parseInt(keyText);
        } catch (NumberFormatException e) {
            return keyText.charAt(0);
        }
    }

    private static String encodeString(String input, char key) {
        char[] xored = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            xored[i] = (char) (input.charAt(i) ^ key);
        }

        String xoredStr = new String(xored);
        return Base64.getEncoder().encodeToString(xoredStr.getBytes());
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}