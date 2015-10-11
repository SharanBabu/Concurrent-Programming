import subprocess
import time

def compile_java(java_file):
    subprocess.check_call(['javac','-cp', '.;./commons-math3-3.5.jar', java_file])

def execute_java(nTh, iRD):
    cmd = ['java','-cp', '.;./commons-math3-3.5.jar', 'ProgramMain', str(nTh), str(iRD)]
    proc = subprocess.Popen(cmd)
    
compile_java('*.java')
i =1;
for numThreads in range(1, 9):
    for interReqDelay in range(0, 11):
        for k in range(1, 11):
            execute_java(numThreads, interReqDelay*10)
            time.sleep(interReqDelay)
        print('\n\n' + str(i) + ' done ' + str(88-i) + ' remaining')
        i = i + 1