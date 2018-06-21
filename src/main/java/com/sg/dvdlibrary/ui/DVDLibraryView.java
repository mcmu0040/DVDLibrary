/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author mcmu0
 */
public class DVDLibraryView {

    UserIO io;

    public DVDLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List all DVDs in the collection");
        io.print("2. Add a new DVD to the collection");
        io.print("3. Remove a DVD from the collection");
        io.print("4. Display a DVD");
        io.print("5. Edit a DVD entry");
        io.print("6. Search for a DVD by title");
        io.print("7. Exit");

        return io.readInt("Please select from the above choices.", 1, 7);
    }

    public DVD getNewDVDInfo() {
        String title = io.readString("Please enter DVD title");
        String releaseDate = io.readString("Please enter release date");//left as string incase user inputted ful date
        String rating = io.readString("Please enter MPAA rating");
        String directorName = io.readString("Please enter the director's name");
        String studio = io.readString("Please enter the studio");
        String userNotes = io.readString("Add additional notes if desired");
        DVD currentDVD = new DVD(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMPAArating(rating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setUserNotes(userNotes);
        return currentDVD;
    }

    public void displayCreateDVDBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("DVD successfully created.  Please hit enter to continue");
    }

    public void displayDVDList(List<DVD> DVDList) {
        for (DVD currentDVD : DVDList) {
            io.print(currentDVD.getTitle() + ": released "
                    + currentDVD.getReleaseDate() + ", rated "
                    + currentDVD.getMPAArating() + ". My thoughts: "
                    + currentDVD.getUserNotes());
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All DVDs ===");
    }

    public void displayDisplayDVDBanner() {
        io.print("=== Display DVD ===");

    }

    public void displayEditDVDBanner() {
        io.print("=== Edit DVD ===");
    }

    public String getDVDChoice() {
        return io.readString("Please enter the DVD.");
    }
    
    public String getSearch() {
        return io.readString("What title do you want to search for?");
    }

    public void displayDVD(DVD currentDVD) {
        if (currentDVD != null) {
            io.print(currentDVD.getTitle());
            io.print(currentDVD.getReleaseDate());
            io.print(currentDVD.getMPAArating());
            io.print(currentDVD.getDirectorName());
            io.print(currentDVD.getStudio());
            io.print(currentDVD.getUserNotes());
            io.print("");
        } else {
            io.print("That DVD is not in your collection . . . yet.");
        }
        io.readString("Please hit enter to continue.");
    }

    public int printEditMenuAndGetSelection() {
        io.print("Edit Menu");
        io.print("1. Edit release year");
        io.print("2. Edit MPAA rating");
        io.print("3. Edit director's name");
        io.print("4. Edit studio name");
        io.print("5. Edit user's notes");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public String editDVDField(String prompt) {
        return io.readString(prompt);
    }
    
    public void displayEditSuccessBanner() {
        io.readString("DVD successfully editted. Please hit enter to continue.");
    }

    public void displayRemoveDVDBanner() {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveSuccessBanner() {
        io.readString("DVD successfully removed. Please hit enter to continue.");
    }
    
    public void displaySearchDVDBanner() {
        io.print("=== Serach DVD by Title ===");
    }
    
    public void displaySearchSuccessBanner() {
        io.readString("DVD search successful. Please hit enter to continue.");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayRemoveFailureBanner(String title) {
        io.readString(title + ": is not in your collection. Removal FAILURE. Hit enter to continue.");
    }
}
