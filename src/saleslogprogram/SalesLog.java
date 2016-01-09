/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package saleslogprogram;

import java.io.File;
import java.util.Date;
import java.util.Dictionary;

/**
 * Class Provides the ability to save Sale Logs to a file and Print out requested invoices. 
 * 
 * @author Kenneth Robertson
 */
        
/**
 * Used to keep track of what kind of payment the customer is using.
 * @author Kenneth
 */
enum PaymentType {
    CASH, CREDIT, CHECK 
}

/**
 * This class holds information about individual sales.
 * @author Kenneth
 */
class Sale
{
    private String customer;
    private java.util.Date saleDate;
    private java.util.Dictionary<String, Integer> productsQuantities;
    private PaymentType paymentType;
    private double amountPayed = Double.NaN;

    public Sale(String customer, Date saleDate, Dictionary<String, Integer> productsQuantities, double amountPayed) 
    {
        this.customer = customer;
        this.saleDate = saleDate;
        this.productsQuantities = productsQuantities;
        this.paymentType = PaymentType.CASH;
        this.amountPayed = amountPayed;
    }

    public Sale(String customer, Date saleDate, Dictionary<String, Integer> productsQuantities, PaymentType paymentType) throws Exception
    {
        this.customer = customer;
        this.saleDate = saleDate;
        this.productsQuantities = productsQuantities;
        this.paymentType = paymentType;
        
        if(paymentType == PaymentType.CASH) throw new Exception("Constructed a sale using cash without providing amount");
    }

    public String getCustomer() {
        return customer;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public Dictionary<String, Integer> getProductsQuantities() {
        return productsQuantities;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public double getAmountPayed() {
        return amountPayed;
    }
    
    
}

public class SalesLog 
{
    //private ProductCatalog productCatalog; //This will be needed to look up the item name and price
    private java.io.File salesLog;
    private static int saleIndex = 1;

    public SalesLog(File salesLog) {
        this.salesLog = salesLog;
    }
    
    /**
     * Method adds a sale invoice to the sale log file.
     * 
     * @param newSale   Object containing the information to be saved to the sale log.
     * @return          A boolean indicating that the sale was added correctly. 
     */
    public boolean addSale(Sale newSale)
    {

        try
        {
              java.io.BufferedWriter bsw = new java.io.BufferedWriter(new java.io.BufferedWriter(new java.io.FileWriter(salesLog,true)));
              double totalCost = 0.0d;
              
              bsw.append("Sale "+Integer.toString(saleIndex));
              
              bsw.newLine();
              bsw.append(newSale.getCustomer());
              bsw.newLine();
              bsw.append(newSale.getSaleDate().toString());
              bsw.newLine();
              
              java.util.Enumeration<String> productIDs = newSale.getProductsQuantities().keys();
              while(productIDs.hasMoreElements())
              {
                  String productID = productIDs.nextElement();
                  
                  
                  
                  //This is temp code, remove it and replace it with the code below
                  int productQuantity = newSale.getProductsQuantities().get(productID);
                  double subtotal = 23.50d * (double)productQuantity;
                  totalCost += subtotal;
                  
                  bsw.append(productID); //Write product name
                  bsw.newLine();
                  bsw.append(Double.toString(23.50d)); //Write product price
                  bsw.newLine();
                  bsw.append(Integer.toString(productQuantity)); //Write product quantity
                  bsw.newLine();
                  bsw.append(Double.toString(subtotal)); //Write subtotal
                  bsw.newLine();
                  bsw.newLine();
                  
                  //Replace with this code
                  /*
                  Product item = productCatalog.getProduct(productID);
                  double productPrice = item.getProductPrice();
                  int productQuantity = newSale.getProductsQuantities().get(productID);
                  double subtotal = productPrice * (double)productQuantity;
                  totalCost += subtotal;
                  
                  bufferFileWriter.append(item.getProductDescription()); //Write product name
                  bufferFileWriter.newLine();
                  bufferFileWriter.append(Double.toString(productPrice)); //Write product price
                  bufferFileWriter.newLine();
                  bufferFileWriter.append(Integer.toString(productQuantity)); //Write product quantity
                  bufferFileWriter.newLine();
                  bufferFileWriter.append(Double.toString(subtotal)); //Write subtotal
                  bufferFileWriter.newLine();
                  */
              }
              
              bsw.append("Purchase List Complete.");
              bsw.newLine();
              bsw.append(Double.toString(totalCost)+ " dollars owed.");
              bsw.newLine();
              
              if(newSale.getPaymentType() != PaymentType.CASH)
              {
                  bsw.append(newSale.getPaymentType().toString()+" used to pay.");
                  bsw.newLine();
                  bsw.append("0.00 dollars returned.");
                  bsw.newLine();
              }
              else
              {
                  bsw.append(Double.toString(newSale.getAmountPayed())+" was paid.");
                  bsw.newLine();
                  bsw.append(Double.toString(newSale.getAmountPayed() - totalCost)+ " dollars returned.");
                  bsw.newLine();
              }
              
              bsw.newLine();   
              bsw.newLine();
              
              
              bsw.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
        
        saleIndex++;
        return true;
    }
    
    /**
     * Method prints the contents of sale log in no particular order.
     * 
     * @return      Returns a boolean indicating that the sale log file was successfully accessed.
     */
    public boolean printSalesLog()
    {
        //Should be replaced with a reference to the actual stores name
        System.out.println("STORE NAME");
        
        try
        {
            java.io.BufferedReader bsr = new java.io.BufferedReader(new java.io.FileReader(salesLog));
            
            String line = bsr.readLine();

            while(line != null)
            {
                //Check if new Sale has been found
                if(line.startsWith("Sale"))
                {                    
                    System.out.println(bsr.readLine() + "     " + bsr.readLine());
                    line = bsr.readLine();
                }
                
                //Print out item list information
                if(!line.startsWith("Purchase"))
                {
                    String quantity;
                    String price;
                    String subtotal;

                    System.out.print(line + ": <");

                    price = bsr.readLine();
                    quantity = bsr.readLine();

                    subtotal = bsr.readLine();
                    System.out.println(quantity + " number @ " + price + " unit price = " + subtotal + " subtotal>");
                    
                    line = bsr.readLine();
                }
                
                //Print out the purchase information
                else
                {      
                    System.out.println("Total $"+bsr.readLine().split(" dollars owed")[0]);
                    
                    String payment = bsr.readLine();
                    
                    
                    if(payment.contains("used"))
                    {
                        System.out.println("Paid with "+payment.split(" used")[0]);
                        line = bsr.readLine();
                    }
                    else
                    {
                        System.out.println("Amount tendered: "+payment.split(" was paid")[0]);
                        System.out.println("Amount returned: "+bsr.readLine().split(" dollars returned")[0]);
                    }
                    
                    line = bsr.readLine();
                }
                
                //Skip blank lines
                while(line != null && line.length() == 0)
                {
                    line = bsr.readLine();
                }
            }
            
            bsr.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

}
