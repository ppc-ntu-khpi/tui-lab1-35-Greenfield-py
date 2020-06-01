package com.mybank.tui;

import com.mybank.data.DataSource;
import com.mybank.domain.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import jexer.TAction;
import jexer.TApplication;
import jexer.TField;
import jexer.TText;
import jexer.TWindow;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class TUIdemo extends TApplication {

    private static final int ABOUT_APP = 2000;
    private static final int CUST_INFO = 2010;

    public static void main(String[] args) throws Exception {
        TUIdemo tdemo = new TUIdemo();
        (new Thread(tdemo)).start();
         DataSource dataSource = new DataSource("D:\\lessons\\Java\\TUIdemo\\src\\com\\mybank\\data\\test.dat");
        dataSource.loadData();

        
        
        
    }

    public TUIdemo() throws Exception {
        super(BackendType.SWING);

        addToolMenu();
        //custom 'File' menu
        TMenu fileMenu = addMenu("&File");
        fileMenu.addItem(CUST_INFO, "&Customer Info");
        fileMenu.addDefaultItem(TMenu.MID_SHELL);
        fileMenu.addSeparator();
        fileMenu.addDefaultItem(TMenu.MID_EXIT);
        //end of 'File' menu  

        addWindowMenu();

        //custom 'Help' menu
        TMenu helpMenu = addMenu("&Help");
        helpMenu.addItem(ABOUT_APP, "&About...");
        //end of 'Help' menu 

        setFocusFollowsMouse(true);
        //Customer window
        ShowCustomerDetails();
    }

    @Override
    protected boolean onMenu(TMenuEvent menu) {
        if (menu.getId() == ABOUT_APP) {
            messageBox("About", "\t\t\t\t\t   Just a simple Jexer demo.\n\nCopyright \u00A9 2019 Alexander \'Taurus\' Babich").show();
            return true;
        }
        if (menu.getId() == CUST_INFO) {
            ShowCustomerDetails();
            return true;
        }
        return super.onMenu(menu);
    }

    private void ShowCustomerDetails() {
        TWindow custWin = addWindow("Customer Window", 2, 1, 40, 10, TWindow.NOZOOMBOX);
        custWin.newStatusBar("Enter valid customer number and press Show...");

        custWin.addLabel("Enter customer number: ", 2, 2);
        TField custNo = custWin.addField(24, 2, 3, false);
        TText details = custWin.addText("Owner Name: \nAccount Type: \nAccount Balance: ", 2, 4, 38, 8);
        custWin.addButton("&Show", 28, 2, new TAction() {
            @Override
            public void DO() {
                try {
                    int custNum = Integer.parseInt(custNo.getText());                              
                    //details about customer with index==custNum
                    if(custNum == 1)
                        details.setText("Owner Name: Jane Simms (id="+custNum+")\nAccount Type: 'Savings'\nAccount Balance: $500.00\nAccount Type: 'Checking'\nAccount Balance: $200.00");
                    else if(custNum == 2)
                        details.setText("Owner Name: Owen Bryant (id="+custNum+")\nAccount Type: 'Checking'\nAccount Balance: $200.00");
                    else if(custNum == 3)
                        details.setText("Owner Name: Tim Soley (id="+custNum+")\nAccount Type: 'Savings'\nAccount Balance: $1500.00\nAccount Type: 'Checking'\nAccount Balance: $200.00");
                    else if(custNum == 4)
                        details.setText("Owner Name: Tim Soley (id="+custNum+")\nAccount Type: 'Savings'\nAccount Balance: $150.00");
                }
                
                    catch (Exception e) {
                    messageBox("Error", "You must provide a valid customer number!").show();
                    }
            }
        });
    }
}
