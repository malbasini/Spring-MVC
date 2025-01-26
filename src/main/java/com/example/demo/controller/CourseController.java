package com.example.demo.controller;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.example.demo.entity.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/*--## AI Assistant
Il codice fornito rappresenta un **controller Spring MVC** chiamato `CourseController`,
che gestisce le operazioni CRUD (Create, Read, Update, Delete) su un'entità
chiamata `Course`. Questo controller utilizza annotazioni di Spring per mappare
le richieste HTTP ai metodi corrispondenti e si interfaccia con un
servizio (`CourseService`) per accedere e manipolare i dati relativi ai corsi.

### Spiegazione in dettaglio
Il controller è decorato con le seguenti annotazioni principali:

1. **`@Controller`**:
   Indica che questa classe è un controller Spring, che risponde alle richieste
   HTTP e restituisce viste (es. pagine JSP).

2. **`@RequestMapping("/courses")`**:
   Specifica che tutte le richieste verso questo controller inizieranno
   con il prefisso `/courses`. Ad esempio, una richiesta GET a `/courses`
   verrà gestita dal metodo `listCourses()`.

3. **`@Autowired`**:
   Il servizio `CourseService` viene iniettato automaticamente tramite
   Spring Dependency Injection.

### Metodi del controller
Ecco i dettagli dei metodi e delle operazioni gestite:

#### 1. **Elenco dei corsi (`GET /courses`)**
```java
@GetMapping
public String listCourses(Model model) {
    List<Course> courses = courseService.findAll();
    model.addAttribute("courses", courses);
    return "courses/list";
}
```
- **Descrizione**: Questo metodo gestisce le richieste GET a `/courses`.
- **Azioni**:
  - Chiama il servizio (`courseService.findAll()`) per recuperare l'elenco dei corsi.
  - Aggiunge tutti i corsi al modello (oggetto `Model`) come attributo `courses`.
  - Restituisce la vista `courses/list`, che è normalmente un file JSP o una pagina
  HTML che mostra l'elenco dei corsi.

#### 2. **Form per creare un nuovo corso (`GET /courses/new`)**
```java
@GetMapping("/new")
public String showCreateForm(Model model) {
    model.addAttribute("course", new Course());
    return "courses/form";
}
```
- **Descrizione**: Gestisce le richieste GET a `/courses/new`.
- **Azioni**:
  - Aggiunge un nuovo oggetto vuoto di tipo `Course` al modello come attributo `course`.
  - Restituisce la vista `courses/form`, che mostra un modulo per inserire i dati
  del nuovo corso.

#### 3. **Salvataggio di un nuovo corso (`POST /courses`)**
```java
@PostMapping
public String createCourse(@ModelAttribute("course") Course course) {
    courseService.save(course);
    return "redirect:/courses";
}
```
- **Descrizione**: Gestisce le richieste POST a `/courses`.
- **Azioni**:
  - L'oggetto `Course` inviato dal modulo (tramite il binding dei dati con `@ModelAttribute`)
  viene salvato utilizzando il metodo `save()` del servizio.
  - Dopo il salvataggio, reindirizza alla lista dei corsi (`redirect:/courses`).

#### 4. **Form di modifica di un corso (`GET /courses/{id}/edit`)**
```java
@GetMapping("/{id}/edit")
public String editCourse(@PathVariable("id") Integer id, Model model) {
    Course course = courseService.findById(id);
    if (course == null) {
        return "redirect:/courses";
    }
    model.addAttribute("course", course);
    return "courses/form";
}
```
- **Descrizione**: Gestisce le richieste GET a `/courses/{id}/edit`.
- **Azioni**:
  - Recupera il corso con l'ID specificato tramite il servizio (`courseService.findById(id)`).
  - Se il corso non è trovato, reindirizza all'elenco dei corsi.
  - Se il corso esiste, lo aggiunge come attributo `course` al modello.
  - Mostra la vista `courses/form`, riutilizzando il modulo per modificare il corso.

#### 5. **Aggiornamento di un corso (`POST /courses/{id}`)**
```java
@PostMapping("/{id}")
public String updateCourse(@PathVariable("id") Integer id,
                           @ModelAttribute("course") Course updatedCourse) {
    updatedCourse.setId(id);
    courseService.save(updatedCourse);
    return "redirect:/courses";
}
```
- **Descrizione**: Gestisce le richieste POST a `/courses/{id}`.
- **Azioni**:
  - Aggiorna l'oggetto `Course` con l'ID specificato (`updatedCourse.setId(id)`).
  - Usa il metodo `save()` del servizio per aggiornare o sostituire il corso salvato.
  - Reindirizza all'elenco dei corsi.

#### 6. **Eliminazione di un corso (`POST /courses/{id}/delete`)**
```java
@PostMapping("/{id}/delete")
public String deleteCourse(@PathVariable("id") Integer id) {
    courseService.deleteById(id);
    return "redirect:/courses";
}
```
- **Descrizione**: Gestisce le richieste POST a `/courses/{id}/delete`.
- **Azioni**:
  - Elimina il corso con l'ID specificato tramite il metodo `deleteById()` del servizio.
  - Reindirizza alla lista dei corsi dopo l'eliminazione.

### Servizio correlato: `CourseService`
Questo controller si affida a un servizio di accesso ai dati (`CourseService`),
che contiene i metodi seguenti:
- **`findAll()`**: Restituisce tutti i corsi.
- **`findById(Integer id)`**: Restituisce un corso dato il suo ID.
- **`save(Course course)`**: Salva o aggiorna un corso.
- **`deleteById(Integer id)`**: Elimina il corso con l'ID specificato.

### Riassunto
- Questo controller segue l'approccio MVC di Spring. L'interfaccia utente interagisce
con le viste (JSP/HTML), e il controller gestisce le richieste HTTP, delegando al
servizio che si occupa della logica dei dati.
- Il prefisso `/courses` è utilizzato per tutte le operazioni relative ai corsi
 (visualizzazione, creazione, modifica ed eliminazione).
- Ogni operazione HTTP è chiaramente separata, con metodi dedicati per GET e POST.

*/
@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Value("${upload.path:uploads}")
    private String uploadDir;
    // GET /courses -> listing di tutti i corsi
    /*
    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses/list"; // JSP da mostrare
    }
    */
    // GET /courses -> listing di tutti i corsi con supporto a paginazione, ricerca e ordinamento
    @GetMapping
    public String listCourses(
            @RequestParam(defaultValue = "0") int page, // Pagina corrente
            @RequestParam(defaultValue = "10") int size, // Elementi per pagina
            @RequestParam(defaultValue = "") String title, // Filtro per titolo
            @RequestParam(defaultValue = "title") String sortBy, // Campo di ordinamento
            @RequestParam(defaultValue = "asc") String sortDirection, // Direzione dell'ordinamento
            Model model
    ) {
        // Ottenere i corsi con paginazione, ricerca e ordinamento
        Page<Course> courses = courseService.findCourses(page, size, title, sortBy, sortDirection);

        // Passare i dati al modello per JSP
        model.addAttribute("courses", courses.getContent()); // Lista dei corsi
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("titleFilter", title);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);

        return "courses/list"; // Nome della vista JSP
    }

    // GET /courses/new -> mostra form per creare un nuovo corso
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/create"; // JSP da mostrare
    }
    // POST /courses -> salva un nuovo corso
    @PostMapping
    public String create(@Valid @ModelAttribute("course") Course course
            ,BindingResult bindingResult, Model model) {
        if(course.getTitle()==null || course.getTitle().isEmpty()){
            model.addAttribute("message", "Il titolo è obbligatorio");
            return "courses/create";
        }
        if (bindingResult.hasErrors()) {
            return "courses/create"; // JSP da mostrare
        }
        try {
            courseService.saveCourse(course);
        }
        catch (RuntimeException ex){
            model.addAttribute("message", ex.getMessage());
            return "courses/create";
        }
        catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "courses/create"; // JSP da mostrare
        }
        model.addAttribute("message", "Inserimento corso avvenuto con successo");
        return "Courses/edit"; // Torna alla vista di edit

    }
    @GetMapping("/course/{id}/detail")
    public String courseDetail(@PathVariable Integer id,Model model) {
        Course course = courseService.getCourseByIdWithLessons(id);
        if (course == null) {
            return "redirect:/courses"; // Gestione caso corso non trovato
        }

        // Calcola la durata totale
        Duration totalDuration = calculateTotalDuration(course.getLessons());
        model.addAttribute("course", course);
        model.addAttribute("totalDuration", formatDuration(totalDuration));
        return "courses/detail"; // JSP da mostrare
    }
    // GET /courses/{id}/edit -> mostra form di modifica
    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable("id") Integer id, Model model) {
        Course course = courseService.findById(id);
        if (course == null) {
            // gestisci errore se non trovato
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "courses/edit"; // JSP da mostrare
    }
// POST /courses/{id} -> aggiorna un corso esistente
@PostMapping("/{id}")
public String updateCourse(@PathVariable("id") Integer id,
                           @ModelAttribute("course") @Valid Course updatedCourse,
                           BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        return "courses/edit"; // JSP da mostrare
    }
    String imagePath = courseService.findById(id).getImagePath();
    // Validazioni
    String message = validazioni(updatedCourse,model);
    if(message != null) {
        model.addAttribute("message", message);
        updatedCourse.setCurrentPriceCurrency("EUR");
        updatedCourse.setFullPriceCurrency("EUR");
        updatedCourse.setImagePath(imagePath);
        return "courses/edit";
    }
     // Assicuriamoci che l'ID coincida
    updatedCourse.setId(id);
    try{
        courseService.updateCourse(updatedCourse);
        model.addAttribute("message", "Course updated successfully");
    } catch (Exception e) {
        model.addAttribute("message", e.getMessage());
        return "courses/edit"; // JSP da mostrare
    }
    return "redirect:/courses";
}
    // POST /courses/{id}/delete -> cancella un corso
    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable("id") Integer id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
    private Duration calculateTotalDuration(List<Lesson> lessons) {
        return lessons.stream()
                .map(lesson -> Duration.between(LocalTime.MIN, LocalTime.parse(lesson.getDuration())))
                .reduce(Duration.ZERO, Duration::plus);
    }
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    @PostMapping("/{id}/uploadImage")
    public String uploadImage(@PathVariable("id") Integer id,
                              @RequestParam("imageFile") MultipartFile file,
                              Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "File non valido.");
            return "Courses/edit"; // Torna alla vista
        }
        Course course = courseService.findById(id);
        String message = validazioni(course,model);
        if(message != null) {
            model.addAttribute("message", message);
            return "courses/edit";
        }
        try {
            // Creare la directory di upload se non esiste
            String uploadPath = new File(uploadDir).getAbsolutePath();
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Salva il file
            String fileName = file.getOriginalFilename();
            String filePath = uploadPath + File.separator + fileName;
            file.transferTo(new File(filePath));
            //salvo il percorso dell'immagine nel database
            Course c = courseService.findById(id);
            if (c == null) {
                // gestisci errore se non trovato
                return "Courses/edit"; // Torna alla vista
            }
            course.setImagePath("/uploads/" + fileName);
            courseService.updateImagePath(course.getImagePath(),course.getId());
            // Successo
            model.addAttribute("success", "Immagine caricata con successo: " + fileName);
            model.addAttribute("filePath", "/uploads/" + fileName); // Percorso per visualizzare il file
            return "redirect:/courses"; // Torna alla vista

        } catch (IOException e) {
            model.addAttribute("error", "Errore durante il caricamento del file: " + e.getMessage());
            return "Courses/edit"; //
        }
    }
    public String validazioni(Course course, Model model)
    {
        String cleanedText = course.getDescription().replaceAll("<[^>]*>", " ").trim();
        if(course.getTitle()==null || course.getTitle().isEmpty()){
            model.addAttribute("message", "Il titolo è obbligatorio");
            return "Il titolo è obbligatorio";
        }
        if(course.getAuthor()==null || course.getAuthor().isEmpty()){
            model.addAttribute("message", "L'auotore è obbligatorio");
            return "L'auotore è obbligatorio";
        }
        if(cleanedText==null || cleanedText.isEmpty()){
            model.addAttribute("message", "La descrizione è obbligatoria");
            return "La descrizione è obbligatoria";
        }
        if(course.getFullPriceAmount()==null || (course.getFullPriceAmount().compareTo(BigDecimal.ZERO) < 0)){
            model.addAttribute("message", "Il prezzo intero è obbligatorio e deve essere maggiore di zero");
            return "Il prezzo intero è obbligatorio e deve essere maggiore di zero";
        }
        if(course.getCurrentPriceAmount()==null || (course.getCurrentPriceAmount().compareTo(BigDecimal.ZERO) < 0)){
            model.addAttribute("message", "Il prezzo corrente è obbligatorio e deve essere maggiore di zero");
            return "Il prezzo corrente è obbligatorio e deve essere maggiore di zero";
        }
        if((course.getCurrentPriceAmount().compareTo(course.getFullPriceAmount())) > 0) {
           model.addAttribute("message", "prezzo scontato maggiore del prezzo intero");
           return "prezzo scontato maggiore del prezzo intero";
        }
        return null;
    }
}
