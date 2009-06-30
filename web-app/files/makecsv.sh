#!/bin/bash
#	This script will create a comma delimted file by parsing output 
#	from tnt, generated by the statepairs script. 
echo -n "Source/Sink"
sed -e '1d' -e 's/,.*//' $1  | \
while read line 
do 
echo -n ",${line}"
done
echo
#Sources in rows, sinks in columns
sed -e '1d' -e 's/,.*//' $1  | \
while read currentlocal
do
echo -n "${currentlocal}"
sed -e '1d' -e 's/,.*//' $1  | \
while read currentsink
do
if [ $currentlocal != $currentsink ] 
then
stuff=`echo -n "change${currentsink}_to_${currentlocal}.txt"`
echo -n ,`cat ${stuff} | sed '/Char/!d' | grep -o "\-[0-9]\{1,\}" | sed 's/-//g' `
#echo $stuff
else 
echo -n ",0"
fi
done
echo
done
