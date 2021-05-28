use nerdygadgets;

/*Toevoegen postcode, adres en plaats kolommen in people*/
ALTER TABLE people
ADD postcode varchar(20) Not null, ADD adres varchar(60) Not null, ADD plaats varchar(60) Not null;

use nerdygadgets;
/*Toevoegen route kolom in orders*/
ALTER TABLE orders
ADD column RouteID int null, ADD Status varchar(50) null, add klantID int(11) not null ;


use nerdygadgets;

/*Toevoegen postcode tabel*/
CREATE TABLE postcode
(
    PostcodeID int NOT NULL,
    Postcode int NOT NULL,
    PostCodePK varchar(15),
    PostCodeNummers varchar(45),
    PostcodeLetters varchar(45),
    Straat varchar(50),
    MinNummer int,
    MaxNummer int,
    Plaats varchar(45),
    Gemeente Varchar(50),
    Provincie varchar(45),
    Latitude decimal(12,8) Not null,
    Longitude decimal(12,8) not null,
    CONSTRAINT PK_postcode PRIMARY KEY (PostcodeID)
);

/*Toevoegen route tabel*/
CREATE TABLE route
(
    RouteID int NOT NULL auto_increment,
    PersonID int NULL,
    AantalPakketten int not null,
    Reistijd decimal(4,2),
    Afstand int(11) not null,
    Provincie varchar(45) not null,
    Status varchar(50) Not null,
    Opmerkingen varchar(200) null,
    CONSTRAINT PK_route PRIMARY KEY (RouteID),
	CONSTRAINT check_status
    CHECK (Status IN ("Klaar voor bezorging", "Onderweg", "Afgerond", "Anders", "Klaar voor sorteren"))
);





/*Kolommen RouteID van orders tabel verbinden aan route tabel*/
ALTER TABLE orders
ADD CONSTRAINT FOREIGN KEY(RouteID) REFERENCES Route(RouteID);



/*Not null kolommen people*/
/*emailadress*/
ALTER TABLE people
MODIFY EmailAddress varchar(256) NOT NULL;

/*phonenumber*/
ALTER TABLE people
MODIFY PhoneNumber varchar(20) NOT NULL;

/*IsSystemUser*/
ALTER TABLE people
MODIFY IsSystemUser tinyint(1) NOT NULL;

/*HashedPassword*/
ALTER TABLE people
MODIFY HashedPassword longblob NOT NULL;

/*null kolommen people*/
/*issalesperson*/
ALTER TABLE people
MODIFY IsSalesperson tinyint(1) NULL;

/*Preferredname*/
ALTER TABLE people
MODIFY PreferredName varchar(50) NULL;

/*Searchname*/
ALTER TABLE people
MODIFY SearchName varchar(101) NULL;

/*IsExternalLogonProvider*/
ALTER TABLE people
MODIFY IsExternalLogonProvider tinyint(1) NULL;



/*Verbinding maken met de klant en de gemaakt bestelling*/
ALTER TABLE orders
ADD FOREIGN KEY (klantID) REFERENCES people(PersonID);
						   


/*Toevoegen routelines tabel*/
CREATE TABLE routelines
(
    RouteID int not null,
    VolgordeID int not null,
    OrderID int not null,
    CONSTRAINT PK_route PRIMARY KEY (RouteID, VolgordeID),
    CONSTRAINT FK_RouteRouteline FOREIGN KEY (RouteID) REFERENCES route(RouteID),
    CONSTRAINT FK_OrderRouteline FOREIGN KEY (OrderID) REFERENCES orders(OrderID)    
);

/* Aanpassen kolom namen naar functie van actoren */   
ALTER TABLE people CHANGE IsSystemUser isStockManager BOOLEAN;
ALTER TABLE people CHANGE IsEmployee isStockSorter BOOLEAN;
ALTER TABLE people CHANGE IsSalesperson isDeliverer BOOLEAN;


/*De status van een route kan niet op 'onderweg', als er geen bezorger is toegewezen*/
DELIMITER // 
CREATE TRIGGER toebedelenBezorger 
    BEFORE UPDATE ON route 
    FOR EACH ROW 
       BEGIN 
         IF new.Status ='Onderweg' AND old.PersonID is null 
         THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Geen bezorger toegewezen aan de route'; 
         END IF; 
       END; 
 // 
DELIMITER ; 


/*bezorger, magazijn sorteerder, magazijn manager account maken*/
CREATE user 'bezorger'@'localhost' IDENTIFIED BY '<eigen wachtwoord>';
CREATE user 'sorteerder'@'localhost' IDENTIFIED BY '<eigen wachtwoord>';
CREATE user 'magazijn_manager'@'localhost' IDENTIFIED BY '<eigen wachtwoord>';

/*grant statements bezorger*/
GRANT SELECT ON nerdygadgets.people TO 'bezorger'@'localhost'; 
GRANT SELECT ON nerdygadgets.personorder TO 'bezorger'@'localhost'; 
GRANT SELECT ON nerdygadgets.orders TO 'bezorger'@'localhost'; 
GRANT SELECT ON nerdygadgets.postcode TO 'bezorger'@'localhost'; 
GRANT SELECT ON nerdygadgets.stockitems TO 'bezorger'@'localhost'; 
GRANT SELECT ON nerdygadgets.stockitemholdings TO 'bezorger'@'localhost'; 
GRANT SELECT ON nerdygadgets.orderlines TO 'bezorger'@'localhost'; 

/*grant statements magazijn sorteerder*/
GRANT SELECT ON nerdygadgets.people TO 'sorteerder'@'localhost'; 
GRANT SELECT ON nerdygadgets.orders TO 'sorteerder'@'localhost'; 
GRANT SELECT ON nerdygadgets.postcode TO 'sorteerder'@'localhost'; 
GRANT SELECT ON nerdygadgets.stockitems TO 'sorteerder'@'localhost'; 
GRANT SELECT ON nerdygadgets.stockitemholdings TO 'sorteerder'@'localhost'; 
GRANT SELECT ON nerdygadgets.orderlines TO 'sorteerder'@'localhost';


/*grant statements magazijn manager*/
GRANT SELECT ON nerdygadgets.people TO 'magazijn_manager'@'localhost'; 
GRANT SELECT, UPDATE ON nerdygadgets.orders TO 'magazijn_manager'@'localhost'; 
GRANT SELECT ON nerdygadgets.postcode TO 'magazijn_manager'@'localhost'; 
GRANT SELECT ON nerdygadgets.stockitems TO 'magazijn_manager'@'localhost'; 
GRANT SELECT ON nerdygadgets.stockitemholdings TO 'magazijn_manager'@'localhost'; 
GRANT SELECT, UPDATE ON nerdygadgets.orderlines TO 'magazijn_manager'@'localhost'; 
GRANT SELECT, UPDATE, DELETE ON nerdygadgets.route TO 'magazijn_manager'@'localhost'; 
GRANT SELECT, UPDATE, DELETE ON nerdygadgets.routelines TO 'magazijn_manager'@'localhost'; 

FLUSH PRIVILEGES;

/*alles users laten zien*/
SELECT * FROM mysql.user;

/*check constraints*/
ALTER TABLE route ADD CONSTRAINT check_aangewezenBezorger CHECK (PersonID); 


/*Weet niet of de check constraint in dezelfde query kan, waarin de kolom waar het om gaat wordt gemaakt*/
ALTER TABLE orders
ADD CONSTRAINT CHK_status_Waarde CHECK (Status IN ("Klaar voor bezorging", "Onderweg", "Anders", "Klaar voor sorteren", "Klaar voor routebepaling"));


