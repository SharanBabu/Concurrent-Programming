#!/bin/bash

echo "TTAS lock results" >> result.txt

for numThreads in 1 2 4 8 16 32
do
	for interReqDelay in 0 20 40 60 80 100
	do 
		java -cp ".:./commons-math3-3.5.jar" ProgramMain_TTAS $numThreads $interReqDelay >> result.txt
	done
done

echo "TTAS_EB lock results" >> result.txt

for numThreads in 1 2 4 8 16 32
do
	for interReqDelay in 0 20 40 60 80 100
	do 
		java -cp ".:./commons-math3-3.5.jar" ProgramMain_TTAS_EB $numThreads $interReqDelay >> result.txt
	done
done

echo "CLH lock results" >> result.txt

for numThreads in 1 2 4 8 16 32
do
	for interReqDelay in 0 20 40 60 80 100
	do 
		java -cp ".:./commons-math3-3.5.jar" ProgramMain_CLHLock $numThreads $interReqDelay >> result.txt
	done
done
