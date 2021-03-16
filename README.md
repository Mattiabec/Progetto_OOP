<a name="indice"></a>
# :house: WeatherCloseRomeApp
Data la città di Roma, abbiamo scritto questo programma in modo di visualizzare tutte le informazioni attuali relative alla temperatura delle città circostanti Roma.

## :pushpin: **Indice contenuti**

* [Introduzione](#introduzione)
* [Diagrammi UML](#uml)
  * [Casi d'uso](#ucs)
  * [Classi](#ucd)
  * [Sequenze](#ucs)
  * [Gerarchia](#ger)
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
## :bar_chart: Use Case Diagram -provvisorio- 
In questo diagramma possiamo vedere come si sviluppa il programma. Abbiamo l'attore *utente* che si interfaccia al programma e richiede i dati sulle temperature, in base al numero di citta, cosi ottendendo le statistiche che sono poi ordinate e filtrate. Mentre l'attore non umano, *OpenWeather*, gestisce il programma. Quindi trammite una chiamata all'API di OpenWeather soddisfa le richieste dell'utente e salva tutti i dati di tutte le citta ogni 5 ore.

![Diagramma dei casi d'uso (1)](https://user-images.githubusercontent.com/44706799/110305538-145c9f80-7ffd-11eb-8ff8-880c78e7caaa.jpg)

<a name="ucd"></a>
## :bar_chart: Class Diagram -provvisorio-
Come possiamo vedere abbiamo diversi package:

- Package controller : contiene la classe controller che gestisce tutte le rotte del programma.
- Package service    : contiene le classi TempService e TempServiceImpl. Il primo contiene l'interfaccia e inizializza i metodi. Il secondo li gestisce e implementa. 
- Package models     : contiene le classi City, FilterBody e SaveModel, ovvero tutti i body dei JSONObject rispettivi???
- Package utilities  : contiene le classi Stats e Filters, rispettivamente gestiscono le statistiche e i filtri
- Package exceptions : contiene tutte le [Eccezioni](#eccezioni)
+ WeatherCloseRomeApp: classe contenente il main che avvia l'applicazione Spring

![OOP Class Diagram2 0 (1)](https://user-images.githubusercontent.com/44706799/111297262-b6a80300-864d-11eb-9a0c-fcf3f4aab02d.jpg)

<a name="usd"></a>
## :bar_chart: Sequence Diagram - provvisorio- 
Definisce la sequenza temporale di ogni rotta, ovvero ciò che succede per ogni chiamata fatta tramite postman.

![Diagramma delle sequenze](https://user-images.githubusercontent.com/44706799/110305616-2b9b8d00-7ffd-11eb-9335-2cd0d0d514b1.jpg)

<a name="ger"></a>
## :bar_chart: Hierarchy Diagram - provvisorio- 
Questo scherma delle gerarchie ci mostra come abbiamo ordinato e gestito i diversi package nel progetto.

![NewModel Package Hierarchy](https://user-images.githubusercontent.com/44706799/111297926-7ac16d80-864e-11eb-8a38-b3a86f64e786.jpg)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
[indice](#indice) [:house:](#indice)  ------- [ritorna ad Diagrammi UML](#uml) [:chart_with_upwards_trend:](#uml)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="api"></a>
# :honeybee: API
Sono fondamentali per il funzionamento del programma e per la raccolta dati. Con il programma *Postman* possiamo usare le rotte, sotto elencate, per far funzionare il nostro servizio.
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
L'utente puo specificare quante citta vuole monitorare tramite il paramentro `number` (da 1 a 50), altrimenti verranno riportate 7 città se non viene aggiunto nessun paramentro.
Con i dati ottenuti creeremo un JSONObject per quante città si vogliono visualizzare, con: temp, tempMin, tempMax, id (della città), nome (della città).
(`number` è l'equivalente di cnt nel programma)

url = `http://localhost:8080/temp` --- key = `number` --- value = `da 1 a 50`

:mag: ESEMPIO (/temp - con 2 città) :
(url = `http://localhost:8080/temp?number=2`)

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
Salviamo nel databese i dati che abbiamo nel JSONObject aggiungendo la data. Vedremo come risultato il percorso(path) dov'è salvato il file, lo stato(status) e la data(time). Questa operazione verrà fatta ogni volta su 50 città.  

url = `http://localhost:8080/save`

:mag: ESEMPIO :
![Screenshot (138)](https://user-images.githubusercontent.com/44706799/111212153-8c176500-85cf-11eb-948f-e15fce1f8ea0.png)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="save5"></a>
### :round_pushpin: GET/saveEvery5Hours:
Salviamo nel databese i dati che abbiamo nel JSONObject aggiungendo la data. Vedremo come risultato il percorso(path) dov'è salvato il file, lo stato(status) e la data(time). Questa operazione verrà fatta ogni 5 ore su 50 città.  

url = `http://localhost:8080/saveEvery5Hours` 

:mag: ESEMPIO :
![Screenshot (140)](https://user-images.githubusercontent.com/44706799/111212305-ba954000-85cf-11eb-8956-85597bd62b2a.png)

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

<a name="stats"></a>
### :round_pushpin: GET/stats:
Ci restituisce le statistiche per ogni città: valore massimo e minimo di temperatura, temperatura media e varianza.  
Dovremmo aggiungere un parametro alla key `field`, che puo essere: Massimo, Minimo, Media e Varianza. (Nel caso il campo sia vuoto, le statistiche verranno ordinate per distanza, ovvero di defaut) 

url = `http://localhost:8080/stats` --- key = `field` --- value = `Massimo o Minimo o Media o Varianza`

:mag: ESEMPIO  (/stats - ordinati per il valore massimo) :
(url = `http://localhost:8080/stats?field=massimo`)

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

url = `http://localhost:8080/date` 

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
### :round_pushpin: POST/filtres: -provvisorio-
Ci restituisce le statistiche filtrate, in un JSONObject, in base alla città o alla periodicità: settimanale, mensile, 10 giorni, custom o in base ad una sottostringa, come citta che iniziano con *A*. Possiamo selezionare quante citta vedere trammite il campo "count" (da 1 a 50). Tutto cio puo essere definito nel body. Mentre possiamo ordinare le statistiche filtrate nella sezione params di postman, la key dovrà essere "field" con valori: Massimo o Minimo o Media o Varianza. (Nel caso il parametro sia vuoto le statistiche verranno ordinate per distanza, ovvero di defaut) 
*  Periodo Custom:  Impostare il campo "period" come `custom`. Nel campo "startDate" bisogna inserire il giorno in cui si vuole iniziare a filtrare, nel campo "endDate" bisogna inserire la data di fine filtraggio. Nel caso non si immetta nulla nel campo "endDate" si deve aggiungere un numero intero nel campo "customPeriod", ovvero ogni quanti giorni fare le statistiche. Se si immette un numero positivo si vedranno le date future al quella inserita, mentre nel caso negativo si vedranno le date passate.
*  Periodo Prestabilito: Impostare il campo "period" come: daily o weekly o monthly. Poi bisogna inserire la data di inizio di filtraggio in "startDate". 
*  Ricerca Per Nome: Bisogna inserire il nome, anche parziale, nel campo "name" e impostare "count"= 50.

url = `http://localhost:8080/filters` --- key = `field` --- value = `Massimo o Minimo o Media o Varianza`

Esempio e spiegazione del Body: 
```ruby
{
"count" : "xx",                 // numero di città da visualizzare (da 1 a 50).
"period" : "xxxx",              // periodo delle statistiche (daily, weekly, monthly, custom).
"startDate" : "xxxx-xx-xx",     // data di inizio filtraggio (anno-mese-giorno).
"endDate" : "xxxx-xx-xx",       // data di fine filtraggio (anno-mese-giorno).
"customPeriod" : "xx",          // numero intero che descrive ogni quanti giorni è stata fatta la statistica
"name" : "xxxx"                 // seleziona le città a seconda di quale sottostringa scriviamo 
}
```

:mag: ESEMPIO 1 (/filters - 1 città, periodo  giornaliero, data 15\3\21 ovvero data dello screen, ordinato per distanza da roma(default)) :
(url `http://localhost:8080/filters`)

![Screenshot (145)](https://user-images.githubusercontent.com/44706799/111214218-f92bfa00-85d1-11eb-90c5-46de922493d5.png)
```ruby
[
    {
        "Massimo": 286.98,
        "name": "Trevi",
        "Media": 285.5933333333333,
        "Minimo": 283.73,
        "Varianza": 1.7860222222222093,
        "id": 6545158
    }
]
```

:mag: ESEMPIO 2 (/filters - ricerca tra tutte le citta di nomi contenenti "cast", periodo settimanale, dal 9\3\21 (al 15\3\21), ordinato per varianza) :
(url `http://localhost:8080/filters?field=varianza`)

![Screenshot (143)](https://user-images.githubusercontent.com/44706799/111214031-b8cc7c00-85d1-11eb-922e-ac57f3eed6fd.png)
```ruby
[
    {
        "Massimo": 289.0,
        "name": "Castelnuovo di Porto",
        "Media": 284.6311764705883,
        "Minimo": 282.97,
        "Varianza": 3.63524567474045,
        "id": 3179521
    },
    {
        "Massimo": 289.2,
        "name": "Castel Gandolfo",
        "Media": 285.7747058823529,
        "Minimo": 283.39,
        "Varianza": 2.023154325259515,
        "id": 3179680
    }
]
```

:mag: ESEMPIO 3 (/filters - ricerca di 2 città, periodo custom, dal 10\3\21 al 14\3\21, ordinato per distanza da roma(default)) :
(url `http://localhost:8080/filters`)

![Screenshot (147)](https://user-images.githubusercontent.com/44706799/111214925-d2ba8e80-85d2-11eb-95dc-73805cf29b59.png)
```ruby
[
    {
        "Massimo": 289.57,
        "name": "Trevi",
        "Media": 286.5914285714286,
        "Minimo": 284.1,
        "Varianza": 3.506726530612215,
        "id": 6545158
    },
    {
        "Massimo": 289.58,
        "name": "Pigna",
        "Media": 286.5957142857143,
        "Minimo": 284.1,
        "Varianza": 3.519853061224448,
        "id": 6545151
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

:mag: ESEMPIO :

![Screenshot (151)](https://user-images.githubusercontent.com/44706799/111303618-01794900-8655-11eb-9cf8-d8664d066efa.png)
```ruby
    {
        "ERROR": "InvalidDateException: Data inserita incorretta."
    }
```

<a name="if"></a>

### :x: InvalidFieldException.java   :
Controlla il campo di filtraggio richiesto. Nel caso la richiesta sia incorretta ci ridà un messaggio di errore con i campi disponibili nel filtraggio.

:mag: ESEMPIO :

![Screenshot (125)](https://user-images.githubusercontent.com/44706799/110495944-4c470e00-80f5-11eb-82a6-d0a05d15ed1f.png)
```ruby
   {
        "ERROR": "InvalidFieldException: campo errato."
   }
```

<a name="in"></a>

### :x: InvalidNumberException.java   :
Controlla se il numero di città richieste è un numero tra 1 e 50, nel caso non sia così il programma ci restituirà un messaggio di errore con il numerodi città accettabile.

:mag: ESEMPIO :

![Screenshot (123)](https://user-images.githubusercontent.com/44706799/110493253-e2c60000-80f2-11eb-9991-7f7f221c4581.png)
```ruby
    {
        "ERROR": "InvalidNumberException: Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)"
    }
```

<a name="sd"></a>
### :x: ShortDatabaseException.java   :
Controlla se nel database contiene le informazioni sufficenti per creare statistiche del periodo scelto. In caso negato avremmo un messaggio di errore con la richiesta di scelgiere un periodo piu breve.

:mag: ESEMPIO :

![Screenshot (153)](https://user-images.githubusercontent.com/44706799/111304343-e9ee9000-8655-11eb-8aea-ab519a4caedc.png)
```ruby
    {
        "ERROR": "ShortDatabaseException: database insufficente."
    }
```

<a name="wp"></a>
### :x: WrongPeriodException.java   :
Controlla se il periodo richiesto nel filtraggio sia corretto/esista, nel caso contrario avremmo un messaggio di errore con riportati i periodi selezionabili.

:mag: ESEMPIO :

![Screenshot (156)](https://user-images.githubusercontent.com/44706799/111304671-494ca000-8656-11eb-8cc6-2c2e0fc1f9bd.png)
```ruby
    {
        "ERROR": "WrongPeriodException: periodo inserito incorretto."
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

