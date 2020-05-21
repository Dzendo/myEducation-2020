# Connect to the Internet - Mars Real Estate
# Подключение к интернету-Недвижимость На Марсе

This is the toy app for Lesson 8 of the [Android App Development in Kotlin course on Udacity](https://classroom.udacity.com/courses/ud9012/).
Это игрушечное приложение для урока 8 [разработка приложений для Android в курсе Kotlin по Udacity](https://classroom.udacity.com/courses/ud9012/).

## MarsRealEstate - Недвижимость На Марсе

MarsRealEstate is a simple demo app using ViewModel & LiveData with Retrofit, Glide and Moshi in Kotlin.
Марс недвижимости простое демонстрационное приложение, используя модель представления данных и видео с модифицированной скользить и Моши в Котлин.

This app demonstrates the following views and techniques:
Это приложение демонстрирует следующие виды и методы:

* [Retrofit](https://square.github.io/retrofit/) to make api calls to an HTTP web service - для выполнения вызовов api к веб-службе HTTP
* [Moshi](https://github.com/square/moshi) which handles the deserialization of the returned JSON to Kotlin data objects - который обрабатывает десериализацию возвращаемого JSON в объекты данных Kotlin
* [Glide](https://bumptech.github.io/glide/) to load and cache images by URL. - для загрузки и кэширования изображений по URL-адресу.
  
It leverages the following components from the Jetpack library:
Он использует следующие компоненты из библиотеки Jetpack: 

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters - с помощью связующих адаптеров
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the SafeArgs plugin for parameter passing between fragments
                с помощью плагина Safe Args для передачи параметров между фрагментами

## Screenshots

![Screenshot 1](screenshots/screen_1.png)
![Screenshot 2](screenshots/screen_2.png)
![Screenshot 3](screenshots/screen_3.png)

## How to use this repo while taking the course
## Как использовать это РЕПО во время прохождения курса

Each code repository in this class has a chain of commits that looks like this:
Каждый репозиторий кода в этом классе имеет цепочку коммитов, которая выглядит следующим образом:

![listofcommits](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58befe2e_listofcommits/listofcommits.png)

These commits show every step you'll take to create the app. Each commit contains instructions for completing the that step.
Эти коммиты показывают каждый шаг, который вы предпримете для создания приложения. Каждая фиксация содержит инструкции для выполнения этого шага.

Each commit also has a **branch** associated with it of the same name as the commit message, as seen below:
Каждая фиксация также имеет связанную с ней ветвь с тем же именем, что и сообщение фиксации, как показано ниже:

![branches](https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590390fe_branches-ud855/branches-ud855.png)
Access all branches from this tab. - Открыть все ветки в этом разделе.

![listofbranches](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58befe76_listofbranches/listofbranches.png)
![branchesdropdown](https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590391a3_branches-dropdown-ud855/branches-dropdown-ud855.png)

The branches are also accessible from the drop-down in the "Code" tab.
Ветви также доступны из выпадающего списка на вкладке "код".

## Working with the Course Code - Работа с исходным кодом

Here are the basic steps for working with and completing exercises in the repo.
Вот основные шаги для работы с упражнениями и выполнения их в отчете.

The basic steps are:
Основные шаги заключаются в следующем:

1. Clone the repo.
2. Check out the branch corresponding to the step you want to attempt.
3. Find and complete the TODOs.
4. Optionally commit your code changes.
5. Compare your code with the solution.
6. Repeat steps 2-5 until you've gone trough all the steps to complete the toy app.

1. Клонировать РЕПО.
2. Проверьте ветвь, соответствующую шагу, который вы хотите попробовать.
3. Найти и завершить задачи.
4. При необходимости зафиксируйте изменения в коде.
5. Сравните свой код с решением.
6. Повторяйте шаги 2-5, пока вы не пройдете все шаги, чтобы завершить приложение для игрушек.

**Step 1: Clone the repo**
** Шаг 1: клонирование РЕПО**

As you go through the course, you'll be instructed to clone the different exercise repositories, so you don't need to set these up now. You can clone a repository from github in a folder of your choice with the command:
Когда вы пройдете курс, вам будет предложено клонировать различные репозитории упражнений, поэтому вам не нужно настраивать их сейчас. Вы можете клонировать репозиторий из github в папку по вашему выбору с помощью команды:

```bash
git clone https://github.com/udacity/REPOSITORY_NAME.git
```

**Step 2: Check out the step branch**
** Шаг 2: Проверьте ветвь шага**

As you go through different steps in the code, you'll be told which step you're on, as well as a link to the corresponding branch.
Когда вы пройдете через различные шаги в коде, вам будет сообщено, на каком шаге вы находитесь, а также ссылка на соответствующую ветвь.

You'll want to check out the branch associated with that step. The command to check out a branch would be:
Вы захотите проверить ветвь, связанную с этим шагом. Команда для проверки ветви будет такой:

```bash
git checkout BRANCH_NAME
```

**Step 3: Find and complete the TODOs**
** Шаг 3: Найдите и завершите TODOs**

Once you've checked out the branch, you'll have the code in the exact state you need. You'll even have TODOs, which are special comments that tell you all the steps you need to complete the exercise. You can easily navigate to all the TODOs using Android Studio's TODO tool. To open the TODO tool, click the button at the bottom of the screen that says TODO. This will display a list of all comments with TODO in the project. 
Как только вы проверите ветку, у вас будет код в точном состоянии, которое вам нужно. Вы даже будете иметь TODOs, которые являются специальными комментариями, которые говорят вам все шаги, необходимые для завершения упражнения. Вы можете легко перемещаться, чтобы все задачи, используя инструмент Android студии Тодо. Чтобы открыть инструмент TODO, нажмите кнопку в нижней части экрана, которая говорит, что нужно сделать. Это позволит отобразить список всех комментариев с TODO в проекте.

We've numbered the TODO steps so you can do them in order:
Мы пронумеровали шаги для выполнения, чтобы вы могли сделать их по порядку:
![todos](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58bf00e7_todos/todos.png)

**Step 4: Commit your code changes**
** Шаг 4: зафиксируйте изменения кода**

After You've completed the TODOs, you can optionally commit your changes. This will allow you to see the code you wrote whenever you return to the branch. The following git code will add and save **all** your changes.
После завершения TODOs вы можете дополнительно зафиксировать свои изменения. Это позволит вам видеть написанный вами код всякий раз, когда вы вернетесь в ветку. Следующий код git добавит и сохранит **все** ваши изменения.

```bash
git add .
git commit -m "Your commit message"
```

**Step 5: Compare with the solution**
** Шаг 5: сравните с решением**

Most exercises will have a list of steps for you to check off in the classroom. Once you've checked these off, you'll see a pop up window with a link to the solution code. Note the **Diff** link:
Большинство упражнений будет иметь список шагов для вас, чтобы проверить в классе. После того как вы их отключите, вы увидите всплывающее окно со ссылкой на код решения. Примечание **различия** ссылка:

![solutionwindow](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58bf00f9_solutionwindow/solutionwindow.png)

The **Diff** link will take you to a Github diff as seen below:
The **Diff** link will take you to a Github diff as seen below:
![diff](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58bf0108_diffsceenshot/diffsceenshot.png)

All of the code that was added in the solution is in green, and the removed code (which will usually be the TODO comments) is in red. 
Весь код, который был добавлен в решение, окрашен в зеленый цвет, а удаленный код (который обычно является комментарием TODO) - в красный.

You can also compare your code locally with the branch of the following step.
Вы также можете сравнить свой код локально с ветвью следующего шага.

## Report Issues Проблемы С Отчетами
Notice any issues with a repository? Please file a github issue in the repository.
Вы заметили какие-либо проблемы с репозиторием? Пожалуйста, запишите проблему github в репозиторий.
