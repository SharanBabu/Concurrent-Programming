#!/bin/bash

echo "Peterson's lock results" >> result.txt

for numThreads in 1 2 4 8 16 32
do
	for interReqDelay in 0 20 40 60 80 100
	do 
		java -cp ".:./commons-math3-3.5.jar" ProgramMain_Peterson $numThreads $interReqDelay >> result.txt
	done
done

echo "TAS lock results" >> result.txt

for numThreads in 1 2 4 8 16 32
do
	for interReqDelay in 0 20 40 60 80 100
	do 
		java -cp ".:./commons-math3-3.5.jar" ProgramMain_TAS $numThreads $interReqDelay >> result.txt
	done
done



