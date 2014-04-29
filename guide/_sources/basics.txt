.. _basics:

#######################
Abstract algebra basics
#######################


Before getting into the details of Algebird, let's go through a few simple abstract algebra
definitions.  Folks interested in more details may wish to refer to the excellent
`Notes on Abstract Algebra <http://www.math.usm.edu/perry/old_classes/mat423fa11/notes_25aug2011.pdf>`_
from University of Southern Mississipi professor John Perry.


Semigroups
==========

Given a set :math:`S` and an operation :math:`*`, we say that :math:`(S, *)` is a *semigroup* if it satisfies the
following properties for any :math:`x, y, z \in S`:

- **Closure** :math:`x * y \in S`
- **Associativity** :math:`(x  * y)  * z = x * (y * z)`

We also say that :math:`S` forms a semigroup under :math:`*`.

Examples of semigroups
----------------------

- Strings form a semigroup under concatenation



Monoids
=======

A monoid is a semigroup with an identity element.  More formally, given a set :math:`M` and an operation
:math:`*`, we say that :math:`(M, *)` is a *monoid* if it satisfies the following properties
for any :math:`x, y, z \in M`:

- **Closure** :math:`x * y \in M`
- **Associativity** :math:`(x  * y)  * z = x * (y * z)`
- **Identity** there exists an :math:`e \in M` such that :math:`e * x = x * e = x`

We also say that :math:`M` is a monoid under :math:`*`.

Examples of monoids
-------------------

- The natural numbers :math:`\mathbb{N}` form a monoid under addition
- :math:`\mathbb{N}` also forms a monoid under multiplication
- Strings form a monoid under concatenation (where ``""`` is the identity element)



Groups
======

We say that :math:`(G, *)` is a *group* if it is a monoid that also satisfies the following property:

- **Invertibility** For every :math:`x \in G` there is an :math:`x^{-1}`
  such that :math:`x * x^{-1} = x^{-1} * x = e`

Moreover, it is an *abelian group* if it satisfies the property:

- **Commutativity** :math:`x * y = y * x` for all :math:`x, y \in G`

Examples of groups
------------------

- The integers, :math:`\mathbb{Z}`, are an abelian group under addition
- The natural numbers are *not* a group under addition (given a number :math:`x \in
  \mathbb{N}`, :math:`-x` is *not* in :math:`\mathbb{N}`)
- Neither the integers nor the natural numbers are a group under multiplication,
  but the set of rational numbers (:math:`\frac{n}{d}` for any
  :math:`n, d \in \mathbb{Z}` where :math:`d \neq 0`) is a (abelian)
  group under multiplication



Rings
=====

While a group is defined by a set and a single operation, a ring is defined by a set and two
operations.  Given a set :math:`R` and operations :math:`*` and :math:`+`, we say that :math:`(R, +, *)` is a ring if
it satisfies the following properties:

- :math:`(R, +)` is an abelian group
- For any :math:`x, y \in R`, :math:`x * y \in R`
- For any :math:`x, y, z \in R`, :math:`(x * y) * z = x * (y * z)`
- For any :math:`x, y, z \in R`, :math:`x * (y + z) = x * y + x * z`
  and :math:`(x + y) * z = x * z + y * z`

Notes on rings:

- A ring necessarily has an identity under :math:`+` called the *additive identity*
  (typically referred to as *zero*, **0**)
- A ring does not necessarily have an identity under :math:`*` (called the *multiplicative identity*)
- A ring is not necessarily commutative under :math:`*`
- A ring does not necessarily satisfy the inverse property under :math:`*`
- A ring's additive identity does not necessarily have any special properties under :math:`*`

A few other types of rings:

- We say that :math:`(R, +, *)` is a *ring with unity* if it has a multiplicative identity,
  referred to as *one*, or **1**.
- We say that :math:`(R, +, *)` is a *commutative ring* if it satisfies the commutative
  property under multiplication, that is for all :math:`x, y \in R`, :math:`x * y = y * x`

Examples of rings
-----------------

- :math:`(\mathbb{Z}, +, *)`
- The set of square square matrices of a given size are a ring



Fields
======

A field is a commutative ring in which every non-zero element contains a
multiplicative inverse. Equivalently, :math:`(F, +, *)` is a field if
:math:`(F', *)` is an abelian group where :math:`F'` is the set of non-zero
elements in :math:`F`.

Examples of fields
------------------

- The rational numbers, :math:`\mathbb{Q}`
- The real numbers, :math:`\mathbb{R}`
- the complex numbers, :math:`\mathbb{C}`


Vector space
============
