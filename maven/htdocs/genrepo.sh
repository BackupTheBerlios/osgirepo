#! /bin/bash

# usage: genrepo.sh <dir> 

DIR=../repository

if [ -n "$1" ]; then DIR=$1; fi

cat generation/repoheader > repository.xml
echo `date` >> repository.xml
cat generation/repodate >> repository.xml

find $DIR -name *.obr -print| xargs cat >> repository.xml

cat generation/repofooter >> repository.xml

