// Environment code for project gui2.mas2j


import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Logger;
import java.util.*;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Epulet extends Environment {

    public static  boolean network = false;
    public static  boolean water = false;
    public static  boolean electricity = false;

    public static final Term checkLiquid = Literal.parseLiteral("checkLiquid");
    public static final Term orderLiquid = Literal.parseLiteral("orderLiquid");
    public static final Term cleanKitchen =   Literal.parseLiteral("cleanKitchen");//szol a fononek hogy kuldjon takaritot
    public static final Term cleanGarage =   Literal.parseLiteral("cleanGarage");//szol a fononek hogy kuldjon takaritot
    public static final Term cleanHall =   Literal.parseLiteral("cleanHall");//szol a fononek hogy kuldjon takaritot
    public static final Term checkAgent = Literal.parseLiteral("checkAgent"); //ugynokok lekerdezese
    public static final Term sendAgent =   Literal.parseLiteral("sendAgent(agent)"); //clean Room hatasara lekerdezi van e szabad ugynok ha igen kikuldi
    public static final Term createFood = Literal.parseLiteral("createFood");
    // TODO ???? check volt e kosz kaja  keszites kozben

    private Logger logger = Logger.getLogger("gui2.mas2j."+Epulet.class.getName());
    public int i=0;
    public Model model = new Model();
    public View view = new View();

    /** Called before the MAS execution with the args informed in .mas2j */

    @Override

    public void init(String[] args) {

        super.init(args);

        Literal isCleanedKitchen = Literal.parseLiteral("cleaned(kitchen)");
        Literal isCleanedGarage = Literal.parseLiteral("cleaned(garage)");
        Literal isCleanedHall = Literal.parseLiteral("cleaned(hall)");

        addPercept("cleanBotAlfa" , isCleanedKitchen);
        addPercept("cleanBotAlfa" ,isCleanedGarage);
        addPercept("cleanBotAlfa" ,isCleanedHall);

        addPercept("cleanBotBeta" , isCleanedKitchen);
        addPercept("cleanBotBeta" ,isCleanedGarage);
        addPercept("cleanBotBeta" ,isCleanedHall);

        addPercept("cleanBotGamma" , isCleanedKitchen);
        addPercept("cleanBotGamma" ,isCleanedGarage);
        addPercept("cleanBotGamma" ,isCleanedHall);
        try {
            view.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    @Override

    public boolean executeAction(String agName, Structure action) {

        System.out.println("executing: "+action+", but not implemented!");
        try {


            if (action.equals(checkLiquid)) {
                System.out.println("Az (java) executioActionban hivok liquid check");
                return model.checkLiquid();
            }
            if (action.equals(orderLiquid)) {
                System.out.println("Az  (java) executioActionban hivok liquid order");
                return model.orderLiquid();
            }
            if (action.equals(cleanKitchen)) {
                System.out.println("Az  (java) executioActionban hivok cleanKitchent");
                return model.cleanKitchen();
            }

            if (action.equals(cleanGarage)) {
                System.out.println("Az  (java) executioActionban hivok cleangaraget");
                return model.cleanGarage();
            }
            if (action.equals(cleanHall)) {
                System.out.println("Az  (java) executioActionban hivok cleanhallt");
                return model.cleanHall();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // the action was executed with success

    }



    /** Called before the end of MAS execution */

    @Override

    public void stop() {

        super.stop();

    }

    class Model extends GridWorldModel {
        public int liquidNum = 10;
        public  final List<String> busy = new LinkedList<String>();// by the way ezt még sehol se használjuk :D
        public  List<String> available = Arrays.asList("cleanBotAlfa", "cleanBotBeta", "cleanBotGamma");
        public final List<String> rooms = Arrays.asList("kitchen", "garage", "hall");



        public Model() {
            super(0,0,0);
        }

        //The gui calls this
        public void makeKitchenDirty() {
            Literal isCleanedKitchen = Literal.parseLiteral("dirtykitchen");
            String tmp = available.get(i%3);
            i++;
            addPercept(tmp, isCleanedKitchen);
        }
        public void makeGarageDirty() {
            Literal isCleanedGarage = Literal.parseLiteral("dirtygarage");
            String tmp = available.get(i%3);
            i++;
            addPercept(tmp, isCleanedGarage);
        }
        public void makeHallDirty() {
            Literal isCleanedHall = Literal.parseLiteral("dirtyhall");
            String tmp = available.get(i%3);
            i++;
            addPercept(tmp, isCleanedHall);
        }

        public boolean checkLiquid() {
            System.out.println("In checkLiquid");
            if (liquidNum > 0) {
                return true;
            }
            //TODO: Order liquid call
            return false;
        }

        public boolean orderLiquid() {
            liquidNum++;
            Literal outOfLiquid = Literal.parseLiteral("outOfLiquid");
            removePercept("takarito", outOfLiquid);
            //updatePercepts();
            //innen nem működik, mert a takarito agensnek ugyanugy marad a dirty hiedelme (nem frissul -> nem fut le ujra a takaritas)
            //ezert ha elfogy a liquid -> nagytakaritas :D :D :D 
            return true;
        }


        //The kitchen is being cleaned, and then the agent believes is it cleaned.
        public boolean cleanKitchen() {
            if (checkLiquid()) {
                liquidNum--;
                Literal isCleanedKitchen = Literal.parseLiteral("dirtykitchen");
                removePercept("takarito", isCleanedKitchen );
                return true;
            }
            //orderLiquid, nem történt semmi
            System.out.println("nincs folyadek, majd rendelni kell");
            Literal outOfLiquid = Literal.parseLiteral("outOfLiquid");
            addPercept("takarito", outOfLiquid);
            //TODO hiedelmek
            return false;
        }
        //The garage is being cleaned, and then the agent believes is it cleaned.
        public boolean cleanGarage() {
            if (checkLiquid()) {
                liquidNum--;
                Literal isCleanedGarage = Literal.parseLiteral("dirtygarage");
                removePercept("takarito", isCleanedGarage );
                return true;
            }
            //orderLiquid, nem történt semmi
            System.out.println("nincs folyadek, majd rendelni kell");
            Literal outOfLiquid = Literal.parseLiteral("outOfLiquid");
            addPercept("takarito", outOfLiquid);
            //TODO hiedelmek
            return false;
        }
        //The hall is being cleaned, and then the agent believes is it cleaned.
        public boolean cleanHall() {
            if (checkLiquid()) {
                liquidNum--;
                Literal isCleanedHall = Literal.parseLiteral("dirtyhall");
                removePercept("takarito", isCleanedHall );
                return true;
            }
            //orderLiquid, nem történt semmi
            System.out.println("nincs folyadek, majd rendelni kell");
            Literal outOfLiquid = Literal.parseLiteral("outOfLiquid");
            addPercept("takarito", outOfLiquid);
            //TODO hiedelmek
            return false;
        }
    }


    public class View {
        public Object execute() throws Exception {

            // get the window title
            String title = "Agensek";

            //Buttons of top panel
            final JLabel resources = new JLabel("Resources:");
            final JCheckBox electricityCB = new JCheckBox("Electricity");
            final JCheckBox waterCB = new JCheckBox("Water");
            final JCheckBox networkCB = new JCheckBox("Network");

            //Panels for the main content ( bufe, garazs.. )
            final JPanel bufePanel = new JPanel();

            //Bufe panel:
            final JButton cleanUpKitchen = new JButton("Clean the Kitchen");

            //Garazs panel:
            final JButton cleanUpGarage = new JButton("Clean the Garage");
            //Hall panel:
            final JButton cleanUpHall = new JButton("Clean the Hall");


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JPanel top = new JPanel();
            JPanel buttons = new JPanel();
            JPanel mainContent = new JPanel();

            // TABBEDPANEEXMAPLE
            JTabbedPane jtp = new JTabbedPane(1);
            JPanel jp1 = new JPanel();
            jp1.setLayout(new BoxLayout(jp1, BoxLayout.Y_AXIS));
            JPanel jp2 = new JPanel();
            JPanel jp3 = new JPanel();
            JLabel label1 = new JLabel();
            label1.setText("Kitchen Agent                               ");
            JLabel label2 = new JLabel();
            label2.setText("Garage Agent                                ");
            JLabel label3 = new JLabel();
            label3.setText("Cleaner Agent                               ");

            jp1.add(label1);
            jp1.add(cleanUpKitchen);

            jp2.add(label2);
            jp2.add(cleanUpGarage);
            jp3.add(label3);
            jp3.add(cleanUpHall);
            jtp.addTab("Kitchen", jp1);
            jtp.addTab("Garage", jp2);
            jtp.addTab("Hall", jp3);


            //BUTTONS PART
            JLabel STATUS = new JLabel("-");
            buttons.add(STATUS);

            //TOP PART
            top.add(resources);
            top.add(electricityCB);
            top.add(waterCB);
            top.add(networkCB);


            //BUFE panel
           /* bufePanel.add(orderFoods);
            bufePanel.add(order);
            bufePanel.add(cleanUpKitchen);*/
            bufePanel.add(jtp);

            //add panels to main
            mainContent.add(bufePanel);

            //MAIN PANEL
            mainPanel.add(top);
            mainPanel.add(mainContent);
            mainPanel.add(buttons);

            JFrame frame = new JFrame(title);
            frame.getContentPane().add(mainPanel);

            frame.pack();
            frame.setVisible(true);

            // add the event listeners
            cleanUpGarage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (water && network && electricity) 
                            model.makeGarageDirty();
                    System.out.println("Garage event handler pressed.");
                }
            });
            cleanUpKitchen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (water && network && electricity) 
                            model.makeKitchenDirty();
                    System.out.println("Kitchen event handler pressed.");
                }
            });
            cleanUpHall.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (water && network && electricity) 
                        model.makeHallDirty();
                    System.out.println("Hall event handler pressed.");
                }
            });

            //final JCheckBox electricityCB = new JCheckBox("Electricity");
            //final JCheckBox waterCB = new JCheckBox("Water");
            //final JCheckBox networkCB = new JCheckBox("Network");
            electricityCB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (electricityCB.isSelected()) {
                        electricity = true;
                    }
                    else {
                        electricity = false;
                    }
                }
            });

            waterCB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (waterCB.isSelected()) {
                        water = true;
                    }
                    else {
                        water = false;
                    }
                }
            });

            networkCB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (networkCB.isSelected()) {
                        network = true;
                    }
                    else {
                        network = false;
                    }
                }
            });


            return true;
        }
    }
}


