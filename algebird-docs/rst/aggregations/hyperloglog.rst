.. _hyperloglog:

HyperLogLog
===========

The HyperLogLog is data structure capable of estimating the cardinality (number of dinstinct values) of a set.  It can
do this in a very space efficient way.

You will need to interact with a few different objects

- ``HyperLogLogMonoid`` - the monoid
- ``HLL`` - the actual instances that wrap a given value
- ``HyperLogLog`` - An object with utility methods... you probably shouldn't use it


Creating the monoid
-------------------

To get started, create a new HyperLogLog monoid.

.. includecode:: ../code/HyperLogLog.scala#create-monoid

The HyperLogLog monoid adds instances of `HLL`. `HLL` is basially a wrapper type
that you can create using either the `apply` or `create` method on the monoid (`create`
is just an alias for `apply`).

.. includecode:: ../code/HyperLogLog.scala#create-hll



Estimating cardinalities
------------------------

Adding together HLL instances allows you to keep track of the number of distinct elements you've seen so far.




Additional information
----------------------


Choosing the number of bits
~~~~~~~~~~~~~~~~~~~~~~~~~~~


Serializing to/from Array[Bytes]
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
