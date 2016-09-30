# OCR

A project that is currently just starting out but is starting to find some traction. Besides just implementing a few beginner methods to help with analzying images that are inputted, I have some working analysis that can determine whether or not it is a specific letter in very specific circumstances. My main method right now is to get it to work in a very controlled enviroment, which will allow me then to expand from a stable base point.  

## Couple of Things It Can Do:
```
  1. Given a white background and a black typed letter, it can pinpoint and retrieve the exact bounds of   
     the letter.  
```
```
  2. Starting from the middle of the letter, it moves 1 right from center, 1 left from center, 2 right from  
     center, 2 left from center, and so on...It analyzes the full height of the letter finding the black  
     pixels in that column. It outputs all the averages of each column into a usable string for the  
     analyzer.
```
```
  3. The analyzer returns the average of all the individual column averages within the character. This resembles  
     the unique ID of each character. From this, it can compare other letters to find which one is a match.
```

## Some Tests That Have Been Done

Although in the mere beginning phase, I was able to input three letter A's. (Helvetica font -- all different font sizes however). I classified each of them into smallA, mediumA, largeA, and then had a testA. When I found the averages of the first three A's, and averaged all three of those averages together, I was able to run that average against the testA. To my pleasant surpirse, there was a .18% percent error between the testA and the average of three test A's. This is very promising because it shows the method I am using can detect similar letters even if I am comparing them in a non-realistic situation because of how controlled the variables are. However, it is a promising start.

## What's On the To-Do List

1. Get all the averages of the letters in the alphabet and see if I input a random letter it can traverse through the averages of all 26 letters and find the closest match to the input.  
2. Determine the percent error threshold that allows for optimal results in identifying which letter is the input.
