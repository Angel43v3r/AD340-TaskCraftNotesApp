# TaskCraft Notes App
![Android Studio](https://github.com/Angel43v3r/AD340-Assignments-TheSkylineMessenger/blob/master/Android_Studio_icon.png)
## Assignment 17 - TaskCraft Notes App in Android
### AD340 - Mobile Application Development
### North Seattle College (Spring 2026)
### Date: June 7, 2026


## Table Of Contents
1. [Objective](#objective)
2. [How to Use](#how-to-use)
3. [Assignment Instruction](#assignment-instruction)
4. [Build App](#build-app)
5. [Version Control](#version-control-github)
6. [Contributing](#contributing)
7. [License](#license)


## Objective
Build a local Note-taking and Task management application to master how MVVM handles user input, database operations on background threads, and real-time UI updates using Android Jetpack Room.

## How to Use
### Prerequisites
Make sure you have the following installed:
- **Visual Studio Code (VS Code)**
    - You can use any editor, VSC is recommended for this project. You can download from [VS Code official website](https://code.visualstudio.com/).

- **Android Studio**
    - **Android Studio** is required to run the app on an Android emulator or a physical device.
    - It provides the Android SDK, emulator, and build tools needed for React Native development.
    - You can download from the [Android Studio official website](https://developer.android.com/studio).

### Installation & Environment Setup
#### 1. Clone the Repository
In the folder you want to save your project in, run:

```bash
git clone git@github.com:Angel43v3r/AD340-TaskCraftNotesApp.git
```

#### 2. Navigate to the app folder:

```
cd AD340-TaskCraftNotesApp
```

#### 3. Navigate to the folder
```
cd <Folder_Name>
```

#### 4. Now, let’s initialize a modern Android project template.


**STEP 1:** Open Android Studio and select `File` -> `New` -> `New Project`.

**STEP 2:** Choose `Empty Activity` (This is crucial to use Kotlin Compose!).

**STEP 3:** Configure the project:

```text
Name: AD340-CryptoTracker

Package name: com.example.ad340-taskcraftnotesapp

Minimum SDK: API 24 (Android 7.0) or higher.

Build configuration language: Kotlin DSL (build.gradle.kts).
```

**STEP 4:** Click Finish

- Wait for Gradle to finish "syncing" (this may take a few minutes on the first run).

## Assignment Instruction

### Requirements & Tasks
#### Task 1: The Local Data Layer (Room Setup)
1. Create a `Note` Entity data class:
2. Create a `NoteDao` (Data Access Object) interface 
3. Set up the abstract `NoteDatabase` class that extends `RoomDatabase`.

#### Task 2: The Repository
1. Create a `NoteRepository` class that takes `NoteDao` as a constructor parameter. 
2. Expose the `Flow<List<Note>>` from the DAO as a property. 
3. Create `suspend` functions for `insert()` and `delete()` so they are forced to run safely within a coroutine scope.

#### Task 3: The ViewModel & State Management
1. Create a `NoteViewModel` that accepts the `NoteRepository`
2. Expose the list of notes to the view. 
3. Implement `insertNote(title: String, content: String)` and `deleteNote(note: Note)` methods that launch in the `viewModelScope`
4. **Validation Logic:** Inside the ViewModel, ensure that a note cannot be saved if the `title` or `content` is blank. If it is blank, trigger a single-use UI event

#### Task 4: The UI Layer (Two Screens or Dialog Input)
1. **Main Screen:** Displays a `RecyclerView` of all saved notes showing their title, content snippet, and creation date.
2. **Input Mechanism:** Add a Floating Action Button (FAB). When clicked, it can either open a new `Activity`/`Fragment` or show a `BottomSheetDialogFragment` with fields for "Title" and "Content" and a "Save" button.
3. **Swipe-to-Delete:** Implement a simple touch helper on the RecyclerView so that swiping a note item left or right calls the ViewModel's delete method.


## Build App

#### Step 1: Fix gradle/libs.versions.toml
```toml
[versions]
room = "2.6.1"
ksp = "2.3.5"

[libraries]
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }

[plugins]
kotlin-ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```
Then click `Sync Now`

#### Step 2: In root level build.gradle.kts, add:
```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.ksp) apply false
}
```
#### Step 3: In app level build.gradle.kts, add:
```kotlin
plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.ksp)
}

android {
  namespace = "com.example.ad340_taskcraftnotesapp"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.example.ad340_taskcraftnotesapp"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  testImplementation(libs.junit)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  debugImplementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  implementation(libs.androidx.compose.material.icons.extended)
  ksp(libs.androidx.room.compiler)
}
```

#### Step 4: Setup Folders
1. In Android Studio, navigate to `app` -> `src` -> `main` -> `java` -> `com.example.ad340_taskcraftnotesapp`
2. Right click `com.example.ad340_taskcraftnotesapp` then create new package, name the folder `data`.
3. Inside the `data` folder create folders `local` and `repository`
4. Inside the `local` folder create files: `Note.kt`, `NoteDao.kt`, and `NoteDatabase.kt`.
5. Inside the `repository` folder create file `NoteRepository.kt`
6. Right click `com.example.ad340_taskcraftnotesapp` then create new package, name the folder `ui`. 
7. Inside the `ui` folder create folders `note` and `theme`
8. Inside the `note` folder create files: `NoteScreen.kt`, `NoteItem.kt`, `AddNoteDialog.kt`, and `SwipeToDelete.kt`
9. Right click `com.example.ad340_taskcraftnotesapp` then create new package, name the folder `viewmodel`. 
10. Inside the `viewmodel` folder create a file `NoteViewModel.kt`

#### Folder Structure
```md
com.yourapp.notesapp
│
├── data
│   ├── local
│   │   ├── Note.kt
│   │   ├── NoteDao.kt
│   │   └── NoteDatabase.kt
│   │
│   └── repository
│       └── NoteRepository.kt
│
├── ui
│   ├── note
│   │   ├── AddNoteDialog.kt
│   │   ├── NoteItem.kt
│   │   ├── NoteScreen.kt
│   │   └── SwipeToDelete.kt
│   │
│   └── theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│   
├── viewmodel
│   └── NoteViewModel.kt
│
└── MainActivity.kt
```

#### Step 5: Create the local data
- Create code for Note entity (Note.kt), DAO (NoteDao.kt), and Database (NoteDatabase.kt).

#### Step 6: Create the repository data
- Create code for NoteRepository.kt

#### Step 7: Create the ViewModel
- Create code for NoteViewModel.kt

#### Step 8: Build the Compose UI 
- Create code for NoteScreen.kt, NoteItem.kt, AddNoteDialog.kt, and SwipeToDelete.kt

#### Step 9: Build the Main Activity
- Create the code for MainActivity.kt

#### Step 10: Run Android Studio to view app


## Version Control (GitHub)
### GitHub Initial Setup
#### 1. Open Android Studio
#### 2. Select `File` -> `Git` -> `Share Project On GitHub`

### To Commit:
Select `File` -> `Git` -> `GitHub` -> Commit

### To Push:
Select `File` -> `Git` -> `GitHub` -> Pull

### To Pull:
Select `File` -> `Git` -> `GitHub` -> Pull


## Contributing
Developed By: **Jovy Ann Nelson**

Instructor: **BC Ko**

Course: **AD340 - Mobile Application Development**

College: **North Seattle College**

Term: **Spring 2026**

Date: **June 1, 2026** to **June 7, 2026**


## License

This project is licensed under the MIT License. Please refer to the [LICENSE](https://github.com/Angel43v3r/AD340-TaskCraftNotesApp/blob/master/LICENSE) for more details.
