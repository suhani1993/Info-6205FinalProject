package Map;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The simplest application with the integrated browser component.
 *
 * <p>This example demonstrates:
 *
 * <ol>
 *     <li>Creating an instance of {@link Engine}.
 *     <li>Creating an instance of {@link Browser}.
 *     <li>Embedding the browser into Swing via {@link BrowserView}.
 *     <li>Loading the "https://html5test.com" web site.
 * </ol>
 */
public final class DistanceMap {

    public static void main(String[] args) {
        // Creating and running Chromium engine
        Engine engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED).
                licenseKey("1BNDHFSC1FXFYTBI5RRY4Q0WMRCK7VN3M54CRUXPUP8T6GQ686N0K2TKA6E97VUU1GVVY6").build());

        Browser browser = engine.newBrowser();
        // Loading the required web page
        browser.navigation().loadUrl("https://html5test.com");

        SwingUtilities.invokeLater(() -> {
            // Creating Swing component for rendering web content
            // loaded in the given Browser instance
            BrowserView view = BrowserView.newInstance(browser);

            // Creating and displaying Swing app frame
            JFrame frame = new JFrame("JxBrowser AWT/Swing");
            // Closing the engine when app frame is about to close
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.add(view, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}