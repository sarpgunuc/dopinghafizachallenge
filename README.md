# dopinghafizachallenge

Dopingim uygulaması Doping Hafıza’nın işe alım sürecinde 2. Aşama olan code challenge’ı başarılı bir şekilde tamamlamak için yazılmış , öğrencilerin test çözebileceği bir backend projesidir. 

Bu dosyanın İngilizce tercümesini , Türkçe anlatımın bitiminde bulabilirsiniz. Ayrıca dosyada anlatılan bilgileri, code challange’ta istenilen özelliklerin karşılandığını tek tek gösterdiğim ve benim istenilene ek olarak neler yaptığımı gösterdiğim videoya aşağıdaki linkten ulaşabilirsiniz.


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

		Response: 	
{
  		  		"token": "jwt_token"
}

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




















