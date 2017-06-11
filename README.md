# Popular Movies Application

A simple Android app that helps users discover movies.

This app is within the scope of Udacity's Android Developer Nanodegree, so far the current project represents only the 1st part of the whole project.

## Features

#### Current features:

- [x] Discover the most popular and the highest rated movies
- [x] Endless scrolling through the list of movies
- [x] Read reviews about movies from other users

#### Upcoming features:

- [] Watch movie trailers
- [] Mask movies ad favorites and save them
- [] Optimized UI for phones and tablets

## Developer setup

#### API key

The app uses themoviedb.org API to get all movies data (Information about the movies, posters, trailers, etc.).

In order to build the app you must provide your own [API key](https://www.themoviedb.org/account/signup). Go to the `app/res/values/strings.xml` file, and insert your own API Key in the following line `<string name="themoviedb_api_key">YOUR_API_KEY</string>`.
