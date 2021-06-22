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
## Notable Features
* Note Title
    * first line of the note will become the title
* Read/Write Mode
    * note can be locked to avoid accidental modification. In the toolbar of a particular note, just toggle the 'edit' checkbox. Setting to write only mode will enable 'copy on select' function, just highlight a word and it will be copied to clipboard.
* Quick Summary
    * consolidate all the last line of notes, last paragraph of each note can be consolidated from the system tray icon. all notes that will be consolidated are filtered in the preferences under the system tray context menu, in Preferences.
    ```
    System Tray > Context Menu(Right Click) > Summarize
    ```
      
    * A line from the last paragraph can be ignored/commented-out using double back-slash at the start of the string
    ```
    \\this line is ignored
    This line will be visible in report
    ```
* Backup
    * store all notes in a text file, access this in Preferences. Available option is to save it into text file and can be found inside
    ```
    user.dir/.postMeNotes/export
    ```
* Reminder
    * create reminder specifically for the note the user is in. user will be notified using system tray notification.
    * unexpired reminders are still stored until they are used.
* Go To Links
    * When a note contains a url in a single line, doing a right click in the editor will open a context menu containing links that can be navigated after clicking.
### Author
Jon






