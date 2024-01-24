# News Bag - Android App

News Bag is an Android application developed using the MVVM design pattern, incorporating ViewBinding, Room Database, Retrofit, and various other modern Android development technologies. This app aims to provide users with the latest news, offering features such as pagination, news page refreshing, searching news, saving, deleting, and managing favorite articles, toggling between dark and light themes, and detecting internet availability.

## Features

#### 1. Display Latest News with Pagination

News Bag displays the latest news in a paginated manner, allowing users to scroll through a list of articles efficiently.

#### 2. Refresh News Page

Users can refresh the news page to get the latest updates by simply pulling down the news list.

#### 3. Search News

The app provides a search functionality, enabling users to search for specific news articles based on keywords.

#### 4. Manage Favorite Articles

Users can **save** articles to their favorites, **delete** unwanted ones, and **undo delete** easily.

#### 5. Toggle Dark/Light Theme

News Bag supports both dark and light themes, allowing users to choose the theme that suits their preferences.

#### 6. Detect Internet Availability

The app detects internet availability and provides appropriate feedback to users when there is no internet connection.

## API

The application utilizes this API: https://newsapi.org to fetch the latest news.

## Tech Stack

- **Kotlin**: programming language for android.
- **MVVM Architecture**: (Model-View-ViewModel) is a design pattern that separates business logic from the user interface, fostering code modularity, testability, and maintainability in Android development
- **Room Database**: SQLite persistence library for Android, provides a higher-level abstraction for local data manipulation, simplifying database interactions within the application. **Used to store saved articles**.
- **Retrofit**: HTTP client that simplifies REST APIs calls.

## Screenshots


| | | |
:-------------------------:|:-------------------------:|:-------------------------:
| **splash screen** |**Home**| **swipe down to refresh**  |
| ![s1](/screenshots/s1.jpg) | ![s2](/screenshots/s2.jpg) | ![s3](/screenshots/s3.jpg)
| **toggle theme button**|**dark mode** | **click on article -> webview**  |
| ![s4](/screenshots/s4.jpg) | ![s5](/screenshots/s5.jpg) | ![s6](/screenshots/s6.jpg) |
| **save article to bookmarks** |**search news**| **saved articles**  |
| ![s7](/screenshots/s7.jpg) | ![s8](/screenshots/s8.jpg) | ![s9](/screenshots/s9.jpg)
| **swipe left an article to delete** | **possible to undo delete** | **No internet connexion swipe down to retry** |
| ![s10](/screenshots/s10.jpg) | ![s11](/screenshots/s11.jpg) | ![s12](/screenshots/s12.jpg) |



