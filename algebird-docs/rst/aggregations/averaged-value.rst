.. _averaged-value:

Averaged value
==============

The ``AveragedValue`` group is a simple group which allows you to think of computing the mean across a group of numbers
just as you would computing the sum via addition.  For example:

.. includecode:: ../code/AveragedValue.scala#example

The trick is to wrap your ``Double`` values with an instance of ``AveragedValue``.  The implicit group will be found within
the companion object of ``AveragedValue`` and your instances will combine using ``+`` like any other Algebird aggregation.

Considerations for large data sets
----------------------------------

We all learned in school that the mean or average is equal to the sum of the values divided by the count of values.
Unfortunatly this approach can lead to accumulated rounding errors when computing averages in parallel across a cluster
as is often the case when using Algebird on Hadoop.

To address this, the ``AveragedValue`` group uses an algorithm
that is more stable across a large number of samples (read more `on Wikipedia
<http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Parallel_algorithm>`_).

See below for a demonstration:

.. includecode:: ../code/AveragedValue.scala#increased-accuracy