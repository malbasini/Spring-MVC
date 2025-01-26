package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Courses")
/*--## AI Assistant
Il codice fornito definisce una classe Java, chiamata `Course`, che rappresenta un'entità di business utilizzata in ambito persistente, probabilmente in un'applicazione Spring Data JPA collegata a un database. Analizziamo in dettaglio i vari aspetti del codice:

### **Annotazioni principali**
1. **`@Entity`**:
   - Indica che questa classe rappresenta una tabella nel database.
   - Utilizzata in JPA (Java Persistence API) per mappare l'entità a una tabella del database.

2. **`@Table(name = "Courses")`**:
   - Specifica il nome della tabella nel database a cui questa entità è mappata, in questo caso una tabella chiamata `Courses`.

3. **`@Id`**:
   - Indica che il campo `id` è la chiave primaria della tabella.

4. **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**:
   - Specifica che il valore dell'`id` sarà generato automaticamente dal database, tipicamente usando una strategia di incremento automatico.

5. **`@Column`**:
   - Annotazione utilizzata per personalizzare ogni colonna della tabella rispetto ai suoi vincoli, nomi e definizioni nel database.

6. **`@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)`**:
   - Indica una relazione **uno-a-molti** con l'entità `Lesson`.
   - `mappedBy = "course"`: Specifica che la relazione è gestita dall'attributo `course` nella classe `Lesson`.
   - `cascade = CascadeType.ALL`: Ogni operazione (persist, merge, remove, ecc.) applicata a un oggetto `Course` sarà automaticamente propagata ai relativi oggetti `Lesson`.
   - `orphanRemoval = true`: Se un `Lesson` viene rimosso dalla lista `lessons`, sarà eliminato dal database.

---

### **Attributi della classe `Course`**
La classe contiene vari campi che rappresentano le colonne della tabella `Courses`:

1. **`id` (int)**:
   - Chiave primaria generata automaticamente.

2. **`title` (String)**:
   - Colonna con lunghezza massima di 450 caratteri.
   - Non ammessi valori `null` (specificato con `nullable = false`).

3. **`description` (String)**:
   - Colonna definita come tipo `LONGTEXT` nel database, per ospitare grandi quantità di testo.

4. **`imagePath` (String)**:
   - Contiene il percorso di un'immagine legata al corso.

5. **`author` (String)**:
   - Autore del corso, con una lunghezza massima di 450 caratteri.

6. **`email` (String)**:
   - Colonna per la memorizzazione dell'email, presumibilmente dell'autore, con limitazione di 450 caratteri.

7. **`rating` (BigDecimal)**:
   - Rappresenta la valutazione del corso come numero decimale.
   - Precisione massima di 18 cifre, di cui 1 decimale (specificato tramite `precision = 18, scale = 1`).

8. **`fullPriceAmount` (BigDecimal)**:
   - Prezzo intero del corso.
   - Precisione con al massimo 18 cifre e 2 decimali.

9. **`fullPriceCurrency` (String)**:
   - Valuta relativa al prezzo intero del corso (es. "USD", "EUR").

10. **`currentPriceAmount` (BigDecimal)**:
    - Prezzo attuale, che può differire dal prezzo intero.
    - Precisione simile a `fullPriceAmount`.

11. **`currentPriceCurrency` (String)**:
    - Valuta del prezzo attuale.

12. **`rowVersion` (String)**:
    - Probabilmente utilizzata per il controllo della versione ottimistica nelle operazioni di aggiornamento.

13. **`status` (String)**:
    - Stato del corso.

14. **`lessons` (List<Lesson>)**:
    - Lista di lezioni associate al corso trattato.
    - Rappresenta una relazione uno-a-molti con l'entità `Lesson`.
    - Qualsiasi `Lesson` rimosso dalla lista sarà rimosso dal database grazie a `orphanRemoval = true`.

---

### **Metodi getter e setter**
- Ogni attributo della classe ha un rispettivo metodo **`get`** (per leggere il valore) e **`set`** (per impostare il valore).
- Questi metodi seguono le convenzioni di JavaBean e promuovono l'incapsulamento, permettendo un accesso controllato ai campi privati della classe.

---

### **Riepilogo**
La classe `Course` è una mappatura completa di un'entità `Courses` per un database relazionale.
- È configurata per rappresentare un corso associato a molte lezioni attraverso la relazione uno-a-molti.
- Gli attributi del corso contengono metadati aggiuntivi per specificare regole per il salvataggio, convalida e generazione automatica dei dati.
- Le relazioni con altre entità (es. `Lesson`) e le restrizioni sulle colonne sono chiaramente definite.

Questo tipo di classe è utilizzato per leggere e scrivere dati dal e verso il database in un'applicazione JPA/Spring Data JPA.
*/
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Title", nullable = false,length = 450,unique = true)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(length = 450)
    private String imagePath;

    @Column(length = 450)
    private String author;

    @Column(length = 450)
    private String email;

    @Column(nullable = false, columnDefinition = "DECIMAL(18,1) DEFAULT 1.0")
    private BigDecimal rating;

    @Column(name = "FullPrice_Amount", precision = 18, scale = 2)
    private BigDecimal fullPriceAmount;

    @Column(name = "FullPrice_Currency", length = 45)
    private String fullPriceCurrency;

    @Column(name = "CurrentPrice_Amount", precision = 18, scale = 2)
    private BigDecimal currentPriceAmount;

    @Column(name = "CurrentPrice_Currency", length = 45)
    private String currentPriceCurrency;

    @Column(length = 45)
    private String rowVersion;

    @Column(length = 45)
    private String status;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public BigDecimal getFullPriceAmount() {
        return fullPriceAmount;
    }

    public void setFullPriceAmount(BigDecimal fullPriceAmount) {
        this.fullPriceAmount = fullPriceAmount;
    }

    public String getFullPriceCurrency() {
        return fullPriceCurrency;
    }

    public void setFullPriceCurrency(String fullPriceCurrency) {
        this.fullPriceCurrency = fullPriceCurrency;
    }

    public BigDecimal getCurrentPriceAmount() {
        return currentPriceAmount;
    }

    public void setCurrentPriceAmount(BigDecimal currentPriceAmount) {
        this.currentPriceAmount = currentPriceAmount;
    }

    public String getCurrentPriceCurrency() {
        return currentPriceCurrency;
    }

    public void setCurrentPriceCurrency(String currentPriceCurrency) {
        this.currentPriceCurrency = currentPriceCurrency;
    }

    public String getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(String rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFullStars() {
        return getRating().intValue();
    }

    public boolean isHasHalfStar() {
        return getRating().subtract(new BigDecimal(getFullStars())).compareTo(new BigDecimal("0.5")) >= 0;
    }

    public int getEmptyStars() {
        return 5 - getFullStars() - (isHasHalfStar() ? 1 : 0);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
