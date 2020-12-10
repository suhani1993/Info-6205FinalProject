package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import main.person.PersonDirectory;
import java.util.logging.Logger;

public class Graph extends javax.swing.JPanel {
	
	static Logger logger = Logger.getLogger(Graph.class.getName());
	
	JFreeChart chart;
	JFrame jFrame;
	JPanel jPanel;
	public Graph(JFrame jFrame, JPanel jPanel) {
		this.jFrame = jFrame;
		this.jPanel = jPanel;
	}
	
	/*
	 * Creates graph from datewise infected people
	 */
	public void populateLineGraphDateWiseInfected() {
		FileInputStream in;
		try {
			//read config file and display factors and their values of config file
			in = new FileInputStream("config.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			
			int total = 0;
			for(Date date : PersonDirectory.perDayInfectedPeople.keySet()) {
	        	total += PersonDirectory.perDayInfectedPeople.get(date).size();
	        }
			
			String str = "";
			for(Object key : p.entrySet()) {
				str += key.toString() + ", ";
			}
			str = str.concat(" Total Infected=" );
			str = str.concat(String.valueOf(total));
			//Create Header label from config file factors and display all factor in a label
			JLabel jLabel = new JLabel(str);
			jLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
			jLabel.setPreferredSize(new Dimension(1300, 24));
			jPanel.add(jLabel);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		jPanel.add(new JSeparator(SwingConstants.VERTICAL));
		
		//Create line chart
		chart = ChartFactory.createLineChart("DayWise Number of Infected People", "Day", "Infected People",
				createDataset(), PlotOrientation.VERTICAL, true, false, false);
		chart.setBorderVisible(true);
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ((CategoryPlot)plot).getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
        ChartPanel chartPanel = new ChartPanel(chart);   
        chartPanel.setPreferredSize(new java.awt.Dimension( 700 , 700 ) );
        jPanel.add(chartPanel, BorderLayout.CENTER);
        jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        jFrame.add(jPanel);
    }
	
	/*
	 * x-axis -> from current date to 1 month period days
	 * y-axis -> per day infected people
	 */
    private DefaultCategoryDataset createDataset() {
       
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	Format f = new SimpleDateFormat("dd/MM");
    	int total = 0;
        for(Date date : PersonDirectory.perDayInfectedPeople.keySet()) {
        	System.out.println("date :: " + date + " infected :: " + PersonDirectory.perDayInfectedPeople.get(date).size());
        	total += PersonDirectory.perDayInfectedPeople.get(date).size();
        	dataset.addValue((Integer)Math.round(PersonDirectory.perDayInfectedPeople.get(date).size()), "Infected People Graph", f.format(date));
        }               
        
        logger.info("total Infections:: " + total);
        return dataset; 
   }
    
}

