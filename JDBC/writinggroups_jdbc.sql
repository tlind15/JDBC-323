--Writing Groups Table

CREATE TABLE WritingGroups (

	GroupName VARCHAR (30) NOT NULL,

	HeadWriter VARCHAR(30),

	YearFormed INTEGER, 

	Subject VARCHAR(20),



	CONSTRAINT writinggroups_pk 

	PRIMARY KEY (GroupName)

);



--Publishers Table

CREATE TABLE Publishers (

	PublisherName VARCHAR(50) NOT NULL,

	PublisherAddress VARCHAR(30), 

	PublisherPhone VARCHAR(15),

	PublisherEmail VARCHAR(30),



	CONSTRAINT publishers_pk

	PRIMARY KEY (PublisherName)

);



--Books Table

CREATE TABLE Books (

	GroupName VARCHAR(30) NOT NULL,

	BookTitle VARCHAR(75) NOT NULL,

	PublisherName VARCHAR(50) NOT NULL,

	YearPublished INTEGER, 

	NumberPages INTEGER, 



    CONSTRAINT books_pk

	PRIMARY KEY (GroupName, BookTitle)

);



--Writing Groups to Books FK

ALTER TABLE Books

    ADD CONSTRAINT writinggroups_books_fk

    FOREIGN KEY (GroupName)

    REFERENCES WritingGroups (GroupName);



--Publishers to Books FK

ALTER TABLE Books

    ADD CONSTRAINT publishers_books_fk

    FOREIGN KEY (PublisherName)

    REFERENCES Publishers (PublisherName);



--Books CK

ALTER TABLE Books

    ADD CONSTRAINT booktitle_pubname_ck

    UNIQUE (BookTitle, PublisherName);


--Publishers data entry

INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)
VALUES ('NARUTO PUBLISHING', '323 DAVE BROWN WAY', '562-985-4285', 'NARUTO@WILDTHINGS.NET'),
('UNIVERSAL', '100 UNIVERSAL CITY PLZ.', '800-864-8377', 'UNIVERSAL@HOLLYWOOD.COM')
,('ER PUBLICATIONS', '11 UML AVE.', '562-985-4285', 'MANY2MANY@CARDINALITY.COM')
,('TARDIS PUBLICATIONS', 'ALL OF SPACE AND TIME', '362-867-0946', 'TIMEY-WIMEY@UNIT.ORG')
;

--Writing Groups data entry

INSERT INTO WritingGroups (GroupName, HeadWriter, YearFormed, Subject)
VALUES ('JACKSON', 'KING KONG', 2001, 'FICTION')
,('CINZOO', 'HARAMBE LIVS', 2015, 'COMIC')
, ('GORILLAZ', 'NARUTO SILVERBACK', 2010, 'EDUCATION')
,('THEBONDS', 'RIVER SONG', 2012, 'CRIME')  ;

--Books data entry

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES ('GORILLAZ', 'DOS AND DONTS: A GUIDE TO THE PERFECT DATABASE', 'NARUTO PUBLISHING', 2012, 323);

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES('CINZOO', 'ZOO LYFE', 'ER PUBLICATIONS', 2014, 233);

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES('GORILLAZ', 'THE LIFE OF A SILVERBACK: A BIOGRAPHY OF NARUTO', 'NARUTO PUBLISHING', 2016, 332);

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES('THEBONDS', 'TARDIS ADVENTURES', 'TARDIS PUBLICATIONS', 2063, 1123);

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES('CINZOO', 'HARAMBE POTTER AND THE CURSED CHILD WHO GOT ME SHOT', 'ER PUBLICATIONS', 2016, 200);
