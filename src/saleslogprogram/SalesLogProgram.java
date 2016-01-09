/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package saleslogprogram;

/**
 *
 * @author Kenneth
 */
public class SalesLogProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        printTestSalesLog();
    }
    
    public static void printTestSalesLog()
    {
        java.io.File salesFile = new java.io.File("c:\\salesLog.txt");
        SalesLog salesLog = new SalesLog(salesFile);
        
        salesLog.printSalesLog();
    }
    
    public static void generateTestSalesLog()
    {
        java.util.Dictionary<String,Integer> prodQuats = new java.util.Hashtable<String,Integer>();
        prodQuats.put("1234", 2);
        prodQuats.put("5555", 1);
        Sale testSale = new Sale("Kenneth Robertson", new java.util.Date(), prodQuats, 100.0d);
        java.io.File salesFile = new java.io.File("c:\\salesLog.txt");
        SalesLog salesLog = new SalesLog(salesFile);
        salesLog.addSale(testSale);
        
        prodQuats.put("6543", 5);
        try
        {
            testSale = new Sale("John Smith", new java.util.Date(), prodQuats, PaymentType.CREDIT);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        
        salesLog.addSale(testSale);
    }
}
