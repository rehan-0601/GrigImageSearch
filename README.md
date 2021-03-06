# GrigImageSearch
# Project 2 - *# GrigImageSearch*

**# GrigImageSearch** is an android app that allows a user to search for images on web using simple filters. The app utilizes [Google Image Search API](https://developers.google.com/image-search/). Please note that API has been officially deprecated as of May 26, 2011.

Time spent: **15** hours spent in total

## User Stories

The following **required** functionality is completed:

* [1] User can **search for images** by specifying a query and launching a search. Search displays a grid of image results from the Google Image API.
* [2] User can click on "settings" which allows selection of **advanced search options** to filter results
* [3] User can configure advanced search filters such as:
  * [ ] Size (small, medium, large, extra-large)
  * [ ] Color filter (black, blue, brown, gray, green, etc...)
  * [ ] Type (faces, photo, clip art, line art)
  * [ ] Site (espn.com)
* [4] Subsequent searches have any filters applied to the search results
* [5] User can tap on any image in results to see the image **full-screen**
* [6] User can **scroll down to see more images**. The maximum number of images is 64 (limited by API).

The following **optional** features are implemented:

* [1] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [2] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [3] User can **share an image** to their friends or email it to themselves
* [4] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [1] Used the [StaggeredGridView](https://github.com/f-barth/AndroidStaggeredGrid) to display improve the grid of image results

The following **additional** features are implemented:

* [1] used googleProgressBar to display an animation while loading.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/rehan-0601/GrigImageSearch/blob/master/image_search.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
