package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;
/*--## AI Assistant
Ecco una spiegazione del codice fornito:

### **Descrizione del codice**

```java
public interface LessonService {
    List<Lesson> findByCourseId(Integer courseId);
    Lesson findById(Integer id);
    Lesson save(Lesson lesson);
    void deleteById(Integer id);
}
```

#### **1. Rappresentazione dell'interfaccia**
- **Interfaccia `LessonService`**:
  Questa interfaccia serve come contratto per la gestione delle entità `Lesson`. Fa parte del "livello di servizio" (Service Layer) in una tipica architettura Spring e definisce metodi per implementare le operazioni principali (CRUD - Create, Read, Update, Delete) relative all'entità `Lesson`.

---

#### **2. Metodi definiti**
Ecco la descrizione dei metodi esposti:

1. **`List<Lesson> findByCourseId(Integer courseId)`**:
   - **Scopo**: Trova tutte le lezioni (`Lesson`) che appartengono a un determinato corso, identificato da `courseId`.
   - **Restituisce**: Una lista (`List`) di oggetti `Lesson` associati al corso identificato tramite l'ID.

2. **`Lesson findById(Integer id)`**:
   - **Scopo**: Recupera una lezione particolare utilizzando il suo identificativo (`id`).
   - **Restituisce**: L'oggetto `Lesson` corrispondente, se esiste (o `null` nel caso in cui la lezione non venga trovata).

3. **`Lesson save(Lesson lesson)`**:
   - **Scopo**: Salva un oggetto `Lesson` nel database. Questo metodo può essere usato sia per creare una nuova lezione che per aggiornare una lezione esistente.
   - **Restituisce**: L'entità `Lesson` appena salvata (che potrebbe includere l'ID generato automaticamente o proprietà aggiornate).

4. **`void deleteById(Integer id)`**:
   - **Scopo**: Elimina una lezione (`Lesson`) dal database, individuandola attraverso il suo identificativo (`id`).
   - **Non restituisce nulla** (`void`).

---

### **Dettagli collegati**
L'interfaccia `LessonService` viene utilizzata come "contratto" che sarà implementato da una classe concreta (ad esempio, `LessonServiceImpl`). Questa implementazione si occupa di definire la logica concreta dei metodi sfruttando un repository (ad esempio, `LessonRepository`) per lavorare con il database.

#### **Esempio di implementazione (LessonServiceImpl):**
Un'implementazione pratica potrebbe seguire il seguente schema:

```java
@Service // Annotazione Spring per il Service Layer
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository; // Dipendenza al livello repository

    @Override
    public List<Lesson> findByCourseId(Integer courseId) {
        return lessonRepository.findByCourseId(courseId); // Metodo repository per query
    }

    @Override
    public Lesson findById(Integer id) {
        return lessonRepository.findById(id).orElse(null); // Restituzione con gestione null
    }

    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson); // Persistenza dell'entità
    }

    @Override
    public void deleteById(Integer id) {
        lessonRepository.deleteById(id); // Eliminazione dal repository
    }
}
```

---

### **Collegamento con l'entità `Lesson`**
Il codice fornito include anche la classe `Lesson`. Breve descrizione:

#### **1. Struttura dell'entità `Lesson`**

- La classe `Lesson` è una **entità** annotata con `@Entity`, che rappresenta una tabella nel database chiamata `Lessons`.
- Campi principali:
  - `id` (chiave primaria, con valore generato automaticamente).
  - Proprietà come `title`, `description`, `duration`, `order`.
  - Relazione `@ManyToOne` con l'entità `Course` (una lezione appartiene a un unico corso).

#### **2. Collegamento con il Service**
Il `LessonService` gestisce entità come `Lesson`. Ad esempio:
- `findByCourseId(Integer courseId)` cercherà tutte le `Lesson` che appartengono a un determinato `Course`.
- `save(Lesson lesson)` consente di aggiornare le proprietà della lezione, ad esempio `title`, `description`, ecc.

---

### **Come si usa nell'applicazione**
L'interfaccia `LessonService` è utilizzata in applicazioni Spring per separare il livello di servizio dalla logica dei controller o del repository:

1. **Esempio di un Controller che utilizza il servizio:**

```java
@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/course/{courseId}")
    public List<Lesson> getLessonsByCourseId(@PathVariable Integer courseId) {
        return lessonService.findByCourseId(courseId);
    }

    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonService.save(lesson);
    }

    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable Integer id) {
        return lessonService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLessonById(@PathVariable Integer id) {
        lessonService.deleteById(id);
    }
}
```

2. **Funzionamento dei metodi nella sequenza:**
   - Il controller riceve una richiesta (`GET`, `POST`, `DELETE`, ecc.).
   - Chiama i metodi nel servizio (`LessonService`).
   - Il servizio interagisce con il repository per ottenere/aggiornare/eliminare dati del database.

---

### **Riassunto**
L'interfaccia `LessonService` rappresenta il livello di servizio nell'architettura Spring e serve per gestire operazioni CRUD relative a entità `Lesson`. L'implementazione di questa interfaccia (es. in `LessonServiceImpl`) utilizza un repository (`LessonRepository`) per interagire con il database, rendendo la logica più strutturata e modulare.
*/
public interface LessonService {
    List<Lesson> findByCourseId(Integer courseId);
    Lesson findById(Integer id);
    Lesson save(Lesson lesson);
    void deleteById(Integer id);
    void saveLesson(String title, int courseId);
    Lesson findByTitleAndCourseId(String title, int courseId);
    void updateLesson(Lesson lesson);
    void deleteLesson(int id);

}