package main.map;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import main.spread.RegionWiseSpread;
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
public class DistanceMap {
	
	private static final int MIN_ZOOM = 0;
    private static final int MAX_ZOOM = 21;
    //private static final String setMarkerScript = "var locations = [\n  ['Bondi Beach', -33.890542, 151.274856, 4],\n  ['Coogee Beach', -33.923036, 151.259052, 5],\n  ['Cronulla Beach', -34.028249, 151.157507, 3],\n  ['Manly Beach', -33.80010128657071, 151.28747820854187, 2],\n  ['Maroubra Beach', -33.950198, 151.259302, 1]\n];\n\nvar marker, i;\n\nfor (i = 0; i < locations.length; i++) {  \n  marker = new google.maps.Marker({\n\tposition: new google.maps.LatLng(locations[i][1], locations[i][2]),\n\tmap: map,\n\ttitle: locations[i][0]\n  });\n}";
    
	public static void setConfig(LocationPoint locationPoint) {
		JFrame frame = new JFrame();
		setNameOfComponent(frame, "Set Configurations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(800, 500);
		boolean isVisible = checkJFrameVisible(frame);
        if(!isVisible) {
        	frame.setVisible(true);
        }
		JPanel mainPanel = new JPanel();
		
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		
		JLabel label = new JLabel();
		setNameOfComponent(label, locationPoint.getName());
		mainPanel.add(label);
		label.setFont(font1);
		
		FileInputStream in;
		try {
			in = readConfigFile("config.properties");
//			in = new FileInputStream("config.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			
			mainPanel.add(new JSeparator(JSeparator.VERTICAL),
			          BorderLayout.LINE_START);
			
			for(Object key : p.keySet()) {
				
				mainPanel.add(new JSeparator(SwingConstants.VERTICAL));
				label = new JLabel((String) key + ": ");
				mainPanel.add(label);
				
				JTextField txtname = new JTextField();
				txtname.setPreferredSize(new Dimension(200, 24));
				mainPanel.add(txtname);
			}
			
			JButton submitButton = new JButton("Submit");
			setNameOfComponent(submitButton, "Submit");
			submitButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						int count = 0;
						for (Component component : mainPanel.getComponents()) {
				            if (component instanceof JTextField) {
				                p.setProperty((String) p.stringPropertyNames().toArray()[count++], ((JTextField) component).getText());
				            }
				        }
						FileOutputStream out = new FileOutputStream("config.properties");
						p.store(out, null);
						
						out.close();
						frame.dispose();
						
						RegionWiseSpread regionWiseSpread = new RegionWiseSpread(); 
						regionWiseSpread.setDataInLocationWiseSpread(regionWiseSpread, locationPoint);
						regionWiseSpread.generateRandomData(regionWiseSpread);
						
			        } catch (Exception e) {
			        	e.printStackTrace();
			        }
				}
			});
			mainPanel.add(submitButton);
		}catch(Exception e) {
			e.printStackTrace();
		}
		frame.add(mainPanel);
	}

    public static FileInputStream readConfigFile(String fileName) {
    	try {
			FileInputStream in = new FileInputStream(fileName);
			if(in.read() != -1) {
				return in;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}

	public static Component setNameOfComponent(Component frame, String name) {
		if(frame instanceof JFrame) {
			JFrame jframe = (JFrame)frame;
			jframe.setName(name);
			return frame;
		}else if(frame instanceof JLabel) {
			JLabel jLabel = (JLabel)frame;
			jLabel.setName(name);
			return jLabel;
		}else if(frame instanceof JButton) {
			JButton button = (JButton)frame;
			button.setName(name);
			return button;
		}
		return null;
	}

	/**
     * In map.html file default zoom value is set to 5.
     */
    private static int zoomValue = 5;

	public boolean selectLocation(LocationPoint locationPoint) {
		

        EngineOptions options =
                EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("1BNDHFSC1FXFYTBI5RRY4Q0WMRCK7VN3M54CRUXPUP8T6GQ686N0K2TKA6E97VUU1GVVY6").build();
        Engine engine = Engine.newInstance(options);
        Browser browser = engine.newBrowser();
        
        String a = "var marker, i;\n\nfor (i = 0; i < locations.length; i++) {  \n  marker = new google.maps.Marker({\n\tposition: new google.maps.LatLng(locations[i][1], locations[i][2]),\n\tmap: map,\n\tlabel: locations[i][0], \n\toptimized: true\n });\n}";
	     String b = "var locations = [\n";
	     String c = "];\n";
	     String d = "['Bondi Beach', -33.890542, 151.274856, 4],\n['Coogee Beach', -33.923036, 151.259052, 5]\n";
	     String setMarkerScript = b + c + a;
        
        
        SwingUtilities.invokeLater(() -> {
            BrowserView view = BrowserView.newInstance(browser);

            JButton zoomInButton = new JButton("Zoom In");
            zoomInButton.addActionListener(e -> {
                if (zoomValue < MAX_ZOOM) {
                    browser.mainFrame().ifPresent(frame ->
                            frame.executeJavaScript("map.setZoom(" +
                                    ++zoomValue + ")"));
                }
            });

            JButton zoomOutButton = new JButton("Zoom Out");
            zoomOutButton.addActionListener(e -> {
                if (zoomValue > MIN_ZOOM) {
                    browser.mainFrame().ifPresent(frame ->
                            frame.executeJavaScript("map.setZoom(" +
                                    --zoomValue + ")"));
                }
            });
            /*browser.mainFrame().ifPresent(frame ->
                            frame.executeJavaScript(setMarkerScript));*/
            JButton setMarkerButton = new JButton("Show Markers");
            setMarkerButton.addActionListener(e ->
                    browser.mainFrame().ifPresent(frame ->
                            frame.executeJavaScript(setMarkerScript)));
            
            JFrame frame = new JFrame("Google Maps");
            JPanel toolBar = new JPanel();
       
            JButton locationButton = new JButton("Set Location");
            locationButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {

			            if (browser.url()!= null) {
			                String[] m = browser.url().split("!3d", 0);
			                String[] arr = m[0].split("place");
			                if(arr != null && arr[1] != null) {
			                	String[] locationArr = arr[1].split("/");
			                	if(locationArr[1] != null) {
			                		String str = locationArr[1];
			                		str = str.replaceAll("[+%&]", " ");
			                		locationPoint.setName(str);
			                	}
			                }
			                String[] n = m[1].split("!4d");
			                System.out.println("Lat" + n[0] + "  " + "Lon" + n[1]);
			                double lat = Double.parseDouble(n[0]);
			                double lon = Double.parseDouble(n[1]);
			                locationPoint.setX((int)lat);
			                locationPoint.setY((int)lon);
			            }
			            toolBar.setVisible(false);
			            frame.dispose();
			            
			            setConfig(locationPoint);
			            
			        } catch (Exception e) {
			        	e.printStackTrace();
			        }
				}
				
				
			});
	        toolBar.add(locationButton);
	        
	        frame.add(toolBar, BorderLayout.SOUTH);
	        frame.add(view, BorderLayout.CENTER);
	        frame.setSize(800, 500);
	        boolean isVisible = checkJFrameVisible(frame);
	        if(!isVisible) {
	        	frame.setVisible(true);
	        }
	        
	        
	        browser.navigation().loadUrl("https://www.google.com/maps");
        });
        return true;
	}

	public static boolean checkJFrameVisible(JFrame frame) {
		if(frame.isVisible()) {
			return true;
		}else {
			return false;
		}
	}
}