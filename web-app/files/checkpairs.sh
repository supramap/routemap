if [[ -f missingfiles.txt ]] || [[ -f missing.script ]]
then
echo deleting stuff
rm missingfiles.txt missing.script
fi
sed -e '1d' -e 's/,.*//' $1  | \
while read hurf 
do sed -e '1d' -e 's/,.*//' $1  | \
while read durf 
do
if [ $hurf != $durf ]
then 
if [ ! -f change${hurf}_to_${durf}.txt ]
then
echo change${hurf}_to_${durf}.txt >> missingfiles.txt
fi
fi
done
done 
if [ ! -f missingfiles.txt ]
then
echo A-ok!
else
echo There were missing files
cat missingfiles.txt | \
while read line
        do
        source=`echo ${line} | sed -e 's/change//' -e 's/_.*$'//`
        sink=`echo ${line} | sed -e 's/^.*to_//' -e 's/\.txt$//'`
        echo "log ${line} ;  change ]./0/${source} ${sink} ; log ;" \
        >> missing.script
        done
exit
fi
echo Please go on
