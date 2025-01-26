package com.example.demo.service;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.CourseJdbcRepository;
/*--## AI Assistant
### **Spiegazione del codice**

Il codice rappresenta una **classe di servizio Spring** che implementa
un'interfaccia denominata `CourseService`. Vediamo i dettagli:

#### **Descrizione generale della classe**
La classe `CourseServiceImpl` è una **implementazione della logica di
business** per la gestione dei corsi (`Course`). Si trova nel livello
di **servizio** di un'applicazione Spring, un livello intermedio che
collega il livello controller (che gestisce le richieste HTTP) con
il livello repository (che interagisce con il database).

---

### **Dettaglio del codice**
#### **Annotazione `@Service`**
```java
@Service
```
- L'annotazione `@Service` indica che questa classe è un **bean Spring** che rappresenta
un **servizio della logica di business**. Sarà automaticamente registrata all'interno
del **contesto dell'applicazione Spring** (Dependency Injection).
- Contrassegnandola come servizio, può essere utilizzata da altri componenti

dell'applicazione (ad esempio controller o altri servizi).

---

#### **Campo `CourseRepository` con `@Autowired`**
```java
@Autowired
private CourseRepository courseRepository;
```
- `courseRepository` è una **dipendenza** che punta a un repository JPA (`CourseRepository`),
il quale contiene i metodi per interagire con i dati dei corsi nel database.
- `@Autowired`: L'annotazione permette a Spring di **iniettare automaticamente** un’istanza
del bean `CourseRepository` al momento della creazione della classe `CourseServiceImpl`.

---

#### **Implementazione dell'interfaccia `CourseService`**
La classe implementa i metodi definiti nell'interfaccia `CourseService`.
Questi metodi corrispondono alle operazioni principali sulla risorsa `Course`
presenti nel sistema.

---

### **I metodi implementati**

1. **Metodo `findAll()`**
```java
@Override
public List<Course> findAll() {
    return courseRepository.findAll();
}
```
- **Descrizione**: Recupera e restituisce la lista di tutti i corsi salvati nel database.
- **Implementazione**: Usa il metodo predefinito `findAll()`
fornito dall'interfaccia `JpaRepository` di Spring Data per restituire tutti i record.

---

2. **Metodo `findById(Integer id)`**
```java
@Override
public Course findById(Integer id) {
    return courseRepository.findById(id).orElse(null);
}
```
- **Descrizione**: Cerca un corso dato un identificativo (`id`).
- **Implementazione**:
  - Usa il metodo predefinito `findById()` di `JpaRepository`,
  che restituisce un oggetto di tipo `Optional<Course>`.
  - Se il corso viene trovato, restituisce l'oggetto `Course`.
  Altrimenti, restituisce `null` (grazie all'uso di `.orElse(null)`).

---

3. **Metodo `save(Course course)`**
```java
@Override
public Course save(Course course) {
    return courseRepository.save(course);
}
```
- **Descrizione**: Salva o aggiorna un corso nel database.
- **Implementazione**:
  - Chiama il metodo predefinito `save()` di `JpaRepository`,
  che salva l'oggetto nel database.
  - Se il corso è nuovo (l'id è `null`), lo crea come un nuovo record.
  - Se il corso già esiste (l'id non è `null`), lo aggiorna.

---

4. **Metodo `deleteById(Integer id)`**
```java
@Override
public void deleteById(Integer id) {
    courseRepository.deleteById(id);
}
```
- **Descrizione**: Elimina un corso dal database dato il suo identificativo (`id`).
- **Implementazione**: Usa il metodo predefinito `deleteById()` di `JpaRepository`
per eliminare il record.

---

### **Funzione della classe nel progetto**
1. **Livello intermedio (Service Layer)**:
   La classe `CourseServiceImpl` agisce come ponte tra il **controller** e il **repository**.
   Qui viene incapsulata la logica di business legata ai corsi.

2. **Manutenzione semplificata**:
   Separando i servizi dal controller e dal livello di persistenza, il codice risulta
   più leggibile, modulare e semplice da modificare.

3. **Utilizzo in un controller**:
   La classe sarà utilizzata da un controller Spring per rispondere alle richieste HTTP.
   Esempio:
```java
@RestController
   @RequestMapping("/courses")
   public class CourseController {
       @Autowired
       private CourseService courseService;

       @GetMapping
       public List<Course> getAllCourses() {
           return courseService.findAll();
       }

       @GetMapping("/{id}")
       public Course getCourseById(@PathVariable Integer id) {
           return courseService.findById(id);
       }

       @PostMapping
       public Course saveCourse(@RequestBody Course course) {
           return courseService.save(course);
       }

       @DeleteMapping("/{id}")
       public void deleteCourse(@PathVariable Integer id) {
           courseService.deleteById(id);
       }
   }
```

---
### **Conclusione**
La classe `CourseServiceImpl` è una tipica implementazione di un servizio in
un'applicazione Spring MVC basata su Spring Data JPA. Consente di:
- **Gestire i corsi** (recuperarli, salvarli, aggiornarli o eliminarli).
- Separare la logica di business dal livello del controller e del repository.
- Riutilizzare le funzionalità predefinite offerte da `JpaRepository`,
riducendo notevolmente la necessità di scrivere codice SQL o logica complessa di accesso ai dati.
*/
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CourseJdbcRepository courseJdbcRepository;
    @Override
    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }
    @Override
    public Course findById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void saveCourse(Course course) {
        if (courseRepository.existsByTitle(course.getTitle())) {
            throw new RuntimeException("Il titolo del corso è già esistente.");
        }
        int rowsAffected = courseJdbcRepository.saveCourse(course);
        if (rowsAffected == 0) {
            throw new RuntimeException("Inserimento fallito");
        }
    }
    @Transactional
    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }
    @Override
    public Course getCourseByIdWithLessons(Integer id) {
        return courseRepository.findCourseWithLessons(id);
    }

    @Override
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }
    public Page<Course> findCourses(int page, int size, String title, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Filtro per titolo
        if (title != null && !title.isEmpty()) {
            return courseRepository.findByTitleContainingIgnoreCase(title, pageable);
        }

        return courseRepository.findAll(pageable);
    }
    public List<Course> getTopRatedCourses() {
        Pageable pageable = PageRequest.of(0, 3); // Prima pagina, 3 risultati
        return courseRepository.findTopByRating(pageable);
    }

    public List<Course> getNewestCourses() {
        Pageable pageable = PageRequest.of(0, 3); // Prima pagina, 3 risultati
        return courseRepository.findTopByNewest(pageable);
    }
    public void updateCourse(Course course) {
        int rowsAffected = courseJdbcRepository.updateCourse(course);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento fallito. Corso con ID " + course.getId() + " non trovato.");
        }
    }
    public void deleteCourse(int id) {
        int rowsAffected = courseJdbcRepository.deleteCourse(id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Eliminazione fallita. Corso con ID " + id + " non trovato.");
        }
    }
    public void updateImagePath(String image, int id) {
        int rowsAffected = courseJdbcRepository.updateImagePath(image, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento dell'immagine fallito. Corso con ID " + id + " non trovato.");
        }
    }
}