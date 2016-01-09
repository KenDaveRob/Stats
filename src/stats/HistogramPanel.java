/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/**
 *
 * @author Kenneth Robertson and Andrew McAninch
 */
public class HistogramPanel extends javax.swing.JPanel {

    private ArrayList<Double> means;
    private int sections = -1;
    
    /**
     * Creates new form HistogramPanel
     */
    public HistogramPanel() {
        initComponents();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2D = (java.awt.Graphics2D)g;
        double screenWidthFactor = 0;
        double screenHeightFactor = 0;
        double screenSectionSize = ((double)this.getWidth())/((double)sections+1.0d);
        
        
        //Dont draw anything without data
        if(means != null && sections > 0)
        {
            HashMap<Integer,Integer> frequencies = new HashMap<Integer,Integer>(); //Keeps track of the frequencies for each class
            double highestMean = means.get(means.size()-1);
            double lowestMean = means.get(0);
            screenWidthFactor = this.getWidth() / highestMean;
            double range = highestMean - lowestMean;
            double currentMean = 0;
            int currentFrequencyClass = 1;
            double nextRange = lowestMean + (range/(sections));
            int highestFrequency = -1;
            double currentFrequencyHeight = 0;
            
 
            for(int i = 1; i <= sections; i++) //Initialize all frequencies to 0
                frequencies.put(i, 0);


            for(int i = 0; i < means.size(); i++)
            {         
                currentMean = means.get(i);

                //As long as the currentMean is < nextRange increase the frequency class and nextRange
                while(nextRange < currentMean && Math.abs(nextRange - currentMean) > .00001) 
                {
                    currentFrequencyClass++;
                    nextRange += (range / (sections));
                }
                
                //This if statement maybe redundant, it checks that that the while statement is now false,and
                //increments the frequency for the currentFrequencyClass
                if(nextRange >= currentMean || Math.abs(nextRange - currentMean) < .00001)         
                    frequencies.put(currentFrequencyClass, frequencies.get(currentFrequencyClass) + 1);
            }
            

            
            //Find the highest frequency
            for(int i = 1; i <= sections; i++)
                if(highestFrequency < frequencies.get(i)) highestFrequency = frequencies.get(i);
                        
            screenHeightFactor = this.getHeight()/highestFrequency;
            
            //Draw the histogram to the panel
            for(int i = 1; i <= sections; i++)
            {
                currentFrequencyClass = frequencies.get(i);

                currentFrequencyHeight = currentFrequencyClass * screenHeightFactor;
                g2D.draw(new java.awt.Rectangle.Double(screenSectionSize * i - (screenSectionSize/2.0d), this.getHeight()-currentFrequencyHeight, 
                        screenSectionSize, currentFrequencyHeight));
            }

            //Add text to histogram
            g2D.setColor(Color.black);
            g2D.drawString(Double.toString(lowestMean).substring(0, 5), (float)(screenSectionSize/2.0f), (float)this.getHeight()-2.0f);
            g2D.drawString(Double.toString(highestMean).substring(0, 5), (float)(screenSectionSize * sections - (screenSectionSize/2.0f)), (float)this.getHeight()-2.0f);
            g2D.drawString("top freq="+Integer.toString(highestFrequency), 2.0f, 12.0f);
        }

    }
    
    
    public void drawHistogram(int sections, ArrayList<Double> means)
    {
        this.sections = sections;
        Collections.sort(means);
        this.means = means;
        this.repaint();
    }
    
    public void redrawHistogram()
    {
        this.repaint();
    }

    public int getSections() {
        return sections;
    }

    public void setSections(int sections) {
        this.sections = sections;
    }

    public ArrayList<Double> getMeans() {
        return means;
    }

    public void setMeans(ArrayList<Double> means) {
        this.means = means;
        Collections.sort(means);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
