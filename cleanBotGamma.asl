// Agent CleanBotGamma in project gui2.mas2j



+!start : true <- .print("hello world.").

+!clean(ebedlo) : dirty(ebedlo) & checkLiquid > 0 <- println("Kitchen clean").
+!cleanKitchen .

+dirtykitchen[source(percept)] : true  <- cleanKitchen;.print("I have cleaned the:  " , kitchen).
+dirtyhall[source(percept)] : true <- cleanHall;.print("I have cleaned the:  " , hall).
+dirtygarage[source(percept)] : true <- cleanGarage;.print("I have cleaned the:  " , garage).

+outOfLiquid[source(percept)] : true <- orderLiquid; cleanKitchen; cleanHall; cleanGarage.

//+dirtyebedlo[source(percept)] : checkLiquid == 0  <- print("liquid > 0"); orderLiquid.

