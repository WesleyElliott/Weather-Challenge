# WhereTher?

<!--- Replace <OWNER> with your Github Username and <REPOSITORY> with the name of your repository. -->
<!--- You can find both of these in the url bar when you open your repository in github. -->
![Workflow result](https://github.com/WesleyElliott/Weather-Challenge/workflows/Check/badge.svg)

<img src="/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png" width="64">

## :scroll: Description
Why have an app to show you the weather when you can just look outside? Wouldn't it be more useful
to have an app that can find the nearest location that has your ideal weather?

This is a proof-of-concept app as a submission to week 4 of the
[#AndroidDevChallenge](https://developer.android.com/dev-challenge).

## :bulb: Motivation and Context
This design in based on a Dribbble submission (see below), as well as functionality I wanted to
try implement in Jetpack Compose. The idea for the app was to have the user select a few options
(such as a sunny, coast region with a warm temperature within 250km) and then present the nearest
location that match those options.

I opted for an "accordion" style picker to select the options, which ended up being a fun challenge
to do in Compose. It was heavily inspired by the Column/Row composables, allowing a dynamic number
of items to be added. Animations were also included to provide smooth transitions between the
"collapsed" and "expanded" state.

I also opted to roll my own navigation handler as I wanted some transitions between the 2 screens.
Considering its a 2 screen app, this wasn't hard to handle - but doesn't scale well with more.

The app allows for both a light and dark theme (at the moment, based only on the system theme),a 
configurable unit system: temperature and distance are initially based off the system locale, but 
can be changed in the app for the duration of the app session (ie, restarting the app
will revert this setting), as well as support for both portrait and landscape. 
## :camera_flash: Screenshots

<img src="/results/screenshot_1.png" width="260">&emsp;<img src="/results/screenshot_2.png" width="260">&emsp;<img src="/results/screenshot_3.png" width="260">

Link to the [video](/results/video.mp4)

## :star: Credits
- Weather report screen design: [Dribbble](https://dribbble.com/shots/6250202-Daily-UI-037-Weather)
- Images:
  - [Tokyo](https://www.pexels.com/photo/2614818)
  - [Paris](https://www.pexels.com/photo/3214982)
  - [New York](https://www.pexels.com/photo/3889855)
  - [Sydney](https://www.pexels.com/photo/1878293)
  - [Rio](https://www.pexels.com/photo/3648269)
  - [Cape Town](https://www.pexels.com/photo/963713)
  - [Grand Canyon](https://www.pexels.com/photo/2542340)
  - [Cappadocia](https://www.pexels.com/photo/2563593)
  - [Namib Desert](https://www.pexels.com/photo/3714898)
  - [Cliffs of Moher](https://www.pexels.com/photo/2382681)
  - [Naples](https://www.pexels.com/photo/2972658)
  - [San Fransisco](https://www.pexels.com/photo/1006965)
  - [Dubai](https://www.pexels.com/photo/4491951)
  - [The Alps](https://www.pexels.com/photo/2437296)
  - [The Fjords](https://www.pexels.com/photo/1562058)
- Icons: [Freepik](https://www.freepik.com) from [Flaticon](https://www.flaticon.com/)

## License
```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```