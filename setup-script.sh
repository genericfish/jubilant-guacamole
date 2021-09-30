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
        sed 's/,/\//g' $f | sed 's/\t/,/g' >> ${clean_path}
    fi

    unset filename
    unset clean_path
done