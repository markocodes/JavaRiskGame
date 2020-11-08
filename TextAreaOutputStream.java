import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * this class helps display output stream on our text area
 * @author Mmedara Josiah
 * @version 1.0.0
 */
public class TextAreaOutputStream extends OutputStream {

    private JTextArea textArea;
    private StringBuilder stringBuilder;

    /**
     * constructs the text are output stream
     * @param textArea is our the area to be used
     */
    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
        stringBuilder = new StringBuilder();
    }

    /**
     * writes a specified byte to the output stream
     * @param b is the specified byte
     */
    public void write(int b) {
        if (b == '\n') {
            final String text = stringBuilder.toString();
            SwingUtilities.invokeLater(new Runnable() {
                public void run()
                {
                    textArea.append(text);
                }
            });
            stringBuilder.setLength(0);
        }
        stringBuilder.append((char) b);
    }
}
