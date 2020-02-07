INF112
Macrohard




Teamet


Oscar : 
* Rolle: Utvikler/tester
* Fag: INF100, INF102, INF122
* Språk: Java, Haskell, Javascript


Henrik : 
* Rolle: Utvikler/tester
* Språk - Java


Bendik :
* Rolle: Utvikler/tester og kundekontakt
* Tatt et emne i objekt-orientert programmering i Java på NTNU
* Har også god kjennskap til andre programmeringsspråk som Python og Haskell fra programmeringsfag ved UiB


Kim : 
* Rolle: Utvikler/tester
* Fag - INF100, INF102, INF122
* Språk - Java, Haskell


Vegard :
* Rolle:Utvikler/tester og Coach
* Har egen erfaring med apputvikling
* Fag - INF100, INF101, INF102, INF112, INF122
* Språk - Java, Python, Haskell


Bendik fikk rollen som kundekontakt fordi han var den som var raskest med å forstå hvordan spillet fungerte. Vegard fikk rollen som coach fordi han var den eneste i teamet som hadde tidligere erfaring med større prosjekter/applikasjoner. Rollen Utvikler/tester er tildelt hele teamet, hvor hensikten er at alle i teamet har et felles ansvar med å skrive kode etter behov. Roller og rolletildelinger kan endres utover prosjektløpet, men dette er rolletildelingen vi ble enige om i startfasen.


Produktbeskrivelse


Mål
Det overordnede målet er å bygge en fullverdig, brukervennlig, digital versjon av brettspillet RoboRally, som ivaretar regler og spillmekanismer fra det originale brettspillet, samt tillater for LAN med opp til 8 spillere.




Kravspesifikasjoner


* Applikasjonen skal tilby grafisk representasjon av et spillbrett 
* Applikasjonen skal tilby grafisk representasjon av spillebrikker
* Spillet skal kunne vinnes
* Spillet skal kunne tapes
* Spillet skal kunne avsluttes
* Spillet skal prioritere rekkefølgen av spillets runder og faser i tur og orden
* Produktet skal inneholde en representasjon av en kortstokk bestående av 84 programkort
* Spillere må kunne bli tildelt programkort
* Programkortene skal ha en prioritetsverdi
* Robotene skal flyttes i henhold til programkortenes prioritetsverdi
* Programkortene må kunne stokkes
* Spillerne må kunne bruke programkortene til å legge ned et spesifikt program
* Programmer bestemt av programkortene må kunne realiseres
* Program må utføres på en måte som overholder prioritetsverdiene til de ulike programkortene for fasen man kjører igjennom 
* En spiller må kunne annonsere Powerdown
* En spiller må kunne gå inn i Powerdown
* Spillbrettet må bestå av noen felter som fungerer som samlebånd
* Spillbrettet må bestå av noen felter som fungerer som hull
* En spiller må kunne falle gjennom hull i brettet 
* Spillbrettet må bestå av noen felter som fungerer som ekspress-samlebånd og vanlig samlebånd
* Spillet må håndtere utdeling av skade
* En spiller må kunne ha roboten reparert
* Spillet må kunne håndtere død og absolutt død
* Spillet må håndtere kollisjoner
* Applikasjonen skal være plattformuavhengig
* Applikasjonen skal ha en hovedmeny 


Krav til første iterasjon


* Applikasjonen skal kunne vise et rute-basert spillbrett
* Applikasjonen skal kunne vise en spillebrikke plassert i en av spillbrettets ruter




Brukerhistorier til første iterasjon
 
Som bruker trenger jeg et synlig spillbrett, slik at jeg kan orientere meg i min spillbrikkes omgivelser


Akseptansekrav:
Gitt at man starter applikasjonen, får man opp et spillbrett 


Som bruker må jeg kunne se brikken min på spillebrettet, slik at jeg vet hvor brikken min befinner seg til enhver tid


Akseptansekrav:
Gitt at man får opp et spillbrett, dukker spillebrikken opp på en posisjon i brettet


Som bruker må jeg kunne se hvilken retning brikken peker, slik at jeg vet hvordan brikken vil forflytte seg 


Akseptansekrav:
Gitt at man har en spillebrikke på spillebrettet, har spillebrikken et synlig retningsanvisning


Som bruker trenger jeg å bevege på brikken, slik at jeg kan avansere i spillet


Akseptansekrav:
Gitt at man befaler brikken å bevege seg, endrer brikkens posisjon seg synlig i brettet




Prosjektmetodikk og prosessplan


* Test-drevet utvikling 
* Parprogrammering
* GitHubs prosjekttavle 
* Åpent kontorlandskap
* Kommunikasjon gjennom Slack og Facebook.
* Møter torsdag og fredag, med mulighet for mer.
* Torsdager med fokus på videre planlegging, bryte ned oppgaver og fordele mellom teamet.
* Fredager med fokus på oppfølging av individuelle oppgaver og parprogrammering
* Felles dokumentskriving på Google Docs
* Felles filoppbevaring, diagram og kodebase i GitHub.
* Bruker Travis Cl og Codacy som er verktøy som hjelper oss med å utvikle fungerende programvare under prosjektet.




I den første delen av prosjektet er hovedfokuset på kommunikasjon og planlegging, hvor mesteparten av koden og prosjektplanen blir utformet og skrevet i fellesskap. Stort fokus på generelle retningslinjer om utvikling av applikasjonen. Vi kom frem til at en kombinasjon av aspekter ved Extreme Programming (XP) og Kanban ville passe oss godt i starten, men dette kan endre seg etterhvert. Vi ble enige om å bruke Kanban-tavle hvor definisjonen av at noe er “Done” er at koden består alle tilhørende tester samt bygger og kjører på alle operativsystem.
Møtereferat
Torsdag 30.01
* Ble kjent med GitHub og dets funksjonaliteter
* Ble kjent med Git
* Sørget for at alle fikk til commit, push og pull
* Kartla kompetanse og delte ut roller i teamet
* Satt opp GitHub project board


Fredag 31.01
* Diskuterte fordeler og ulemper ved forskjellige prosjektmetodikker
* Sørget for at alle hadde lik oppfatning av spilleregler og spillets gang
* Utformet produktbeskrivelse, kravspesifikasjoner og brukerhistorier.
* Utformet foreløpig prosessplan med ønskede elementer fra forskjellige prosjektmetodikker
* Parprogrammerte klasser for brett og spiller
* Opprettet development-branch for videre utvikling


Tirsdag 04.02
* Gikk gjennom tutorial i libgdx/tiled
* Diskuterte hvordan bruk av libgdx/tiled vil påvirke eksisterende kode 
* Ble enige om å bruke libgdx/tiled inntil videre for å oppfylle kravene til første iterasjon
* Parprogrammerte endringer i BoardGraphic-klassen slik at både brett og spiller vises.
* Utdeling av resterende arbeidsoppgaver




Torsdag 06.02
* Ble enige om å legge til Travis som hjelper oss med å sjekke at alle grener bygger på forskjellige maskiner/operativsystem.
* Ble enige om å legge til Codacy som hjelper oss med å sjekke generell kodekvalitet og eventuelle problemer
* Reformaterte eksisterende kode
* Slettet kode som ikke var i bruk




Retrospektiv


Jobbingen frem til første obligatoriske oppgave har fungert godt. Ved å samles på forhåndsbookede grupperom på campus flere ganger i uka, har vi lykkes i å benytte XP-praksiser som par-programmering og åpent arbeidslandskap. Samtidig har vi innfridd kravene for første produktleveranse; spillbrettet kan vises og spillebrikke kan plasseres og flyttes både grafisk og konseptuelt. Selv om vi muligens undervurderte arbeidsmengden til å begynne med, så mobiliserte vi og samarbeidet godt den siste uken og kom i mål. En ting som ikke gikk som planlagt under første iterasjon var test drevet utvikling. Vi lå godt an helt til vi implementerte libgdx og tiled, siden ingen i teamet visste hvordan noe av dette fungerte og vi endte opp med å prøve oss frem. Ved innleveringsfristen noterte vi oss at det hadde vært lurt med et større fokus på abstraksjon og single responsibility principle under første iterasjon av applikasjonen.
