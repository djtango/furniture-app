`github.com/fmd/gogoljs`
said i'd show this to you on friday. couldn't find a contact for you. feel free to close the PR. p.s. my code for it is godawful atm



SofAR
================================


An Android app that uses Augmented Reality to help you visualise potential furniture purchases in your home.
---------------------------------

***Team members: Aaron Sweeney, Deon Tan, George Maddocks and Rajeev Hejib***

Ten weeks into Makers Academy, the time came to start work on the culmination of our efforts: the two-week-long final project. Over a hundred ideas had been pitched but eventually we chose one that we thought found ambitious and challenging, but though would be achievable. The idea was to use Augmented Reality technology to help online furniture shoppers visualise a new piece of furniture within their home.

The practice of shopping for furniture is filled with potential pitfalls, especially online: you can never quite know just how what you're buying will look once it has been delivered, or even how it will look at all. When all you have to go by is a picture gallery you are always running a risk, especially when making an expensive purchase. SofAR addresses this problem. Short of seeing and touching the goods within the home context, it's the best preparation out there.

Users can select from a number of 3D furniture models. Once they have picked, they are taken to the phone's camera view, with the 3D model visible within it. They can swipe to move the piece forwards, backwards, left and right; the ROTATE / MOVE button toggles between swiping to move it and swiping to rotate it. The RAISE and LOWER buttons allow users to do move the item up and down. There's also a help panel that explains these things to the user.

We are very pleased with how the app turned out. We learned a great amount; working in a new language (Java) and using the Android framework for the first time inevitably had its challenges but overcoming them and ultimately crafting a usable, useful app was highly satisfying for all involved.


Approach and Technologies
---------
Here are the core user stories that we followed:
```
As somebody shopping for furniture online
So that I can examine a piece of furniture closely
I want to be able to see it as a 3D model

As somebody shopping for furniture online
So that I can try out different furnishings
I want to be able to see a list of available 3D furniture models

As somebody shopping for furniture online
So that I can imagine how a piece of furniture would look in my home
I would like to see the 3D model within a 3D view of the relevant room in my house
```

After some deliberation over which approach to take, we decided to make use of Augmented Reality, adding the computer-generated models to the user's camera view. We used the following technologies and languages to achieve our aim:

* [Wikitude](http://www.wikitude.com/), a mobile-focused Augmented Reality technology. We extracted Wikitude's existing Javascript functions for use in our app.
* Java - much of the app uses this language, which is the native language for Android development. Java was new to all of us, so we had to learn to adapt.
* Javascript. This is a 'hybrid' app, rather than a native one, meaning that Java and Javascript are used in tandem. The Augmented Reality features are implemented using Javascript.
* HTML & CSS - the camera view is laid over a traditional HTML template.

We worked as a professional development team would, using development branches and a master to separate and then ultimately merge our codesets.

Challenges
----------
Working on the project meant overcoming several challenges, including the following:

* Figuring out our approach, and what technology we were going to use. Once we had decided on Wikitude we spent a large amount of time learning to reverse-engineer it successfully.
* Deciding whether our app was going to use Java solely or be ‘hybrid’, making use of both Java and Javascript.
* Learning to use Java and to code with it in the Android Studio context. Both the language and the framework were new to all of us.
* Making the Java Android activity for the 'browse furniture' view ‘speak’ to the Javascript/HTML view, telling it which piece of furniture to render.
* Finding 3D Models. It was hard to find furniture models with working textures. We had to work with various pieces of professional modelling software (such as Blender and Maya) in order to find suitable models and edit them.
* Accessing the phone’s inbuilt compass in order to align the furniture in front of the user, and make the swiping controls more intuitive. This was possible using native Android Java functions.
* Styling the app - this involved both traditional Android XML and some tricky CSS for the camera view, which uses HTML and Javascript.

Setup
-----
To run the app:

1. Download [Android Studio](http://developer.android.com/sdk/index.html)

2. Fork this repository and then clone it using `git clone <url>`

3. cd into the project

4. On your Android phone, go to 'Settings' and find 'Build number' (usually in 'About device'). Tap on 'Build number' seven times. The
'Developer options' selection will now be available. In there, check the box next to 'USB debugging'

5. Open the FurnituAR folder in Android Studio. Connect your Android phone to your computer using a USB cable and click on the ► (play) button in Android studio. This will cause the app to be built on your phone, and then to run

Happy furniture-hunting!
