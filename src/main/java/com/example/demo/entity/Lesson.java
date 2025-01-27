package com.example.demo.entity;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.persistence.*;

@Entity
@Table(name = "Lessons")
/*--## AI Assistant
Questa classe Java rappresenta un'entità JPA (Java Persistence API) chiamata `Lesson`. È una classe mappata su una tabella di database denominata `Lessons`. Ecco una spiegazione dettagliata del codice:

### Annotazioni principali e mappatura JPA

1. **`@Entity`**:
   Questa annotazione definisce che la classe `Lesson` è un'entità JPA. Significa che sarà mappata a una tabella del database.

2. **`@Table(name = "Lessons")`**:
   Specifica che questa entità è mappata alla tabella `Lessons` nel database.

### Proprietà della classe e mappature alle colonne della tabella

1. **`@Id`**:
   Indica che il campo `id` è la chiave primaria della tabella.

2. **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**:
   Indica che il valore di `id` viene generato automaticamente dal database utilizzando una strategia di incremento automatico (ad esempio, un contatore).

3. **`@Column(length = 450, nullable = false)`**:
   - Il campo `title` è mappato a una colonna con una lunghezza massima di 450 caratteri.
   - Il parametro `nullable = false` indica che questa colonna non può contenere valori nulli.

4. **`@Column(columnDefinition = "TEXT")`**:
   Il campo `description` è mappato a una colonna di tipo `TEXT` (adatto per contenuto di lunghezza variabile, tipicamente usato per descrizioni più lunghe).

5. **`@Column(length = 100)`**:
   Il campo `duration` è mappato a una colonna con una lunghezza massima di 100 caratteri.

6. **`@Column(name = "`Order`")`**:
   - Il campo `order` è mappato a una colonna chiamata `Order`.
   - La notazione con backtick (\`` `)\` serve per gestire nomi di colonne che potrebbero essere parole riservate nel database (come "ORDER").

7. **`@Column(length = 45)`**:
   Il campo `rowVersion` è mappato a una colonna con una lunghezza massima di 45 caratteri. Potrebbe essere utilizzato per funzioni di versionamento o identificazione della riga.

8. **`@ManyToOne` e `@JoinColumn(name = "CourseId", nullable = false)`**:
   - Il campo `course` definisce una relazione molti-a-uno con un'altra entità (`Course`).
   - `@JoinColumn` specifica che il campo `course` è mappato a una colonna chiamata `CourseId` nella tabella corrente (`Lessons`).
   - `nullable = false` indica che questa relazione è obbligatoria e non può essere `null`.

### Getter e Setter

La classe contiene i metodi getter e setter per ogni campo. Questi metodi sono utilizzati per accedere e modificare i valori dei campi:

- I **getter** (come `getId()`, `getTitle()`, ecc.) restituiscono il valore del campo.
- I **setter** (come `setId(int id)`, `setTitle(String title)`, ecc.) consentono di modificare il valore del campo.

### Riassunto del modello di dati

- La tabella `Lessons` avrà colonne corrispondenti ai campi della classe (`id`, `title`, `description`, `duration`, `order`, `rowVersion`, e `CourseId`).
- La relazione molti-a-uno con `Course` consente di collegare ogni `Lesson` a un corso specifico, utilizzando la colonna `CourseId`.
- La struttura prevede vincoli come la non nullabilità per alcune colonne (`title`, `CourseId`).

### Applicazioni

Questa classe fa parte di un'applicazione basata su Spring Data JPA e viene utilizzata per:

1. **Rappresentare i dati** di una lezione nella programmazione orientata agli oggetti.
2. **Gestire la persistenza** dei dati nel database mediante il framework JPA, consentendo operazioni CRUD (Create, Read, Update, Delete) con semplicità.
3. Collegarsi ad altre entità (es. `Course`) sfruttando relazioni di tipo JPA.

In sintesi, la classe `Lesson` è un modello che mappa la struttura dei dati della tabella `Lessons`, rispettando vincoli e configurazioni personalizzate per ogni colonna.
*/
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "Id")
    private Integer id;

    @Column(nullable = false, length = 450)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 100)
    private String duration;
    @Column(length = 100)
    private String orderLesson = "1000";
    @Column(length = 450)
    private String rowVersion;
    @ManyToOne
    @JoinColumn(name = "CourseId", nullable = false)
    private Course course;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(String rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        if (duration == null || duration.isEmpty()) {
            return "00:00:00"; // Valore predefinito
        }

        try {
            LocalTime time = LocalTime.parse(duration, DateTimeFormatter.ofPattern("H:m:s"));
            return time.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Normalizza a HH:mm:ss
        } catch (DateTimeParseException e) {
            return duration; // Restituisce il valore originale se non è valido
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
