
use oncourse;

TRUNCATE TABLE course_package;

INSERT INTO course_package
VALUES (NULL,1,"PDF", 1, "./files/McDonald.pdf", "FULL");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 2, "./files/1.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 2, "./files/2.pdf", "RIGHT");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 3, "./files/3.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,1,"YOUTUBE", 3, "-epDdKaLPN0", "TR");

INSERT INTO course_package
VALUES (NULL,1,"YOUTUBE", 3, "c170GdSKkyg", "BR");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 4, "./files/4.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 4, "./files/5.pdf", "RIGHT");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 5, "./files/6.pdf", "RIGHT");

INSERT INTO course_package
VALUES (NULL,1,"MP3", 5, "./files/style.mp3", "TL");

INSERT INTO course_package
VALUES (NULL,1,"YOUTUBE", 5, "dtm_tIkEbMc", "BL");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 6, "./files/7.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,1,"PDF", 6, "./files/8.pdf", "RIGHT");
