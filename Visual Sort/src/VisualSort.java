import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class VisualSort extends JPanel implements ActionListener,Runnable {
	
	public static double[] data;
	public static int sortIndex;
	static ArrayList<Rectangle2D.Double> dataBars;
	Rectangle2D.Double workingData;
	public boolean complete = false;

	public VisualSort() {
		JFrame frame = new JFrame("Visualized data sorting");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,850);
		frame.add(this);

		workingData=null;
		dataBars = new ArrayList<Rectangle2D.Double>();
		data = new double[400];
		for(int x  = 0; x < data.length; x++){
			data[x]=Math.random();
		}
		sortIndex=0;
		
		repaint();
		frame.revalidate();
		Thread go = new Thread(this);
		go.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if(complete){
			g2d.setColor(Color.green);
		}
		else{
			g2d.setColor(Color.white);
		}
		g2d.fill(new Rectangle2D.Double(0,0,800,800));
		


		for(int x = 0; x < dataBars.size(); x++){
			if(sortIndex==x){
				g2d.setColor(Color.red);
			}
			else{
				g2d.setColor(Color.black);
			}
			g2d.fill(dataBars.get(x));
		}


		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}
	
	@Override
	public void run() {
		//paint original data
		while(sortIndex<data.length){
			dataBars.add(new Rectangle2D.Double(sortIndex*(800.0/data.length), 0, 800.0/data.length, 800.0*data[sortIndex]));
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sortIndex++;
		}
		sortIndex=0;

		//every data point for sortData should correspond to dataBars throughout the sort
		shakerSort();

	}
	
	public void bubbleSort(){
		ArrayList<java.lang.Double> sortData = new ArrayList<java.lang.Double>();
		for(int x = 0; x < data.length; x++){
			sortData.add(new java.lang.Double(data[x]));
		}
		complete = false;
		boolean swapped = true;
		int sorted = sortData.size()-1;
		while(swapped){
			swapped=false;
			for(sortIndex=0;sortIndex < sorted; sortIndex++){
				if(sortData.get(sortIndex).doubleValue()>sortData.get(sortIndex+1).doubleValue()){
					swapped=true;
					//data sorts
					java.lang.Double temp = sortData.get(sortIndex);
					sortData.set(sortIndex, sortData.get(sortIndex+1));
					sortData.set(sortIndex+1, temp);
					//bars sort
					double xtemp = dataBars.get(sortIndex+1).getX();
					double heighttemp = dataBars.get(sortIndex).getHeight();
					dataBars.set(sortIndex, new Rectangle2D.Double(dataBars.get(sortIndex).getX(),0,800.0/data.length,dataBars.get(sortIndex+1).getHeight()));
					dataBars.set(sortIndex+1, new Rectangle2D.Double(xtemp,0,800.0/data.length,heighttemp));
					
				}
				else if((sortData.get(sortIndex).doubleValue()<sortData.get(sortIndex+1).doubleValue()) && sortIndex+1==sorted){
					sorted=sortIndex;
				}
				repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		complete = true;
		repaint();
	}
	
	public void shakerSort(){
		ArrayList<java.lang.Double> sortData = new ArrayList<java.lang.Double>();
		for(int x = 0; x < data.length; x++){
			sortData.add(new java.lang.Double(data[x]));
		}
		complete = false;
		boolean swapped = true;
		int sortedright = sortData.size()-1;
		int sortedleft = 0;
		while(swapped){
			swapped=false;
			for(sortIndex=sortedleft;sortIndex < sortedright; sortIndex++){
				if(sortData.get(sortIndex).doubleValue()>sortData.get(sortIndex+1).doubleValue()){
					swapped=true;
					//data sorts
					java.lang.Double temp = sortData.get(sortIndex);
					sortData.set(sortIndex, sortData.get(sortIndex+1));
					sortData.set(sortIndex+1, temp);
					//bars sort
					double xtemp = dataBars.get(sortIndex+1).getX();
					double heighttemp = dataBars.get(sortIndex).getHeight();
					dataBars.set(sortIndex, new Rectangle2D.Double(dataBars.get(sortIndex).getX(),0,800.0/data.length,dataBars.get(sortIndex+1).getHeight()));
					dataBars.set(sortIndex+1, new Rectangle2D.Double(xtemp,0,800.0/data.length,heighttemp));
					
				}
				else if((sortData.get(sortIndex).doubleValue()<sortData.get(sortIndex+1).doubleValue()) && sortIndex+1==sortedright){
					sortedright=sortIndex;
				}
				repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(sortIndex = sortedright;sortIndex > sortedleft; sortIndex--){
				if(sortData.get(sortIndex).doubleValue()<sortData.get(sortIndex-1).doubleValue()){
					swapped=true;
					//data sorts
					java.lang.Double temp = sortData.get(sortIndex);
					sortData.set(sortIndex, sortData.get(sortIndex-1));
					sortData.set(sortIndex-1, temp);
					//bars sort
					double xtemp = dataBars.get(sortIndex-1).getX();
					double heighttemp = dataBars.get(sortIndex).getHeight();
					dataBars.set(sortIndex, new Rectangle2D.Double(dataBars.get(sortIndex).getX(),0,800.0/data.length,dataBars.get(sortIndex-1).getHeight()));
					dataBars.set(sortIndex-1, new Rectangle2D.Double(xtemp,0,800.0/data.length,heighttemp));
					
				}
				else if((sortData.get(sortIndex).doubleValue()>sortData.get(sortIndex-1).doubleValue()) && sortIndex-1==sortedleft){
					sortedleft=sortIndex;
				}
				repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		complete = true;
		repaint();
	}

	public static void main(String[] args) {
		VisualSort go = new VisualSort();

	}



}
