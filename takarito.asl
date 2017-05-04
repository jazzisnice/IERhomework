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
//�p�let takar�t�sa( bej�v? k�r�s)

+!kqml_received(A,B,C,_) : B == tell <- .print("takaritani kell a szobat: " , C);
								!clean(C).

//K�szenl�ti �llapot (t�lt�s alatt)
//Takar�t� szerek nyilv�ntart�sa /rendel�se
//nap v�gi takar�t�s ( id? sziml�cio)

//GUI 
//kijelezz�k ha:
//valamelyik �p�letbe takar�t�s kell ( kik�ld�tt robot azonos�t�ja, a takar�tott helys�g neve)id?tartam pl 5perc 
//visszat�r�s jelz�se (takar�t�sb�l)
//Minden robot a takar�t�sn�l pl 1 egys�g takar�t�szert haszn�l fel. Ha kifogyy a rakt�rb�l automatikus rendel�s lead�s(ki�rjuk hogy rendel�nk )
//
