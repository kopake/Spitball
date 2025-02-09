# Spitball

Spitball is a word-guessing game inspired by Catchphrase, designed for players who want more control over their gameplay experience. This app was created because my friends and I wanted the ability to play with custom word lists and special settings that allow for a more personalized game.

## Rules
- [PDF Link](https://docs.google.com/viewer?url=https://raw.githubusercontent.com/kopake/Spitball/main/app/src/main/assets/spitball_rules.pdf)

## Features

- **Included Word Lists** - App comes packaged with 5 pre-made word lists.
- **Custom Word Lists** – Play with words you create, see below for more details.
- **Custom Gameplay Settings** – Adjust rules to fit your group's play style.
- **Simple and Intuitive UI** – Easy to pick up and play with friends.

## Installation
- Navigate to the 'Releases' section of this git repository (typically below this text when viewing on mobile)
- Click on the lastest release
- Click on the spitball-*.*.*-release.apk file and follow the prompts from your device

## Advanced: Adding custom word lists
- Custom word lists format:
  * .txt file extension
  * Each line of the file is a word/phrase
  * Blank lines and lines beginning with a '#' character are ignored
- Custom word lists recommendations:
  * Having more words is preferred. For reference each included word list has around 1,500 words.
  * Avoid including phrases that contain too many words.
  * The app automatically ignores duplicated words, but you should still avoid including duplicated items in your lists.
- Installing a custom word list
  * Open the Spitball app, and navigate to the info screen using the 'i' icon on the home screen.
  * At the bottom of the info screen, the 'Custom Word List Directory' will be shown.
  * Place your custom txt file(s) in the Custom Word List Directory
  * After restarting the app, your word list(s) should be selectable from the Categories menu
