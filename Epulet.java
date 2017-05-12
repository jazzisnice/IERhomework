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

    public static final boolean network = true;
    public static final boolean water = true;
    public static final boolean electricity = true;

    public static final Term checkLiquid = Literal.parseLiteral("checkLiquid");
    public static final Term orderLiquid = Literal.parseLiteral("orderLiquid");
    public static final Term cleanKitchen =   Literal.parseLiteral("cleanKitchen"); //szol a fononek hogy kuldjon takaritot
    public static final Term checkAgent = Literal.parseLiteral("checkAgent"); //ugynokok lekerdezese
    public static final Term sendAgent =   Literal.parseLiteral("sendAgent(agent)"); //clean Room hatasara lekerdezi van e szabad ugynok ha igen kikuldi
    public static final Term createFood = Literal.parseLiteral("createFood");
    // TODO ???? check volt e kosz kaja  keszites kozben

    private Logger logger = Logger.getLogger("gui2.mas2j."+Epulet.class.getName());

    public Model model = new Model();
    public View view = new View();

    /** Called before the MAS execution with the args informed in .mas2j */

    @Override

    public void init(String[] args) {

        super.init(args);

        Literal isCleanedKitchen = Literal.parseLiteral("cleaned(kitchen)");
        Literal isCleanedGarage = Literal.parseLiteral("cleaned(garage)");
        Literal isCleanedHall = Literal.parseLiteral("cleaned(hall)");

        addPercept("takarito" , isCleanedKitchen);
        addPercept("takarito" ,isCleanedGarage);
        addPercept("takarito" ,isCleanedHall);
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
                System.out.println("liquid check");
                return model.checkLiquid();
            } 
            if (action.equals(orderLiquid)) {
                System.out.println("liquid order");
                return model.orderLiquid();
            }
            if (action.equals(cleanKitchen)) {
                System.out.println("clean room");
                return model.cleanKitchen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return true; // the action was executed with success 

    }



    /** Called before the end of MAS execution */

    @Override

    public void stop() {

        super.stop();

    }

    class Model extends GridWorldModel {
        public int liquidNum = 10;
        public  final List<String> busy = new LinkedList<String>();
        public  final List<String> available = Arrays.asList("Zsombor", "Jeni", "Szipu");

        public final List<String> rooms = Arrays.asList("kitchen", "garage", "hall");



        public Model() {
            super(0,0,0);
        }

        //The gui calls this
        public void makeKitchenDirty() {
            Literal isCleanedKitchen = Literal.parseLiteral("dirtykitchen");
            addPercept("takarito", isCleanedKitchen);
        }

        public boolean checkLiquid() {
            System.out.println("In checkLiquid");
            if (liquidNum > 0) {
                return true;
            }
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

    }


    public class View {
        public Object execute() throws Exception {
            
            // get the window title
            String title = "Agensek";

            //Buttons of top panel
            final JLabel resources = new JLabel("Eroforrasok:");
            final JCheckBox electricityCB = new JCheckBox("Aram");
            final JCheckBox waterCB = new JCheckBox("Viz");
            final JCheckBox networkCB = new JCheckBox("Halozat");

            //Panels for the main content ( bufe, garazs.. )
            final JPanel bufePanel = new JPanel();

            //Bufe panel:
            final JButton cleanUpKitchen = new JButton("Konyha Takaritas");

            //Garazs panel:
            final JButton cleanUpGarage = new JButton("Garazs Takaritas");
            //Hall panel:
            final JButton cleanUpHall = new JButton("Hall Takaritas");
            

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
            label1.setText("BUFE AGENS                               ");
            JLabel label2 = new JLabel();
            label2.setText("GARAZS AGENS                                ");
            JLabel label3 = new JLabel();
            label3.setText("TAKARITO AGENS                               ");
            
            jp1.add(label1);
            jp1.add(cleanUpKitchen);

            jp2.add(label2);
            jp2.add(cleanUpGarage);
            jp3.add(label3);
            jp3.add(cleanUpHall);
            jtp.addTab("BUFE", jp1);
            jtp.addTab("GARAZS", jp2);
            jtp.addTab("HALL", jp3);


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
                public void actionPerformed(ActionEvent e) {/*
                    // creates a new event +!run so that the agent can react to the button
                    runCount++;
                    ts.getC().addAchvGoal(Literal.parseLiteral("takaritas(garazs)"), null);
                    STATUS.setText(ts.getC().toString());*/
                }
            });
            cleanUpKitchen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    model.makeKitchenDirty();
                    System.out.println("Kitchen event handler pressed.");
                }
            });
            cleanUpHall.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {/*
                    // creates a new event +!run so that the agent can react to the button
                    runCount++;
                    ts.getC().addAchvGoal(Literal.parseLiteral("takaritas(hall)"), null);*/
                }
            });
            return true;
        }
    }
}


