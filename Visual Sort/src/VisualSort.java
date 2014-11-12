import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class VisualSort extends JPanel implements ActionListener,Runnable {
	
	public static double[] data;
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
		data = new double[10000];
		for(int x  = 0; x < data.length; x++){
			data[x]=Math.random();
		}
		
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
			g2d.setColor(Color.black);
			g2d.fill(dataBars.get(x));
		}


		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}
	
	@Override
	public void run() {
		//paint original data
		int i = 0;
		while(i<data.length){
			dataBars.add(new Rectangle2D.Double(i*(800.0/data.length), 0, 800.0/data.length, 800.0*data[i]));
			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}

		ArrayList<java.lang.Double> sortData = new ArrayList<java.lang.Double>();
		for(int x = 0; x < data.length; x++){
			sortData.add(new java.lang.Double(data[x]));
		}
		
		quickSort(sortData);
		//selectionSort(sortData);
		//bubbleSort(sortData);
		//shakerSort(sortData);

	}
	
	public void quickSort(ArrayList<java.lang.Double> sortData){

		complete = false;
		int pivotIndex=sortData.size()-1;
		java.lang.Double pivot = sortData.get(pivotIndex);
		/*
		 * move pivot to a point where values below it are < it and values above it are > than it.
		 * Do this by moving the pivot towards the middle by one index and swapping the first term
		 * before the pivot that is greater than it with the term that now occupies the spot where
		 * the pivot just was.  
		 */
		boolean swapped=true;
		while(swapped){
			swapped=false;
			for (int i = 0; i < pivotIndex; i++) {
				if(sortData.get(i).compareTo(sortData.get(pivotIndex))>0){
					sortData.set(pivotIndex, sortData.get(pivotIndex-1));
					sortData.set(pivotIndex-1, pivot);
					java.lang.Double temp = sortData.get(pivotIndex);
					sortData.set(pivotIndex, sortData.get(i));
					sortData.set(i, temp);
					pivotIndex--;
					swapped=true;
					break;
				}
			}
			for(int x = 0; x < sortData.size(); x++){
				dataBars.get(x).setRect(x*(800.0/data.length), 0, 800.0/data.length, 800.0*sortData.get(x));
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
	
	public void selectionSort(ArrayList<java.lang.Double> sortData){
		complete = false;
		int smallestIndex=0;
		int endOfSort=0;
		while(endOfSort<sortData.size()){
			smallestIndex=endOfSort;
			for(int i = endOfSort; i< sortData.size(); i++){
				if(sortData.get(i).compareTo(sortData.get(smallestIndex))<0){
					smallestIndex=i;
				}

			}
			java.lang.Double smallest = new java.lang.Double(sortData.get(smallestIndex));
			sortData.remove(smallestIndex);
			sortData.add(endOfSort, smallest);
			
			Rectangle2D.Double smallestBar = new Rectangle2D.Double(dataBars.get(endOfSort).getX(),0,800.0/data.length, dataBars.get(smallestIndex).getHeight());
			dataBars.remove(smallestIndex);
			dataBars.add(endOfSort, smallestBar);
			
			for(int i = 0; i < sortData.size(); i++){
				dataBars.get(i).setRect(i*(800.0/data.length), 0, 800.0/data.length, 800.0*sortData.get(i));
			}
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		endOfSort++;
		}
		complete=true;
		repaint();
	}
	
	public void bubbleSort(ArrayList<java.lang.Double> sortData){

		complete = false;
		boolean swapped = true;
		int sorted = sortData.size()-1;
		while(swapped){
			swapped=false;
			for(int i = 0;i < sorted; i++){
				if(sortData.get(i).doubleValue()>sortData.get(i+1).doubleValue()){
					swapped=true;
					//data sorts
					java.lang.Double temp = sortData.get(i);
					sortData.set(i, sortData.get(i+1));
					sortData.set(i+1, temp);
					//bars sort
					double xtemp = dataBars.get(i+1).getX();
					double heighttemp = dataBars.get(i).getHeight();
					dataBars.set(i, new Rectangle2D.Double(dataBars.get(i).getX(),0,800.0/data.length,dataBars.get(i+1).getHeight()));
					dataBars.set(i+1, new Rectangle2D.Double(xtemp,0,800.0/data.length,heighttemp));
					
				}
				else if((sortData.get(i).doubleValue()<sortData.get(i+1).doubleValue()) && i+1==sorted){
					sorted=i;
				}

			}
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		complete = true;
		repaint();
	}
	
	public void shakerSort(ArrayList<java.lang.Double> sortData){

		complete = false;
		boolean swapped = true;
		int sortedright = sortData.size()-1;
		int sortedleft = 0;
		while(swapped){
			swapped=false;
			for(int i =sortedleft;i < sortedright; i++){
				if(sortData.get(i).doubleValue()>sortData.get(i+1).doubleValue()){
					swapped=true;
					//data sorts
					java.lang.Double temp = sortData.get(i);
					sortData.set(i, sortData.get(i+1));
					sortData.set(i+1, temp);
					//bars sort
					double xtemp = dataBars.get(i+1).getX();
					double heighttemp = dataBars.get(i).getHeight();
					dataBars.set(i, new Rectangle2D.Double(dataBars.get(i).getX(),0,800.0/data.length,dataBars.get(i+1).getHeight()));
					dataBars.set(i+1, new Rectangle2D.Double(xtemp,0,800.0/data.length,heighttemp));
					
				}
				else if((sortData.get(i).doubleValue()<sortData.get(i+1).doubleValue()) && i+1==sortedright){
					sortedright=i;
				}
			}
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = sortedright;i > sortedleft; i--){
				if(sortData.get(i).doubleValue()<sortData.get(i-1).doubleValue()){
					swapped=true;
					//data sorts
					java.lang.Double temp = sortData.get(i);
					sortData.set(i, sortData.get(i-1));
					sortData.set(i-1, temp);
					//bars sort
					double xtemp = dataBars.get(i-1).getX();
					double heighttemp = dataBars.get(i).getHeight();
					dataBars.set(i, new Rectangle2D.Double(dataBars.get(i).getX(),0,800.0/data.length,dataBars.get(i-1).getHeight()));
					dataBars.set(i-1, new Rectangle2D.Double(xtemp,0,800.0/data.length,heighttemp));
					
				}
				else if((sortData.get(i).doubleValue()>sortData.get(i-1).doubleValue()) && i-1==sortedleft){
					sortedleft=i;
				}
			}
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		complete = true;
		repaint();
	}

	public static void main(String[] args) {
		VisualSort go = new VisualSort();

	}



}
