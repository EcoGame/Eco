echo compiREBORN
echo Returning from the ashes to live another day…
cd ~/Documents/eco
echo Compiling...
javac -cp .:../lib/* src/**/*.java
clear
echo Compiled...
cd ~/Documents/eco/src
sleep 1
echo Running…
clear
java -cp .:../../lib/* -Djava.library.path=../native eco/Main
