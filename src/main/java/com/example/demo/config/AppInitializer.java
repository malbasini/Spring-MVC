package com.example.demo.config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*--## AI Assistant
Il codice iniziale rappresenta una classe di configurazione che estende `AbstractAnnotationConfigDispatcherServletInitializer`, ed è utilizzata per configurare un'applicazione Java Spring basata su **Spring MVC** e **Hibernate**. Questa classe funge da punto di inizializzazione (simile a un file `web.xml` in applicazioni più datate) per una web application e definisce la configurazione root e quella specifica per il contesto web.

Esaminiamo ciascun componente nel dettaglio.

### 1. **Annotazioni**

```java
@Configuration
@ComponentScan(basePackages = "com.example.demo")
```

- `@Configuration`: Indica a Spring che questa classe contiene definizioni di configurazioni Bean.
- `@ComponentScan(basePackages = "com.example.demo")`: Consente a Spring di scansionare il package `com.example.demo` e i suoi sottopacchetti per rilevare componenti e configurazioni (annotati con `@Component`, `@Service`, `@Repository`, ecc.).

---

### 2. **Estensione di `AbstractAnnotationConfigDispatcherServletInitializer`**

La classe estende `AbstractAnnotationConfigDispatcherServletInitializer`, che è una classe di utilità fornita da Spring per configurare applicazioni web basate su `DispatcherServlet`. Qui viene configurato in modo programmatico il dispatcher servlet e i contesti di configurazione dell'applicazione.

Ecco cosa viene configurato:

#### a. **Metodo `getRootConfigClasses()`**

```java
@Override
protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{HibernateConfig.class};
}
```

- Questo metodo ritorna le classi di configurazione della parte **root** dell'applicazione, che è il contesto globale e condiviso.
  - Qui viene passata la classe `HibernateConfig.class`, che configura Hibernate per la gestione della persistenza, JDBC, transazioni, ecc.

#### b. **Metodo `getServletConfigClasses()`**

```java
@Override
protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[]{WebMvcConfig.class};
}
```

- Questo metodo ritorna le classi di configurazione relative alla parte web dell'applicazione, cioè il contesto specifico per **Spring MVC**.
  - Qui viene passata la classe `WebMvcConfig.class`, che include la configurazione di visualizzatori (es. JSP) e mapping per risorse statiche come file CSS, JavaScript, immagini.

#### c. **Metodo `getServletMappings()`**

```java
@Override
protected String[] getServletMappings() {
    return new String[]{"/"};
}
```

- Questo metodo specifica il mapping delle richieste HTTP da gestire nel contesto da `DispatcherServlet`.
  - In questo caso, tutte le richieste che corrispondono all'URL `/*` saranno consegnate al `DispatcherServlet`.

---

### 3. **Classi di configurazione aggiuntive**

Visto che sono state fornite le classi `HibernateConfig` e `WebMvcConfig`, esaminiamo il loro contenuto per capire il ruolo di questa configurazione.

#### a. **HibernateConfig**

Questa classe configura Hibernate per la gestione di database e persistenza degli oggetti. Alcuni punti chiave:

- **Annotazioni**:
  - `@EnableJpaRepositories`: Attiva il supporto per i repository JPA (nel package `com.example.demo.repository`).
  - `@EnableTransactionManagement`: Abilita la gestione delle transazioni (usata con Hibernate o un database relazionale supportato).

- **Definizione `entityManagerFactory`**:
  - Configura un Entity Manager Factory per Hibernate, specificando la connessione al database (`dataSource`) e altre proprietà JPA (dialetto, creazione schema, ecc.).
  - Le entità sono cercate nel package `com.example.demo.entity`.

- **Definizione `dataSource`**:
  - Implementa una connessione a un database MySQL usando `ComboPooledDataSource`.
  - Configura il pooling delle connessioni (min/max connessioni, timeout).

- **Session Factory e Gestione transazioni**:
  - Configura una Session Factory (`LocalSessionFactoryBean`) e il gestore delle transazioni tramite Hibernate (`HibernateTransactionManager`).

---

#### b. **WebMvcConfig**

Questa classe configura Spring MVC per la gestione del front-end. Alcuni punti importanti:

- **Annotazioni**:
  - `@EnableWebMvc`: Abilita le funzionalità di Spring MVC (handler mapping, view resolver, ecc.).
- **Metodo `configureViewResolvers`**:
  - Configura un resolver che individua le viste (JSP) nella directory `/WEB-INF/views/` con estensione `.jsp`.
- **Metodo `addResourceHandlers`**:
  - Gestisce risorse statiche come CSS, JS e immagini per tutti gli URL che iniziano con `/resources/`.

---

### 4. **Riepilogo del processo**

1. Spring avvia l'app utilizzando `AppInitializer` come punto di configurazione principale.
2. La configurazione root (`HibernateConfig`) è caricata per gestire la persistenza e le transazioni.
3. La configurazione servlet (`WebMvcConfig`) è caricata per gestire la parte web (MVC):
   - Mappatura richieste,
   - Rendering delle viste JSP,
   - Risorse statiche.
4. Tutte le richieste all'applicazione sono intercettate dal `DispatcherServlet` per inoltrare a controller relativi (definiti nel package `com.example.demo.controller` o simili).

---

### 5. **Esempio di Flusso**

1. Un utente apre l’URL `/home`.
2. Il `DispatcherServlet` mappa la richiesta a un controller appropriato, ad esempio una classe in `com.example.demo.controller.HomeController`.
3. Il controller elabora la richiesta, usa Hibernate per interagire col database, e ritorna una view (es. `home.jsp`), che viene visualizzata all’utente.

In sintesi, questa configurazione è un tipico esempio di un'applicazione Spring MVC + Hibernate configurata programmaticamente (senza `web.xml`).
*/
@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Configurazione root (ad esempio Hibernate, sicurezza, ecc.)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{HibernateConfig.class}; // Configurazioni di Hibernate
    }

    // Configurazione del contesto Web (Spring MVC)
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfig.class}; // Configurazioni MVC
    }

    // Mappatura delle richieste per il DispatcherServlet
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; // Tutte le richieste passano per il DispatcherServlet
    }
}
