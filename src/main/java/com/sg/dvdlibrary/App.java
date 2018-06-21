/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary;

import com.sg.dvdlibrary.controller.DVDLibraryController;
import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import com.sg.dvdlibrary.ui.UserIO;
import com.sg.dvdlibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author mcmu0
 */
public class App {
    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl(); //allows for ease of chaning IO schemes based off of UserIO
        DVDLibraryView myView = new DVDLibraryView(myIo); //allows for ease of changing DVDLibraryView
        DVDLibraryDao myDao = new DVDLibraryDaoFileImpl(); //allows for ease of changing dao based off of DVDLibraryDao
        DVDLibraryController controller = new DVDLibraryController(myDao, myView); //makes the new controller 
        controller.run(); //runs the program
    }
}
