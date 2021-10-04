#!/bin/bash 
PSQL_FLAGS="-h csce-315-db.engr.tamu.edu -U csce315_913_4_user -d csce315_913_4_db" 
psql -c "SELECT * FROM customerratings LIMIT 20;"  $PSQL_FLAGS
psql -c "SELECT * FROM titles LIMIT 4;"  $PSQL_FLAGS
psql -c "SELECT * FROM crew LIMIT 10;"  $PSQL_FLAGS
psql -c "SELECT * FROM principals LIMIT 5;"  $PSQL_FLAGS
psql -c "SELECT * FROM names LIMIT 2;"  $PSQL_FLAGS
psql -c "SELECT MIN(rating) FROM customerratings;"  $PSQL_FLAGS
psql -c "SELECT MAX(titleId) FROM titles;"  $PSQL_FLAGS
psql -c "SELECT MAX(directors) FROM crew;"  $PSQL_FLAGS
psql -c "SELECT MIN(nconst) FROM principals;"  $PSQL_FLAGS
psql -c "SELECT MAX(birthYear) FROM names;"  $PSQL_FLAGS
psql -c "SELECT COUNT(*) FROM customerratings;"  $PSQL_FLAGS
psql -c "SELECT COUNT(*) FROM titles;"  $PSQL_FLAGS
psql -c "SELECT COUNT(*) FROM crew;"  $PSQL_FLAGS
psql -c "SELECT COUNT(*) FROM principals;"  $PSQL_FLAGS
psql -c "SELECT COUNT(*) FROM names;"  $PSQL_FLAGS
psql -c "SELECT * FROM customerratings LIMIT 30;"  $PSQL_FLAGS
psql -c "SELECT * FROM titles LIMIT 50;"  $PSQL_FLAGS
psql -c "SELECT * FROM crew LIMIT 40;"  $PSQL_FLAGS
psql -c "SELECT * FROM principals LIMIT 30;"  $PSQL_FLAGS
psql -c "SELECT * FROM names LIMIT 20;"  $PSQL_FLAGS