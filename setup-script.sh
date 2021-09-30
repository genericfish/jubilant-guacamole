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
        tail -n+2 $f | sed 's/,/\//g' | awk '{FNR > 1; OFS=","; $1=""; print $0}' | sed 's/^,//g' >> ${clean_path}
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

# Remove duplicates in nconst column
awk -F"," '!seen[$1]++' $DATA_CLEAN_PATH/names.csv > $DATA_CLEAN_PATH/temp.csv
mv $DATA_CLEAN_PATH/temp.csv $DATA_CLEAN_PATH/names.csv
