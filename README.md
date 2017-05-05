# CLion Plug-in: valgrind memory analyzer

## Зависимости
1. Последняя версия Intellij IDEA (2017.1.2) а также CLion (2017.1)

## Как собрать проект
1. Открыть проект с помощью Intellij IDEA.
2. Проверить, что в качестве sdk проекта указана Intellij Platform Plugin SDK со ссылкой на установочную папку CLion.
   Запустить проект с конфигурацией Plugin (запустится инстанс CLion'а с подключенным плагином clion-valgrind)
   (Как это сделать можно посмотреть на http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/setting_up_environment.html)
3. Запустить проект с конфигурацией **Plugin**.
4. Ждем запуска CLion. Выполнить шаги из **Запуск valgrind в CLion**

## Запуск valgrind в CLion
1. Открыть проект, который хотим проверить на ошибки при работе с памятью.
2. Собрать проект (Build)
3. Открыть **Run -> Edit Configuration**
4. Добавить конфигурацию **valgrind**. Можно поменять название конфигурации (не обязательно)
5. Запустить проект с конфигурацией которую настроили выше
