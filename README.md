
<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/Sleeplessmen/VEDictionary-MintTwister">
    <img src="src\resources\icon.jpg" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">MintTwister</h3>

  <p align="center">
    An English learning application, made as an OOP project, by Le Cong Hoang, Nguyen Cong Khai and Do Hoai Nam
    <br />
    <a href="https://github.com/Sleeplessmen/VEDictionary-MintTwister"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/Sleeplessmen/VEDictionary-MintTwister">View Demo</a>
    ·
    <a href="https://github.com/Sleeplessmen/VEDictionary-MintTwister/issues">Report Bug</a>
    ·
    <a href="https://github.com/Sleeplessmen/VEDictionary-MintTwister/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#inheritance-tree">Inheritance Tree</a></li>
      </ul>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
### Inheritance Tree
![Inheritance_Tree](https://raw.githubusercontent.com/Sleeplessmen/VEDictionary-MintTwister/main/src/resources/caykethuariel.png)


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

![Java](https://img.shields.io/badge/Java-d8dee9?style=flat-square&labelColor=2e3440 "Java Logo") and ![JavaFX](https://img.shields.io/badge/JavaFX-2e3440?style=flat-square&labelColor=d8dee9 "JavaFX Logo")
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

* Java JDK 20+
* Maven
* XAMPP
* An IDE like Intellij, Eclipse,...

### Installation

* Start XAMPP (Start Apache and MYSQL).
* Ensure localhost port is 3306 (XAMPP default port).
* Add a new user:
* Name: root
* Password: minttwister
* Create and Import dictionary.sql into the database dict2 (src/resources/dictionary.sql).
Note: You can configure your own username, port, password, ... by changing those accordingly in src/main/base/DBDictionary.java
* After uploading the dictionary.sql file, next time you only need to start XAMPP (Start Apache and MYSQL) if you want to use the dictionary with MYSQL Database.
* Import necessary libraries to the project, you can find it in the lib folder
* Navigate to src/main/DictApplication.java to run the GUI version.
* Run the main function in src/main/base/DictionaryCommandline.java to run the CLI version.


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

This is a simple GUI English learning application with as many features we can think of as possible, such as:
* Add / Modify / Remove word from the database
* Translate word / phrase with Google Translate
* Text to speech
* Add favourite words
* Import from / Export to file
* 3 games for learning English

![Screenshot_2](https://raw.githubusercontent.com/Sleeplessmen/VEDictionary-MintTwister/testUI/src/resources/screenshot2.png)
![Screenshot_3](https://raw.githubusercontent.com/Sleeplessmen/VEDictionary-MintTwister/testUI/src/resources/screenshot3.png)
![Screenshot_4](https://raw.githubusercontent.com/Sleeplessmen/VEDictionary-MintTwister/testUI/src/resources/screenshot4.png)
![Screenshot_5](https://raw.githubusercontent.com/Sleeplessmen/VEDictionary-MintTwister/testUI/src/resources/screenshot5.png)
![Screenshot_6](https://raw.githubusercontent.com/Sleeplessmen/VEDictionary-MintTwister/testUI/src/resources/screenshot6.png)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [v] Commandline Version
- [v] GUI Version
- [ ] More improvements

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

* Le Cong Hoang - 22026555@vnu.edu.vn

* Nguyen Cong Khai - 22026562@vnu.edu.vn

* Do Hoai Nam - 22026528@vnu.edu.vn

Project Link: [MintTwister](https://github.com/Sleeplessmen/VEDictionary-MintTwister)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments
Here's the things we used to create this project
* [Bro Code's JavaFX Playlist](https://www.youtube.com/playlist?list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev)
* [Intellij](https://www.jetbrains.com/idea/)
* [Photoshop](https://www.adobe.com/products/photoshop.html)
* [RapidAPI](https://rapidapi.com/googlecloud/api/google-translate1)
* [VoiceRSS](https://www.voicerss.org/)
* [ChatGPT](https://chat.openai.com/)
* [AtlantaFX](https://github.com/mkpaz/atlantafx)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Sleeplessmen/VEDictionary-MintTwister.svg?style=for-the-badge
[contributors-url]: https://github.com/Sleeplessmen/VEDictionary-MintTwister/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Sleeplessmen/VEDictionary-MintTwister.svg?style=for-the-badge
[forks-url]: https://github.com/Sleeplessmen/VEDictionary-MintTwister/network
[stars-shield]: https://img.shields.io/github/stars/Sleeplessmen/VEDictionary-MintTwister.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/Sleeplessmen/VEDictionary-MintTwister.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/Sleeplessmen/VEDictionary-MintTwister.svg?style=for-the-badge
[license-url]: https://github.com/Sleeplessmen/VEDictionary-MintTwister/blob/main/LICENSE
[product-screenshot]: src\resources\screenshot1.png
