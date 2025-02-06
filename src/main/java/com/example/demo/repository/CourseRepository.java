package com.example.demo.repository;
import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

/*--## AI Assistant
Certo! Ecco una spiegazione chiara e dettagliata del codice in italiano:

### **Il codice spiegato:**

```java
public interface CourseRepository extends JpaRepository<Course, Integer> {
    // Se serve, qui puoi aggiungere metodi di query personalizzati
}
```

1. **Dichiarazione di un'interfaccia**:
   - La parola chiave `public interface` indica che stiamo creando un'interfaccia Java
   denominata `CourseRepository`.
   - Le interfacce in Java definiscono un contratto che le classi implementatrici
   devono rispettare, ma in questo caso, non viene implementata direttamente,
   bensì estende un'altra interfaccia.

2. **Extends JpaRepository**:
   - L'interfaccia `CourseRepository` **estende** l'interfaccia generica di
   Spring Data JPA chiamata `JpaRepository`.
   - Questo significa che `CourseRepository` eredita tutti i metodi pronti
   all'uso forniti da `JpaRepository`, come quelli per eseguire operazioni
   comuni sui database.

3. **Parametri generici `<Course, Integer>`**:
   - `JpaRepository<Course, Integer>` è parametrizzato con:
     - **`Course`**: È la **classe di entità** (entità legata alla persistenza),
     ovvero il tipo di oggetto che questa repository lavorerà (nel nostro caso,
     rappresenta un'entità chiamata `Course`).
     - **`Integer`**: È il tipo della **chiave primaria** dell'entità `Course`.
     Indica che l'attributo identificativo della classe `Course` è di tipo `Integer`.

4. **Cosa fa `JpaRepository`**:
   - `JpaRepository` è un’interfaccia fornita da Spring Data JPA che eredita
   ulteriori interfacce come `CrudRepository` e `PagingAndSortingRepository`.
   Default, ti permette di usare tanti metodi predefiniti per lavorare con i
   dati senza dover scrivere codice SQL, come ad esempio:
       - `save()`: Salvare un'entità nel database.
       - `findAll()`: Recuperare l'elenco di tutte le entità salvate.
       - `findById()`: Cercare un'entità per il suo identificativo.
       - `deleteById()`: Eliminare un'entità per il suo identificativo.
       - Molti altri.

5. **Commento nel codice**:
   - Il commento `// Se serve, qui puoi aggiungere metodi di query personalizzati`
   indica che puoi **aggiungere metodi specifici** per esigenze particolari.
   Ad esempio, se devi fare una query che non è fornita di default da `JpaRepository`,
    puoi dichiarare metodi personalizzati nell'interfaccia, che Spring Data proverà
    automaticamente a implementare usando la **parsing dei nomi dei metodi**
    (metodo query derivato - *query method derivation*).

   **Esempio**: Aggiunta di un metodo personalizzato.
```java
List<Course> findByTitleContaining(String keyword);
```
   Questo metodo cercherà tutti i corsi il cui titolo contiene la stringa specificata
   nel parametro `keyword`.

### **Ruolo di `CourseRepository` nel progetto**:
- `CourseRepository` è pensato per essere utilizzato nel livello di **persistenza
dei dati** (repository layer), ovvero il livello del progetto che si occupa di
interagire con il database grazie a Spring Data JPA.
- Con questo approccio, sfrutti i metodi predefiniti e personalizzati senza
dover scrivere manualmente il codice SQL per accedere alle tabelle del database.

### **Conclusione**:
Il `CourseRepository` è parte integrante del **pattern Repository**, che aiuta a
separare la logica di accesso ai dati (Data Access Layer) dalla logica di business
dell'applicazione. Usando Spring Data JPA, gran parte del lavoro viene automatizzato,
rendendo il codice più semplice, leggibile e manutenibile.
*/
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Courses (Title) VALUES (:title)", nativeQuery = true)
    int insertIgnore(@Param("title") String title);
    @Query("SELECT c FROM Course c WHERE c.title = :title")
    Course findByTitle(@Param("title") String title);
    Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    // Corsi con la valutazione più alta (limite 3)
    @Query("SELECT c FROM Course c ORDER BY c.rating DESC")
    List<Course> findTopByRating(Pageable pageable);
    // Ultimi corsi aggiunti (limite 3)
    @Query("SELECT c FROM Course c ORDER BY c.id DESC")
    List<Course> findTopByNewest(Pageable pageable);
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lessons WHERE c.id = :id")
    Course findCourseWithLessons(@Param("id") Integer id);
    boolean existsByTitle(String title);
    Course findCourseByAuthorAndId(String author, int courseId);
    Course findCourseById(Integer id);

    Course findById(int id);
}


