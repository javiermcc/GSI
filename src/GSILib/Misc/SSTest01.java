/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILib.Misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.model.OpenDocument;

/**
 *
 * @author linux1
 */
public class SSTest01 {
    
    public static void main(String[] args) throws IOException {
        
        Object[][] miArray=new Object[4][6];
        SpreadSheet miSs;
        File outfile=new File("test01.ods");
        
        
        for (int i=0;i<4;i++){
            for (int j=0;j<6;j++){
                miArray[i][j]=(int) Math.floor(Math.random()*24+1);
                System.out.print(" "+miArray[i][j]);
            }
            System.out.println();
        }
        
        String[] columns=new String[] {"A","B","C","D","E","F"};
        
        TableModel model=new DefaultTableModel(miArray,columns);
        
        try{
            SpreadSheet.createEmpty(model).saveAs(outfile);
        }catch(FileNotFoundException e){
            System.out.println("No se ha encontrado el fichero deseado");
        }
        
        /*final OpenDocument doc = new OpenDocument();
        doc.loadFrom("test01.ods");
        
        final JFrame mainFrame = new JFrame("Viewer");
        DefaultDocumentPrinter printer = new DefaultDocumentPrinter();

        ODSViewerPanel viewerPanel = new ODSViewerPanel(doc, printer, true);

        mainFrame.setContentPane(viewerPanel);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(10, 10);
        mainFrame.setVisible(true);*/
            
    }
    
}
