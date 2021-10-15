WITH Dataset AS (
    SELECT B.titleid, B.actors, C.nconst AS director
    FROM (
        SELECT A.titleid, STRING_AGG(principals.nconst,',') AS actors
        FROM (
            SELECT * FROM principals
            WHERE nconst='%s' AND (
                category='actor' OR
                category='actress'
            )
        )A
        INNER JOIN principals
        ON A.titleid=principals.titleid
        WHERE (
            principals.category='actor' OR
            principals.category='actress'
        ) AND principals.nconst <> A.nconst
        GROUP BY A.titleid
    )B
    INNER JOIN
    (
        SELECT * FROM principals
        WHERE category='director'
    )C
    ON C.titleid=B.titleid
),
ActorTitles AS (
    SELECT Z.actors, STRING_AGG(titleid, ',') AS titles
    FROM (
        SELECT DISTINCT UNNEST(STRING_TO_ARRAY(STRING_AGG(actors,','), ',')) AS actors
        FROM Dataset
    )Z
    INNER JOIN principals
    ON Z.actors=nconst
    WHERE category='actor' OR category='actress'
    GROUP BY Z.actors
),
DirectorTitles AS (
    SELECT Z.titles, ARRAY_AGG(nconst) AS directors
    FROM (
        SELECT DISTINCT UNNEST(STRING_TO_ARRAY(STRING_AGG(titles,','), ',')) AS titles
        FROM ActorTitles
    )Z
    INNER JOIN principals
    ON Z.titles=titleid
    WHERE category='director'
    GROUP BY Z.titles
),
IndirectDirector AS (
    SELECT titles, ARRAY_TO_STRING(directors, ',') as directors
    FROM DirectorTitles
    WHERE NOT EXISTS (
        SELECT * FROM (
            SELECT DISTINCT STRING_TO_ARRAY(STRING_AGG(director,','), ',') AS blacklist
            FROM Dataset
        )Z
        WHERE blacklist && DirectorTitles.directors
    )
)

SELECT primaryname, appearances
FROM (
    SELECT Z.director, COUNT(*) AS appearances
    FROM (
        SELECT UNNEST(STRING_TO_ARRAY(directors, ',')) AS director
        FROM IndirectDirector
    )Z
    GROUP BY Z.director
    ORDER BY appearances DESC
)Y
INNER JOIN names
ON director=nconst;