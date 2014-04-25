.. _index:

#########
 Algebird
#########


Abstract algebra for Scala. This code is targeted at building aggregation systems (via `Scalding <https://github.com/twitter/scalding>`_ or `Storm <https://github.com/nathanmarz/storm>`_). It was originally developed as part of Scalding's Matrix API, where Matrices had values which are elements of Monoids, Groups, or Rings. Subsequently, it was clear that the code had broader application within Scalding and on other projects within Twitter.

See the `current API documentation <http://twitter.github.com/algebird>`_ for more information.



.. _what-can-you-do-with-algebird:

What can you do with Algebird?
==============================

Algebird allows you to generalize the notion of *addition* to a much broader array of (exotic!) data structures.  A simple example would be something like:

.. includecode:: code/Example.scala#intro-example

The class ``Max`` signifies that the ``+`` operator should actually be *maximum*.  Algebird achieves this by supplying implicit objects (often instances of the typeclass ``Monoid``) which handle adding objects together using whatever specialized logic is necessary.

This turns out to be particularly useful in MapReduce-style systems, as monoids can be used to compute sums in parallel and combine naturally during reduction to a single value.  Algebird helps you:

* Model a wide class of "reductions" as a sum on some iterator of a particular value type
  * For example, average, moving average, max/min, set, union, approximate set size (in much less memory with HyperLogLog), approximate item counting (using CountMinSketch).
* Combine monoid instances naturally across tuples, vectors, maps, options and more standard Scala classes.
* Use monoid implementations of interesting approximation algorithms, such as :ref:`Bloom filters <bloom-filter>`,
  the :ref:`HyperLogLog <hyperloglog>` and the :ref:`Count-min sketch <countmin-sketch>`

Algebird's powerful abstractions allow you to think of these sophisticated operations like you might numbers and add them up in Hadoop or online to produce powerful statistics and analytics.


.. toctree::
  :maxdepth: 2

  basics
  monoids
