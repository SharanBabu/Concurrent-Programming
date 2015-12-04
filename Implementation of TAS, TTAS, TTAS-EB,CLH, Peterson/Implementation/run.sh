#!/bin/bash
if [ "$1" == "peterson" ]
then
	java -cp ".:./commons-math3-3.5.jar" ProgramMain_Peterson $2 $3
elif [ "$1" == "tas" ]
then
	java -cp ".:./commons-math3-3.5.jar" ProgramMain_TAS $2 $3
elif [ "$1" == "ttas" ]
then
	java -cp ".:./commons-math3-3.5.jar" ProgramMain_TTAS $2 $3
elif [ "$1" == "ttas-eb" ]
then
	java -cp ".:./commons-math3-3.5.jar" ProgramMain_TTAS_EB $2 $3
elif [ "$1" == "clh" ]
then
	java -cp ".:./commons-math3-3.5.jar" ProgramMain_CLHLock $2 $3
else
	echo "incorrect arguments"
fi
