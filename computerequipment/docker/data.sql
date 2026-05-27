-- -----------------------------------------------------
-- Schema full-stack-computer-equipment
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `computer-equipment`;

USE `computer-equipment`;
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- Odbacivanje postojeće tabele ako postoje
DROP TABLE IF EXISTS Proizvod;
DROP TABLE IF EXISTS Marka;
DROP TABLE IF EXISTS Tip;


-- Kreiranje tabele Marka
CREATE TABLE `Marka` (
                         `Id` INT AUTO_INCREMENT PRIMARY KEY,
                         `Naziv` VARCHAR(255) NOT NULL
);

-- Ubacivanje podataka u tabelu Marka
INSERT INTO Marka (Naziv) VALUES
                             ('Lenovo'),
                             ('Dell'),
                             ('Asus'),
                             ('Apple'),
                             ('Intel'),
                             ('Kingston'),
                             ('Logitech');

-- Kreiranje tabele Tip
CREATE TABLE `Tip` (
                        `Id` INT AUTO_INCREMENT PRIMARY KEY,
                        `Naziv` VARCHAR(255) NOT NULL
);

-- Ubacivanje podataka u tabelu Tip
INSERT INTO Tip (Naziv) VALUES
                            ('Laptop računar'),
                            ('Desktop računar'),
                            ('Procesor'),
                            ('RAM Memorija'),
                            ('Periferija');

-- Kreiranje tabele Proizvod
CREATE TABLE `Proizvod` (
                           `Id` INT AUTO_INCREMENT PRIMARY KEY,
                           `Naziv` VARCHAR(255) NOT NULL,
                           `Opis` TEXT,
                           `Cena` DECIMAL(10, 2) NOT NULL,
                           `UrlSlike` VARCHAR(255),
                           `TipProizvodaId` INT NOT NULL,
                           `MarkaProizvodaId` INT NOT NULL,
                           FOREIGN KEY (`TipProizvodaId`) REFERENCES `Tip`(`Id`),
                           FOREIGN KEY (`MarkaProizvodaId`) REFERENCES `Marka`(`Id`)
);

-- Dodavanje podataka u tabelu Proizvod
INSERT INTO Proizvod (Naziv, Opis, Cena, UrlSlike, TipProizvodaId, MarkaProizvodaId) VALUES
                                                                                              ('LENOVO Legion 7 16IAX10 U9/32/1TB/5070', 'Lenovo Legion 7 je moćan gaming laptop sa Intel Ultra 9 procesorom, 32GB RAM-a, 1TB SSD-a i NVIDIA GeForce RTX 5070 grafičkom karticom, namenjen zahtevnim igračima i kreativcima', 299000, 'images/products/LenovoLegion7.webp', 1, 1),
                                                                                              ('LENOVO IdeaPad Slim 3 16ARP10 R5/16/1TB', 'Lenovo IdeaPad Slim 3 je pristupačan svakodnevni laptop sa AMD Ryzen 5 procesorom, 16GB RAM-a i 1TB SSD-a, idealan za kancelarijski rad i školovanje.', 84999, 'images/products/LenovoIdeaPad.webp', 1, 1),
                                                                                              ('LENOVO LOQ 15IAX9 i5/16/512/3050', 'Lenovo LOQ 15 je pristupačan entry-level gaming laptop sa Intel Core i5 procesorom, 16GB RAM-a, 512GB SSD-a i NVIDIA GeForce RTX 3050 grafičkom karticom.', 109999, 'images/products/LenovoLOQ.webp', 1, 1),
                                                                                              ('RAČUNAR Prime Pro PBA Neuron Powered by Asus', 'Prime Pro PBA Neuron je moćan gaming desktop računar sa AMD Ryzen 9 7900 procesorom (12 jezgara), 32GB DDR5 RAM-a na 6000MHz, liquid coolingom i ASUS TUF komponentama, spreman za zahtevne igre i multitasking — bez grafičke kartice u specifikacijama, što znači da se dokupljuje posebno.', 249999, 'images/products/ASUSPrimePro.webp', 2, 3),
                                                                                              ('RAČUNAR 7900X64G2TRTX5070TI Powered by Asus', 'Vrhunski gaming desktop sa AMD Ryzen 9 7900X procesorom, 64GB DDR5 RAM-a na 6400MHz, NVIDIA RTX 5070 Ti grafičkom karticom sa 16GB GDDR7 memorije, 2TB NVMe SSD-om i premium ASUS ROG/TUF komponentama — ozbiljna mašina za najzahtevnije igre i kreativne zadatke.', 454999, 'images/products/ASUS7900x64.webp', 2, 3),
                                                                                              ('RAČUNAR 7700X32G1TRTX5070 Powered by Asus', 'Solidan mid-range gaming desktop sa AMD Ryzen 7 7700X procesorom (8 jezgara), 32GB DDR5 RAM-a na 5600MHz, NVIDIA RTX 5070 grafičkom karticom sa 12GB GDDR7 memorije i 1TB brzog NVMe SSD-a — odličan balans performansi i cene za moderne igre.', 279999, 'images/products/ASUS7700x32.webp', 2, 3),
                                                                                              ('DELL 23.8" IPS SE2426HG Monitor', 'Dell SE2426HG je 23.8-inčni IPS gaming monitor sa FHD rezolucijom, namenjen igračima koji traže kvalitetnu reprodukciju boja i uglove gledanja po pristupačnoj ceni.', 13999, 'images/products/DELL23.8.webp', 5, 2),
                                                                                              ('DELL Pro P 27" IPS P2726H Monitor', 'Dell Pro P2726H je 27-inčni IPS poslovni monitor dizajniran za profesionalce koji zahtevaju preciznu reprodukciju boja, široke uglove gledanja i ergonomsku podesivost za dugotrajan rad.', 27999, 'images/products/DELLProP.webp', 5, 2),
                                                                                              ('INTEL Core i3-14100 3.50GHz (4.70GHz) Procesor', 'Intel Core i3-14100 je četvorojezgarni procesor entry-level klase sa baznom frekvencijom 3.50GHz i turbo do 4.70GHz, pogodan za kancelarijski rad i svakodnevne zadatke po pristupačnoj ceni.', 29999, 'images/products/INTELCorei3.webp', 3, 5),
                                                                                              ('INTEL Core i5-12400F 2.50 GHz (4.40 GHz)', 'Intel Core i5-12400F je šestojezgarni procesor srednje klase bez integrisane grafike, sa baznom frekvencijom 2.50GHz i turbo do 4.40GHz, popularan izbor za gaming računare u kombinaciji sa dedicated grafičkom karticom.', 16999, 'images/products/INTELCorei5.png', 3, 5),
                                                                                              ('INTEL Core i5-13400F 2.5GHz (4.6GHz) - Procesor', 'Intel Core i5-13400F je desetojezgarni procesor (6P+4E jezgara) srednje klase bez integrisane grafike, sa turbo do 4.6GHz, odličan izbor za gaming računare koji nude solidan skok performansi u odnosu na prethodnu generaciju.', 23999, 'images/products/INTELCorei5-13400F.webp', 3, 5),
                                                                                              ('KINGSTON NV3 1TB M.2 2280 PCIe 4.0 NVMe SNV3S/1000G SSD', 'Kingston NV3 je 1TB M.2 NVMe SSD sa PCIe 4.0 interfejsom, koji nudi brze brzine čitanja/pisanja po pristupačnoj ceni, idealan kao primarni ili sekundarni disk za svakodnevnu upotrebu.', 15999, 'images/products/KingstonNV3.webp', 4, 6),
                                                                                              ('KINGSTON SODIMM 32GB DDR4 3200MHz CL22 - KVR32S22D8/32', 'Kingston 32GB DDR4 SO-DIMM memorija na 3200MHz je laptop/mini-PC RAM modul, pogodan za nadogradnju prenosivih računara koji zahtevaju veću količinu memorije.', 24999, 'images/products/KingstonSODIMM.png', 4, 6),
                                                                                              ('KINGSTON Beast 8GB DDR4 3200MHz CL16 - KF432C16BB/8', 'Kingston FURY Beast 8GB DDR4 na 3200MHz sa CL16 latencijom je brza desktop RAM memorija namenjena gaming i performansnim sistemima po pristupačnoj ceni.', 9999, 'images/products/KingstonBeast.png', 4, 6),
                                                                                              ('LOGITECH MX Master 4 Graphite', 'Logitech MX Master 4 je premium ergonomski bežični miš za profesionalce, sa naprednim sensorom, prilagodljivim točkićem i podrškom za rad na više uređaja istovremeno.', 15999, 'images/products/LogitechMX.webp', 5, 7),
                                                                                              ('LOGITECH G502 HERO 910-005471 - Žični gejmerski miš', 'Logitech G502 HERO je popularni žični gaming miš sa preciznim HERO senzorom do 25600 DPI, podesivim tegovima i 11 programabilnih tastera, omiljeni izbor među igračima.', 7999, 'images/products/LogitechG502.webp', 5, 7),
                                                                                              ('LOGITECH MK295 Silent Wireless US 920-009800 Crna Bežična tastatura i miš', 'Logitech MK295 Silent je bežični set tastature i miša sa tehnologijom tihih tastera koja smanjuje buku kucanja do 90%, idealan za kancelarijski rad u tihim okruženjima.', 6499, 'images/products/LogitechMK295.webp', 5, 7),
                                                                                              ('ASUS Zenbook 14 UM3406KA-QD025 R AI 5/16/512GB', 'ASUS Zenbook 14 je tanak i lagan premium ultrabook sa AMD Ryzen AI 5 procesorom, 16GB RAM-a i 512GB SSD-a, dizajniran za mobilne profesionalce koji traže balans između performansi i prenosivosti.', 99999, 'images/products/ASUSZenbook.webp', 1, 3),
                                                                                              ('APPLE MacBook Air 13.6 M4 16/256GB Sky Blue MC6T4ZE/A OUTLET', 'Apple MacBook Air 13.6 je tanak i lagan laptop sa Apple M4 čipom, 16GB RAM-a i 256GB SSD-a, poznat po odličnoj autonomiji baterije i kompaktnom dizajnu u prepoznatljivom Sky Blue izdanju.', 109999, 'images/products/AppleMacBookAir13.6.webp', 1, 4),
                                                                                              ('APPLE MacBook Air 13 M5/16/512GB Midnight MDHE4CR/A', 'Apple MacBook Air 13 je novi ultrabook sa najnovijim Apple M5 čipom, 16GB RAM-a i 512GB SSD-a u elegantnom Midnight crnom dizajnu, koji donosi vrhunske performanse i autonomiju baterije u izuzetno tankom kućištu.', 161999, 'images/products/AppleMacBookAir13.webp', 1, 4),
                                                                                              ('APPLE MacBook Pro 16 M5 Pro/48/1TB Space Black MGEC4CR/A', 'Apple MacBook Pro 16 je vrhunski profesionalni laptop sa Apple M5 Pro čipom, 48GB unifikovane memorije i 1TB SSD-a u elegantnom Space Black dizajnu, namenjen zahtevnim profesionalcima koji rade sa video produkcijom, 3D modelovanjem i drugim resursno intenzivnim zadacima.', 459999, 'images/products/AppleMacBookPro16.webp', 1, 4),
                                                                                              ('LOGITECH gejmerske slušalice G432 (Crna/Plava) - 981-000769', 'Logitech G432 su žične gaming slušalice sa 7.1 surround zvukom, 50mm drajverima i sklopivim mikrofonom, koje nude solidne audio performanse za igrače po pristupačnoj ceni.', 9999, 'images/products/LogitechGejmerskeSlusalice.webp', 5, 7),
                                                                                              ('LOGITECH G321 Lightspeed White', 'Logitech G321 LIGHTSPEED donosi profesionalni bežični gejming zvuk u laganom i udobnom dizajnu.', 6999, 'images/products/LogitechG321.webp', 5, 7),
                                                                                              ('LOGITECH Set Volan G29 i menjač', 'Realistično iskustvo vožnje uz aluminijumski volan obložen kožom. Rotacija od 900° i dva motora za vibraciju prenose svaki detalj sa staze.', 44999, 'images/products/LogitechSetVolanG29.webp', 5, 7),
                                                                                              ('ASUS Vivobook Go 15 E1504FA-BQ2786 R5/16/512GB', 'ASUS Vivobook Go 15 je pristupačan svakodnevni laptop sa AMD Ryzen 5 procesorom, 16GB RAM-a i 512GB SSD-a, idealan za studente i kancelarijske korisnike koji traže pouzdanu mašinu bez suvišnih troškova.', 79999, 'images/products/ASUSVivobook.webp', 1, 3),
                                                                                              ('ASUS ROG Strix OLED 27" XG27AQDMG Monitor', 'ASUS ROG Strix XG27AQDMG je 27-inčni premium gaming monitor sa OLED panelom, QHD rezolucijom i visokim refresh rate-om, koji pruža izuzetnu reprodukciju boja, savršene crne i munjevito brze odzive za kompetitivne igrače.', 69999, 'images/products/ASUSRog.webp', 5, 3),
                                                                                              ('DELL Alienware 27" IPS AW2725DM', 'Dell Alienware AW2725DM je 27-inčni premium gaming monitor sa IPS panelom, dizajniran za kompetitivne igrače koji traže visok refresh rate, brz odziv i karakteristični Alienware dizajn.', 31999, 'images/products/DELLAlienware.webp', 5, 2),
                                                                                              ('DELL UltraSharp 23.8" IPS U2424HE Monitor', 'Dell UltraSharp U2424HE je 23.8-inčni profesionalni IPS monitor sa preciznom reprodukcijom boja i USB-C hub funkcionalnošću, namenjen poslovnim korisnicima i kreativcima koji zahtevaju tačnost prikaza i praktičnu povezivost.', 34999, 'images/products/DELLUltraSharp.webp', 5, 2),
                                                                                              ('DELL Pro 27" IPS E2726DS Monitor', 'Dell Pro E2726DS je 27-inčni IPS poslovni monitor sa QHD rezolucijom, namenjen profesionalcima koji traže oštriji prikaz i kvalitetnu reprodukciju boja za svakodnevni kancelarijski rad.', 23499, 'images/products/DELLPro27.webp', 5, 2),
                                                                                              ('INTEL Core i9-13900K 3GHz (5.8GHz) - Procesor', 'Intel Core i9-13900K je flagship procesor sa 24 jezgara (8P+16E), baznom frekvencijom 3GHz i turbo do 5.8GHz, namenjen entuzijastima i profesionalcima koji zahtevaju maksimalne performanse za gaming, renderovanje i multitasking.', 54999, 'images/products/INTELCorei9.webp', 3, 5),
                                                                                              ('INTEL Core i9-14900KF 3.20GHz (6.00GHz) Procesor', 'Intel Core i9-14900KF je vrhunski desktop procesor sa 24 jezgara bez integrisane grafike, baznom frekvencijom 3.20GHz i turbo do impresivnih 6.00GHz, namenjen entuzijastima koji grade ekstremno moćne gaming i workstation računare.', 61999, 'images/products/INTELCorei9-14900KF.webp', 3, 5),
                                                                                              ('INTEL Core Ultra 7 265KF 3.90GHz (5.50GHz) Procesor', 'Intel Core Ultra 7 265KF je moderni procesor nove Arrow Lake generacije bez integrisane grafike, sa 20 jezgara i turbo do 5.50GHz, koji donosi poboljšanu energetsku efikasnost i AI mogućnosti za zahtevne gaming i kreativne workload-ove.', 39999, 'images/products/IntelCoreUltra7.webp', 3, 5),
                                                                                              ('APPLE MacBook Air 13 M5/16/2TB Midnight', 'Apple MacBook Air 13 je ultrabook sa Apple M5 čipom, 16GB RAM-a i velikim 2TB SSD-om u Midnight dizajnu, idealan za korisnike kojima je potrebno obilato lokalno skladište uz vrhunsku autonomiju baterije i kompaktnost.', 254999, 'images/products/AppleMacBookAirMidnight.webp', 1, 4),
                                                                                              ('APPLE MacBook Air 15 M5/32/1TB Midnight', 'Apple MacBook Air 15 je veći ultrabook sa Apple M5 čipom, 32GB unifikovane memorije i 1TB SSD-a u Midnight dizajnu, koji nudi premium performanse i veliki ekran u iznenađujuće tankom i lakom kućištu.', 294999, 'images/products/AppleMacBookAir15Midnight.webp', 1, 4),
                                                                                              ('APPLE MacBook Pro 16 M5 Pro/64/2TB Space Black', 'Apple MacBook Pro 16 je vrhunska profesionalna radna stanica sa Apple M5 Pro čipom, impresivnih 64GB unifikovane memorije i 2TB SSD-a u Space Black dizajnu, namenjena profesionalcima koji rade najzahtevnije zadatke poput 3D renderovanja, video produkcije i razvoja softvera.', 554999, 'images/products/AppleMacBookPro16SpaceBlack.webp', 1, 4);