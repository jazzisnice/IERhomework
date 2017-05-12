+!start : true <- .print("hello world.").

+!clean(ebedlo) : dirty(ebedlo) & checkLiquid > 0 <- println("Kitchen clean").

+dirtyebedlo[source(percept)] : checkLiquid > 0  <- print("liquid > 0"); cleanRoom.

+dirtyebedlo[source(percept)] : checkLiquid == 0  <- print("liquid > 0"); orderLiquid.


