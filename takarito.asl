// Agent takarito in project gui2.mas2j

/* Initial beliefs and rules */

/* Initial goals */
//+!make_it_clean(szoba): enough_liquid <- make_it_clean(szoba).
//+!make_it_clean(a).

/* Plans */
+!start : true <- .print("hello world.").

+!clean(hall)[source(self)] : true <- .print("hall takaritas").
+!clean(garazs)[source(self)] : true <- .print("garazs takaritas").
+!clean(ebedlo)[source(self)] : true <- .print("ebedlo takaritas").



//TODO
//Épület takarítása( bejöv? kérés)

+!kqml_received(A,B,C,_) : B == tell & checkLiquid > 0 <- .print("takaritani kell a szobat: " , C);
								!clean( C ).

//Készenléti állapot (töltés alatt)
//Takarító szerek nyilvántartása /rendelése
//nap végi takarítás ( id? szimlácio)

//GUI 
//kijelezzük ha:
//valamelyik épületbe takarítás kell ( kiküldött robot azonosítója, a takarított helység neve)id?tartam pl 5perc 
//visszatérés jelzése (takarításból)
//Minden robot a takarításnál pl 1 egység takarítószert használ fel. Ha kifogyy a raktárból automatikus rendelés leadás(kiírjuk hogy rendelünk )
//
