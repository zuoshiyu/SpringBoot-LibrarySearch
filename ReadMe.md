# SpringBoot-LibrarySearch
A simple school project for searching and filtering books in a library

To run this project, please go to the release page, download the latest release, extract and go to the folder, import latest sql into your mysql database, and run "java -jar ./springboot-librarysearch-0.0.1-SNAPSHOT.jar" under the folder.

Then, open your browser and go to localhost:8080  
Supported search url: ~/simplesearch, ~/advancedsearch, ~/textsearch, ~/hotwordsearch and ~/oversearch
Supported edit url: ~/showEntitys, ~/addEntity, ~/updatEntity  
Supported Entity: PhysicalBook, DigitalBook, User

You might need to login with username: user and password: test  
before allowed accessing the search urls
Or username: admin and password: test
before allowed accessing the edit urls