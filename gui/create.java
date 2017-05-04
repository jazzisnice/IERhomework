package gui;

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

/** internal action that creates a simple GUI with two buttons that trigger AS plans */
public class create extends DefaultInternalAction {

    int runCount = 0;
    
    public void printTime(final TransitionSystem ts) {
        Thread th = new Thread() {
            public void run() {
                while(true) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH,mm,ss");
                    ts.getC().addAchvGoal(Literal.parseLiteral("printTime(" + sdf.format(cal.getTime()) + ")"), null);
                    try {
                        Thread.sleep(10000);
                    } 
                    catch(InterruptedException e) {
                        System.out.print("exception");
                    }
                }
            }
        };
        th.start();
    } 

    @Override
    public Object execute(final TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        
        // get the window title
        String title = ((StringTerm)args[0]).getString();

        printTime(ts);


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
            public void actionPerformed(ActionEvent e) {
                // creates a new event +!run so that the agent can react to the button
                runCount++;
                ts.getC().addAchvGoal(Literal.parseLiteral("takaritas(garazs)"), null);
                STATUS.setText(ts.getC().toString());
            }
        });
        cleanUpKitchen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // creates a new event +!run so that the agent can react to the button
                runCount++;
                ts.getC().addAchvGoal(Literal.parseLiteral("takaritas(ebedlo)"), null);
            }
        });
        cleanUpHall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // creates a new event +!run so that the agent can react to the button
                runCount++;
                ts.getC().addAchvGoal(Literal.parseLiteral("takaritas(hall)"), null);
            }
        });
        return true;
    }
}
