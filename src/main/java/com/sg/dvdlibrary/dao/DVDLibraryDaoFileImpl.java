/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author mcmu0
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao {
    
    private Map<String, DVD> DVDcollection = new HashMap<>();
    public static final String COLLECTION_FILE = "collection.txt";
    public static final String DELIMITER = "::";

    @Override
    public DVD addDVD(String title, DVD newDVDentry) throws DVDLibraryDaoException {
        DVD newDVD = DVDcollection.put(title, newDVDentry);
        writeCollection();
        return newDVD;
    }
    
    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException {
        loadCollection();
        return new ArrayList<DVD>(DVDcollection.values());
    }
    
    private void loadCollection() throws DVDLibraryDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(COLLECTION_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException("-_- Could not load collection data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentTokens holds each of the parts of the currentLine after it has
        // been split on our DELIMITER
        
        String[] currentTokens;
        // Go through COLLECTION_FILE line by line, decoding each line into a DVD object.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // break up the line into tokens
            currentTokens = currentLine.split(DELIMITER);
            // Create a new DVD object and put it into the map of DVDcollection
            DVD currentDVD = new DVD(currentTokens[0]);
            // Set the remaining vlaues on currentDVD manually
            currentDVD.setReleaseDate(currentTokens[1]);
            currentDVD.setMPAArating(currentTokens[2]);
            currentDVD.setDirectorName(currentTokens[3]);
            currentDVD.setStudio(currentTokens[4]);
            currentDVD.setUserNotes(currentTokens[5]);
            

            // Put currentDVD into the map using title as the key
            DVDcollection.put(currentDVD.getTitle(), currentDVD);
        }
        // close scanner
        scanner.close();
    }
    
    
    private void writeCollection() throws DVDLibraryDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(COLLECTION_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException("Could not save DVD data.", e);
        }

        // Write out the DVD objects to the DVDcollection file.
        List<DVD> DVDList = this.getAllDVDs();
        for (DVD currentDVD : DVDList) {
            // write the DVD object to the file
            out.println(currentDVD.getTitle() + DELIMITER
                    + currentDVD.getReleaseDate() + DELIMITER
                    + currentDVD.getMPAArating() + DELIMITER
                    + currentDVD.getDirectorName() + DELIMITER
                    + currentDVD.getStudio() + DELIMITER
                    + currentDVD.getUserNotes());
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException {
        DVD removedDVD = DVDcollection.remove(title);
        writeCollection();
        return removedDVD;
    }

    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException {
        loadCollection();
        return DVDcollection.get(title);
    }

    @Override
    public List<DVD> searchDVDs(String phrase) throws DVDLibraryDaoException {
        loadCollection();
        Set<String> keySet = DVDcollection.keySet(); //loads all titles into keySet
        List<DVD> matchingDVDs = new ArrayList<>(); //list to hold all the matches
        String phraseLC = phrase.toLowerCase(); //convert to LC before comparison
        
        for (String k : keySet) { //look at every title, compare title to phrase and add to list if necessary
            String keyLC = k.toLowerCase(); //convert to LC before see if key contains phrase
            if (keyLC.contains(phraseLC)) {
                matchingDVDs.add(DVDcollection.get(k));
            }
        }
        
        return matchingDVDs;
    }
}
