.. _bloom-filter:

Bloom filter
============

From Wikipedia

    A Bloom filter is a space-efficient probabilistic data structure, conceived by Burton Howard Bloom in 1970, that
    is used to test whether an element is a member of a set. False positive matches are possible, but false negatives
    are not; i.e. a query returns either "possibly in set" or "definitely not in set"... The more elements that are
    added to the set, the larger the probability of false positives.

As an example

.. includecode:: ../code/BloomFilter.scala#example


Creating the monoid
-------------------