# 🧪 Скрипт запуска тестов с измерением покрытия кода
# Для Свиноматора-3000 - потому что даже хаос нужно мерить!

Write-Host "🐷💣 Запуск тестов с покрытием для Cursor Mod..." -ForegroundColor Cyan

# Очищаем предыдущие результаты
Write-Host "🧹 Очистка старых результатов..." -ForegroundColor Yellow
./gradlew clean

# Запускаем тесты с покрытием
Write-Host "🧪 Запуск тестов..." -ForegroundColor Green
./gradlew test jacocoTestReport

# Проверяем результат
if ($LASTEXITCODE -eq 0) {
  Write-Host "✅ Тесты завершены успешно!" -ForegroundColor Green
    
  # Открываем HTML отчёты
  Write-Host "📊 Открытие отчётов..." -ForegroundColor Cyan
    
  $coverageReport = "build\reports\jacoco\test\html\index.html"
  $testReport = "build\reports\tests\test\index.html"
    
  if (Test-Path $coverageReport) {
    Write-Host "📈 Отчёт о покрытии: $coverageReport" -ForegroundColor Magenta
    Start-Process $coverageReport
  }
    
  if (Test-Path $testReport) {
    Write-Host "🧪 Отчёт о тестах: $testReport" -ForegroundColor Magenta
    Start-Process $testReport
  }
    
  Write-Host "`n🎉 Готово! Даже хаос теперь измерен!" -ForegroundColor Green
}
else {
  Write-Host "❌ Тесты провалились! Хаос победил..." -ForegroundColor Red
  exit 1
}

