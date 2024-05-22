# dopinghafizachallenge

Dopingim uygulaması Doping Hafıza’nın işe alım sürecinde 2. Aşama olan code challenge’ı başarılı bir şekilde tamamlamak için yazılmış , öğrencilerin test çözebileceği bir backend projesidir. 

Bu dosyanın İngilizce tercümesini , Türkçe anlatımın bitiminde bulabilirsiniz. Ayrıca dosyada anlatılan bilgileri, code challange’ta istenilen özelliklerin karşılandığını tek tek gösterdiğim ve benim istenilene ek olarak neler yaptığımı gösterdiğim videoya aşağıdaki linkten ulaşabilirsiniz.



VİDEO LİNKİ : https://youtu.be/-qler4lVTmU

İÇİNDEKİLER

	Kullanılan Teknolojiler
	Proje Kurulumu
	Veritabanı Yapılandırılması
	Uygulama Yapılandırması 
	API Kullanımı
	Önbellek Yapılandırması
	Testler 





Kullanılan Teknolojiler: 

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- Spring Cache (Caffeine)
- H2 Database
- JWT (JSON Web Token)
- Gradle 
- JUnit (Birim Testler)
- Mockito (Test için Mocklama)

Proje Kurulumu:
-	Projeyi klonlayıp gerekli bağımlılıkları yükleyin: 
sh https://github.com/sarpgunuc/dopinghafizachallenge.git
Cd dopingim
Sh ./gradlew build

Veritabanı Yapılandırması:
	Yönergede önerilmesi üzerine uygulama H2 veritabanı kullanmaktadır. 
	Veritabanı yapılandırması application.properties dosyasındadır: 

spring.datasource.url=jdbc:h2:file:./data/testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update

	H2 konslouna erişmek için tarayıcınızdan  http://localhost:8080/h2-console  adresine gidebilirsiniz.

Uygulama Yapılandırması:
	JWT , Cache gibi uygulama yapılandırmaları application.properties dosyasında bulabilirsiniz:

jwt.secret=mysupersecretkeywhichis32byteslong123456
spring.cache.type=CAFFEINE
Caffeine cache configuration
spring.cache.cache-names=students, quizzes, questions
spring.cache.caffeine.spec=maximumSize=500,expireAfterAccess=10m

API Kullanımı: 
	Öğrenci İşlemleri:

o	Kayıt olma işlemi: 
	POST /api/students/signup
Body: {
    "firstName": "sercan",
    "lastName": "balabanli",
    "username": "sercanbalabanli",
    "password": "password"
}

o	Giriş yapma işlemi : 
	POST /api/students/student-login
Body: {
    "username": "sercanbalabanli",
    "password": "password"
}

--> Login olduktan sonra jwt token alacaksınız. Bundan sonra ki işlemlere devam etmek için  jwt token 'ı  Authoritazion da Bearer Token ı seçip yanında ki token bölümün yapıştırmanız gerekir. Bu işlem uygulamanın güvenilir olmasını sağlar

Response: 	{ "token": "jwt_token"}

o	Student getirme işlemi: 
	GET /api/students/{username}

Quiz İşlemleri:

o	Quiz oluşturma:
	POST /api/quizzes
Body:{
  "name": "Doping Testi",
  "questions": [
    {
      "questionText": "Türkçe dopingim hangi ders ile ilgilidir?",
      "answer": "Türkçe"
    },
    {
      "questionText": "Doping Teknolojileri backend developer adayı kimdir ? ",
      "answer": "Sarp Günüç"
    }
  ]
}

o	Tüm Quizleri Getir: 
	GET /api/quizzes
o	Spesifik Quizi Getir : 
	GET /api/quizzes/{id}

Soru İşlemleri:

o	Soru Oluştur:
	POST /api/questions
Body: {
    "questionText": "What is 2+2?",
    "answer": "4",
    "quiz": {"id": 1}
}
o	Tüm Soruları Getir:
	GET /api/questions
o	Spesifik Soruları Getir: 
	GET /api/questions{id}


Öğrenci Quiz İşlemleri : 

o	Öğrenci Quize Başlaması: 
(Öğrencinin quize aktif bir kaydı olmasını sağlar .Eğer öğrenci bir quize kayıt olmadıysa o quizi çözemez)

	POST /api/student-quizzes/start
Body: {
    "student": {"id":{student_id}},
    "quiz": {"id": {quiz_id}}
}
o	Tüm Öğrenci Quizlerini Getir:
	GET /api/student-quizzes
o	Spesifik Öğrenci Quizlerini Getir: 
	GET /api/student-quizzes/{id}
o	Öğrenci’nin Quiz Puanını Getir: 
	GET /api/student-quizzes/student/{studentId}/quiz/{quizId}/score
o	Öğrencinin Tüm Quizlerini Getir: 
	GET /api/student-quizzes/student/{studentId}


Öğrenci Cevap İşlemleri 
o	Öğrenci Cevabı Oluştur: 
	POST /api/student-answers
Body: {
    "studentQuiz": {"id": 1},
    "question": {"id": 1},
    "answer": "4"
}
o	Tüm Öğrenci Cevaplarını Getir: 
	GET /api/student-answers
o	Spesifik Öğrenci Cevaplarını Getir: 
	GET /api/student-answers/{id}



Önbellek Yapılandırılması: 

 Caffeine kullanılarak önbellekleme yapılmıştır. 
 İlgili servis metotlarına @Cacheable anotasyonu eklendi.


Testler: 

JUnit ve Mockito kullanılarak birim testler yazılmıştır. 







----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




The Dopingim application is a backend project written to successfully complete the code challenge, which is the second stage of the Doping Hafıza recruitment process. This project allows students to take tests.

Contents
Technologies Used
Project Setup
Database Configuration
Application Configuration
API Usage
Cache Configuration
Tests

Technologies: 

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- Spring Cache (Caffeine)
- H2 Database
- JWT (JSON Web Token)
- Gradle 
- JUnit 
- Mockito 



Project Setup: 

sh https://github.com/sarpgunuc/dopinghafizachallenge.git
Cd dopingim
Sh ./gradlew build

Database Configuration:
 The application uses the H2 database as recommended in the directive. 
 The database configuration is in the application.properties file:

spring.datasource.url=jdbc:h2:file:./data/testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update


--> You can find h2 database console :   http://localhost:8080/h2-console

Application Configuration:
 You can find application configurations such as JWT, Cache in the application.properties file:


jwt.secret=mysupersecretkeywhichis32byteslong123456
spring.cache.type=CAFFEINE
Caffeine cache configuration
spring.cache.cache-names=students, quizzes, questions
spring.cache.caffeine.spec=maximumSize=500,expireAfterAccess=10m



API Usage: 
	Student:

o	Signup: 
	POST /api/students/signup
Body: {
    "firstName": "sercan",
    "lastName": "balabanli",
    "username": "sercanbalabanli",
    "password": "password"
}

o	Login : 
	POST /api/students/student-login
Body: {
    "username": "sercanbalabanli",
    "password": "password"
}

--> After logging in, you will receive jwt tokens. To continue with the following transactions, you must select the jwt token and Bearer Token in Authoritazion and paste it into the token section next to it. This process ensures that the application is reliable

Response: 	{ "token": "jwt_token"}

o	Get students by username: 
	GET /api/students/{username}

Quiz :

o	Create quiz:
	POST /api/quizzes
Body:{
  "name": "Doping Testi",
  "questions": [
    {
      "questionText": "Türkçe dopingim hangi ders ile ilgilidir?",
      "answer": "Türkçe"
    },
    {
      "questionText": "Doping Teknolojileri backend developer adayı kimdir ? ",
      "answer": "Sarp Günüç"
    }
  ]
}

o	Get all quiz: 
	GET /api/quizzes
o	Get quiz by id  : 
	GET /api/quizzes/{id}

Question:

o	Create question:
	POST /api/questions
Body: {
    "questionText": "What is 2+2?",
    "answer": "4",
    "quiz": {"id": 1}
}
o	Get all question:
	GET /api/questions
o	Spesifik Soruları Getir: 
	GET /api/questions{id}


StudentQuiz  : 

o	Starting quiz: 
(Ensures that the student has an active registration for the quiz. If the student has not registered for a quiz, she cannot solve that quiz)

	POST /api/student-quizzes/start
Body: {
    "student": {"id":{student_id}},
    "quiz": {"id": {quiz_id}}
}
o	Get all student-quiz:
	GET /api/student-quizzes
o	Get student-quiz by id: 
	GET /api/student-quizzes/{id}
o	Get score of student : 
	GET /api/student-quizzes/student/{studentId}/quiz/{quizId}/score
o	Get all quiz : 
	GET /api/student-quizzes/student/{studentId}

Student Answer 
o	Create student answer: 
	POST /api/student-answers
Body: {
    "studentQuiz": {"id": 1},
    "question": {"id": 1},
    "answer": "4"
}
o	Get all student answer: 
	GET /api/student-answers
o	Get student-answer by id: 
	GET /api/student-answers/{id}



Cache Configuration: 

 Caching was done using Caffeine. 
 @Cacheable annotation was added to the relevant service methods.


Tests: 

Written unit tests using JUnit and Mockito.


