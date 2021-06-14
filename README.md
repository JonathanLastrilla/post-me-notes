# post-me-notes
## IMPORTANT: DO NOT USE FOR STORING SENSITIVE DATA
## Description
 This is a tailor-made sticky note application with specific feature the author never bothered searching the internet for. The main purpose is to compensate the lack of task management skills without using relatively complex software the author has never encountered so far.
 
## Getting Started

## Building
* Language: Java
* Project Type: maven
* Source/Binary: 11
## Execution
```
javaw -cp <jarname> com.jon.postmenotes.Main
```
will provide sophisticated way sometime.
## Features
* Note Title
    * first line of the note will become the title
* Read/Write Mode
    * note can be locked to avoid accidental modification
    * when in read-only mode, any text highlighted will be copied to clipboard.
* Summary
    * consolidate all the last line of notes, use (---) button to add line. At the bottom of the note, the paragraph that precedes the separator line will appear in dialog box accessed trough:
     System Tray > PostMeNotes (Context Menu) > Summary
* Backup
    * store all notes in a text file (TODO: restore)

## Supported settings in Preferences
* Font Style
* Font Size
* Filter for Notes to be used in Summary
* Export notes into text file found under:
```
{user.home}/.postMeNotes/export
```


### Author
Jon






