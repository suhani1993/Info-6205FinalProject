package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import main.person.PersonDirectory;

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
        chartPanel.setPreferredSize(new java.awt.Dimension( 600 , 600 ) );
        jPanel.add(chartPanel, BorderLayout.CENTER);
        jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        jFrame.add(jPanel);
    }
	
	/*
	 * x-axis -> from current date to 1 month period days
	 * y-axis -> per day infected people
	 */
	public void timeSeriesChart() {
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
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
	            "DateWise Number of Infected People",  // title
	            "Date",             // x-axis label
	            "Infected People",   // y-axis label
	            createTimeSeriesSet(),            // data
	            true,               // create legend?
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
		
		XYPlot xyPlot = (XYPlot)chart.getPlot();
		xyPlot.setBackgroundPaint(Color.lightGray);
		xyPlot.setDomainGridlinePaint(Color.white);
		xyPlot.setRangeGridlinePaint(Color.white);
		xyPlot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		xyPlot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        ChartPanel chartPanel = new ChartPanel(chart);   
        chartPanel.setPreferredSize(new java.awt.Dimension( 600 , 600 ) );
        jPanel.add(chartPanel, BorderLayout.CENTER);
        jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        jFrame.add(jPanel);
	}
	
	private XYDataset createTimeSeriesSet() {
		TimeSeries series1 = new TimeSeries("");
		for(Date date : PersonDirectory.perDayInfectedPeople.keySet()) {
			series1.add(new Day(date),(Integer)Math.round(PersonDirectory.perDayInfectedPeople.get(date).size()));
		}
	    TimeSeriesCollection dataset = new TimeSeriesCollection();
	    dataset.addSeries(series1);
	    
	    return dataset;
	}

	/*
	 * x-axis -> from current date to 1 month period days
	 * y-axis -> per day infected people
	 */
    private DefaultCategoryDataset createDataset() {
       
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	Format f = new SimpleDateFormat("dd/MM");
//    	DateTickUnit unit = new DateTickUnit( null, 7, new SimpleDateFormat("MM/dd/yyyy"));
    	int total = 0;
        for(Date date : PersonDirectory.perDayInfectedPeople.keySet()) {
        	System.out.println("date :: " + date + " infected :: " + PersonDirectory.perDayInfectedPeople.get(date).size());
        	total += PersonDirectory.perDayInfectedPeople.get(date).size();
        	dataset.addValue((Integer)Math.round(PersonDirectory.perDayInfectedPeople.get(date).size()), "", new Day(date));
        }               
        
        logger.info("total Infections:: " + total);
        return dataset; 
   }
    
}

