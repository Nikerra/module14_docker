# Module 14. Контейнеризация Docker. Design Patterns.

### Требования: 
Реализовать Spring Boot Rest приложение, отображающее информацию о книгах.
При обращении http://localhost:8080/v1/api/books должен возращаться список книг
При общении к http://localhost:8080/v1/api/category/it должны отображаться книги из выбранной категории

Ответ должен быть в виде json:
Название книги, автор, язык, категория.
Отобращаться должны только активные книги.

## Критерии приемки:
1. Использовать паттерн проектирования MVC (Model-View-Controller)
2. В качестве базы данных можно использовать postgresql (предпочтение)
3. Приложение должно находиться в Docker-e, для настройки использовать docker-compose
4. Написать unit tests для слоя Controller, Service, Repository (задание со звёздочкой)
5. Использовать вместо Book сущность BookWithRelations, реализовать поддержку свзей между таблицами (задание со звёздочкой)