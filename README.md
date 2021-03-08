# WeatherCloseRomeApp
Data la città di Roma, abbiamo scritto questo programma in modo di visualizzare tutte le informazioni attuali relative alla temperatura delle città circostanti Roma.

# Introduzione
Abbiamo implentato un servizio meteo che ci permette di monitorare le temperature nelle citta circostanti a Roma. La ricerca avviene inserendo il numero di citta che si vogliono visualizzare, per un minimo di 5 ad un massimo di 50. Salveremo le informazioni delle 50 città ogni 5 ore, in un database, cosi da avere tutti i dati pronti per il calcolo di statistiche. L'utente puo consultare diverse statistiche come i valori periodici riguardanti valori minimi, massimi, media e varianza delle temperature per ogni città.
Inoltre si possono ordinare le statistiche in base ai campi selezionati: valori minimi, massimi, media e varianza. Infine possiamo filtrare le statistiche in base al numero delle città, alla periodicità (giornaliera, settimanale, mensile o range personalizzabile) o in base ad una sottostringa contenuta nel nome della città (Citta che iniziano per A).

# API e Rotte
Sono fondamentali per il funzionamento del programma e per la raccolta dati. Con il programma *Postman* possiamo usare le rotte, sotto elencate, per far funzionare il nostro servizio.
## API
Per rispondere alle richieste degli utenti e avere un database abbiamo usato l'api: https://openweathermap.org/current#cycle.
## Rotte
Le rotte definite sono le seguenti:

N° | Tipo | Rotta | Descrizione
----- | ------------ | -------------------- | ----------------------
[1](#1) | ` GET ` | `/temp` | *restituisce un JSONArray che contiene: temperatura,  temp. max e min, pressione e umidità.*
[2](#2) | ` GET ` | `/save` | *aggiorna il databese.*
[3](#3) | ` GET ` | `/stats` | *restituisce le statistiche sulle temperature di ogni citta: valori minimi, massimi, media e varianza*
[4](#4) | ` GET ` | `/data` | *restituisce le date nel file in una stringa*
[5](#5) | ` POST ` | `/filtri` | *restituisce un JSONObject con le statistiche filtrate in base alla città, periodicità o sottostringa.*

* GET\temp:
L'utente puo specificare quante citta vuole monitorare tramite il paramentro `cnt` (da 5 a 50).
Con i dati ottenuti creeremo un JSONObject per quante città si vogliono visualizzare, con: temp, tempMin, tempMax, id (della città), nome (della città).

* GET\save:
Salviamo nel databese i dati che abbiamo nel JSONObject aggiungendo la data, questa operazione verrà fatta ogni volta su 50 città.

* GET\stats:
Ci restituisce le statistiche per ogni città: valore massimo e minimo di temperatura, temperatura media e varianza. Per il numero di città volute e ordinate secondo un parametro scelto.


* GET\data:


* POST\filtri:
Ci restituisce le statistiche filtrate, in un JSONObject, in base alla città o alla periodicità: settimanale, mensile, 10 giorni o in base ad una sottostringa, come citta che iniziiano con *A*.

# UML
Usiamo questo linguaggio di modellazione e di specifica per sviluppare il nostro programma.

## Use Case Diagram -provvisorio- 
In questo diagramma possiamo vedere come si sviluppa il programma. Abbiamo l'attore *utente* che si interfaccia al programma e richiede i dati sulle temperature, in base al numero di citta, cosi ottendendo le statistiche che sono poi ordinate e filtrate. Mentre l'attore non umano, *OpenWeather*, gestisce il programma. Quindi trammite una chiamata all'API di OpenWeather soddisfa le richieste dell'utente e salva tutti i dati di tutte le citta ogni 5 ore.

![Diagramma dei casi d'uso (1)](https://user-images.githubusercontent.com/44706799/110305538-145c9f80-7ffd-11eb-8ff8-880c78e7caaa.jpg)

## Class Diagram -provvisorio-

![Diagramma delle classi (1)](https://user-images.githubusercontent.com/44706799/110305557-19215380-7ffd-11eb-80cd-dcbd86479a32.jpg)

## Sequence Diagram

![Diagramma delle sequenze](https://user-images.githubusercontent.com/44706799/110305616-2b9b8d00-7ffd-11eb-9335-2cd0d0d514b1.jpg)

# Eccezioni

# Test

# Autori
Il programma è stato sviluppato equamente da Mattia Beccerica, Alessandro Fermanelli e Giulio Gattari.


