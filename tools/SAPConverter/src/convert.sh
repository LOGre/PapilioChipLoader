./cp $1 $1.old
./asap2wav -R -o $1 $1.old
./lha a $1.lha $1
./mv $1.lha $1
