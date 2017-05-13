// Agent CleanBotAlfa in project gui2.mas2j

+!start : true <- .print("hello world.").

+!clean(ebedlo) : dirty(ebedlo) & checkLiquid > 0 <- println("Kitchen cleanup needed!").
+!cleanKitchen .
+!clean(garage) : dirty(garage) & checkLiquid > 0 <- println("Garage cleanup needed!").
+!cleanGarage .
+!clean(hall) : dirty(hall) & checkLiquid > 0 <- println("Hall cleanup needed!").
+!cleanHall .



+dirtykitchen[source(percept)] : true  <- cleanKitchen;.print("I have cleaned the:  " , kitchen).
+dirtyhall[source(percept)] : true <- cleanHall;.print("I have cleaned the:  " , hall).
+dirtygarage[source(percept)] : true <- cleanGarage;.print("I have cleaned the:  " , garage).

+outOfLiquid[source(percept)] : true <- orderLiquid; cleanKitchen; cleanHall; cleanGarage.

//+dirtyebedlo[source(percept)] : checkLiquid == 0  <- print("liquid > 0"); orderLiquid.

