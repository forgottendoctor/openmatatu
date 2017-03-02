# openmatatu
an open source implementation of the ugandan game matatu. Shamelessly inspired by but not affiliated to 
https://play.google.com/store/apps/details?id=com.matatu.common
I did this when I had alot of time on my hands. Now I have lost interest. Feel free to learn from me.
<h1>Quick compile video</h1>
[![youtube compile video](https://img.youtube.com/vi/dcCa47C6c44/0.jpg)](https://www.youtube.com/watch?v=dcCa47C6c44)

<h1>SUPPORTED PLATFORMS</h1>

-windows

-android

-web(not tested)

-iphone(not tested)

<h1>DEPENDENCIES</h1>

-Java Development Kit (JDK) . On my machine, I have JDK 7 and JDK8 and both work as far as i know

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

-libGDX for the game engine for cross platform game development

https://libgdx.badlogicgames.com/

-Tween engine 

http://www.aurelienribon.com/blog/projects/universal-tween-engine/

-gradle for compiling

-android development sdk if you want to compile apk for android.

https://developer.android.com/studio/index.html


https://gradle.org/install

<h1>How to compile</h1>
-An internet connection is required the first time you compile so that all dependencies can be downloaded. However you must have JDK already installed. (Data saving tip for users in Uganda =>Put on 1GB night bundle from the likes of MTN, AIRTEL, and start the compilation at night because our data is damn expensive. I miss Makerere internet)<br>
<h2>Building for windows</h2>
use the command : gradlew desktop:run
<h2>Building for android</h2>
use the command : gradlew android:installDebug android:run
<h2>Building for iphone and html/web</h2>
Its possible. But I have no interest in web and iphone. Use your brain.
<h1>Common Errors</h1>
Error occured during initialisation of VM. Could not reserve enough space for 1536000kb object heap.  Solution: edit gradle.properties file and reduce the amount of ram required by changing org.gradle.jvmargs=-Xms128m -Xmx1500m to some thing lower
