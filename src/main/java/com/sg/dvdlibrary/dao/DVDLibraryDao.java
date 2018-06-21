/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author mcmu0
 */
public interface DVDLibraryDao {
    
    // add a DVD to the collection and then save the collection
    DVD addDVD(String title, DVD newDVD) throws DVDLibraryDaoException;
    
    // return a List of all the DVD objects
    List<DVD> getAllDVDs() throws DVDLibraryDaoException;
    
    // remove DVD from collection, save the updated collection the file, return the removed DVD
    DVD removeDVD(String title) throws DVDLibraryDaoException;
    
    // return DVD object for a given title
    DVD getDVD(String title) throws DVDLibraryDaoException;
    
    // method that returns all DVDs that contain "phrase" in their title
    List<DVD> searchDVDs(String phrase) throws DVDLibraryDaoException;
}
