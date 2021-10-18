WITH Dataset AS (
    SELECT
        A.nconst AS Actor,
        C.primaryname AS ActorName,
        A.titleid AS Content,
        averagerating AS Rating
    FROM principals AS A
    INNER JOIN titles AS B
        ON A.titleid=B.titleid
    INNER JOIN names AS C
        ON A.nconst=C.nconst
    WHERE
        A.category='actor' OR
        A.category='actress'
)

SELECT
    A.ActorName AS Person1,
    B.ActorName AS Person2,
    COUNT(*) AS Appearances,
    ROUND(10.0 * (COUNT(CASE WHEN A.Rating >= 6.95 THEN 1.0 END) + 1.0) / (COUNT(*) + 2.0), 2) AS Rating
FROM Dataset AS A
JOIN Dataset AS B
ON A.Actor < B.Actor
WHERE A.Content = B.Content
GROUP BY Person1, Person2
ORDER BY
    Rating DESC,
    Appearances DESC
LIMIT 50;
