# Popular Movies Application

An Android app that helps users discover movies.

This app is within the scope of Udacity's Android Developer Nanodegree.

## Features

#### Current features:

- [x] Discover the most popular and the highest rated movies
- [x] Endless scrolling through the list of movies
- [x] Read reviews about movies from other users
- [x] Watch movie trailers
- [x] Mark movies as favorites and save them
- [x] Offline mode supported
- [x] Optimized UI for phones and tablets

#### Upcoming features:

- [] Support for different languages
- [] Auto update of movies frequently
- [] Offline mode for all movies


## libraries used in this project

- [RecyclerView and CardView](https://developer.android.com/training/material/lists-cards.html)
- [Picasso](http://square.github.io/picasso/)
- [HttpURLConnection](https://developer.android.com/reference/java/net/HttpURLConnection.html)
- [Jackson core annotations and Data binding](https://github.com/FasterXML/jackson-docs)
- [Expandable Layout](https://github.com/cachapa/ExpandableLayout)
- [JUnit](https://developer.android.com/training/testing/unit-testing/local-unit-tests.html)


## Developer setup

#### API key

The app uses themoviedb.org API to get all movies data (Information about the movies, posters, trailers, etc.).

In order to build the app you must provide your own [API key](https://www.themoviedb.org/account/signup). Go to the `app/res/values/strings.xml` file, and insert your own API Key in the following line `<string name="themoviedb_api_key">YOUR_API_KEY</string>`.


### Do you want to contribute
Feel free to report or add any useful feature, I will be glad to improve it with your help!


# Licence
```
Copyright 2017 Husayn Abdul Hakeem

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
