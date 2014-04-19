#Garbage Collection Assignment 

##The task
 
The goal of this assignment is to implement a of a Garbage collection 
algorithm for a Java virtual machine. The simulation is to be similar to 
that implemented as an applet at: 
http://www.artima.com/insidejvm/applets/HeapOfFish.html 

##The project 
The stages to this project would include the following:

* Research and comprehend the Garbage collection algorithm. Mark 
and Sweep is the demonstrated algorithm. However, Java 
implementations can vary the garbage collection algorithm. You 
may choose an alternative algorithm. An outline of the algorithm 
chosen should be documented in the accompanying implementation 
report. 

* Investigate the functionality required. 
 
* Choose appropriate Data Structures. Include a justification for the 
choice in the accompanying implementation report. 

* Choose an appropriate User Interface. The heap of fish example 
includes animation graphics which are not expected in this Project. 

* A more basic user interface would be acceptable. 
 
##General Description
 
To facilitate compaction, this heap uses indirect handles to objects instead 
of direct references. It is called Heap of Fish because the only types of 
objects stored on the heap for this demonstration are fish objects, defined 
as follows: 

```java
class YellowFish { 
     YellowFish myFriend; 
 } 
 
class BlueFish { 
    BlueFish myFriend; 
    YellowFish myLunch; 
} 
 
class RedFish { 
    RedFish myFriend; 
    BlueFish myLunch; 
    YellowFish mySnack; 
} 
```

As you can see, there are three classes of fish: red, yellow, and blue. The 
red fish is the largest as it has three instance variables. The yellow fish, with only one instance variable, is the smallest fish. The blue fish has two instance variables and is therefore medium-sized. 
The instance variables of fish objects are references to other fish 
objects. BlueFish.myLunch, for example, is a reference to 
a YellowFish object. In this implementation of a garbage-collected heap, a 
reference to an object occupies four bytes. Therefore, the size of the 
instance data of a RedFish object is twelve bytes, a BlueFish object is 
eight bytes, and a YellowFish object is four bytes. 

Our Heap of Fish has four modes, which can be selected via radio buttons. 

The four modes— 
* allocate fish 
  * you can instantiate new fish objects 
* assign references 
   * you can build a network of local variables and fish that refer 
to other fish 
* garbage collect 
  * a garbage collection algorithm will free any unreferenced fish 
* compact heap 
  * allows you to slide heap objects so that they are side by side 
at one end of the heap, leaving all free memory as one large 
contiguous block at the other end of the heap 
A more complete description of one implementation can be found at: 
http://www.artima.com/insidejvm/applets/HeapOfFish.html 

### Deliverables: 

1. A short (Formative) preliminary report, (due 20th
 March) indicating: 
  1. the garbage collection algorithms investigated. 
  2. the garbage collection algorithm chosen with reasoning. 
  3. proposed data structures. 
  4. envisaged user interface. 

2. Working code (due 9th April) to include: 
  1. user-interface, 
  2. object (fish) allocation functionality 
  3. object referencing functionality 
  4. garbage collection code 
  4. heap compaction code 
  5. current representation of data structures 

3. Implementation Report 
  1. the garbage collection algorithms investigated. 
  2. the garbage collection algorithm chosen with reasoning. 
  3. data structures. 
  4. user interface. 
  5. statement of authorship 

Note: This is an individual assignment. All the work done as part of this 
assignment must be done by you personally.