````markdown
# Bab 2. Hasil dan Pembahasan

## 2.1 Tugas Praktikum

Tugas pada pertemuan 12 ini berfokus pada implementasi Gherkin Syntax dan Cucumber pada studi kasus login SauceDemo, eksplorasi penggunaan Tag Gherkin dan parameter penyaringan pada Test Runner JUnit 5 Suite, serta penerapan Cucumber Hooks.

---

## 2.1.1 Konfigurasi Dependensi BDD (pom.xml)

Untuk membangun proyek pengujian berbasis JUnit 5 Platform Suite, Cucumber, dan Selenium WebDriver, diperlukan beberapa dependensi utama pada file `pom.xml`.

### Potongan Kode Dependensi

```xml
<dependencies>

    <!-- Selenium Java -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.43.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- JUnit Jupiter -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.13.4</version>
        <scope>test</scope>
    </dependency>

    <!-- Cucumber Java -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.33.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Cucumber JUnit Platform Engine -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit-platform-engine</artifactId>
        <version>7.33.0</version>
        <scope>test</scope>
    </dependency>

    <!-- JUnit Platform Suite -->
    <dependency>
        <groupId>org.junit.platform</groupId>
        <artifactId>junit-platform-suite</artifactId>
        <version>1.13.4</version>
        <scope>test</scope>
    </dependency>

</dependencies>
````

### Penjelasan Dependensi

1. **selenium-java**
   Digunakan untuk melakukan otomatisasi browser menggunakan Selenium WebDriver.

2. **junit-jupiter**
   Framework pengujian utama yang menyediakan assertion seperti `assertEquals()` dan `assertTrue()`.

3. **cucumber-java**
   Digunakan untuk memetakan sintaks Gherkin seperti `Given`, `When`, dan `Then` ke dalam kode Java.

4. **cucumber-junit-platform-engine**
   Berfungsi sebagai penghubung antara Cucumber dan JUnit 5.

5. **junit-platform-suite**
   Digunakan untuk menjalankan Test Suite berbasis JUnit 5 dan mendukung filtering tag.

---

## 2.1.2 Tugas 1: Implementasi Gherkin Syntax dan Cucumber

Tugas pertama berfokus pada implementasi skenario login SauceDemo menggunakan pendekatan Behavior Driven Development (BDD).

### 1. Skenario Gherkin (`login.feature`)

```gherkin
@Login
Feature: SauceDemo Login Scenarios

Background:
  Given User is on the login page

@StandardUser @Positive
Scenario: Successful login with standard user
  When User enter username "standard_user" and password "secret_sauce"
  And User click the login button
  Then User should be redirected to the inventory page

@LockedOut @Negative
Scenario: Login failure with locked out user
  When User enter username "locked_out_user" and password "secret_sauce"
  And User click the login button
  Then User should see error message "Sorry, this user has been locked out."
```

### Penjelasan

* `@Login` digunakan sebagai tag global untuk seluruh skenario.
* `Background` digunakan untuk menjalankan langkah yang sama sebelum setiap skenario dimulai.
* `Scenario` mendefinisikan alur pengujian berdasarkan perilaku pengguna.
* `Given`, `When`, `Then`, dan `And` digunakan untuk mendeskripsikan langkah pengujian dalam bahasa natural.

---

## 2. Page Object Model (POM) — `loginPage.java`

Class `loginPage` digunakan untuk merepresentasikan halaman login pada SauceDemo.

### Potongan Kode

```java
public class loginPage extends basePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");

    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }
}
```

### Penjelasan

* Locator digunakan untuk mencari elemen halaman seperti input username, password, dan tombol login.
* Method seperti `enterUsername()` dan `clickLogin()` digunakan untuk memisahkan logika interaksi halaman agar kode lebih terstruktur.

---

## 3. Page Object Model (POM) — `inventoryPage.java`

Class `inventoryPage` merepresentasikan halaman inventory setelah login berhasil.

### Potongan Kode

```java
public class inventoryPage extends basePage {

    private final By inventoryList = By.className("inventory_list");

    public boolean isInventoryListDisplayed() {
        return waitUntil(inventoryList).isDisplayed();
    }
}
```

### Penjelasan

* `inventoryList` digunakan untuk memastikan daftar produk berhasil tampil.
* Method `isInventoryListDisplayed()` digunakan untuk melakukan validasi tampilan halaman inventory.

---

## 4. Step Definitions dan Hooks (`loginStepDef.java`)

Class ini menghubungkan langkah Gherkin dengan implementasi Selenium WebDriver.

### Potongan Kode Hooks

```java
@Before
public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
}

@After
public void tearDown() {
    if(driver != null) {
        driver.quit();
    }
}
```

### Penjelasan

* `@Before` digunakan untuk menjalankan proses setup sebelum skenario dimulai.
* `@After` digunakan untuk menutup browser setelah pengujian selesai.
* Hooks membantu menjaga kebersihan resource dan mempermudah manajemen browser.

---

## 5. Test Runner — JUnit 5 Suite

Test Runner digunakan untuk menjalankan file feature menggunakan engine Cucumber.

### Potongan Kode

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/login.feature")
@ConfigurationParameter(
    key = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@Login"
)
public class LoginSuiteRunner {
}
```

### Penjelasan

* `@Suite` mendefinisikan class sebagai test suite.
* `@IncludeEngines("cucumber")` mengaktifkan engine Cucumber.
* `@SelectClasspathResource()` menentukan file `.feature` yang akan dijalankan.
* `FILTER_TAGS_PROPERTY_NAME` digunakan untuk melakukan filtering skenario berdasarkan tag.

---

## 6. Hasil Eksekusi Tugas 1

Seluruh skenario login berhasil dijalankan menggunakan JUnit 5 Runner dan memperoleh status passed.

### Penjelasan Gambar 2.1

**Gambar 2.1 — Hasil Eksekusi 6 Skenario Login SauceDemo (@Login)**

Gambar tersebut menunjukkan hasil eksekusi seluruh skenario login pada IntelliJ IDEA. Seluruh test case berhasil dijalankan dengan status sukses (passed) yang ditandai dengan ikon centang hijau.

---

## 2.1.3 Tugas 2: Eksplorasi Tag Gherkin dan Filter Tag pada Test Runner

Pada tahap ini dilakukan filtering pengujian menggunakan tag `@Negative`.

### Konfigurasi Filter Tag

```java
@ConfigurationParameter(
    key = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@Negative"
)
```

### Penjelasan

* Hanya skenario yang memiliki tag `@Negative` yang akan dijalankan.
* Skenario lain otomatis dilewati (skipped).

### Hasil Analisis

JUnit 5 Suite hanya menjalankan skenario login gagal (`locked_out_user`) dan melewati seluruh skenario positif.

### Penjelasan Gambar 2.2

**Gambar 2.2 — Hasil Eksekusi Filter Tag @Negative**

Gambar menunjukkan bahwa hanya satu skenario gagal yang berhasil dieksekusi sesuai filter tag `@Negative`.

### Penjelasan Gambar 2.3

**Gambar 2.3 — Pesan Filter Mismatch**

Gambar menunjukkan pesan pada console IntelliJ bahwa beberapa skenario dilewati karena tidak sesuai dengan filter tag yang digunakan.

---

## 2.1.4 Tugas 3: Penerapan Saringan Test Spesifik via Test Runner

Pada tahap ini dilakukan filtering hanya pada skenario `@StandardUser`.

### Konfigurasi Filter

```java
@ConfigurationParameter(
    key = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@StandardUser"
)
```

### Penjelasan

* Hanya satu skenario login standard user yang dijalankan.
* Seluruh skenario lain dilewati.

### Hasil Analisis

Filtering tag membantu proses debugging dan pengujian ulang secara cepat pada satu fitur tertentu.

### Penjelasan Gambar 2.4

**Gambar 2.4 — Hasil Eksekusi Filter Tag @StandardUser**

Gambar menunjukkan bahwa hanya satu skenario login standard user yang dijalankan dan berhasil sukses.

### Penjelasan Gambar 2.5

**Gambar 2.5 — Pesan Filter Mismatch Non-StandardUser**

Gambar memperlihatkan console IntelliJ yang menampilkan pesan bahwa skenario lain tidak dijalankan karena tidak memiliki tag `@StandardUser`.

---

## 2.1.5 Poin Tambahan (Bonus): Implementasi Hooks

Hooks digunakan untuk mengelola lifecycle pengujian secara otomatis.

### Implementasi Hooks

```java
@Before
public void setUp() {
    driver = new ChromeDriver();
}

@After
public void tearDown() {
    driver.quit();
}
```

### Keuntungan Penggunaan Hooks

1. **Browser Lifecycle Management**
   Browser selalu dibuka dalam kondisi bersih sebelum pengujian.

2. **Automatic Resource Cleanup**
   Browser otomatis ditutup sehingga mencegah memory leak.

3. **Code Reusability**
   Kode setup dan cleanup tidak perlu ditulis berulang.

---

## 2.2 Kode Sumber Lengkap di GitHub

Seluruh source code pengujian dapat diakses melalui repository GitHub berikut:

```text
https://github.com/akmalmanggala/BDD-gherkin.git
```

```
```
