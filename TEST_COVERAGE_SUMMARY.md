# 🧪 Test Coverage Summary

## Всего написано тестов: 12 классов / 72 теста

### 📦 Блоки и Block Entities
1. **PigSpawnerBlockTest** (5 тестов)
   - Проверка существования класса
   - Наследование от BaseEntityBlock
   - Методы newBlockEntity и getTicker

2. **PigSpawnerBlockEntityTest** (5 тестов)
   - Проверка существования класса
   - Наследование от BlockEntity
   - Методы saveAdditional, load и tick

### 🐷 Сущности
3. **ExplodingPigTest** (5 тестов)
   - Проверка существования класса
   - Наследование от Entity
   - Методы createAttributes и explode

4. **CarrotProjectileTest** (7 тестов)
   - Проверка существования класса
   - Наследование от ThrowableItemProjectile
   - Методы getDefaultItem, onHitEntity, onHit и tick

### 🔫 Оружие
5. **ChickenGunTest** (6 тестов)
   - Проверка существования класса
   - Наследование от Item
   - Методы use, getUseDuration и getUseAnimation

6. **CarrotCannonTest** (6 тестов)
   - Проверка существования класса
   - Наследование от Item
   - Методы use, getUseDuration и getUseAnimation

7. **LaserGunTest** (6 тестов)
   - Проверка существования класса
   - Наследование от Item
   - Методы use, getUseDuration и getUseAnimation

8. **PizzaSwordTest** (8 тестов)
   - Проверка существования класса
   - Наследование от SwordItem
   - Методы hurtEnemy, getUseAnimation, getUseDuration, isEdible и finishUsingItem

### 🍺 Еда и напитки
9. **VodkaItemTest** (7 тестов)
   - Проверка существования класса
   - Наследование от Item
   - Методы finishUsingItem, getUseDuration, getUseAnimation и isEdible

10. **RedBananaItemTest** (5 тестов)
    - Проверка существования класса
    - Наследование от Item
    - Методы finishUsingItem и getUseDuration

### 💊 Эффекты
11. **BigPigEffectTest** (6 тестов)
    - Проверка существования класса
    - Наследование от MobEffect
    - Статическое поле BIG_PIG_EFFECT
    - Методы applyEffectTick и isDurationEffectTick

12. **DrunkEffectTest** (6 тестов)
    - Проверка существования класса
    - Наследование от MobEffect
    - Статическое поле DRUNK_EFFECT
    - Методы applyEffectTick и isDurationEffectTick

## ✅ Результаты

- **Все тесты прошли успешно!** ✓
- **Exit code:** 0
- **Время выполнения:** ~14-16 секунд
- **JaCoCo coverage report:** Сгенерирован

## 📝 Примечания

- Все тесты написаны в стиле BDD с эмодзи 🎉
- Используется JUnit 5 (Jupiter)
- Minecraft Bootstrap инициализируется в @BeforeAll
- Тесты проверяют существование классов, наследование и методов
- JaCoCo жалуется на Java 23, но это не влияет на выполнение тестов

## 🎯 Покрытие

Теперь протестированы следующие категории:
- ✅ Блоки и Block Entities
- ✅ Сущности (Entity)
- ✅ Предметы (Items)
- ✅ Эффекты (MobEffects)
- ✅ Оружие
- ✅ Еда

## 🚀 Как запустить тесты

```powershell
# Запустить все тесты
.\gradlew.bat test

# Запустить конкретный тест
.\gradlew.bat test --tests "com.cursormod.item.VodkaItemTest"

# Запустить тесты с покрытием
.\gradlew.bat test jacocoTestReport
```

---

**Создано с цинизмом и юмором** 🍺🐷💣

