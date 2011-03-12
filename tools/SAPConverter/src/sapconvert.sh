cp $1 $1.old
./asap2wav -R -o R_$1 $1
lha a $1.lha $1
mv $1.lha $1
rm R_$1
