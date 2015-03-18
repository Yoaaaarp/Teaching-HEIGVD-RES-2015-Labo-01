package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    //on visit le File courant
    vistor.visit(rootDirectory);
    // On recupere tous les sous-dossiers/fichiers
    File listOfFiles[] = rootDirectory.listFiles();
  
    // si la liste n'est pas vide alors on continue notre exploration
    // sur les File trouves 
    if (listOfFiles != null){
        Arrays.sort(listOfFiles);
        // on commence par réordonner la liste pour que les fichiers apparaissent
        // avant les dossiers (test unitaire oblige...)
        int nbrFichier = 0;
        File temp;
        for (int i = 0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isFile()){
                if (i != nbrFichier){
                    temp = listOfFiles[i];
                    listOfFiles[i] = listOfFiles[nbrFichier];
                    listOfFiles[nbrFichier] = temp;
                }
                nbrFichier++;
            }
        }
        // continue l'exploration
        for (File file : listOfFiles){
            explore(file, vistor);
        }
    }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
