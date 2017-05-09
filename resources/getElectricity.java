// Internal action code for project gui2.mas2j



package resources;



import jason.*;

import jason.asSemantics.*;

import jason.asSyntax.*;



public class getElectricity extends DefaultInternalAction {

    public int liquidNum = 10;

    @Override

    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {

        // execute the internal action
        //TODO is there electricity?
        return true;
    }
}


