<a name="indice"></a>
# :house: WeatherCloseRomeApp
Data la città di Roma, abbiamo scritto questo programma in modo di visualizzare tutte le informazioni attuali relative alla temperatura delle città circostanti Roma.

## :pushpin: **Indice contenuti**

* [Introduzione](#introduzione)
* [Diagrammi UML](#uml)
  * [Casi d'uso](#ucs)
  * [Classi](#ucd)
  * [Sequenze](#ucs)
* [API](#api)
* [Rotte](#rotte)
  * [Temperature](#temp)
  * [Salvataggio dati](#save)
  * [Salvataggio ogni 5 Ore](#save5)
  * [Date Disponibili](#date)  
  * [Statistiche](#stats)
  * [Filtri](#filters)
* [Eccezioni](#eccezioni)
  * [Data non valida](#id)
  * [Campo non valido](#if)
  * [Numero citta non valido](#in)
  * [Date introvabili](#sd)
  * [Periodo sbagliato](#wp)
* [Test](#test)
* [Software e Librerie](#soft)
* [Installazione](#funz)
* [Autori](#autori)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="introduzione"></a>
# :scroll: Introduzione
Abbiamo implentato un servizio meteo che ci permette di monitorare le temperature nelle citta circostanti a Roma. La ricerca avviene inserendo il numero di citta che si vogliono visualizzare, per un minimo di 1 ad un massimo di 50. Salveremo le informazioni delle 50 città ogni 5 ore, in un database, cosi da avere tutti i dati pronti per il calcolo di statistiche. L'utente puo consultare diverse statistiche, ovvero valori minimi, massimi, media e varianza delle temperature per ogni città ed ordinarli secondo i precedenti paramentri.
Infine possiamo filtrare le statistiche in base al numero delle città, alla periodicità (giornaliera, settimanale, mensile o range personalizzabile) o in base ad una sottostringa contenuta nel nome della città (Citta che iniziano per A).

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna ad introduzione](#introduzione) [:scroll:](#introduzione)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="uml"></a>
# :chart_with_upwards_trend: Diagrammi UML
Usiamo questo linguaggio di modellazione e di specifica per sviluppare il nostro programma.  
Abbiamo creato 3 diagrammi: [Casi d'uso](#ucs), [Classi](#ucd), [Sequenze](#ucs).

<a name="ucs"></a>
## Use Case Diagram -provvisorio- 
In questo diagramma possiamo vedere come si sviluppa il programma. Abbiamo l'attore *utente* che si interfaccia al programma e richiede i dati sulle temperature, in base al numero di citta, cosi ottendendo le statistiche che sono poi ordinate e filtrate. Mentre l'attore non umano, *OpenWeather*, gestisce il programma. Quindi trammite una chiamata all'API di OpenWeather soddisfa le richieste dell'utente e salva tutti i dati di tutte le citta ogni 5 ore.

![Diagramma dei casi d'uso (1)](https://user-images.githubusercontent.com/44706799/110305538-145c9f80-7ffd-11eb-8ff8-880c78e7caaa.jpg)

<a name="ucd"></a>
## Class Diagram -provvisorio-
Come possiamo vedere abbiamo diversi package:

- Package controller : contiene la classe controller che gestisce tutte le rotte del programma.
- Package service    : contiene le classi TempService e TempServiceImpl. Il primo contiene l'interfaccia e inizializza i metodi. Il secondo li gestisce e implementa. 
- Package models     : contiene le classi City, FilterBody e SaveModel, ovvero tutti i body dei JSONObject rispettivi???
- Package utilities  : contiene le classi Stats e Filters, rispettivamente gestiscono le statistiche e i filtri
- Package exceptions : contiene tutte le [Eccezioni](#eccezioni)
+ WeatherCloseRomeApp: classe contenente il main che avvia l'applicazione Spring

![Diagramma delle classi (1)](https://user-images.githubusercontent.com/44706799/110305557-19215380-7ffd-11eb-80cd-dcbd86479a32.jpg)

<a name="usd"></a>
## Sequence Diagram

![Diagramma delle sequenze](https://user-images.githubusercontent.com/44706799/110305616-2b9b8d00-7ffd-11eb-9335-2cd0d0d514b1.jpg)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice)  ------- [ritorna ad Diagrammi UML](#uml) [:chart_with_upwards_trend:](#uml)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="api"></a>
# :honeybee: API
Sono fondamentali per il funzionamento del programma e per la raccolta dati. Con il programma *Postman* possiamo usare le rotte, sotto elencate,  
per far funzionare il nostro servizio.
Per rispondere alle richieste degli utenti e avere un database abbiamo usato l'api: https://openweathermap.org/current#cycle.
L'API restituisce i dati delle città disposte all'interno di un cerchio, definito dal un punto centrale ( `lat`, `lon`), nel nostro caso roma (41.902782, 12.496365),     
e dal numero previsto di città ( `cnt`) attorno a questo punto.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna ad API](#api) [:honeybee:](#api)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="rotte"></a>
# 🚩 Rotte
Le rotte definite sono le seguenti:

N° | Tipo | Rotta | Descrizione
----- | ------------ | -------------------- | ----------------------
[1](#1) | ` GET ` | [`/temp`](#temp) | *restituisce un JSONArray che contiene: temperatura,  temp. max e min, pressione e umidità.*
[2](#2) | ` GET ` | [`/save`](#save) | *aggiorna il databese.*
[3](#3) | ` GET ` | [`/saveEvery5Hours`](#save5) | *aggiorna il databese ogni 5 ore.*
[4](#4) | ` GET ` | [`/stats`](#stats) | *restituisce le statistiche sulle temperature di ogni citta: valori minimi, massimi, media e varianza*
[5](#5) | ` GET ` | [`/date`](#date) | *restituisce le date nel file in una stringa*
[6](#6) | ` POST ` | [`/filters`](#filters) | *restituisce un JSONObject con le statistiche filtrate in base alla città, periodicità o sottostringa.*

<a name="temp"></a>
### :round_pushpin: GET/temp:
L'utente puo specificare quante citta vuole monitorare tramite il paramentro `number` (da 1 a 50),altrimenti verranno riportate 7 città.
Con i dati ottenuti creeremo un JSONObject per quante città si vogliono visualizzare, con: temp, tempMin, tempMax, id (della città), nome (della città).
(`number` è l'equivalente di cnt nel programma)

:mag: ESEMPIO 1 (/temp - senza specificare il numero di città) :

![Screenshot (82)](https://user-images.githubusercontent.com/44706799/110312160-06128180-8005-11eb-8591-42d49a66040a.png)
```ruby
[
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Trevi",
        "id": 6545158,
        "tempMin": 283.15
    },
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Pigna",
        "id": 6545151,
        "tempMin": 283.15
    },
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Rome",
        "id": 3169070,
        "tempMin": 283.15
    },
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Vatican City",
        "id": 6691831,
        "tempMin": 283.15
    },
    {
        "tempMax": 284.82,
        "temp": 284.18,
        "name": "State of the Vatican City",
        "id": 3164670,
        "tempMin": 283.71
    },
    {
        "tempMax": 284.82,
        "temp": 283.92,
        "name": "Via di Villa Chigi",
        "id": 7670575,
        "tempMin": 282.59
    },
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Ardeatino",
        "id": 7521552,
        "tempMin": 283.15
    }
]
```

:mag: ESEMPIO 2 (/temp - con 2 città) :

![Screenshot (84)](https://user-images.githubusercontent.com/44706799/110312817-e16ad980-8005-11eb-8546-6cabdf50fef5.png)
```ruby
[
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Trevi",
        "id": 6545158,
        "tempMin": 283.15
    },
    {
        "tempMax": 284.82,
        "temp": 284.08,
        "name": "Pigna",
        "id": 6545151,
        "tempMin": 283.15
    }
]

```

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="save"></a>
### :round_pushpin: GET/save:
Salviamo nel databese i dati che abbiamo nel JSONObject aggiungendo la data, questa operazione verrà fatta ogni volta su 50 città.  

:mag: ESEMPIO :

![Screenshot (129)](https://user-images.githubusercontent.com/44706799/110497692-fc694680-80f6-11eb-966e-9a6047b1fe75.png)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="save5"></a>
### :round_pushpin: GET/saveEvery5Hours:
Salviamo nel databese i dati che abbiamo nel JSONObject aggiungendo la data, questa operazione verrà fatta ogni 5 ore su 50 città.  

:mag: ESEMPIO :

![Screenshot (131)](https://user-images.githubusercontent.com/44706799/110611395-7816d200-818f-11eb-84dd-41c4e209530a.png)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="stats"></a>
### :round_pushpin: GET/stats:
Ci restituisce le statistiche per ogni città: valore massimo e minimo di temperatura, temperatura media e varianza.  
Dovremmo aggiungere un parametro alla key `field`, che puo essere: Massimo, Minimo, Media e Varianza. (come nell'esempio 2)

:mag: ESEMPIO 1 (/stats - ordinati per la vicinanza al centro di roma, default):

![Screenshot (89)](https://user-images.githubusercontent.com/44706799/110314929-cbaae380-8008-11eb-8aed-94978654326d.png)
```ruby
[
    {
        "Massimo": 285.02,
        "name": "Trevi",
        "Media": 283.52799999999996,
        "Minimo": 282.63,
        "Varianza": 0.7784559999999934,
        "id": 6545158
    },
    {
        "Massimo": 285.02,
        "name": "Pigna",
        "Media": 283.534,
        "Minimo": 282.64,
        "Varianza": 0.7721439999999989,
        "id": 6545151
    },
    
    .
    .
    .
    
    {
        "Massimo": 283.57,
        "name": "Rocca Priora",
        "Media": 282.686,
        "Minimo": 281.83,
        "Varianza": 0.32214400000000254,
        "id": 3169149
    },
    {
        "Massimo": 288.96,
        "name": "Ariccia",
        "Media": 285.4055454545449,
        "Minimo": 279.71,
        "Varianza": 6.722560763085377,
        "id": 3182851
    }
]
```

:mag: ESEMPIO 2 (/stats - ordinati per il valore max) :

![Screenshot (109)](https://user-images.githubusercontent.com/44706799/110317394-63f69780-800c-11eb-8f93-cbcb9380461a.png)
```ruby
[
    {
        "Massimo": 288.96,
        "name": "Ariccia",
        "Media": 285.39936555891177,
        "Minimo": 279.71,
        "Varianza": 6.714853978149156,
        "id": 6545158
    },
    {
        "Massimo": 285.84,
        "name": "Guidonia",
        "Media": 283.23500000000007,
        "Minimo": 281.76,
        "Varianza": 1.9915583333333409,
        "id": 6545151
    },
    .
    .
    .
    {
        "Massimo": 283.57,
        "name": "Rocca Priora",
        "Media": 282.59499999999997,
        "Minimo": 281.83,
        "Varianza": 0.3098583333333377,
        "id": 3182851
    }
]
```

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="date"></a>
### :round_pushpin: GET/date:
Ci  mostra le date disponibili nel database in cui sono state salvate le temperature (il server non ha tutte le date perche è attivato da noi, solo in alcuni momenti) 

:mag: ESEMPIO :

![Screenshot (113)](https://user-images.githubusercontent.com/44706799/110461223-7685d500-80cf-11eb-981d-adb150a9b5ee.png)
```ruby
[
    {
        "data": "2021-03-02"
    },
    {
        "data": "2021-03-04"
    },
  .
  .
  .
    {
        "data": "2021-03-09"
    }
]
```

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="filters"></a>
### :round_pushpin: POST/filtres:
Ci restituisce le statistiche filtrate, in un JSONObject, in base alla città o alla periodicità: settimanale, mensile, 10 giorni o in base ad una sottostringa, come citta che iniziiano con *A*. Nel caso si voglia usare il CustomPeriod bisogna impostare il campo period come `null` (ovvero = ""). Se si immette un numero positivo si vedranno le date future al quella inserita, mentre nel caso negativo si vedranno le date passate.
Esempio e spiegazione del Body: 
```ruby
{
"count" : "xx",                // numero di città da visualizzare (da 1 a 50).
"period" : "xxxx",         // periodo delle statistiche (daily, weekly, monthly).
"data" : "xxxx-xx-xx",      // data di partenza delle statistiche (anno-mese-giorno).
"name" : "xxxx",                // Seleziona le città a seconda di quale sottostringa scriviamo (esempio 2.
"customPeriod" : "xx"         // conta quante date vogliamo vedere.
}
```

:mag: ESEMPIO 1 (/filters - 1 città, periodo  giornaliero, data 8\3\21) :

![Screenshot (91)](https://user-images.githubusercontent.com/44706799/110316197-adde7e00-800a-11eb-9c76-e2052a547894.png)
```ruby
[
    {
        "Massimo": 284.27,
        "name": "Trevi",
        "Media": 284.005,
        "Minimo": 283.74,
        "Varianza": 0.07022499999999277,
        "id": 6545158
    }
]
```

:mag: ESEMPIO 2 (/filters - ricerca tra tutte le citta di nomi contenenti "castel", periodo  giornaliero, data 8\3\21) :

![Screenshot (111)](https://user-images.githubusercontent.com/44706799/110329452-e4bd8f80-801c-11eb-960c-df80a7999411.png)
```ruby
[
    {
        "Massimo": 284.19,
        "name": "Castel Gandolfo",
        "Media": 283.8066666666667,
        "Minimo": 283.49,
        "Varianza": 0.083888888888886,
        "id": 3179680
    },
    {
        "Massimo": 283.77,
        "name": "Castelnuovo di Porto",
        "Media": 283.46,
        "Minimo": 282.97,
        "Varianza": 0.12286666666665236,
        "id": 3179521
    }
]
```

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna a Rotte](#rotte) [🚩](#rotte)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="eccezioni"></a>
# :no_entry: Eccezioni
Abbiamo scritto 5 eccezioni: [InvalidDate](#id), [InvalidField](#if), [InvalidNumber](#in), [ShortDatabase](#sd), [WrongPeriod](#wp).

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="id"></a>
### :x: InvalidDateException.java   :
Controlla la data nella richiesta di filtraggio. Nel caso la data richiesta sia incorretta, si chiede di controllare la rotta `/date` per le date disponibili.

:mag: ESEMPIO (`Data inserita incorretta. Controllare la rotta \"/date\" per le date disponibili.`) :

![Screenshot (117)](https://user-images.githubusercontent.com/44706799/110466383-062e8200-80d6-11eb-8943-0562da92b201.png)
```ruby
{
    "timestamp": "2021-03-09T11:43:37.275+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "InvalidDateException: Data inserita incorretta.\r\n\tat it.univpm.WeatherCloseRomeApp.utilities.Filter.filterPeriod(Filter.java:108)\r\n\tat... 
    .
    .
    .
    "message": "Data inserita incorretta. Controllare la rotta \"/date\" per le date disponibili.",
    "path": "/filters"
}
```

<a name="if"></a>

### :x: InvalidFieldException.java   :
Controlla il campo di filtraggio richiesto. Nel caso la richiesta sia incorretta ci ridà un messaggio di errore con i campi disponibili nel filtraggio.

:mag: ESEMPIO (`Campo errato. I campi disponibili sono massimo,minimo,media,varianza.`) :

![Screenshot (125)](https://user-images.githubusercontent.com/44706799/110495944-4c470e00-80f5-11eb-82a6-d0a05d15ed1f.png)
```ruby
{
    "timestamp": "2021-03-09T15:33:18.067+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "InvalidFieldException: campo errato.\r\n\tat it.univpm.WeatherCloseRomeApp.utilities.Stats.orderStats(Stats.java:134)\r\n\tat...
    .
    .
    .
    "message": "Campo errato. I campi disponibili sono massimo,minimo,media,varianza.",
    "path": "/stats"
}
```

<a name="in"></a>

### :x: InvalidNumberException.java   :
Controlla se il numero di città richieste è un numero tra 1 e 50, nel caso non sia così il programma ci restituirà un messaggio di errore con il numerodi città accettabile.

:mag: ESEMPIO (`Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi).`) :

![Screenshot (123)](https://user-images.githubusercontent.com/44706799/110493253-e2c60000-80f2-11eb-9991-7f7f221c4581.png)
```ruby
{
    "timestamp": "2021-03-09T15:15:48.494+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "InvalidNumberException: Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)\r\n\tat...
    .
    .
    .
    "message": "Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)",
    "path": "/temp"
}
```

<a name="sd"></a>
### :x: ShortDatabaseException.java   :
Controlla se nel database contiene le informazioni sufficenti per creare statistiche del periodo scelto. In caso negato avremmo un messaggio di errore con la richiesta di scelgiere un periodo piu breve.

:mag: ESEMPIO (`Database non contiene abbastanza informazioni. Scegliere un periodo più breve.`) :

![Screenshot (121)](https://user-images.githubusercontent.com/44706799/110491675-cbd2de00-80f1-11eb-9158-62d66970d352.png)
```ruby
{
    "timestamp": "2021-03-09T15:06:05.284+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "ShortDatabaseException: database insufficente.\r\n\tat it.univpm.WeatherCloseRomeApp.utilities.Filter.filterPeriod(Filter.java:135)\r\n\tat...
    .
    .
    .
    "message": "Database non contiene abbastanza informazioni. Scegliere un periodo più breve.",
    "path": "/filters"
}
```

<a name="wp"></a>
### :x: WrongPeriodException.java   :
Controlla se il periodo richiesto nel filtraggio sia corretto/esista, nel caso contrario avremmo un messaggio di errore con riportati i periodi selezionabili.

:mag: ESEMPIO (`Periodo inserito incorretto. Scegliere tra: daily, weekly, monthly, oppure null se si vuole customperiod.`) :

![Screenshot (127)](https://user-images.githubusercontent.com/44706799/110496477-cb3c4680-80f5-11eb-9bd1-e26409856d6d.png)
```ruby
{
    "timestamp": "2021-03-09T15:35:35.955+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "trace": "InvalidDateException: Data inserita incorretta.\r\n\tat it.univpm.WeatherCloseRomeApp.controller.TempController.filters(TempController.java:193)\r\n\tat...
    .
    .
    .
    "message": "Periodo inserito incorretto. Scegliere tra: daily,weekly,monthly, oppure null se si vuole customperiod.",
    "path": "/filters"
}
```

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna alle Eccezioni](#eccezioni) [:no_entry:](#eccezioni)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="test"></a>
# :ok: Test
Abbiamo fatto diversi test per verificare il giusto e corretto funzionamento del programma.
Package dei Test effettuati:
- Service : abbiamo testato il giusto funzionamento del costruttore city, la corretta generazione dell'eccezione InvalidNumberException e la verifica della funzionalità del metodo scambia.
- Utilities : abbiamo testato il giusto funzionamento delle eccezioni.

![ClassDiagram TestClass](https://user-images.githubusercontent.com/44706799/110626151-ab159180-81a0-11eb-9145-d4e5aa42ee82.jpg)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna ai Test](#test) [:ok:](#test)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="soft"></a>
# :inbox_tray: Strumenti utilizzati
Software utilizzati:
* [Eclipse](https://www.eclipse.org/) - IDE che abbiamo usato per scrivere il programma
* [PostMan](https://www.postman.com/) - Programma per testare il funzionamento del programma e l'iterazione con l'API
* [UML Designer](http://www.umldesigner.org/) - programma usato per creare i diagrammi UML 
* [Github](https://github.com/) -  è un servizio che abbiamo usato per hostare il nostro progetto   
*  

Librerie usate in Eclipse
* Spring Tools 4 (quindi Spring Boot)
* Maven Integration for Eclipse (e m2e)
* JSON simple (dependency)
* JUnit (per i test)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna ai Software e Librerie](#soft) [:inbox_tray:](#soft)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="funz"></a>
# :computer: Installazione del nostro programma
* avviare Eclipse
* Importare questo progetto. [(come importare su progetto su eclipse)](https://qastack.it/programming/6760115/importing-a-github-project-into-eclipse) 
* Lanciare l'applicazione `WeatherCloseRomeAppApplication.java` con *Spring Boot App* (in *run as*)
* Avviare Postman.
* Interrogare le API usate dal nostro programma usando le [Rotte](#rotte), come negli esempi.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna alla Installazione](#funz) [:computer:](#funz)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="autori"></a>
# Autori
Il programma è stato sviluppato equamente da Mattia Beccerica, Alessandro Fermanelli e Giulio Gattari.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice) ------- [ritorna agli Autori](#autori)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

