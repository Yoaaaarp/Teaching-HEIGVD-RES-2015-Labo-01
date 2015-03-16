package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import ch.heigvd.res.lab01.impl.Utils;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCounter = 1;
  private boolean newLine = true;
  private boolean lastCharWasR = false;
  
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      boolean stop = false;
      String subStr = str.substring(off, off+len);
      String lines[];
      String line;
      
       while(!stop){
           lines = Utils.getNextLine(subStr);
           // pas de nouvelle ligne
           if (lines[0].equals("")){
               if (newLine){
                   line = lineCounter + "\t" + lines[1];
                   lineCounter++;
                   newLine = false;
               } else {
                   line = lines[1];
               }
               out.write(line);
               stop = true;
           } // plus de nouvelle ligne disponible
           else if (lines[1].equals("")){
               if (newLine){
                   line = lineCounter + "\t" + lines[0];
                   lineCounter++;
                   newLine = false;
               } else {
                   line = lines[0];
               }
               line += lineCounter + "\t";
               lineCounter++;
               out.write(line);
               stop = true;
           } // on a recu une ligne et il en reste
           else {
               if (newLine){
                   line = lineCounter + "\t" + lines[0];
                   lineCounter++;
                   newLine = false;
               } else {
                   line = lines[0];
               }
               out.write(line);
           }
       }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      String newStr = String.valueOf(cbuf);
      out.write(newStr, off, len);
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
      String newStr;
      if (((char)c) == '\r'){
          lastCharWasR = true;
      } else if (lastCharWasR){
          if (((char)c) == '\n'){ // new line on Windows
              newStr = "\r\n" + lineCounter + "\t";
              lineCounter++;
          } else { // was a new line on Mac
              newStr = "\r" + lineCounter + "\t" + String.valueOf((char)c);
              lineCounter++;
          }
          newLine = false;
          lastCharWasR = false;
          out.write(newStr);
      } else if (((char)c) == '\n') { // new line on Unix
          newStr = "\n" + lineCounter + "\t";
          lineCounter++;
          out.write(newStr);
          newLine = false;
      } else {
          if (newLine){
              newStr = lineCounter + "\t" + String.valueOf((char)c);
              lineCounter++;
              newLine = false;
          } else {
              newStr = String.valueOf((char)c);
          }
          out.write(newStr);
      }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
