package com.example.demo.service;
import com.example.demo.repository.LessonJdbcRepository;
import com.example.demo.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Lesson;
/*--## AI Assistant
Questo codice illustra l'implementazione di un servizio Spring (`LessonServiceImpl`)
che interagisce con un repository JPA (`LessonRepository`) per gestire entità
di tipo `Lesson`. Ecco una spiegazione dettagliata per ciascuna parte del codice:

---

### **Anatomia della classe `LessonServiceImpl`**

1. **Annotazione `@Service`**
   - La classe è annotata con `@Service`, il che significa che è un **componente
   del livello di servizio** in un'applicazione Spring.
   - Questa annotazione indica a Spring che la classe fa parte del contesto
   applicativo e dovrebbe essere gestita automaticamente dal framework.

2. **Dipendenza `LessonRepository` con `@Autowired`**
   - La classe utilizza il meccanismo di **Inversione del Controllo (IoC)**
   di Spring per iniettare automaticamente un'istanza di `LessonRepository`
   nella variabile `lessonRepository`.
   - Annotato con `@Autowired`, Spring gestisce l'iniezione della dipendenza
   al momento dell'esecuzione.

3. **Implementazione dell'interfaccia `LessonService`**
   - `LessonServiceImpl` implementa un'interfaccia chiamata `LessonService`.
   Questo approccio è utile per favorire l'**astrazione**, consentendo di
   separare l'implementazione concreta dalle dichiarazioni dei metodi.
   - Questa interfaccia include metodi definiti per operazioni
   CRUD (Create, Read, Update, Delete) su entità `Lesson`.

---

### **Metodi Implementati**

1. **`findByCourseId(Integer courseId)`**
```java
@Override
   public List<Lesson> findByCourseId(Integer courseId) {
       return lessonRepository.findByCourseId(courseId);
   }
```
   - Questo metodo richiama la funzione `findByCourseId` definita nel repository.
   - Recupera una lista di entità `Lesson` associate a un dato `courseId`.
   - Utilizza una **query automatica** generata dal metodo del repository
   (tipico di Spring Data JPA, che crea query in base ai nomi dei metodi).

2. **`findById(Integer id)`**
```java
@Override
   public Lesson findById(Integer id) {
       return lessonRepository.findById(id).orElse(null);
   }
```
   - Cerca un'istanza `Lesson` per ID utilizzando il metodo `findById` del repository.
   - Restituisce l'entità se presente nel database; in caso contrario,
   restituisce `null` grazie alla funzione `orElse(null)`.

3. **`save(Lesson lesson)`**
```java
@Override
   public Lesson save(Lesson lesson) {
       return lessonRepository.save(lesson);
   }
```
   - Salva o aggiorna un'istanza di `Lesson` nel database.
   - Chiama il metodo `save` del repository, che può fungere sia da metodo
   di **insert** che **update**, a seconda della presenza dell'ID nell'entità.

4. **`deleteById(Integer id)`**
```java
@Override
   public void deleteById(Integer id) {
       lessonRepository.deleteById(id);
   }
```
   - Rimuove un'istanza `Lesson` dal database in base al suo `id`.
   - Utilizza il metodo `deleteById` del repository per eliminare direttamente.

---

### **LessonService e LessonRepository**

1. **Interfaccia `LessonService`**
```java
public interface LessonService {
       List<Lesson> findByCourseId(Integer courseId);
       Lesson findById(Integer id);
       Lesson save(Lesson lesson);
       void deleteById(Integer id);
   }
```
   - Definisce una serie di metodi astratti che il servizio deve implementare.
   - Favorisce la modularità e la testabilità, consentendo di definire facilmente
   implementazioni differenti (ad esempio, fittizie per test).

2. **Interfaccia `LessonRepository`**
```java
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
       List<Lesson> findByCourseId(Integer courseId);
   }
```
   - Estende `JpaRepository`, permettendo l'accesso a una serie di metodi predefiniti
   per la gestione CRUD delle entità.
   - Include il metodo personalizzato `findByCourseId(Integer courseId)`
   per recuperare lezioni filtrate per ID corso.

---

### **Relazione tra componenti**

1. **`LessonServiceImpl`**
   - È il livello del servizio che contiene la logica applicativa.
   - Coordina l'interazione tra il livello del repository e altri componenti.

2. **`LessonRepository`**
   - Interagisce direttamente con il database tramite JPA per eseguire operazioni di persistenza.

3. **Benefici della separazione**
   - La separazione tra `LessonService` e `LessonServiceImpl` consente di seguire
   il principio di **inversione delle dipendenze**, rendendo il codice più gestibile e testabile.

---

### **Punti Chiave**

1. **Riutilizzo del repository:** Ogni metodo del servizio delega completamente l'elaborazione
al repository, evitando ridondanze di codice.
2. **Gestione delle eccezioni:** Il metodo `findById` utilizza `.orElse(null)` per restituire
un valore predefinito nel caso l'ID non esista.
3. **Semplicità dell'approccio Spring Data JPA:** Il repository deriva da `JpaRepository`,
ottenendo numerose funzionalità predefinite (metodi CRUD, gestione delle transazioni, query personalizzate basate sui nomi).
4. **Modularità:** Questo approccio consente modifiche o miglioramenti al livello di
servizio o di repository in maniera indipendente.

---

In sintesi, il codice è un'implementazione standard di un servizio Spring che coordina
le operazioni di accesso ai dati per un'entità `Lesson` usando Spring Data JPA.
Segue i principi di separazione delle responsabilità e modularità, rendendolo ideale
per modifiche e test in contesti applicativi scalabili.
*/
@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonJdbcRepository lessonJdbcRepository;
    @Override
    public List<Lesson> findByCourseId(Integer courseId) {
        return lessonRepository.findByCourseId(courseId);
    }
    @Override
    public Lesson findById(Integer id) {
        return lessonRepository.findLessonsById(id);
    }
    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
    @Override
    public void deleteById(Integer id) {
        lessonRepository.deleteById(id);
    }
    @Override
    public void saveLesson(String title, int courseId) {
        if (lessonRepository.existsByTitleAndCourseId(title, courseId)) {
            throw new RuntimeException("Il titolo della lezione è già esistente.");
        }
        int rowsAffected = lessonJdbcRepository.saveLesson(title, courseId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Inserimento fallito");
        }
    }
    public Lesson findByTitleAndCourseId(String title, int courseId) {
        return lessonRepository.findByTitleAndCourseId(title, courseId);
    }
    public void updateLesson(Lesson lesson) {
        int rowsAffected = lessonJdbcRepository.updateLesson(lesson);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento fallito. Lesson con ID " + lesson.getId() + " non trovata.");
        }
    }
    public void deleteLesson(int id) {
        int rowsAffected = lessonJdbcRepository.deleteLesson(id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Cancellazione fallita. Lesson con ID " + id + " non trovata.");
        }
    }
}