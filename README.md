# jbstag : Java GBS Tagger
<img src="jbstag.png" alt="Java GBS Tagger in action" align="right">

[jbstag](https://github.com/ullenius/jbstag) is a tagger for GBS-files made in Java (Swing).

Reads and edits tags from Game Boy music files (GBS).


## Features
* :heavy_check_mark: 100% Java (Java 8) :coffee:
* :heavy_check_mark: Supports UTF-8 encoding in the tags!
* :heavy_check_mark: Multi-platform.

## Building
This is a Maven-project.

Run:
```sh
mvn clean install assembly:single
```
to build the JAR-file.

Or run the build-script provided:
```sh
./build.sh
```


## Binaries
I've included a executable jar-file to simplify for end-users who can't compile stuff on their platform.

* Download [jbstag.zip](https://github.com/ullenius/jbstag/blob/master/jbstag-binaries.zip)

## Issues
There might be some issues on your platform with the UTF-8 fonts not rendering properly. This is a Swing issue.


## Development
This is mostly a fork of [gbstag](https://github.com/ullenius/gbstag) with write-support added.
The GUI was made using Netbeans 8.2 Swing editor. The `.form`-file is included in the repo.

## Changelog
* 0.1 - first release! November 2019

## Licence
GPL 3 only. See COPYING

### Credits
The [Gameboy icon](https://www.iconfinder.com/icons/381627/gameboy_icon) used in the program is made by [Tahsin Tahil](https://tahsintahil.tumblr.com/) and licensed under [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/)
