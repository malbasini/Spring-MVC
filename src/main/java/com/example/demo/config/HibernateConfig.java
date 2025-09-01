package com.example.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@ComponentScan(basePackages = "com.example.demo")
@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@EnableTransactionManagement
/*--## AI Assistant
Il codice fornito implementa una configurazione per Hibernate e JPA in un progetto Java che utilizza **Spring Framework**, con un database relazionale (in questo caso MySQL). Vediamo i dettagli del codice e il suo significato.

---

### **1. Annotations principali**

Il file di configurazione contiene diverse annotazioni che definiscono il comportamento e la configurazione dei componenti Spring:

#### **a. `@ComponentScan`**
```java
@ComponentScan(basePackages = "com.example.demo")
```
- Dice a Spring di scansionare il pacchetto `com.example.demo` e i suoi sottopacchetti per individuare i bean annotati con annotazioni come `@Service`, `@Repository`, `@Controller`, ecc.

#### **b. `@Configuration`**
```java
@Configuration
```
- Indica che la classe `HibernateConfig` è una classe di configurazione Spring e include metodi annotati con `@Bean` per creare e configurare bean nel contesto dell'applicazione.

#### **c. `@EnableJpaRepositories`**
```java
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
```
- Abilita il supporto per i repository JPA in Spring Data e specifica il pacchetto dove cercare le interfacce dei repository (`com.example.demo.repository`).

#### **d. `@EnableTransactionManagement`**
```java
@EnableTransactionManagement
```
- Abilita la gestione delle transazioni declarative in Spring, permettendo l'uso di annotazioni come `@Transactional`.

---

### **2. Metodi principali**

#### **a. `entityManagerFactory`**
```java
@Bean
public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
```
- Configura il bean `LocalContainerEntityManagerFactoryBean`, che è il punto chiave per la gestione di entità JPA in Hibernate.
- Configurazioni importanti incluse:
  - **DataSource:** Viene specificata la sorgente dati (`DataSource`), che contiene le informazioni sulla connessione al database.
  - **Pacchetto delle entità:** Specificato da `setPackagesToScan("com.example.demo.entity")`, individua le classi JPA annotate con `@Entity`.
  - **Hibernate JPA Vendor Adapter:** Configurato come `HibernateJpaVendorAdapter`.
  - **Proprietà JPA:** Definite con un oggetto `Properties`, queste configurano Hibernate per:
    - Dialetto SQL: `MySQL8Dialect` (Hibernate genererà query compatibili con MySQL 8).
    - Output SQL: Mostrare (`show_sql`) e formattare (`format_sql`) le query SQL nel log.
    - Schema del database: Aggiornare le tabelle automaticamente (`hbm2ddl.auto = update`).

---

#### **b. `dataSource`**
```java
@Bean
public DataSource dataSource()
```
- Crea e configura un **DataSource** utilizzando `ComboPooledDataSource` di C3P0, una libreria per la gestione del pooling delle connessioni al database.
- Proprietà principali di configurazione del database:
  - **Driver:** `com.mysql.cj.jdbc.Driver`.
  - **URL:** Connessione al database MySQL, configurata con timezone e senza SSL: `jdbc:mysql://localhost:3306/MyCourse`.
  - **Utente e password:** Specificati per accedere al database.
- Proprietà aggiuntive di C3P0:
  - Configurazioni per il massimo e minimo di connessioni nel pool (`setMinPoolSize` e `setMaxPoolSize`).
  - Timeout delle connessioni non restituite e inattive.

---

#### **c. `sessionFactory`**
```java
@Bean
public LocalSessionFactoryBean sessionFactory(DataSource dataSource)
```
- Configura il `sessionFactory` di Hibernate e specifica:
  - **DataSource:** Utilizza la sorgente dati configurata.
  - **Pacchetto delle entità:** `setPackagesToScan("com.example.demo.entity")`.
  - **Proprietà Hibernate:** Specificate nel metodo `hibernateProperties()`.

---

#### **d. `hibernateProperties`**
```java
private Properties hibernateProperties()
```
- Configura le proprietà Hibernate, tra cui:
  - **Dialetto SQL:** `hibernate.dialect = MySQL8Dialect`.
  - **Abilitazione dei log SQL:** `hibernate.show_sql = true` e `hibernate.format_sql = true`.
  - **Gestione schema database:** `hibernate.hbm2ddl.auto = update` aggiorna automaticamente lo schema del database in base alle entità.

---

#### **e. `transactionManager`**
```java
@Bean
public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
```
- Configura un `HibernateTransactionManager` per gestire le transazioni Hibernate. Questo bean è necessario per il supporto della gestione declarativa delle transazioni tramite annotazioni (`@Transactional`).

---

### **Funzionamento d'insieme**
Questa classe configura Spring con Hibernate e JPA per lavorare con MySQL. Gli step principali sono:
1. **Connessione al database (DataSource):**
   - Le credenziali e le configurazioni del database sono fornite tramite il `ComboPooledDataSource`.

2. **Gestione delle entità e dello schema database:**
   - Gli oggetti Entity Manager (`entityManagerFactory`) e Session Factory (`sessionFactory`) si occupano della mappatura tra le entità Java e le tabelle del database.
   - Hibernate, tramite `hbm2ddl.auto`, aggiorna lo schema del database automaticamente in base alle entità.

3. **Transazioni:**
   - L'abilitazione della gestione delle transazioni (`@EnableTransactionManagement`) permette un controllo robusto delle operazioni di commit e rollback.

---

### **Relazione con `WebMvcConfig.java`**
La classe `WebMvcConfig` è complementare a `HibernateConfig`. Mentre `HibernateConfig` si occupa del livello di persistenza dei dati usando Hibernate e JPA, `WebMvcConfig` abilita le funzionalità Spring MVC per creare un'applicazione web.

In particolare:
1. Configura il **ViewResolver** per cercare le viste all'interno della directory `/WEB-INF/views/`, con estensione `.jsp`.
2. Gestisce le risorse statiche come CSS, script, immagini, ecc., mappandole al percorso `/resources/**`.

---

### **Conclusione**
Questo progetto utilizza **Spring Framework**, **Spring Data JPA**, e **Hibernate** per implementare un'architettura di persistenza robusta. Configura una sorgente dati con gestione del pooling (C3P0), il mapping tra le entità Java e il database, e la gestione delle transazioni. È ideale per sviluppare applicazioni web con un backend basato su database relazionale come MySQL e uno strato frontend integrato con **Spring MVC**.
*/
public class HibernateConfig {

    @Autowired
    private Environment env;
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.demo.entity"); // pacchetto delle entità
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Proprietà di Hibernate
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        emf.setJpaProperties(jpaProperties);

        return emf;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Errore nella configurazione del driver", e);
        }
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/MyCourseMvc?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("KHJuial0a100?");

        // Configurazioni c3p0 per gestire connessioni abbandonate
        dataSource.setMaxIdleTimeExcessConnections(300); // Tempo massimo di inattività per le connessioni in eccesso
        dataSource.setUnreturnedConnectionTimeout(300);  // Timeout per connessioni non restituite
        dataSource.setDebugUnreturnedConnectionStackTraces(true);
        // Configurazioni c3p0
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setMaxIdleTimeExcessConnections(300); // Connessioni inattive chiuse dopo 300 secondi
        dataSource.setUnreturnedConnectionTimeout(300);  // Timeout per connessioni non restituite

        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.example.demo.entity"); // Pacchetto delle entità
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("logging.level.org.hibernate.SQL", "DEBUG");
        properties.put("logging.level.org.hibernate.type.descriptor.sql" , "TRACE");
        properties.put("spring.jpa.properties.hibernate.format_sql" ,"true");
        return properties;
    }
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}
