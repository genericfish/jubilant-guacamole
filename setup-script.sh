#!/bin/bash

################################################
# PART 1: Process the input data from data.zip #
################################################

DATA_PATH="${1-data}"
DATA_CLEAN_PATH="${DATA_PATH}/clean"

rm -rf ${DATA_CLEAN_PATH}

if [ ! -d "$DATA_PATH" ]; then
    echo "Invalid path \"${DATA_PATH}\" specified."
    exit 1
fi

# Make new "clean" directory if not exists in DATA_PATH
mkdir -p ${DATA_CLEAN_PATH}

# We depend on GNU extensions to awk, check for awk before falling back to awk
AWK_CMD="gawk"

if ! command -v gawk > /dev/null; then
    AWK_CMD="awk"
fi

# Clean all files
for f in $DATA_PATH/*; do
    if [[ ${f##*.} == "csv" ]]; then
        filename="${f##*/}"
        clean_path="${DATA_CLEAN_PATH}/${filename}"

        # Replace commas with forward slashes to preserve lists
        # Replace tabs with commas to get into CSV format, and remove first column of junk data
        # Remove leading commas if they exist
        # Save to $clean_path
        cat $f | sed 's/,/\//g' | $AWK_CMD -F'\t' '{FNR > 1; OFS=","; $1=""; print $0}' | sed 's/^,//g' >> ${clean_path}
    fi

    unset filename
    unset clean_path
done

# do awk command, first parameter is awk command, second is filename
dawk () {
    $AWK_CMD -F, "$1" $DATA_CLEAN_PATH/$2 >> $DATA_CLEAN_PATH/temp.csv
    mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/$2
}

# Remove duplicates in titleId column in titles.csv
dawk '!seen[$1]++' titles.csv

# Remove duplicates in nconst column in principals.csv
dawk '!seen[$2]++' principals.csv

# Remove duplicates in nconst column in names.csv
dawk '!seen[$1]++' names.csv

# Ensure crew.csv always has 3 columns
dawk 'BEGIN{OFS=","} NF=3' crew.csv

# Delete any rows from titles.csv that do not have exactly 10 columns
dawk 'NF == 10' titles.csv

# Prepend a key column into principals.csv and customer_ratings.csv to act as primary key
echo "key,titleId,nconst,category,job,characters" > $DATA_CLEAN_PATH/temp.csv
awk -F"," 'NR > 1 {print NR-1","$0;}' $DATA_CLEAN_PATH/principals.csv >> $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/principals.csv

echo "key,customerId,rating,date,titleId" > $DATA_CLEAN_PATH/temp.csv
awk -F"," 'NR > 1 {print NR-1","$0;}' $DATA_CLEAN_PATH/customer_ratings.csv >> $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/customer_ratings.csv
#########################################
# PART 2: Upload clean data to database #
#########################################

PSQL_FLAGS="-h csce-315-db.engr.tamu.edu -U csce315_913_4_user -d csce315_913_4_db"

psql -c "CREATE TABLE IF NOT EXISTS crew(titleId TEXT PRIMARY KEY,directors TEXT,writers TEXT);CREATE TABLE IF NOT EXISTS titles(titleId TEXT PRIMARY KEY,titleType TEXT,originalTitle TEXT, startYear INTEGER, endYear INTEGER, runtimeMinutes INTEGER, genres TEXT, Year INTEGER, averageRating REAL, numVotes TEXT);CREATE TABLE IF NOT EXISTS customerRatings(key TEXT PRIMARY KEY, customerId TEXT, rating REAL, date DATE, titleId TEXT);CREATE TABLE IF NOT EXISTS names(nconst TEXT,primaryName TEXT, birthYear INTEGER, deathYear INTEGER, primaryProfession TEXT);CREATE TABLE IF NOT EXISTS principals(key TEXT PRIMARY KEY, titleId TEXT, nconst TEXT, category TEXT, job TEXT, characters TEXT);" $PSQL_FLAGS

psql -c "\copy crew from 'data/clean/crew.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy titles from 'data/clean/titles.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy customerratings from 'data/clean/customer_ratings.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy names from 'data/clean/names.csv' CSV HEADER" $PSQL_FLAGS
psql -c "\copy principals from 'data/clean/principals.csv' CSV HEADER" $PSQL_FLAGS
