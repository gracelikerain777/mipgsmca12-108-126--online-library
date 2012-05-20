import java.io.FileInputStream;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
public class ExcelReader
{
public static void main(String[] args)
{
String fileName = "1.xls";
Vector dataHolder = ReadCSV(fileName);
printCellDataToConsole(dataHolder);
}
public static Vector ReadCSV(String fileName)
{
Vector cellVectorHolder = new Vector();
try
{
FileInputStream myInput = new FileInputStream(fileName);
POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
HSSFSheet mySheet = myWorkBook.getSheetAt(0);
Iterator rowIter = mySheet.rowIterator();
while (rowIter.hasNext())
{
HSSFRow myRow = (HSSFRow) rowIter.next();
Iterator cellIter = myRow.cellIterator();
Vector cellStoreVector = new Vector();
while (cellIter.hasNext())
{
HSSFCell myCell = (HSSFCell) cellIter.next();
cellStoreVector.addElement(myCell);
}
cellVectorHolder.addElement(cellStoreVector);
}
}
catch (Exception e)
{
e.printStackTrace();
}
return cellVectorHolder;
}
private static void printCellDataToConsole(Vector dataHolder)
{
String s="";
for (int i = 0; i < dataHolder.size(); i++)
{
Vector cellStoreVector = (Vector) dataHolder.elementAt(i);
for (int j = 0; j < cellStoreVector.size(); j++)
{
HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
s+=myCell.toString();
}
s+="\n";                
}
System.out.println(s);       
}
}