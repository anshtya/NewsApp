

# NewsApp

News app created using Kotlin. This app uses [NewsAPI](https://newsapi.org/) for displaying and searching news. You can also bookmark news articles.

## 👨‍💻Tech Stack
- <a href="https://developer.android.com/topic/architecture#recommended-app-arch">MVVM Architecture</a> - Seperating UI from business logic.
- <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt</a> - Dependency injection library.
- <a href="https://developer.android.com/training/data-storage/room">Room</a> - Provides an abstraction layer over SQLite used for offline data caching.
- <a href="https://developer.android.com/kotlin/flow">Flow</a> and <a href="https://developer.android.com/kotlin/coroutines">Coroutines</a> - Perform asynchronous programming.

## 📸 Screenshots
||||
|---|---|---|
| ![news](/screenshots/news.png) | ![saved](/screenshots/saved.png) | ![search](/screenshots/search.png) |

## 🖥️Installation

To clone the project, run

```
git clone https://github.com/anshtya/NewsApp.git
```
Open the project in Android Studio.

This project uses NewsAPI API key. To get yours visit [NewsAPI](https://newsapi.org/). To use the api key open `local.poperties` file and type
```
API_KEY = your_api_key
```
Open `Constants.kt` file and replace the value of `COUNTRY_CODE`
```
const val COUNTRY_CODE = "your_country_code"
```
and rebuild the project.

