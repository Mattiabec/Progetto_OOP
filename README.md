# WeatherCloseRomeApp
Data la città di Roma, abbiamo scritto questo programma in modo di visualizzare tutte le informazioni attuali relative alla temperatura delle città circostanti Roma.

# Introduzione
Abbiamo implentato un servizio meteo che ci permette di monitorare le temperature nelle citta circostanti a Roma. La ricerca avviene inserendo il numero di citta che si vogliono visualizzare, per un minimo di 5 ad un massimo di 50. Salveremo le informazioni delle 50 città ogni 5 ore, in un database, cosi da avere tutti i dati pronti per il calcolo di statistiche. L'utente puo consultare diverse statistiche come i valori periodici riguardanti valori minimi, massimi, media e varianza delle temperature per ogni città.
Inoltre si possono ordinare le statistiche in base ai campi selezionati: valori minimi, massimi, media e varianza. Infine possiamo filtrare le statistiche in base al numero delle città, alla periodicità (giornaliera, settimanale, mensile o range personalizzabile) o in base ad una sottostringa contenuta nel nome della città (Citta che iniziano per A).

# API
Per rispondere alle richieste degli utenti e avere un database abbiamo usato l'api: https://openweathermap.org/current#cycle






