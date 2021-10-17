WITH Dataset AS (
    SELECT 
        customerratings.titleid AS title,
        titles.genres AS genre
    FROM customerratings
    INNER JOIN titles
    ON customerratings.titleid=titles.titleid
    WHERE customerid = '%s' AND rating > 3 
    ORDER BY date DESC, key DESC
),
Genres AS (
    SELECT UNNEST(STRING_TO_ARRAY(STRING_AGG(genre,'/'), '/')) AS genre
    FROM Dataset
),
FavoriteGenre AS (
    SELECT genre, COUNT(genre) AS occurences
    FROM Genres
    GROUP BY genre
),
TopGenres AS (
    SELECT * FROM FavoriteGenre ORDER BY FavoriteGenre.occurences DESC LIMIT 5
),

History AS (
    SELECT * FROM customerratings where customerid = '%s'
),

Popular AS (
    SELECT count, titles.titleid, STRING_TO_ARRAY(titles.genres, '/') AS genre FROM 
    (SELECT titleid, COUNT(*) FROM customerratings GROUP BY titleid)T 
    INNER JOIN titles 
    ON T.titleid=titles.titleid 
    WHERE T.titleid not in (select titleid FROM History)
    ORDER BY count DESC
),

Recommendations AS (
    SELECT titles.originaltitle, titles.genres, titles.year, titles.runtimeminutes, titles.titleid FROM Popular
    INNER JOIN titles
    ON titles.titleid = Popular.titleid
    WHERE EXISTS (
        SELECT * FROM (
        SELECT ARRAY_AGG(genre) AS fave FROM TopGenres
        ) R
        WHERE fave && Popular.genre
    )
)


SELECT * FROM Recommendations;