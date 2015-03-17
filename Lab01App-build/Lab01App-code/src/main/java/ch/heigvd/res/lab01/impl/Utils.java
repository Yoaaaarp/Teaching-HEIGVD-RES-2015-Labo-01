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
  
  public static String[] getNextLine(String lines){
      int taille = lines.length();
      int index = 0;
      int nbrChar = 1;
      char cCourant;
      String lignes[] = new String[2];
      // cas par defaut, pas de retour ligne detecte
      lignes[0] = "";
      lignes[1] = lines;
      
      while(index < taille){
          cCourant = lines.charAt(index);
          if (cCourant == '\r' || cCourant == '\n'){ // RL detecte
              // controle si RL windows et qu'on ne se trouve pas deja a la fin
              // du fichier
              if (cCourant == '\r' && index != taille-1 && lines.charAt(index+1) == '\n'){
                  nbrChar++;
              }
              lignes[0] = lines.substring(0, index+nbrChar);
              lignes[1] = lines.substring(index+nbrChar, taille);
              return lignes;
          }
          
          index++;
      }
      return lignes;
  }
}
