# MangaDex Reader (`com.example.MangaDex`)

Android-приложение для чтения манги поверх публичного [MangaDex API](https://api.mangadex.org/docs/), с авторизацией и синхронизацией избранного через Firebase. Проект исторически назывался и жил в репозитории `Dota2` (пакет `com.example.dota2`) — это наследие старого названия, к содержимому приложения отношения не имеет.

## Стек

- **UI** — Jetpack Compose, Material 3, Navigation Compose
- **DI** — Hilt
- **Асинхронность** — Kotlin Coroutines / Flow
- **Сеть** — Retrofit + Gson (MangaDex API, `https://api.mangadex.org`)
- **Локальное хранилище** — Room (кэш избранного) + DataStore (настройки темы)
- **Бэкенд для профиля/избранного** — Firebase Auth, Firestore, Storage, Analytics
- **Изображения** — Coil
- **Архитектура** — Clean-Architecture-подобное разделение: `data` (remote/local/repository) → `domain` (model/repository/useCase) → `presentation` (screens/viewModel/navigation)

## Структура модулей

```
app/src/main/java/com/example/MangaDex/
├── application/          # App : Application, @HiltAndroidApp
├── data/
│   ├── remote/
│   │   ├── mangaDex/       # Retrofit API (MangaDexApi) + DTO под ответы MangaDex
│   │   └── auth/           # Firebase-модуль (FirebaseAuth/Firestore/Storage) + DTO профиля/избранного
│   ├── local/              # Room: AppDatabase, DAO, Entity, миграции (DatabaseModule)
│   ├── dataSource/          # Обёртки над remote/local источниками (Auth/Favorite/Profile/MangaDex/Theme)
│   └── repository/          # Реализации доменных репозиториев + DI-модуль (RepositoryDi)
├── domain/
│   ├── model/                # Доменные модели (server/profile)
│   ├── repository/           # Интерфейсы репозиториев (auth/favorite/profile/manga/theme)
│   └── useCase/               # Бизнес-логика: auth, manga (список/поиск/детали/главы/обложки), author, chapter, cover, profile
├── mapper/                    # DTO <-> Entity <-> Domain модели
├── presentation/
│   ├── navigation/
│   │   ├── authRoot/            # RootScreen — гейт по AuthState (Authorized/Unauthorized)
│   │   └── mainRoot/             # AppNavGraph, NavRoutes — основной граф после логина
│   ├── screens/                  # Compose-экраны (auth/, mangaScreens/, components/, extensions/)
│   ├── viewModel/                 # ViewModel'и экранов
│   └── uiState/                   # UI-состояния экранов
└── material_theme/                # Тема (Color/Theme/Type) и кастомные Compose-компоненты
```

## Навигация

`MainActivity` рендерит `RootScreen()`, который слушает `AuthViewModel.authState`:
- `AuthState.Unauthorized` → `AuthRoot()` (локальный `NavHost` с экранами `login`/`register`);
- `AuthState.Authorized` → `MainRoot()` → `AppNavGraph` со стартовым экраном `Main` и маршрутами: главная, поиск, избранное, настройки, смена профиля, деталка манги, ридер, а также вложенный граф каталога (`catalog_graph`: каталог → фильтры → теги) с общей `CatalogScreenViewModel` на все три экрана через `hiltViewModel(parentEntry)`.

## Локальная база данных и миграции

Избранное кэшируется в Room (`AppDatabase`, таблица `favorite_manga`) и синхронизируется с Firestore (`FavoriteRepositoryImpl.syncFromFirestore`).

При повышении версии схемы **обязательно**:
1. Написать `Migration` в `data/local/di/Migration.kt`, которая переносит данные (пересоздание таблицы через `RENAME TO` + `INSERT ... SELECT`, а не `DROP TABLE`).
2. Зарегистрировать её в `DatabaseModule.provideDatabase` через `.addMigrations(...)`.
3. Покрыть миграцию тестом в `androidTest` (`MigrationTestHelper`), см. `MigrationTest.kt`.

`fallbackToDestructiveMigration()` в проекте **не используется** — любое отсутствие миграции для реального апгрейда версии должно явно падать, а не молча стирать пользовательские данные.

## Firebase

Модуль `FirebaseModule` (`data/remote/auth/di/AuthModule.kt`) предоставляет синглтоны `FirebaseAuth`, `FirebaseFirestore`, `FirebaseStorage`. Для сборки нужен файл `app/google-services.json` с `package_name`, совпадающим с `applicationId` (`com.example.MangaDex`) — он используется плагином `com.google.gms.google-services` и подключён в `app/build.gradle.kts`.

## Сборка

```bash
./gradlew assembleDebug
```

Для релизной сборки нужен `keystore.properties` (см. `signingProp` в `app/build.gradle.kts`) со свойствами `storeFile`, `storePassword`, `keyAlias`, `keyPassword`, либо соответствующие переменные окружения `RELEASE_STORE_FILE`, `RELEASE_STORE_PASSWORD`, `RELEASE_KEY_ALIAS`, `RELEASE_KEY_PASSWORD`.

> После смены `namespace`/`applicationId` (переименование пакета) обязательно выполнить `./gradlew clean` перед следующей сборкой — иначе кэш Hilt/kapt может ссылаться на классы под старым именем пакета и compileDebug свалится с ошибкой вида `Could not find class file for '<старый.пакет>.application.App'`.

## Тесты

```bash
# Юнит-тесты (JVM)
./gradlew testDebugUnitTest

# Инструментальные тесты (нужен эмулятор/устройство), включая тест миграции БД
./gradlew connectedDebugAndroidTest
```

`MigrationTest` (`app/src/androidTest/.../data/local/di/MigrationTest.kt`) поднимает таблицу `favorite_manga` в схеме v1, прогоняет `MIGRATION_1_2` и проверяет, что существовавшие строки не потерялись — это регрессионный тест на баг, из-за которого апгрейд версии БД стирал всё избранное.

