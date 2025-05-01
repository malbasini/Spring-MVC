package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Lesson;
/*--Certamente! Ecco una spiegazione chiara di questo frammento di codice Java:

### Il contesto
Il codice è un'interfaccia di repository in un'applicazione Spring Data JPA.
Il principale obiettivo del repository è la comunicazione con il database per
eseguire operazioni CRUD (Create, Read, Update, Delete) e query personalizzate
sulla **tabella associata all'entità `Lesson`**.

### Linea per linea:

```java
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
```

1. **`public interface LessonRepository`**: Il codice definisce un'interfaccia.
   - Si tratta di un repository, una componente di Spring Data JPA utilizzata
   per gestire le operazioni sui dati.

2. **`extends JpaRepository<Lesson, Integer>`**:
   - L'interfaccia estende `JpaRepository`, la quale fornisce una serie di metodi
   predefiniti per interagire con la base di dati.
   - **`<Lesson, Integer>`**:
     - **`Lesson`**: Indica la classe **entità** (ad esempio un modello rappresentato nel database). In altre parole, Spring Data JPA utilizzerà questa classe per comprendere quale tabella deve essere gestita.
     - **`Integer`**: Indica il tipo del campo **ID primario** (il campo identificatore della tabella associata, che in questo caso è di tipo `Integer`).

In sintesi, questa linea specifica che:
- Questo repository sarà utilizzato per gestire oggetti di tipo `Lesson` e
che usa un ID di tipo `Integer`.

```java
List<Lesson> findByCourseId(Integer courseId);
```

3. **Metodo personalizzato `findByCourseId(Integer courseId)`**:
   - Si tratta di una query derivata. Spring Data JPA è in grado di generare
   automaticamente il codice per una query basandosi sul nome del metodo.
   - **Cosa fa?**: Recupera una lista di oggetti della classe `Lesson`
   per un dato valore di `courseId`.
     - **`findBy`**: Indica che stiamo cercando delle entità.
     - **`CourseId`**: È un attributo della classe **`Lesson`**, ovvero il filtro
     che viene applicato nella query al database.
   - **Parametro**: Il metodo accetta un parametro `courseId` (di tipo `Integer`),
   che viene utilizzato per eseguire il filtro sulla base dati.

4. **`List<Lesson>`**:
   - Il metodo restituisce una **lista** di oggetti `Lesson` che corrispondono al
   criterio specificato (cioè, tutte le lezioni collegate a un determinato `courseId`).

---

### Funzionalità del codice

- **Genera automaticamente query personalizzate**: Grazie alla convenzione sui
nomi (metodo chiamato `findByCourseId`), Spring Data JPA costruirà internamente
una query come questa:

```sql
SELECT * FROM lesson WHERE course_id = ?;
```

- **Elimina la necessità di scrivere query manuali**: Non è necessario implementare
il metodo o scrivere query SQL. Spring gestisce tutto per noi.

---

### Integrazione pratica
Supponendo di avere la seguente entità `Lesson`:

```java
@Entity
public class Lesson {
    @Id
    private Integer id;
    private Integer courseId;
    private String title;
    private String description;
    // Altri campi e getter/setter
}
```

Ecco un esempio di utilizzo del repository:

```java
@Autowired
private LessonRepository lessonRepository;

// Recupera tutte le lezioni per un corso con courseId = 1
List<Lesson> lessonsForCourse = lessonRepository.findByCourseId(1);

// Stampa i titoli delle lezioni
for (Lesson l : lessonsForCourse) {
    System.out.println(l.getTitle());
}
```

---

### Riassunto
Questo codice:
- Definisce un repository JPA per interagire con le entità `Lesson`.
- Offre un metodo personalizzato `findByCourseId`, che utilizza una convenzione di
Spring Data JPA per generare query automaticamente.
- Rende più semplice e leggibile il codice, poiché delega gran parte della gestione
dei dati a Spring Data JPA senza bisogno di scrivere query SQL manuali.*/

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    // Esempio: recupera tutte le lessons di un certo courseId
    List<Lesson> findByCourseId(Integer courseId);
    boolean existsByTitleAndCourseId(String title,int courseId);
    Lesson findByTitleAndCourseId(String title,int courseId);

    Lesson findLessonsById(Integer id);
}