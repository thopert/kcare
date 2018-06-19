# kCare
###### Eine Software zur Verwaltung einer interaktiven Kinderkrippe


## Wie startet man den Server?


### Konsole
Das Projekt kann bequem über die Konsole gestartet werden.
Dafür müssen jedoch einige Voraussetzungen gegeben sein:
- Java 8 SDK (JDK8)
- Maven
- Lokaler MYSQL-Server mit den Zugangsdaten (Benutzername: `root`, Passwort: ``)
- Die Projekt-Daten selbst

Anschließend kann das Projekt über die Konsole (innerhalb des Projektordners) mit
```
mvn spring-boot:run
```
gestartet werden.

_**Hinweis:** Es könnte sein, dass zuvor der `mysql-server` gestartet werden muss, damit die Applikation zugriff auf die Datenbank hat._


### Virtuelle Maschine mit Vagrant
#### Virtuelle Maschine installieren
Mithilfe von `vagrant` wird eine virtuelle Maschine aufbereitet, um einen plattformunabhängigen und auch reibungslosen Start zu ermöglichen.

Dafür laden Sie sich bitte die Projekt-Daten herunter und installieren `vagrant` inkl. Abhängigkeiten!

Um die Aufbereitung der virtuellen Maschine anzustoßen, muss in der Konsole nur noch **ausgehend vom Projektordner**
```
vagrant up
```
ausgeführt werden.

Nach einer Weile ist der Server mitsamt einer MYSQL-Datenbank betriebsbereit.


#### Server innerhalb der virtuellen Maschine starten
Mit
```
vagrant ssh
```
kann anschließend auf die virtuelle Maschine zugegriffen werden.

Um den Server nun zu starten, müssen folgende Befehle über die **SSH-Verbindung** ausgeführt werden:
```
cd /vagrant
mvn spring-boot:run
```
Wenn der Server dann hochgefahren ist (das kann beim ersten Start bis zu 15 Minuten dauern, da alle Abhängigkeiten aufgelöst und heruntergeladen werden müssen), kann man über
die unten stehende URL (siehe `Wie greife ich auf die Applikation zu?`) mit dem Server verbinden und mit den unten stehenden Login-Daten (siehe `Wie logge ich mich bei kCare ein?`) anmelden.

Die Verwendung von Vagrant wurde auf folgenden Plattformen getestet:
- Windows 10
- Ubuntu 16.04.2 LTS


## Wie greife ich auf die Applikation zu?
Nachdem der Server gestartet wurde, kann die Website über folgende URL aufgerufen werden:

[http://localhost:8080/](http://localhost:8080/)

Sie sollten nun die Login-Seite von `kcare` sehen!


## Wie logge ich mich bei kCare ein?
Geben Sie hierfür folgende Nutzerdaten ein:

###### als Administrator:
- Benutzername: `admin`
- Passwort: `passwd`

###### als Betreuer:
- Benutzername: `user1`
- Passwort: `passwd`

###### als Eltern:

(ein Account für Mutter und Vater zusammen)

- Benutzername: `user2`
- Passwort: `passwd`

## Wie verwende ich kCare?

Da kCare sehr viele Funtionen bietet, haben wir versucht, einzelne Aufgaben und Möglichkeiten zu kapseln und zu gruppieren.
Jeder Benutzer hat ein eigenes **Hauptmenü**, über welches **alle berechtigten** Funktionalitäten von kcare des **aktuell angemeldeten Nutzers** erreichbar sind.

Im folgenden werden noch kurz die einzelnen Möglichkeiten für die jeweiligen Benutzer-Gruppen beschrieben:

### als Administrator:
##### über Kopfzeile:
- **Benutzer bearbeiten:** Auflistung aller registrierten Benutzer. Jeder Benutzer verfügt über einen **Bearbeiten**-Button, über welchen die Nutzerdaten bearbeitet werden können, und einen **Löschen**-Button über welchen ein Nutzer gelöscht werden kann.
- **Betreuer bearbeiten:** Auflistung aller registrierten Betreuer. Der Mechanismus ist der Selbe wie bei _Benutzer bearbeiten_, jedoch können hier auch über einen **Details**-Button, die Daten in einer übersichtlichen Struktur für jeden Betreuer angesehen werden.
- **Betreuer hinzufügen:** Hier haben Sie die Möglichkeit neue Betreuer anzulegen. Wir haben uns dazu entschieden, dass nur ein Administrator der Kinderkrippe neue Betreuer anlegen kann, damit die Betreuer selbst entlastet werden.
- **Passwort ändern:** Hier können Sie das Passwort für den aktuell angemeldeten Administrator-Nutzer ändern.
- **Abmelden:** Mit diesem Button melden Sie sich sicher von der Applikation ab.

### als Betreuer:
##### über Kopfzeile:
- **Hauptmenü:** Strukturierte Auflistung aller Funktionen eines Betreuers in einzelne Gruppen.
- **Passwort ändern:** Hier können Sie das Passwort für den aktuell angemeldeten Administrator-Nutzer ändern.
- **Abmelden:** Mit diesem Button loggen Sie sich sicher aus der Applikation aus.

##### über das Hauptmenü:
- **Kinder anzeigen:** Hier sehen Sie alle aktuell registrierten Kinder. Sie können hier, gleich wie beim Administrator die Daten ansehen, bearbeiten oder ein Kind aus der Datebank entfernen.
- **Eltern anzeigen:** Hier sehen Sie alle aktuell registrierten Eltern. Auch hier könne Sie alle Daten ansehen und bearbeiten oder einen Eltern-Account aus der Datenbank entfernen.
- **Bezugspersonen anzeigen:** Hier sehen Sie alle aktuell registrierten Bezugspersonen, welche von den Eltern eingetragen worden sind. Hier haben Sie zusätzlich als Betreuer die Möglichkeit, einen Betreuer als **Verifiziert** zu setzen, wenn die Kinder-Krippen-Betreuung damit einverstanden ist.
- **Tagesplan anzeigen:** Bietet Ihnen als Betreuer die Möglichkeit für ein gewähltes Datum die aktuell angemeldeten Kinder anzusehen.
- **Wochenplan anzeigen:** Bietet Ihnen eine übersichtliche Auflistung aller aktuell reigstierten Kinder für die kommende Woche. Sie können auch in der Fußleiste zwischen den einzelnen Wochen navigieren.
- **Jobs anzeigen:** Hier sehen Sie alle aktuell registeriten Jobs und können diese als Betreuer als **Erledigt** markieren.
- **Kontaktlisten anzeigen:** Hier werden für jedes Kind die Eltern und aktuell gemeldeten Bezugspersonen (= Kontakte) aufgelistet.
- **Kind hinzufügen:** Ermöglicht Ihnen als Betreuer, ein neues Kind anzulegen und für dieses ein Profilbild zu setzen.
- **Eltern hinzufügen:** Ermöglicht Ihnen als Betreuer, einen neuen Eltern-Account anzulegen und für jeden Teil des Eltern-Accounts ein Profilbild zu setzen.
- **Tagesplan hinzufügen:** Über diese Funktion können Sie für den jeweilig gewählten Werktag die Maximal-Belegung, angemeldeten Betreuer und angemeldeten Kinder setzen oder nachbearbeiten.
- **Job erstellen:** Bietet Ihnen als Betreuer die Möglichkeit, einen neuen Job anzulegen. Das Erinnerungs-Feld gibt an, wie viele Tage vor dem Start-Datum die gemeldeten Eltern automatisch eine Erinnerungs-Mail erhalten sollen.
- **Mittagessen-Report erstellen:** Mithilfe dieses Tools haben Sie die Möglichkeit, sich einen Report auszudrucken oder als CSV- oder XML-Datei herunterzuladen. Dafür wählen Sie einfach einen Zeitrahmen und einen Pauschalbetrag der Kosten für das Mittagessen innerhalb dieses Zeitrahmens. Anschließend können Sie noch die berücksichtigten Kinder auswählen.
- **Feiertage erstellen:** Hier können Sie alle Feiertage anlegen. Wenn der Feiertag nur ein Tag lang ist, einfach das Start- und End-Datum auf den selben Tag setzen.

_**Hinweis:** Alle oben genannten Listen können über die Browser-Druck-Funktion ausgedruckt werden. Der Browser sollte anhand vorgegebener Stil-Anpassungen die Liste druckbar gestalten._

### als Elternteil:
##### über Kopfzeile:
- **Hauptmenü:** Strukturierte Auflistung aller Funktionen eines Eltern-Kontos in einzelne Gruppen.
- **Passwort ändern:** Hier können Sie das Passwort für den aktuell angemeldeten Administrator-Nutzer ändern.
- **Abmelden:** Mit diesem Button loggen Sie sich sicher aus der Applikation aus.

##### über das Hauptmenü:
- **Bezugspersonen anzeigen:** Hier können Sie die eingetragenen Bezugspersonen für die eingenen Kinder ansehen, bearbeiten oder entfernen.
- **Jobs anzeigen:** Hier können Sie sich für noch offene Jobs eintragen.
- **Kind als abwesend melden:** Über diese Funktion können Sie ein Kind für einen gewissen Tag als abwesend eintragen. Dieser Eintrag wird dann für alle Betreuer über eine wöchentliche Mail ersichtlich.
- **Abholperson melden:** Hier können Sie für ein Kind individuell für jeden Tag eine Abholperson festlegen. Auch diese Information ist in der wöchentlichen Mail der Betreuer enthalten.
- **Anmeldung Mittagessen:** Hier können Sie Ihr(e) Kind(er) für das Mittagessen für die kommende Woche anmelden. Diese Information ist ebenfalls in der wöchentlichen Mail und in der Auflistung für Betreuer ersichtlich.
- **Kinder verwalten:** Hier können Sie die Daten Ihres Kindes ansehen, ausdrucken (über Browser-Funktion) und bearbeiten.
- **Bezugsperson hinzufügen:** Hier können Sie eine neue Bezugsperson eintragen. Diese Bezugsperson wird, nach der Verifizierung eines Betreuers, dann für alle Kinder dieser Familie gesetzt.
- **Einstellungen:** Hier können Sie Änderungen am eigenen Eltern-Konto vornehmen.

_**Hinweis:** Alle oben genannten Listen können über die Browser-Druck-Funktion ausgedruckt werden. Der Browser sollte anhand vorgegebener Stil-Anpassungen die Liste druckbar gestalten._

## Wie teste ich kcare?

### Komplett (sowohl das Backend als auch das Frontend)
`mvn test[ -Dbrowser=<browser>]`

Das Browser-Argument kann frei gelassen werden oder auf `chrome`, `firefox` oder `safari` gesetzt werden.
(Wenn frei gelassen, dann wird automatisch PhantomJS, ein headless browser, verwendet)

### Nur das Frontend (funktioniert auf Windows nur mit PhantomJS, da maven keine mehrfachen Defintionen akzeptiert)
`mvn test[ -Dbrowser=<browser>] -Dtest=test=at.qe.sepm.skeleton.tests.GUITest`
oder
`./selenium.sh <browser>`

_**Hinweis:** Die Test-Funktion erstellt automatisch eine eigene Instanz der Applikation auf einem zufälligen Port!
Wir raten daher dringend davon ab, während dem Testdurchlauf weitere Instanzen laufen zu lassen!_

_**Hinweis 2:** Sobald die Selenium-Tests starten wird der Browser (standardmäßig PhantomJS) gestartet. Ab diesem Zeitpunkt startet dann, wie beschrieben in Hinweis 1, eine Applikations-Instanz auf einem zufälligen Port. Also bitte einfach abwarten, bis die Instanz läuft und der Browser zur eigentlichen Applikations-URL navigiert!_

_**Hinweis 3:** Abhängig von der Performance kann es zu kleineren Problemen kommen, die aber die Korrektheit der Applikation nicht beeinflussen. Beispielsweise kann es sein, dass Spring mit dem Authentifizierungssystem nicht hinterherkommt. Wenn man etwas speichern/bearbeiten will und sich zu früh ausloggt, kann es passieren, dass der Wert nicht gesetzt wird, da man sozusagen eine ungültige Session hätte (bekannt als Athentification-Exception oder Pipe-Error). In kritischen Fällen wurden dafür großzügige Wartezeiten und strenge Inhalts-Überprüfungen eingebaut, sodass dieses Problem nur sehr selten, bis gar nicht, auftreten sollte._

_**Hinweis 4:** Wir haben versucht, die GUI-Tests plattformunabhängig zu gestalten. Dafür wurden speziell bei der Pfad-Setzung einige Änderungen vorgenommen. Die Tests liefen bei uns erfolgreich auf `Windows 10`, `macOS 10.12.5` und `Ubuntu 16.04.2 LTS`._

## Es gibt Probleme bei der MySQL-Verbindung. Wie löst man diese?
- Kontrollieren Sie zu Beginn ob der *MySQL-Server* läuft und über die richtige *URL* inkl. *Port* erreichbar ist.
- Überprüfen Sie anschließend, ob der Benutzer (und dessen Passwort) in MySQL über ausreichend Berechtigungen verfügt. (Berechtigungen sind in der `mysql/user`-Datenbank zu finden)
- Überprüfen Sie, ob die Einstellungen in der Datei `src/main/resources/application.properties` korrekt eingestellt sind.
- Andernfalls bleibt Ihnen nur die Möglichkeit, sich den *Stack-Trace* anzusehen.

