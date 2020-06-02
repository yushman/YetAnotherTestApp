# YetAnotherTestApp
Yet Another Test App

Приложение, загружающее список фильмов с удаленного Api
Используется - Retrofit, Gson, Glide, Koin, MVVM, LD, ROOM, RX

Что выполнено:
* Короткий тап - Тост с названием
* Длинный тап - Добавление/Удаление в избранное и тост об этом
* Если домотать до конца загруженной страницы - будет футер загрузка (если это не последняя страница)
* Если при загрузке ошибка - будет футер с кнопкой перезагрузить
* Pull-to-refresh
* Поиск по "Избранным"
* Если поиск пустой - экран "Пустой поиск"
* Задержка в 1сек после ввода текста для поиска, что бы не спамить в бд
* Пагинация