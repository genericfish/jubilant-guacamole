#!/bin/bash

# Sanitize data
DATA_PATH="${1-data}"
DATA_CLEAN_PATH="${DATA_PATH}/clean"

# Make new "clean" directory if not exists in DATA_PATH
mkdir -p ${DATA_CLEAN_PATH}

# Clean all files
for f in $DATA_PATH/*; do
    if [[ ${f##*.} == "csv" ]]; then
        filename="${f##*/}"
        clean_path="${DATA_CLEAN_PATH}/${filename}"

        # Replace commas with forward slashes to preserve lists
        # Replace tabs with commas to get into CSV format
        # Save to DATA_CLEAN_PATH/filename-clean.csv
        head -n1 $f | sed 's/\t/,/g' | sed 's/.//' > ${clean_path}
        tail -n+2 $f | sed 's/,/\//g' | awk -F"\t" '{FNR > 1; OFS=","; $1=""; print $0}' | sed 's/^,//g' >> ${clean_path}
    fi

    unset filename
    unset clean_path
done

# Remove duplicates in titleId column in titles.csv
awk -F"," '!seen[$1]++' $DATA_CLEAN_PATH/titles.csv > $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/titles.csv

# Remove duplicates in nconst column in principals.csv
awk -F"," '!seen[$2]++' $DATA_CLEAN_PATH/principals.csv > $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/principals.csv

# Remove duplicates in nconst column in names.csv
awk -F"," '!seen[$1]++' $DATA_CLEAN_PATH/names.csv > $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/names.csv

# Make sure crew.csv always has enough columns
awk -F"," '{ if(NF == 2) print $0","; else if(NF == 1) print $0",,"; else print $0; }' $DATA_CLEAN_PATH/crew.csv > $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/crew.csv

# Remove all bad data from titles.csv
# sed 's/,,/,NA,/g' $DATA_CLEAN_PATH/titles.csv > $DATA_CLEAN_PATH/temp.csv
awk -F"," 'NF == 10 { print $0 }' $DATA_CLEAN_PATH/titles.csv > $DATA_CLEAN_PATH/temp2.csv
mv $DATA_CLEAN_PATH/temp2.csv $DATA_CLEAN_PATH/titles.csv


echo "key,titleId,nconst,category,job,characters" > $DATA_CLEAN_PATH/temp.csv
awk -F"," 'NR > 1 {print NR-1","$0;}' $DATA_CLEAN_PATH/principals.csv >> $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/principals.csv

echo "key,customerId,rating,date,titleId" > $DATA_CLEAN_PATH/temp.csv
awk -F"," 'NR > 1 {print NR-1","$0;}' $DATA_CLEAN_PATH/customer_ratings.csv >> $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/customer_ratings.csv

PSQL_FLAGS="-h csce-315-db.engr.tamu.edu -U csce315_913_4_user -d csce315_913_4_db"

psql -c "CREATE TABLE IF NOT EXISTS crew(titleId TEXT PRIMARY KEY,directors TEXT,writers TEXT);" $PSQL_FLAGS
psql -c "CREATE TABLE IF NOT EXISTS titles(titleId TEXT PRIMARY KEY,titleType TEXT,originalTitle TEXT, startYear TEXT, endYear TEXT, runtimeMinutes TEXT, genres TEXT, Year TEXT, averageRating TEXT, numVotes TEXT);" $PSQL_FLAGS
psql -c "CREATE TABLE IF NOT EXISTS customerRatings(key TEXT PRIMARY KEY, customerId TEXT, rating TEXT, date TEXT, titleId TEXT);" $PSQL_FLAGS
psql -c "CREATE TABLE IF NOT EXISTS names(nconst TEXT,primaryName TEXT, birthYear TEXT, deathYear TEXT, primaryProfession TEXT);" $PSQL_FLAGS
psql -c "CREATE TABLE IF NOT EXISTS principals(key TEXT PRIMARY KEY, titleId TEXT, nconst TEXT, category TEXT, job TEXT, characters TEXT);" $PSQL_FLAGS

psql -c "\copy crew from 'data/clean/crew.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy titles from 'data/clean/titles.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy customerRatings from 'data/clean/customer_ratings.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy names from 'data/clean/names.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy principals from 'data/clean/principals.csv' CSV HEADER" $PSQL_FLAGS
