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


--Writing Groups data entry
INSERT INTO WritingGroups (GroupName, HeadWriter, YearFormed, Subject)
VALUES ('Jackson', 'King Kong', 2001, 'Fiction');

INSERT INTO WritingGroups (GroupName, HeadWriter, YearFormed, Subject)
VALUES ('Cinzoo', 'Harambe Livs', 2015, 'Comic');

INSERT INTO WritingGroups (GroupName, HeadWriter, YearFormed, Subject)
VALUES ('Gorillaz', 'Naruto Silverback', 2010, 'Education');

--Publishers data entry
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)
VALUES ('Naruto Publishing', '323 Dave Brown Way', '562-985-4285', 'naruto@wildthings.net');

INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)
VALUES ('Universal', '100 Universal City Plz.', '800-864-8377', 'universal@hollywood.com');

INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)
VALUES ('ER Publications', '11 UML Ave.', '562-985-4285', 'many2many@cardinality.com');

--Books data entry
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES ('Gorillaz', 'The Life of a Silverback: A Biography of Naruto', 'Naruto Publishing', 2016, 332);

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES ('Gorillaz', 'Dos and Donts: A Guide to the Perfect Database', 'Naruto Publishing', 2016, 323);

INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) 
VALUES ('Cinzoo', 'Zoo Lyfe', 'ER Publications', 2016, 233);
