# 🐳 Docker Тестирование Minecraft Мода

Этот документ описывает, как запускать тесты мода в изолированной Docker среде.

## 🚀 Быстрый старт

### Запуск тестов
```powershell
# Простой запуск тестов
.\run-tests-docker.ps1

# Очистка и пересборка
.\run-tests-docker.ps1 -Clean -Build

# Только тесты без покрытия
.\run-tests-docker.ps1 -Command "test-only"

# С веб-сервером для отчетов
.\run-tests-docker.ps1 -Reports
```

### Прямые команды Docker Compose
```powershell
# Запуск тестов
docker-compose -f docker-compose.test.yml up --build

# Только тесты
docker-compose -f docker-compose.test.yml run --rm test-runner ./gradlew test

# С покрытием
docker-compose -f docker-compose.test.yml run --rm test-runner ./gradlew clean test jacocoTestReport
```

## 📊 Отчеты покрытия

После выполнения тестов отчеты будут доступны в:
- **HTML:** `test-reports/jacoco/test/html/index.html`
- **CSV:** `test-reports/jacoco/test/jacocoTestReport.csv`
- **XML:** `test-reports/jacoco/test/jacocoTestReport.xml`

### Веб-просмотр отчетов
```powershell
# Запуск веб-сервера
.\run-tests-docker.ps1 -Reports

# Откройте в браузере
# http://localhost:8080
```

## 🔧 Конфигурация

### Dockerfile.test
- **Базовый образ:** OpenJDK 17 (совместимый с JaCoCo)
- **Рабочая директория:** `/app`
- **Gradle кэш:** Сохраняется в volume

### docker-compose.test.yml
- **test-runner:** Основной сервис для тестов
- **report-viewer:** Nginx для просмотра отчетов (опционально)
- **volumes:** 
  - `test-reports` - отчеты покрытия
  - `gradle-cache` - кэш Gradle

## 🐛 Отладка

### Просмотр логов
```powershell
# Логи тестов
docker-compose -f docker-compose.test.yml logs test-runner

# Интерактивная сессия
docker-compose -f docker-compose.test.yml run --rm test-runner bash
```

### Очистка
```powershell
# Очистка контейнеров и образов
docker-compose -f docker-compose.test.yml down --volumes --remove-orphans
docker system prune -f
```

## 📈 Преимущества Docker тестирования

1. **Изоляция:** Тесты выполняются в чистой среде
2. **Совместимость:** Java 17 + JaCoCo работают без конфликтов
3. **Воспроизводимость:** Одинаковые результаты на любой машине
4. **Кэширование:** Gradle кэш сохраняется между запусками
5. **Отчеты:** Автоматическое сохранение отчетов на хост

## 🎯 Результаты

После запуска тестов вы получите:
- ✅ **16 тестов** - все проходят успешно
- 📊 **Отчеты покрытия** - HTML, CSV, XML
- 🐳 **Изолированная среда** - без влияния на основную систему
- ⚡ **Быстрая сборка** - благодаря кэшированию

## 🔄 CI/CD интеграция

Для автоматизации в CI/CD:
```yaml
# GitHub Actions пример
- name: Run tests in Docker
  run: |
    docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit
    docker-compose -f docker-compose.test.yml down
```

## 📝 Примечания

- Тесты используют **Java 17** для совместимости с JaCoCo
- **Gradle кэш** сохраняется в Docker volume
- **Отчеты** автоматически копируются на хост
- **Веб-сервер** для отчетов работает на порту 8080
