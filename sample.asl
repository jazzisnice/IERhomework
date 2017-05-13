/* Initial goals */


/* Plans */



// the event !run is created by the GUI
//+!run(X) <- .print("running ",X,"...."); .wait(500); !!run(X).


// the event !stop is created by the GUI
//+!stop(X) <- .drop_desire(run(X)); .print("stop ",X).


+!takaritas(garazs) : true <- .send(takarito,tell,garazs); .print("megmondtam neki hogy takaritson:  " , garazs).
+!takaritas(ebedlo) : true <- .send(takarito,tell,ebedlo); .print("megmondtam neki hogy takaritson:  " , ebedlo).
+!takaritas(hall) : true <- .send(takarito,tell,hall); .print("megmondtam neki hogy takaritson:  " , hall).

+szia[source(A)] : true <- .print(A," udvozol"); .send(A,tell,szia).

+!printTime(A,B,C) : true <- .print("Az ido:" , A , ":" , B , ":" , C).
