package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
public static String[] getNextLine(String lines) {
    String str[] = new String[2];
    String rn = "\r\n";
    char r = '\r';
    char n = '\n';
    int indexRN = lines.indexOf(rn);
    int indexR = lines.indexOf(r);
    int indexN = lines.indexOf(n);
    int indexUsed;
    int nbrChar = 1;
      
    // WINDOWS
    if (indexRN != -1){
        indexUsed = indexRN;
        nbrChar = rn.length();
    } // MAC 
    else if (indexR != -1){
        indexUsed = indexR;
    } // UNIX
    else if (indexN != -1){
        indexUsed = indexN;
    } // pas de nouvelle ligne
    else {
        str[0] = "";
        str[1] = lines;
        return str;
    }
    
    indexUsed += nbrChar;
    // premiere ligne est jusqu'au delimiteur (inclus)
    str[0] = lines.substring(0, indexUsed);
    // le reste du texte est depuis le delimiteur (non-incus)
    str[1] = lines.substring(indexUsed);
      
    return str;
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }
}
