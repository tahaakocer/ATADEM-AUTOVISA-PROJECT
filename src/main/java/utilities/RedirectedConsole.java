package utilities;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class RedirectedConsole {
    private JTextArea textArea;

    public RedirectedConsole(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void redirectSystemOutAndErr() {
        TextAreaOutputStream outputStream = new TextAreaOutputStream(textArea);
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
    }
}

class TextAreaOutputStream extends OutputStream {
    private JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        SwingUtilities.invokeLater(() -> textArea.append(String.valueOf((char) b)));
    }
}