Java Utility Library (JAUTL), a collection of useful Java classes.



Introduction
----------------------------------------------------------------------------
This library is the result of many years of Java development, during which I
accumulated a good deal of code to complement the default JDK toolkit.

Here and there there are parts which could be written in a more refined way
(or even elimintated) using recents JDK versions but, since JAUTL is used in
production also in older JDK environement, the old fashioned code has, for the
time being, to remain (try to tell an admistrator to update the JDK in a working
enterprise application environment because you could write better looking code...). 

Building the library
----------------------------------------------------------------------------
Currently I support only the Maven way of building (pom.xml in the root directory).
Should there be a real need for it, I may provide also an Ant build.xml (please,
if you do not yet use Maven, do yourself a big favour and explore it, it is
one of the best things which ever came out of the Java world).

Using the library
----------------------------------------------------------------------------
Using the library is very simple: just add the generated jar to your dependencies
(if using an IDE), or by adding the appropriate <depedency> to your pom.xml.

Samples
----------------------------------------------------------------------------
You can find some samples for this library in the project jautl_samples here
on GitHub (to find it see the list of all my projects on GitHub at
https://github.com/alexcoppo). 

Documentation and its generation
----------------------------------------------------------------------------
The documentation of the library is wholly contained in the usual special
purpose javadoc comments and therefore has be generated.

I support both the generation by means of the the standard javadoc tool or 
using Doxygen (http://www.stack.nl/~dimitri/doxygen/).

In the doc subdirectory you can find the copyright text, a file (Doxyfile)
containing the settings I usually employ to create very extensive and
detailed documentation using Doxygen and two subdirectories set aside for
the javadoc (doc/javadoc) and Doxygen (doc/doxygen) generated contents.

Credits
----------------------------------------------------------------------------
Whenever I relied upon third party software, articles or papers, I have cited
the source in the documentation. If there are omissions, please notify me so
that I can update the documentation.

Contact information
----------------------------------------------------------------------------
You can contact me using

a l e x c o p p o
@
a l i c e
.
i t

(vain hope of twarting spammers...).