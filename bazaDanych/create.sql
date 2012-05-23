CREATE TABLE Przedmioty (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nazwa varchar(60) NOT NULL,
	typ varchar(20) NOT NULL,
	rodzaj varchar(20) NOT NULL,
	priorytet int unsigned NOT NULL,
	komputery varchar(1) NOT NULL,#1 jesli tak, 0 jesli nie
	rzutnik varchar(1) NOT NULL,#j.w.
	tablicaInt varchar(1) NOT NULL#tablica interaktywna oznaczenia j.w.
);

CREATE TABLE Wykladowcy (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	tytuly varchar(40),
	imie varchar(40),
	nazwisko varchar(40),
	pon int unsigned NOT NULL,#zakres 1-5
	wt int unsigned NOT NULL,#zakres 1-5
	sr int unsigned NOT NULL,#zakres 1-5
	czw int unsigned NOT NULL,#zakres 1-5
	pt int unsigned NOT NULL#zakres 1-5
);

CREATE TABLE Grupy (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	symbol varchar(10) NOT NULL,	
	ilosc int unsigned NOT NULL,
	typ varchar(20) NOT NULL
);

CREATE TABLE WykGru (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_Wyk int unsigned NOT NULL,
	id_Gru int unsigned NOT NULL
);

CREATE TABLE PrzedGru (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_Przed int unsigned NOT NULL,
	id_Gru int unsigned NOT NULL
);

CREATE TABLE Studenci (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	indeks varchar(6) NOT NULL,
	pesel varchar(11),
	imie varchar(40),
	nazwisko varchar(40),
	kierunek varchar(10),
	specjalnosc varchar(30),
	rok int unsigned
);


CREATE TABLE StudGru (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_Stud int unsigned NOT NULL,
	id_Gru int unsigned NOT NULL
);

CREATE TABLE Sale (
	id int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nr int unsigned NOT NULL,
	pojemnosc int unsigned NOT NULL,
	komputery varchar(1) NOT NULL,
	rzutnik  varchar(1) NOT NULL,
	tablicaInt  varchar(1) NOT NULL
);
