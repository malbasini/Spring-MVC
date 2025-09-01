package com.example.demo.controller;
import com.example.demo.service.CaptchaValidator;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;
/*--## AI Assistant
Il codice fornito implementa un **controller Spring MVC** chiamato `LessonController`,
che gestisce le operazioni relative alle entità "Lezione" (`Lesson`) in un'applicazione
 che utilizza Spring Framework. Questo controller si occupa di fornire alle viste web
 diverse funzionalità CRUD (Create, Read, Update, Delete) per gestire lezioni
 associate a corsi (`Course`).

Ecco una spiegazione dettagliata del codice:

### 1. Annotazioni
- **`@Controller`**: Indica che questa classe è un controller MVC di Spring che gestisce
richieste HTTP e restituisce risposte, generalmente in forma di pagine web (view).
- **`@RequestMapping("/lessons")`**: Specifica che tutte le richieste a questa classe
iniziano con il percorso `/lessons`.

Spring inietta automaticamente i servizi necessari (`LessonService` e `CourseService`)
usando **`@Autowired`**.

---

### 2. Metodo: `listLessonsByCourse`
```java
@GetMapping("/course/{courseId}")
public String listLessonsByCourse(@PathVariable("courseId") Integer courseId, Model model) {
    List<Lesson> lessons = lessonService.findByCourseId(courseId);
    Course course = courseService.findById(courseId);
    model.addAttribute("lessons", lessons);
    model.addAttribute("course", course);
    return "lessons/list";
}
```
- **Scopo**: Mostra un elenco delle lezioni associate a un corso specifico.
- **Percorso associato**: `GET /lessons/course/{courseId}`.
- **Funzionamento**:
  1. Recupera la lista delle lezioni con `lessonService.findByCourseId(courseId)`.
  2. Recupera i dettagli del corso specificato con `courseService.findById(courseId)`.
  3. Aggiunge i dati al modello (`model`) per essere utilizzati nella vista.
  4. Restituisce la vista `lessons/list`, che potrebbe essere una pagina HTML
  che visualizza la lista.

---

### 3. Metodo: `showCreateForm`
```java
@GetMapping("/new")
public String showCreateForm(@RequestParam("courseId") Integer courseId, Model model) {
    Lesson lesson = new Lesson();
    Course course = courseService.findById(courseId);
    lesson.setCourse(course);
    model.addAttribute("lesson", lesson);
    model.addAttribute("courseId", courseId);
    return "lessons/form";
}
```
- **Scopo**: Mostra un modulo vuoto per creare una nuova lezione.
- **Percorso associato**: `GET /lessons/new?courseId=`.
- **Funzionamento**:
  1. Crea una nuova istanza della lezione.
  2. Recupera il corso al quale la lezione sarà associata.
  3. Inserisce i dati nel modello (`model`) per il rendering nella vista.
  4. Restituisce la vista `lessons/form`, una pagina HTML con un modulo per creare una lezione.

---

### 4. Metodo: `createLesson`
```java
@PostMapping
public String createLesson(@ModelAttribute("lesson") Lesson lesson,
                           @RequestParam("courseId") Integer courseId) {
    Course course = courseService.findById(courseId);
    lesson.setCourse(course);
    lessonService.save(lesson);
    return "redirect:/lessons/course/" + courseId;
}
```
- **Scopo**: Salva una nuova lezione nel database.
- **Percorso associato**: `POST /lessons`.
- **Funzionamento**:
  1. Associa la nuova lezione al corso corrispondente.
  2. Salva la lezione tramite il servizio `lessonService.save(lesson)`.
  3. Reindirizza l'utente alla lista delle lezioni del corso
  con `redirect:/lessons/course/{courseId}`.

---

### 5. Metodo: `editLesson`
```java
@GetMapping("/{id}/edit")
public String editLesson(@PathVariable("id") Integer id, Model model) {
    Lesson lesson = lessonService.findById(id);
    if (lesson == null) {
        return "redirect:/courses";
    }
    model.addAttribute("lesson", lesson);
    return "lessons/form";
}
```
- **Scopo**: Mostra un modulo per modificare una lezione esistente.
- **Percorso associato**: `GET /lessons/{id}/edit`.
- **Funzionamento**:
  1. Recupera la lezione tramite ID.
  2. Se la lezione non esiste, reindirizza alla lista dei corsi.
  3. Altrimenti, aggiunge la lezione al modello e mostra la vista `lessons/form`
  precompilata con i dati della lezione.

---

### 6. Metodo: `updateLesson`
```java
@PostMapping("/{id}")
public String updateLesson(@PathVariable("id") Integer id,
                           @ModelAttribute("lesson") Lesson updatedLesson) {
    Lesson existing = lessonService.findById(id);
    if (existing == null) {
        return "redirect:/courses";
    }
    existing.setTitle(updatedLesson.getTitle());
    existing.setDescription(updatedLesson.getDescription());
    existing.setDuration(updatedLesson.getDuration());
    existing.setOrderNumber(updatedLesson.getOrderNumber());
    existing.setCourse(updatedLesson.getCourse());
    lessonService.save(existing);
    return "redirect:/lessons/course/" + existing.getCourse().getId();
}
```
- **Scopo**: Aggiorna i dati di una lezione esistente.
- **Percorso associato**: `POST /lessons/{id}`.
- **Funzionamento**:
  1. Recupera la lezione esistente dall'ID.
  2. Se non viene trovata, reindirizza alla lista dei corsi.
  3. Altrimenti, aggiorna i campi della lezione esistente con i dati della lezione aggiornata.
  4. Salva l'entità aggiornata e reindirizza alla lista delle lezioni del corso associato.

---

### 7. Metodo: `deleteLesson`
```java
@PostMapping("/{id}/delete")
public String deleteLesson(@PathVariable("id") Integer id) {
    Lesson lesson = lessonService.findById(id);
    if (lesson != null) {
        Integer courseId = lesson.getCourse().getId();
        lessonService.deleteById(id);
        return "redirect:/lessons/course/" + courseId;
    }
    return "redirect:/courses";
}
```
- **Scopo**: Elimina una lezione dal database.
- **Percorso associato**: `POST /lessons/{id}/delete`.
- **Funzionamento**:
  1. Recupera la lezione tramite ID.
  2. Se la lezione esiste, ne elimina l'ID e reindirizza alla lista delle lezioni del
  corso associato.
  3. Se la lezione non è trovata, reindirizza alla lista dei corsi.

---

### Conclusione
`LessonController` coordina le operazioni per gestire lezioni all'interno di corsi. I metodi
 utilizzano due servizi principali:
1. **`LessonService`**: Si occupa di gestire le operazioni relative a lezioni, come "trova
per ID", "salva", "elimina".
2. **`CourseService`**: Consente il recupero di informazioni sui corsi, come "trova per ID".

La logica MVC:
- **Controller**: Gestisce le richieste HTTP.
- **Service**: Contiene la logica di business.
- **Model e View**: Condividono dati con la vista (`Model`) e restituiscono
le pagine HTML (`lesson/form`, `lessons/list`).
*/
@Controller
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private CaptchaValidator captchaValidator;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CourseService courseService;
    // GET /lessons/course/{courseId} -> visualizzazione delle lezioni di un corso
    @GetMapping("/course/{courseId}")
    public String listLessonsByCourse(@PathVariable("courseId") Integer courseId,
                                      Model model) {
        List<Lesson> lessons = lessonService.findByCourseId(courseId);
        Course course = courseService.findById(courseId);
        model.addAttribute("lessons", lessons);
        model.addAttribute("course", course);
        return "lessons/list";
    }
    // GET /lessons/new?courseId=... -> mostra form per creare una lezione
    @GetMapping("/new/{courseId}")
    public String showCreateForm(@PathVariable("courseId") Integer courseId, Model
            model) {
        Lesson lesson = new Lesson();
        // Impostiamo il corso nella lesson
        Course course = courseService.findById(courseId);
        lesson.setCourse(course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId", courseId);
        model.addAttribute("sitetkey", captchaValidator.getSiteKey());
        return "lessons/create";
    }
    // POST /lessons -> creazione (salvataggio) della lezione
    @PostMapping
    public String createLesson(@ModelAttribute("lesson") Lesson lesson,
                               @RequestParam("courseId") Integer courseId,
                               @RequestParam("g-recaptcha-response") String captchaResponse,
                               Model model) {
        // Associa la lezione al corso
        Course course = courseService.findById(courseId);
        model.addAttribute("courseId", courseId);
        lesson.setCourse(course);
        boolean isCaptchaValid = captchaValidator.verifyCaptcha(captchaResponse);
        if (!isCaptchaValid) {
            model.addAttribute("error", "Captcha non valido. Riprova.");
            model.addAttribute("sitetkey", captchaValidator.getSiteKey());
            model.addAttribute("lesson", lesson);
            return "lessons/create";// Torna alla pagina del form
        }
        model.addAttribute("sitetkey", captchaValidator.getSiteKey());
        if(lesson.getTitle().isEmpty()|| lesson.getTitle() == null) {
            model.addAttribute("message", "Il Titolo è obbligatorio");
            return "lessons/create"; // JSP da mostrare
        }
        try {
            lessonService.saveLesson(lesson.getTitle(),courseId);
        }
        catch (RuntimeException ex){
            model.addAttribute("message", ex.getMessage());
            return "lessons/create";
        }
        catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "lessons/create"; // JSP da mostrare
        }
        Lesson l = lessonService.findByTitleAndCourseId(lesson.getTitle(), courseId);
        model.addAttribute("lesson", l);
        return "/lessons/edit";
    }
    // GET /lessons/{id}/edit -> mostra form di modifica
    @GetMapping("/{id}/edit")
    public String editLesson(@PathVariable("id") Integer id, Model model) {
        Lesson lesson = lessonService.findById(id);
        if (!lesson.equals(null)) {
            model.addAttribute("lesson", lesson);
            // gestisci errore se non trovato
            return "/lessons/edit"; // JSP da mostrare";

        }
        return "redirect:/lessons/edit";
    }
        // POST /lessons/{id} -> aggiornamento
    @PostMapping("/{id}/{courseId}")
    public String updateLesson(@PathVariable("id") Integer id,
                               @PathVariable("courseId") Integer courseId,
                               @ModelAttribute("lesson") Lesson updatedLesson,
                               Model model) {
        Lesson existing = lessonService.findById(id);
        Course course = courseService.findById(courseId);
        model.addAttribute("courseId", courseId);
        updatedLesson.setCourse(course);
        String message = this.validazioni(updatedLesson);
        if(message != null){
            model.addAttribute("message", message);
            return "lessons/edit";
        }
        if (existing == null) {
            // gestisci errore se non trovato
            return "redirect:/courses";
        } else {
            // Copia i campi
            existing.setTitle(updatedLesson.getTitle());
            existing.setDescription(updatedLesson.getDescription());
            existing.setDuration(updatedLesson.getDuration());
            // Non dimenticare di reimpostare il course!
            existing.setCourse(updatedLesson.getCourse());
            lessonService.updateLesson(existing);
            return "redirect:/courses";
        }
    }
        // POST /lessons/{id}/delete -> cancellazione
        @PostMapping("/{id}/delete")
        public String deleteLesson(@PathVariable("id") Integer id) {
            Lesson lesson = lessonService.findById(id);
            if (!lesson.equals(null)) {
                lessonService.deleteLesson(id);
                return "redirect:/courses";
            }
            return "redirect:/courses";
        }
    @GetMapping("/{id}/detail")
    public String detailLesson(@PathVariable("id") Integer id,Model model) {
        Lesson lesson = lessonService.findById(id);
        if (!lesson.equals(null))
        {
            model.addAttribute("lesson", lesson);
            return "lessons/detail";
        }
        return "redirect:/courses";
    }

    public String validazioni(Lesson lesson){

        String cleanedText = lesson.getDescription().replaceAll("<[^>]*>", " ").trim();

        if((lesson.getTitle().isEmpty()) || (lesson.getTitle() == null)){
            return "Il titolo non può essere vuoto";
        }
        if((lesson.getDuration() == null) || (lesson.getDuration().isEmpty())|| lesson.getDuration().equals("00:00:00")){
            return "La durata deve essere valorizzata";
        }
        if((cleanedText == null) || (cleanedText.isEmpty())){
            return "La descrizione deve essere valorizzata";
        }


        return null;
    }
}
