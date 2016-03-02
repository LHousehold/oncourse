
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





INSERT INTO course_package
VALUES (NULL,2,"PDF", 1, "./jsftut/title.pdf", "FULL");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 2, "./jsftut/page1_1.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 2, "./jsftut/page1_2.pdf", "RIGHT");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 3, "./jsftut/page2_1.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 3, "./jsftut/page2_2.pdf", "RIGHT");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 4, "./jsftut/page2_3.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,2,"YOUTUBE", 4, "Q0WyiOfOGCc", "TR");

INSERT INTO course_package
VALUES (NULL,2,"YOUTUBE", 4, "IaMgMVO765o", "BR");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 5, "./jsftut/page3_1.pdf", "LEFT");

INSERT INTO course_package
VALUES (NULL,2,"PDF", 5, "./jsftut/page3_2.pdf", "RIGHT");


INSERT INTO course_package_section
VALUES (NULL,2,1,"Title","section",1);

INSERT INTO course_package_section
VALUES (NULL,2,2,"1. JSP Overview","section",2);

INSERT INTO course_package_section
VALUES (NULL,2,2.1,"Why use JSP?","subsection",2);

INSERT INTO course_package_section
VALUES (NULL,2,2.2,"Advantages of JSP","subsection",2);

INSERT INTO course_package_section
VALUES (NULL,2,3,"2. JSP Environment Setup","section",3);

INSERT INTO course_package_section
VALUES (NULL,2,3.1,"Setting up Java Development Kit","subsection",3);

INSERT INTO course_package_section
VALUES (NULL,2,3.2,"Setting up Web Server: Tomcat","subsection",3);


INSERT INTO course_package_name
VALUES (NULL,"McDonald's Nutrition","MCD100",1);

INSERT INTO course_package_name
VALUES (NULL,"Introduction to JSP","JSP100",2);
