// Agent takarito in project gui2.mas2j

/* Initial beliefs and rules */

/* Initial goals */
//+!make_it_clean(szoba): enough_liquid <- make_it_clean(szoba).
//+!make_it_clean(a).
+!clean(garazs).
+!clean(hall).
+!clean(ebedlo).
+!enough_liquid.

/* Plans */
+!start : true <- .print("hello world.").

+!clean(hall) : resources.getLiquidNum <- .print("hall takaritas").
+!clean(garazs) : enough_liquid <- .print("garazs takaritas").
+!clean(ebedlo) : enough_liquid <- .print("ebedlo takaritas").

//TODO
//Épület takarítása( bejöv? kérés)

+!kqml_received(A,B,C,_) : B == tell <- .print("takaritani kell a szobat: " , C);
								!clean(C).

//Készenléti állapot (töltés alatt)
//Takarító szerek nyilvántartása /rendelése
//nap végi takarítás ( id? szimlácio)

//GUI 
//kijelezzük ha:
//valamelyik épületbe takarítás kell ( kiküldött robot azonosítója, a takarított helység neve)id?tartam pl 5perc 
//visszatérés jelzése (takarításból)
//Minden robot a takarításnál pl 1 egység takarítószert használ fel. Ha kifogyy a raktárból automatikus rendelés leadás(kiírjuk hogy rendelünk )
//
