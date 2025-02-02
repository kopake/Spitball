# Spitball

Spitball is a word-guessing game inspired by Catchphrase, designed for players who want more control over their gameplay experience. This app was created because my friends and I wanted the ability to play with custom word lists and special settings that allow for a more personalized game.

## Rules
- TODO insert link

## Features

- **Included Word Lists** - App comes packaged with 5 pre-made word lists.
- **Custom Word Lists** – Play with words you create, see below for more details.
- **Custom Gameplay Settings** – Adjust rules to fit your group's play style.
- **Simple and Intuitive UI** – Easy to pick up and play with friends.

## Installation
- TODO

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
  * Place your custom txt file(s) in the external files directory for the spitball app, in the 'word_lists' directory. (Usually Android\data\io.github.kopake.spitball\files\word_lists) NOTE: uninstalling the app will also delete your custom word lists from this directory
  * After restarting the app, your word list(s) should be selectable in the app 
