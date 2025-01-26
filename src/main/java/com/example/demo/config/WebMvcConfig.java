package com.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.demo")
/*--Il codice fornito rappresenta una configurazione per un'applicazione **Spring MVC**. La classe `WebMvcConfig` utilizza un approccio basato su annotazioni per configurare il framework Spring senza la necessità di un file XML.
Vediamo in dettaglio la configurazione della classe `WebMvcConfig`.
### Analisi del Codice
#### 1. **Annotazioni**
``` java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.demo")
```
- **`@Configuration` **: Indica che questa classe contiene le definizioni di configurazioni che verranno gestite dal contenitore Spring come bean.
- **`@EnableWebMvc` **: Abilita le funzionalità di Spring MVC, facendo sì che questa classe configuri internamente gli aspetti legati al framework MVC, come il dispatcher servlet, il `HandlerMapping` e il supporto per i controller.
- **`@ComponentScan(basePackages = "com.example.demo")` **: Consente a Spring di scansionare il package `com.example.demo` e i suoi sottopacchetti per individuare componenti (come controller, servizi e repository) da gestire nel contesto dell'applicazione.

#### 2. **Implementazione di `WebMvcConfigurer`**
La classe implementa l'interfaccia `WebMvcConfigurer`. Questo permette di personalizzare il comportamento standard di Spring MVC tramite **override** di diversi metodi configurativi.
Vediamo i metodi implementati passo per passo.
##### a) Configurazione del View Resolver
``` java
@Override
public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp("/WEB-INF/views/", ".jsp");
}
```
- Questo metodo configura un **resolver di viste** per Spring MVC.
    - Il `ViewResolver` specifica dove trovare i file delle viste JSP utilizzati per il rendering del contenuto HTML.
    - Qui viene indicato che:
        - Le viste si trovano nella directory `/WEB-INF/views/`.
        - I file delle viste hanno estensione `.jsp`.

- Ad esempio, se un controller restituisce `return "home";`, Spring cercherà il file `home.jsp` nella directory `/WEB-INF/views/`.

##### b) Configurazione dei Resource Handlers
``` java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
}
```
- Questo metodo gestisce le **risorse statiche** come file CSS, immagini e JavaScript.
    - `addResourceHandler("/resources/**")`: Definisce che tutte le risorse richieste con un URL che corrisponde a `/resources/**` devono essere gestite separatamente da Spring MVC.
    - `addResourceLocations("/resources/")`: Specifica che tali risorse devono essere caricate dalla directory `/resources/`.

In sintesi, una richiesta per un file come `http://localhost:8080/resources/style.css` verrà gestita prelevando il file dalla directory `/resources/`.
#### 3. **Il Ruolo di `WebMvcConfig`**
Questa classe, in combinazione con il file `AppInitializer`, rappresenta la configurazione del contesto web dell'applicazione. Più in dettaglio:
- Configura Spring MVC come nucleo per la gestione delle richieste HTTP.
- Definisce il mapping ai file delle JSP per generare le risposte HTML.
- Permette la gestione di risorse statiche per personalizzare l'interfaccia dell'applicazione.

### Relazione con `AppInitializer`
La classe `AppInitializer` specificata nel file correlato completa la configurazione generale dell'applicazione. Spring utilizza `AppInitializer` per inizializzare il contesto e caricare la configurazione di `WebMvcConfig`. Ecco il flusso:
1. La classe `AppInitializer` estende `AbstractAnnotationConfigDispatcherServletInitializer`, una base per configurare il **DispatcherServlet**, che è il cuore di Spring MVC.
2. `WebMvcConfig` è richiamata da `getServletConfigClasses()` di `AppInitializer` per configurare tutti gli aspetti legati alla gestione delle richieste web.

### Schema del Flusso di Funzionamento
1. L'applicazione è avviata e `AppInitializer` configura i contesti (root e servlet).
2. Le richieste HTTP arrivate al `DispatcherServlet` sono analizzate:
    - Le risorse statiche vengono servite tramite il `ResourceHandler`.
    - Le richieste dinamiche (es. `/home`) passano ai controller.

3. I controller elaborano la logica e restituiscono nomi di viste, che vengono risolti dal `ViewResolver` per individuare i file `.jsp`.
4. La vista (ad esempio `home.jsp`) genera una risposta HTML per l'utente.

### Conclusione
Questa configurazione è un tipico esempio per un'applicazione Spring MVC che:
- Fa uso di file JSP per le viste.
- Gestisce risorse statiche con resource handler.
- Utilizza un approccio a configurazione Java (senza XML).
*/
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/Users/malbasini/.SmartTomcat/MyCourse/MyCourse/uploads/");
    }
    // Opzionale: Configura la mappatura della home senza controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}
