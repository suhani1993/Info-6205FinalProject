package main;

import java.awt.BorderLayout;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import main.person.PersonDirectory;


public class Graph extends javax.swing.JPanel {

	JFreeChart chart;
	JFrame jFrame;
	JPanel jPanel;
	public Graph(JFrame jFrame, JPanel jPanel) {
		this.jFrame = jFrame;
		this.jPanel = jPanel;
	}
	
	public void populateBarGraph() {
		
		chart = ChartFactory.createLineChart("DayWise Number of Infected People", "Day", "Infected People",
				createDataset(), PlotOrientation.VERTICAL, true, false, false);
      
//        barChart = ChartFactory.createPieChart(
//         "Number of Infected People Per day",                     
//         createDataset(),          
//         true, true, false);
         
        ChartPanel chartPanel = new ChartPanel(chart);   
        chartPanel.setPreferredSize(new java.awt.Dimension( 800 , 800 ) );
        jPanel.add(chartPanel, BorderLayout.CENTER);
        jFrame.add(jPanel);
//        jPanel1.validate();
        /*chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
        setContentPane( chartPanel );*/ 
        
    }
    
    private DefaultCategoryDataset createDataset() {
       
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//    	Calendar calendar = Calendar.getInstance();
    	
    	Format f = new SimpleDateFormat("dd");
    	System.out.println();
        for(Date date : PersonDirectory.perDayInfectedPeople.keySet()) {
//        	calendar.setTime(date);
        	System.out.println("Date :: " + date + " no of infected :: " + PersonDirectory.perDayInfectedPeople.get(date).size());
        	dataset.addValue(PersonDirectory.perDayInfectedPeople.get(date).size(), "Infected People", f.format(date));
//            dataset.setValue(date, PersonDirectory.perDayInfectedPeople.get(date).size());
        }               

        return dataset; 
   }
}
