package com.momenton.orgchart;

import com.momenton.orgchart.employee.TreeImpl;
import com.momenton.orgchart.input.CommandParser;
import com.momenton.orgchart.input.Input;
import com.momenton.orgchart.input.Sanitiser;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        setUpLogging();
        runSimulation(args);

    }

    private static void runSimulation(String[] args) {
        TreeImpl tree = new TreeImpl();
        new Input().apply(args)
                .map(new Sanitiser())
                .map(new CommandParser())
                .map(argList -> tree.addChild(argList))
                .collect(Collectors.toList());
          System.out.println("===================printing output===============");
          tree.print();
    }


    private static void setUpLogging() {
        Logger rootLog = Logger.getLogger("");
        rootLog.setLevel(Level.FINE);
        rootLog.getHandlers()[0].setLevel(Level.FINE);
    }
}
