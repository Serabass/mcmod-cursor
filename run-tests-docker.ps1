# Скрипт для запуска тестов в Docker
# 🐳🧪 Тестируем мод в изолированной среде!

param(
    [switch]$Clean,
    [switch]$Reports,
    [switch]$Build,
    [string]$Command = "test"
)

Write-Host "🐳 Запуск тестов Minecraft мода в Docker..." -ForegroundColor Green
Write-Host "===============================================" -ForegroundColor Green

# Создаем директорию для отчетов
if (!(Test-Path "test-reports")) {
    New-Item -ItemType Directory -Path "test-reports" | Out-Null
    Write-Host "📁 Создана директория для отчетов: test-reports" -ForegroundColor Yellow
}

# Очистка (если нужно)
if ($Clean) {
    Write-Host "🧹 Очистка Docker контейнеров и образов..." -ForegroundColor Yellow
    docker-compose -f docker-compose.test.yml down --volumes --remove-orphans
    docker system prune -f
    Write-Host "✅ Очистка завершена!" -ForegroundColor Green
}

# Сборка образа
if ($Build -or $Clean) {
    Write-Host "🔨 Сборка Docker образа для тестов..." -ForegroundColor Yellow
    docker-compose -f docker-compose.test.yml build --no-cache
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Ошибка сборки Docker образа!" -ForegroundColor Red
        exit 1
    }
    Write-Host "✅ Образ собран успешно!" -ForegroundColor Green
}

# Запуск тестов
Write-Host "🧪 Запуск тестов..." -ForegroundColor Yellow
Write-Host "Команда: $Command" -ForegroundColor Cyan

# Определяем команду для выполнения
$dockerCommand = switch ($Command) {
    "test" { @("clean", "test", "jacocoTestReport") }
    "test-only" { @("test") }
    "coverage" { @("clean", "test", "jacocoTestReport") }
    "build" { @("build") }
    default { $Command -split " " }
}

# Запускаем тесты
docker-compose -f docker-compose.test.yml run --rm test-runner ./gradlew $dockerCommand

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Тесты выполнены успешно!" -ForegroundColor Green
    
    # Показываем статистику отчетов
    if (Test-Path "test-reports\jacoco\test\html\index.html") {
        Write-Host "📊 Отчеты покрытия доступны в:" -ForegroundColor Cyan
        Write-Host "   📁 test-reports\jacoco\test\html\index.html" -ForegroundColor White
        Write-Host "   📁 test-reports\jacoco\test\jacocoTestReport.csv" -ForegroundColor White
    }
    
    # Запуск веб-сервера для отчетов (если нужно)
    if ($Reports) {
        Write-Host "🌐 Запуск веб-сервера для просмотра отчетов..." -ForegroundColor Yellow
        Write-Host "   Откройте: http://localhost:8080" -ForegroundColor Cyan
        docker-compose -f docker-compose.test.yml --profile reports up -d report-viewer
    }
} else {
    Write-Host "❌ Тесты завершились с ошибкой!" -ForegroundColor Red
    exit 1
}

Write-Host "🎉 Готово!" -ForegroundColor Green