!isCleanedKitchen. 
!isCleanedHall.
!isCleanedGarage.
!enoughLiquid.

//+!isCleanedGarage : true <- cleanGarage.
//+!isCleanedHall : true <- cleanHall.
//+!isCleanedKitchen : true <- cleanKitchen.

+isCleanedKitchen : true <- cleanKitchen; !isCleanedKitchen. 

-!isCleanedKitchen : true <- !isCleanedKitchen.

+!enoughLiquid : true <- orderLiquid.


//+!clean(ebedlo) : dirty(ebedlo) & checkLiquid > 0 <- println("Kitchen clean").
//+!cleanKitchen .



/*
+dirtykitchen[source(percept)] : true  <- cleanKitchen.
+dirtyhall[source(percept)] : true <- cleanHall.
+dirtygarage[source(percept)] : true <- cleanGarage.

+outOfLiquid[source(percept)] : true <- orderLiquid; cleanKitchen; cleanHall; cleanGarage.
*/
//+dirtyebedlo[source(percept)] : checkLiquid == 0  <- print("liquid > 0"); orderLiquid.


