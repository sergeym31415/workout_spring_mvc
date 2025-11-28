
# Workour app  
Web приложение на Spring Boot для назначения тренером программы тренировок атлету  
## Инструкция по развертыванию с помощью docer-compose  
- Клонируй репозиторий  
```git clone https://github.com/sergeym31415/workout_spring_mvc.git```  
- Перейди в терминале в папку проекта и выполни  
```docker-compose up --build```
## Миграции через liquibase
## Схема БД  
![Схема бд](https://github.com/sergeym31415/workout_spring_mvc/blob/master/bd_schema.PNG)
## FAQ
#### Как подключиться к приложению после развертывания?
- порт 8181  
- логин admin  
- пароль password1  
#### Где сменить пароль от БД?
- liquibase.properties  
- src/main/resources/application.properties  
