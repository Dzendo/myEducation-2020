package com.mechanitis.demo.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Это и есть класс для тестирования со своим именем и он лежит в ветке test. класс для хранения тестов.
// Если нет @Test внутри класса, то это просто обычный класс или метод функция.
// для тестов JUnit 5 метод тестирования не обязательно должен быть общедоступным, чтобы он работал
class ASExampleTest {
    //Кроме @Test может быть еще @Перед, @Перед всеми, @Ignory см. JUnit И много разных @ и несколько сразу.
    @Test
   // @Disabled( "Еще не реализовано" )
    @DisplayName( "Должно продемонстрировать простое утверждение" )
    // это метод - функция shouldShowSimpleAssertion класса, которая и тестирует.
    void shouldShowSimpleAssertion() {
        //assert- утверждаю что равно/не равно/ совпадают / нул не нул/таймаут/....см. язык JUnit
        assertEquals(1,1); // Утверждаю, что 1 = 1
        assertEquals("Papa","Papa");
    }
// Итого JUnit состоит из двух конструкций: Аннотаций - @Чтонибудь и утверждаю (assert) что
    // Сахароза Лексикона утверждения JUnit подменяет его на другую запись: assertThat - утверждаю что
    // Эта другая форма записи оператора, которую обеспечивают Хамкрест, но это то де утверждение JUnit по другому написанное.

    @Test
    //Следует проверить все пункты в списке True
    @DisplayName("Should check all items in the list True")
    void shouldCheckAllItemsInTheListTrue() {
// объявляем список целых чисел numbers сразу создаем его экземпляр и заполняем его 4-мя цифрами, которые
// нумеруются 0-1-2-3 (это Java), а значения 2-3-5-7, т.е. 0 - 2, 1-3, 2-5, 3-7
        List<Integer> numbers = List.of(2, 3, 5, 7);
        // теперь проверяем:
        assertEquals(2, numbers.get(0));
        assertEquals(3, numbers.get(1));
        assertEquals(5, numbers.get(2));
        assertEquals(7, numbers.get(3));
    }
// Запустите тест, чтобы еще раз убедиться, что только первое утверждение не работает,
// мы понятия не имеем, что другие также не работают.
    @Test
    @DisplayName("Should check all items in the list false")
    void shouldCheckAllItemsInTheListFalse() {
        List<Integer> numbers = List.of(2, 3, 5, 7);
        assertEquals(1, numbers.get(0));
        assertEquals(1, numbers.get(1));
        assertEquals(1, numbers.get(2));
        assertEquals(1, numbers.get(3));
    }
    // команда Junit5 проверять все!!!
    // При пачке ассертов JUnit 4 наткнувшись на первый неисправный сообщает и бросает проверку
    // в результате программист исправляет и получает следующую ошибку
    // Это не всегда удобно - Иногда надо бы проверить все равно до конца.
    // Для этого ввели в JUnit5 проверить все до конца утверждения
    // Дает ответ на каждый, но IDE не до конца под это подогнанно.
    @Test
    @DisplayName("Should check all items in the list all false")
    void shouldCheckAllItemsInTheListAllFalse() {
        List<Integer> numbers = List.of(2, 3, 5, 7);
        Assertions.assertAll(
                () -> assertEquals(1, numbers.get(0)),
                () -> assertEquals(1, numbers.get(1)),
                () -> assertEquals(1, numbers.get(2)),
                () -> assertEquals(1, numbers.get(3)));
    }
    @Test
    @DisplayName("Should check all items in the list all true")
    void shouldCheckAllItemsInTheListAllTrue() {
        List<Integer> numbers = List.of(2, 3, 5, 7);
        Assertions.assertAll(
                () -> assertEquals(2, numbers.get(0)),
                () -> assertEquals(3, numbers.get(1)),
                () -> assertEquals(5, numbers.get(2)),
                () -> assertEquals(7, numbers.get(3)));
    }

    @Test
    // Следует запускать тест только в том случае, если соблюдены некоторые критерии
    // Например: Проверять только в ландшафте, или только если батарейка не садится, или .... т.п.
    // в JUnit4 этого не было и в результате были непрошедшие тесты, которые пугали программиста и тем более инвестора
    // в JUnit5 ввели прогонять тест по предусловиям. Не только внешние, но и внутренние то же.
    @DisplayName("Should only run the test if some criteria are met")
    void shouldOnlyRunTheTestIfSomeCriteriaAreMet() {
        // При условии, что это правда, будем что-нибудь проверять
        // В данном случае вызываеься функция apiVersion класса Fixture, т.е. 9
        // Сам логический оператор, который в скобках, сооружается Java как угодно и не относится к пакету тестирования
        // JUnit5 Assumptions.assumeTrue проверяет правда или нет стоит в сколбках.
        // и если это да, то выполняет проверки которые стоят за ним, а если нет, то пропускает.
        Assumptions.assumeTrue(Fixture.apiVersion() > 10); //предположим, что Это Правда
        assertEquals(1, 1);
    }

    // Демонстрация новой конструкции JUnit5 @ParameterizedTest
    // Т.к. большинство тестов состоит из набора проверок, то для удобства сделана такая конструкция
    // Это просто удобнее, т.к. не надо писать эту кучу ассертов
    // Это то же самое,ч то написать 5 ассертов один за другим
// Запустите тест. На самом деле тест запускается не один раз.
// Тест запускается для каждого из int значений, которые мы помещаем в ValueSource аннотацию.
    @ParameterizedTest (name = "{0}") // это просто отображаемое имя - необязательно
    @DisplayName("Should create shapes with different numbers of sides")
    @ValueSource(ints = {3, 4, 5, 8, 14})
    void shouldCreateShapesWithDifferentNumbersOfSides(int expectedNumberOfSides) {
        // Java создает новый экземпляр класса многоугольника
        // и передает ему очередную цифру из ValueSource
        Shape shape = new Shape(expectedNumberOfSides);
        // JUnit проверяет равно ли два значения
        assertEquals(expectedNumberOfSides, shape.numberOfSides());
    }
// слишком большое

    @ParameterizedTest
    // Не следует создавать фигуры с недопустимым числом сторон ошибка
    // На какие-то случаи тестировщик решает, что они не допустимы, хотя технически программа работает.
    // JUnit5 теперь дает возможность возбудить прерывание по ошибке в этих случаях.
    // Надо знать, что такое прерывание в Java, как оно возбуждантеся и обрабатывается.
    @DisplayName("Should not create shapes with invalid numbers of sides error")
    @ValueSource(ints = {0, 1, 2, Integer.MAX_VALUE})
    void shouldNotCreateShapesWithInvalidNumbersOfSidesError(int expectedNumberOfSides) {
    }
    // Проверка исключений не понял вроде не работает
    // Вернуться сюда.
    @ParameterizedTest(name = "{0}")
    @DisplayName("Should not create shapes with invalid numbers of sides throws")
    @ValueSource(ints = {0, 1, 2, Integer.MAX_VALUE})
    void shouldNotCreateShapesWithInvalidNumbersOfSidesThrows(int expectedNumberOfSides) {
        assertThrows(IllegalArgumentException.class,
                () -> new Shape(expectedNumberOfSides));
    }
// вложенные тесты
    // JUnit5 дает такую радость, как вложенные тесты и они выглядят так:
    // Есть класс WhenShapeExists в котором тесты, а в него вложены еще несколько внутренних классов
    // А в эти внутренние классы могут быть еще вложены внутренние классы и т.д.
    // Внешние и Внутренние классы должны быть аннотированы @Nested, что бы JUnit знал, что здесь вложение
    // Тогда: 1. в проекте они будут светиться со сдвижкой, что удобно для тестировщика
    // 2. При запуске будет отдельно галка на каждый внутренний тест - что то же удобно
    // от этого Улучшается читаемость тестов и результатов
    // На самом деле это очень удобная для написания тестов возможность, так же как подкаталоги удобны для каталогов
    // Дает еще всякие возможности, которые еще даже не видим. (Можно свои методы, функции, переменные)
    // Взят из тестNG, за счет этого тест NG и использовали.
    @Nested
    // Когда фигура была создана
    @DisplayName("When a shape has been created")
    class WhenShapeExists {
        private final Shape shape = new Shape(4);

        @Nested
        @DisplayName("Should allow")
        class ShouldAllow {
            @Test
            @DisplayName("seeing the number of sides")
            void seeingTheNumberOfSides() {
                assertEquals(4, shape.numberOfSides());
            }

            @Test
            @DisplayName("seeing the description")
            void seeingTheDescription() {
                assertEquals("Square", shape.description());
            }
        }

        @Nested
        @DisplayName("Should not")
        class ShouldNot {
            @Test
            @DisplayName("be equal to another shape with the same number of sides")
            void beEqualToAnotherShapeWithTheSameNumberOfSides() {
                assertNotEquals(new Shape(4), shape);
            }
        }
    }

}
// В этом примере основные нововведения JUnit5 по сравнению с JUnit4, но не все.
// Есть тестирование на основе данных и наверное еще какие-то.
// Но самые главные из них, которые радуют тестировщиков - это
// @DisplayName - дает в отчете нормальный язык вместо имени beEqualToAnotherShapeWithTheSameNumberOfSides
// @Nested - которые дают иерархию тестов
// @ParameterizedTest  - сокращает перечень превращая его в одну строчку
// Assertions.assertAll - меньше запусков тестов, больше инфо
// Assumptions.assumeTrue - избавляет от ложно непрошедших тестов
// assertThrows - прерывание, дает тестировщику хитрое окончание теста.
// Всякие еще возможности есть, но не много.
// Ребята дописывают следующее.
// Примечание: к сожалению под android буду работать с JUnit4 без всех этих радостей.
// Ждем пока прикрутят - т.к. IDE уже выполняет, а AS новый Fox с нее берет.
// Ребята из JUnit дали винтаж, который выполняет тесты JUnit4