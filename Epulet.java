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



public class Epulet extends Environment {

    public static final boolean network = true;
    public static final boolean water = true;
    public static final boolean electricity = true;

    public static final Term checkLiquid = Literal.parseLiteral("checkLiquid");
    public static final Term orderLiquid = Literal.parseLiteral("orderLiquid");
    public static final Term cleanRoom =   Literal.parseLiteral("cleanRoom(room)"); //szol a fononek hogy kuldjon takaritot
    public static final Term checkAgent = Literal.parseLiteral("checkAgent"); //ugynokok lekerdezese
    public static final Term sendAgent =   Literal.parseLiteral("sendAgent(agent)"); //clean Room hatasara lekerdezi van e szabad ugynok ha igen kikuldi
    public static final Term createFood = Literal.parseLiteral("createFood");
    // TODO ???? check volt e kosz kaja  keszites kozben

    private Logger logger = Logger.getLogger("gui2.mas2j."+Epulet.class.getName());

    public Model model = new Model();

    /** Called before the MAS execution with the args informed in .mas2j */

    @Override

    public void init(String[] args) {

        super.init(args);

    }



    @Override

    public boolean executeAction(String agName, Structure action) {

        System.out.println("executing: "+action+", but not implemented!");


        try {
            if (action.equals(checkLiquid)) {
                return model.checkLiquid();
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
        public  final int liquidNum = 10;
        public  final List<String> busy = new LinkedList<String>();
        public  final List<String> available = Arrays.asList("Zsombor", "Jeni", "Szipu");

        public Model() {
            super(0,0,0);
        }

        public boolean checkLiquid() {
            if (liquidNum > 0) {
                return true;
            }
            return false;
        }

    }

}


