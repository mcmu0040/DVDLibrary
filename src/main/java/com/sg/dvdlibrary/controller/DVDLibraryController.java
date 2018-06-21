/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/**
 *
 * @author mcmu0
 */
public class DVDLibraryController {
    // declaring but not instantiating these objects will allow the main app to control how we
    // implement a view and DAO scheme
    DVDLibraryView view;
    DVDLibraryDao dao;

    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true; // while we want to keep going, as the user for another operation to perform
        int menuSelection = 0;
        
        try {
            
            //noticed a bug where if upon start-up, if I add a DVD before I view all DVDs, 
            //the newly added DVD replaces all the DVDs in the save file. 
            //to mitigate, I load the collection before anything else to make sure I don't lose everything.
            initializeCollection();
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listAllDVDs();
                        break;
                    case 2:
                        addDVD();
                        break;
                    case 3:
                        removeDVD();
                        break;
                    case 4:
                        displayDVD();
                        break;
                    case 5:
                        editDVD();
                        break;
                    case 6:
                        searchDVDByTitle();
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
        exitMessage();
    }
    
    private void initializeCollection() throws DVDLibraryDaoException {
        dao.getAllDVDs();
    }
    
    //the below methods should only use dao and view. Leave the IO work to view, retreiving data to dao
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addDVD() throws DVDLibraryDaoException {
        view.displayCreateDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getTitle(), newDVD);
        view.displayCreateSuccessBanner();
    }
    
    private void removeDVD() throws DVDLibraryDaoException {
        view.displayRemoveDVDBanner();
        String title = view.getDVDChoice();
        DVD badDVD = dao.removeDVD(title);
        if (badDVD != null) {
            view.displayRemoveSuccessBanner();
        } else {
            view.displayRemoveFailureBanner(title);
        }
    }
    
    private void listAllDVDs() throws DVDLibraryDaoException {
        view.displayDisplayAllBanner();
        List<DVD> DVDList = dao.getAllDVDs();
        view.displayDVDList(DVDList);
    }

    private void displayDVD() throws DVDLibraryDaoException {
        view.displayDisplayDVDBanner();
        String title = view.getDVDChoice();
        DVD currentDVD = dao.getDVD(title);
        view.displayDVD(currentDVD);
    }

    private void editDVD() throws DVDLibraryDaoException {
        //Steps
        //1. Get the DVD you want by title
        //2. Remove that DVD from the collection
        //3. Take DVD and update info
        //4. Add DVD back to collection
        view.displayEditDVDBanner();
        String title = view.getDVDChoice();
        DVD currentDVD = dao.removeDVD(title); 
        //here I have the DVD I want to edit and it has been removed from the collection
        boolean done = false; //loop so I can edit all the fields I want

        while (!done) {
            int selection = view.printEditMenuAndGetSelection();

            switch (selection) {
                case 1: //releasDate
                    String releaseDate = view.editDVDField("Please enter updated release date");
                    currentDVD.setReleaseDate(releaseDate);
                    break;
                case 2: //rating
                    String rating = view.editDVDField("Please enter updated MPAA rating");
                    currentDVD.setMPAArating(rating);
                    break;
                case 3: //director
                    String directorName = view.editDVDField("Please enter the director's name");
                    currentDVD.setDirectorName(directorName);
                    break;
                case 4: //studio
                    String studio = view.editDVDField("Please enter the studio");
                    currentDVD.setStudio(studio);
                    break;
                case 5: //notes
                    String userNotes = view.editDVDField("Add additional notes if desired");
                    currentDVD.setUserNotes(userNotes);
                    break;
                case 6:
                    done = true;
                    break;
            }
        }
        dao.addDVD(title, currentDVD); //adds DVD back to collection and writes t0 file
        view.displayEditSuccessBanner();
    }

    private void searchDVDByTitle() throws DVDLibraryDaoException {
        //Goal, given a title or phrase return all DVDs that match (ie "pool" returns "Deadpool" or "Deadpool 2")
        view.displaySearchDVDBanner();
        String phrase = view.getSearch();
        List<DVD> DVDmatches = dao.searchDVDs(phrase);
        view.displayDVDList(DVDmatches);
        view.displaySearchSuccessBanner();
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
