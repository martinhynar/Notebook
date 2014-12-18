---
title: "R Programming"
output: html_document
---

-------



* basic types
    * chararacter
    * number (double precision real number), integer, complex number
    * boolean (values `TRUE` and `FALSE`)
* vector - collection of values of the same type (create empty vector with function `vector()`)
* list - values could be of mixed type

* to determine type of object:

```{r}
x <- 5
typeof(x)
```

* when declaring number a real number type will be used in general. If integer is desired, use **L** suffix

```{r}
x <- 5L
typeof(x)
```

There is special symbol for infinity - `Inf` that could be used in ordinary calculations and a special sysmbol for Not a Number -`NaN` that could be considered a *missing value*.

```{r}
print(1/0)
print(Inf + 2)
print(0/0)
```

In R, each value is object that could hold attributes. These could be retrieved using `attributes()` function. Attributes could be - length, names, dimension, class, ...

```{r}
v <- matrix()
attributes(v)
```

To create an integer sequence, use `:` operator.

```{r}
1:20
x <- 34:38
x
```

### More on creating vectors

Function `c()` will concatenate all its arguments into a vector. If arguments are not of the same data type, they will be all coerced to common data type.
```{r}
c(1, 2, 3) # numeric
c(1, "a", 2) # character (coerced)
c(TRUE, 2) # numeric (coerced)
c(FALSE, "a") # characted (coerced)
```

Values could be coerced explicitly using functions `as.*`

```{r}
as.logical(0:3)
as.character(c(TRUE, FALSE))
as.complex("2+5i")
```

Some values cannot be coerced because there is no logically correct conversion.

```{r}
as.numeric("hi")
```

Function `vector()` could be used to construct empty vector of given length with items of given data type.

```{r}
vector(mode = "numeric", length = 5)
```

### Matrices

We can create matrices of given dimension using `matrix()` function

```{r}
m <- matrix(data = 0:5, nrow = 3, ncol = 2)
m
dim(m)
attributes(m)
```

We can alco convert a vector into a matrix by assigning it a dimensions

```{r}
m <- 0:5
m
dim(m) <- c(2, 3)
m
```

Another way to construct matrix is by column binding `cbind()` and row binding `rbind()`. This means that vectors are attached as new columns or new rows to a vector or a matrix. The names of the vectors that were bound are remembered as `dimnames` attributes. If anonymous row or column is bound, no name is attached.

```{r}
x <- 1:3
y <- 11:13
z <- 21:23
r <- 31:33
xy <- cbind(x, y)
xy
xyz <- cbind(xy, z)
xyz
xyzr <- rbind(xyz, r)
xyzr
xyzrr <- rbind(xyzr, 41:43)
xyzrr
attributes(xyzrr)
```

### Lists
Lists could hold data of variable data types. Lists are created using `list()` function, all arguments passed to it are concatenated to construct a list. When list is printed out, it is not presented in a single row, but all elements are printed on separate lined. In double brackets, there is index into the list and then the value itself is printed on new line.

```{r}
l <- list("a", 1, 3.14, 3+1i, 5:6)
l
l[1]
```

### Factors
Factors are used to represent categorical data, either ordered ("small", "medium", "large") or unordered ("male", "female"). It is good approach to use self-describing factors rather than numerical codes ("male" = 1, "female" = 2)

Factors are treated specially by modelling functions like `lm()` or `glm()`

To create factors, use function `factor()` that consumes vector of categorical data. This factorization could be, for example, presented using `table()` function. Also, factors could be converted to numerical values using `unclass()` function (which might be sometimes useful).

```{r}
f <- factor(c("yes", "yes", "no", "no", "yes"))
f
table(f)
unclass(f)
```

To give specific ordering for the factors in the data vector, use `levels` parameter. By default, lexicographic ordering is used ("no" comes first in previous example).

Look at the next example to see that giving specific order to factor values gives different numerical values in unclassified output.
```{r}
unclass(factor(x = c("large", "small", "medium", "large", "medium")))
unclass(factor(x = c("large", "small", "medium", "large", "medium"), levels = c("small", "medium", "large")))
```

### Missing values

* When value is missing - `NA` - and such values have a class also (integer `NA`, character `NA`)
* When undefined mathematical operation -`NaN`
* `NaN` is also a `NA` but oposite is not true

These values could be tested by:

```{r}
is.na(NA)
is.nan(NaN)
is.nan(c(1, NaN, 2))
is.na(c(1, NaN, NA))
```

### Data Frames

* Used to store tabular data
* Represented as a special type of list where each element of a list has to have same length.
* Think of it as if each element of a list is a column of data and length of each column is number of rows
* In contrast to matrices, data frames can contain different data types (as list are allowed to)
* Data frames have special attribute `row.names`
* Data frames are constructed from data using `read.table()` or `read.csv()`
* Data frames could be converted to matrices using `data.matrix()`

```{r}
df <- data.frame(c1 = 1:4, c2 = 11:14)
df
nrow(df)
ncol(df)
df <- data.frame(c1 = 1:4, c2 = 11:14, row.names = c("r1", "r2", "r3", "r4"))
df <- data.frame(c1 = 1:4, c2 = 11:14, rn = c("r1", "r2", "r3", "r4"), row.names = "rn")
df
```

### Assigning names to values

Values can be assigned with name.

```{r}
x <- c(1, 2)
names(x)
x
names(x) <- c("first", "second")
names(x)
x
x <- 5
names(x)
names(x) <- c("single")
names(x)
x
```

Also elements of list could be assigned names. They could be assighen using `names()` or directly in `list()` call. When such list os printed out, instead of double brackets with index number `[[1]]` a named reference is printed with `$` prefix `$a`.

```{r}
list(a=1, b="b")
```

In case of matrices, we could name both rows and columns using `dimnames()` function.

```{r}
m <- matrix(data = 0:3, nrow = 2, ncol = 2)
dimnames(m) <- list(c("r1", "r2"), c("c1", "c2"))
m
```

### Subsetting objects

* `[]` - selects objects of the same type as the original, could be used to select more than one object
* `[[]]` - selects objects from data frame or list. Selects only single element and the data type could any data type
* `$` - selects objects from data frame or list by name. Semantics similar to `[[]]`

```{r}
x <- 1:6
x[0]
x[1:2]
# Select all values greater than 3 - using logical selector
x[x > 3]
# Creating a logical index
u <- x > 4
u
# Now use it to select elements
x[u]
```

Also matrices can be subsetted

```{r}
m <- matrix(1:4, ncol = 2, nrow = 2)
m
# select single element
m[1, 1]
# select row
m[1,]
# select column
m[,1]
```

Subsetting from a matrix returns by default vector of values. This could be sometimes unwanted, and it could be switched off to get another matrix by adding extra argument `drop = FALSE`.

```{r}
m <- matrix(1:4, 2, 2)
# Get vector
m[1, 1]
# Get matrix
m[1, 1, drop = FALSE]
# Get matrix containing first row
m[1, , drop = FALSE]
```

Subsetting a list has few more options

* `[]` returns list containing element on given index - `[]` always returns object of the same type as original
* `[[]]` returns just the element on given index
* `$name` or `l["name"]` returns element by name
```{r}
l <- list(v = 1:5, i = 0.5)
# Extract first element [] - getting list
l[1]
# Extract first element [[]] - getting element (vector n this case)
l[[1]]
# Extract first element by its name
l$v
l["v"]
```

Also a list of element could be extracted using `[]`

```{r}
l[c(1,2)]
```

And the argument to the `[[]]` could be variable
```{r}
ind <- "v"
l[[ind]]
```

When having nested list, we can still use `[[]]` to access inner elements.

```{r}
l <- list(a = list(aa = 1:3), b = list(bb = 11:13))
l
```
Either by using integer vector passed to `[[]]`
```{r}
l[[c(2, 1)]]
```
Or by multiple `[[]]` one after another
```{r}
l[[2]][[1]]
```
And if the list' element is collection:
```{r}
l[[c(2, 1, 1)]]
```


### Partial matching

Named elements could be selected also using *partial matching*. This allows to select element by only part of its name.

```{r}
l <- list(longname = 1:5)
l$lo
# [[]] operator, will not work. It is for exact matching
l[["lo"]]
# But nothing is lost
l[["lo", exact = FALSE]]
```

### Removing NA values

In real data there are usually missing values that can't be calculated or analysed in any way. These are subject for removal before actual analysis begins. A way to remove it is to create a logical vector of *filled* and *vacant* positions in original data vector. Then filtering out elements that are `NA`.

```{r}
data <- c(NA, 1, 2, 5, NA, 6)
vacant <- is.na(data)
# Now, select only those elements that are not vacant
data[!vacant]
```

But what if we have n-dimensional data in several vectors? There is a function `complete.cases()` that will create logical vector for those position where all values are present.

```{r}
x <- c(1, 2, NA, NA, 5)
y <- c("A", NA, "C", "D", "E")
# Only 1-A and 5-E are both filled positions
complete <- complete.cases(x, y)
x[complete]
y[complete]
```

Function `complete.cases()` will work also for data frame with multiple columns.

```{r}
df <- data.frame(ac = c(1,   2,  NA,  NA,  5),
                 bc = c("A", NA, "C", "D", "E"),
                 cc = c(T,   T,  NA,  F,   NA))
# Just the first row is complete
complete <- complete.cases(df)
dfc <- df[complete,]
dfc
```

To select from data frame only those rows that fulfill some condition, there is handy function `subset()`

```{r}
df <- data.frame(ac = c(1,   2,   3 ,  4 ,  5),
                 bc = c("A", "B", "C", "D", "E"))
dfs <- subset(df, ac > 3, select = ac:bc)
dfs
```

### Ordering

To sort a collecttion, there is a function `order()` that does that

```{r}
df <- data.frame(num = c (3, 4, 1, 2), char = c("D", "C", "A", "B"), withtie = c(2, 2, 1, 1))
print(df)
# get ordering
ord <- order(df$num)
str(ord)
# put data frame in order
df <- df[ord, ]
print(df)
# Now order with 2 columns and directly assign to original data frame
df <- df[order(df$withtie, df$char), ]
print(df)
```


### Reading and writing data

There are several functions for reading data in R

* read/write tabular data - `read.table()`/`write.table()` and `read.csv()`/`write.csv()`
* read/write text lines - `readLines()`/`writeLines()`
* read/write R source code - `source()`/`dump()`
* read/write text encoded (deparsed) R objects - `dget()`/`dput()`
* read/write binary objects (e.g. workspace) - `load()`/`save()` and `unserialize()`/`serialize()`

When reading large data sets with `read.table()` there is number of hints to take into account

* Read help page for `read.table()`
* Calculate size that data will consume in memory. If it does not fit into available RAM, don't read it.
* Set `comment.char = ""` if there are no comments
* Set `colClasses` to avoid investigation of column classes. This could make reading data **much** faster. If all columns are e.g. numeric, just set `colClasses = "numeric"`
* Calculate number of rows in advance (e.g. using `wc -l`) and set `nrows`. Small overestimate does not hurt. This does not make reading faster but helps with memory usage.

#### Work with textual files
R is able to detect and read/write data types it uses (including metadata) into/from textual data. The metadata is the reason, why it more suitable than CSV because with CSV, metadata are lost or must be manually renewed each time data are read.

* **+** textual format is better fit for version control of data
* **+** textual format is more suitable when data are long-lived and if there is a corruption, it could be hand-fixed
* **-** textual format is more space consuming

One way to pass data around in textual format is by *dputtting* them
```{r}
data <- data.frame(num_field = 1, text_field = "text")
dput(data)
# Also possible to feed it into file and read it back
tfile <- tempfile(pattern = "file", tmpdir = tempdir(), fileext = ".R")
dput(data, file = tfile)
new.data <- dget(tfile)
new.data
```
If there is bunh of objects, `dump()` will store them all in file. When `dump()` is used, the assignment to the variable is preserved so when such file is read back using `source()`, variables are created/overwritten.

```{r}
foo <- 1:5
foo
tfile <- tempfile(pattern = "file", tmpdir = tempdir(), fileext = ".R")
dump(c("foo"), file = tfile)
rm(foo)
exists("foo")
source(tfile)
foo
```
In general, data a accessed using *connection* interfaces. Mostly, connections are created to files using `file()` but also to other data sources
* gzipped file with `gzfile()`
* bzipped file with `bzfile()`
* web resource with `url()`

In practice, there's often no need to interact directly with connections, as for example reading file (assuming it is csv)

```{r}
# prepare file
tfile <- tfile <- tempfile(pattern = "file", tmpdir = tempdir())
dump(c("foo"), file = tfile)
# read using connection
conn <- file(tfile, "r")
data <- read.csv(conn)
close(conn)
# is the same as
data <- read.csv(tfile)
```

But in some cases, working with connections allows for more posibilities. E.g. when reading compressed files or reading data directly from web.

```{r}
tfile <- tempfile(pattern = "gzipped", tmpdir = tempdir(), fileext = ".gz")
lines <- c("line 1", "line 2")

gzwrite <- gzfile(tfile, "w")
writeLines(lines, gzwrite)
close(gzwrite)

gzread <- gzfile(tfile, "r")
line1 <- readLines(gzread, 1)
line1
close(gzread)
```

### Couple of sequence utility functions
If we are about to traverse some vector, we can create a sequence of integers that is of the same length as the original sequence.

```{r}
# If we know length of the vector
seq_len(3)
# If we have the vector in hand
seq_along(c(9, 77, 5, 45))
```
These index sequences could be used in loops to access items of the original vector.

### Date and time

Date is stored in `Date` class and time is stored either in `POSIXct` or `POSIXlt` class. Date instance is internally stored and number of days since 1970-01-01 and time as number of seconds since the same reference date.

A date can be coerced from string using `as.Date()` function

```{r}
d <- as.Date("2014-05-24")
d
# check days since start of epoch
unclass(as.Date("1970-01-01"))
unclass(as.Date("1970-01-02"))
```

The two data types for representing time follow different strategies.
* `POSIXct` uses internally big integer number, so it is usefull for usage in data frames
* `POSIXlt` is internally list of various information about the date (day of the week, day of the year, month, day of the month, ...)

```{r}
# time in POSIXct
t <- Sys.time()
t
unclass(t)
lt <- as.POSIXlt(t)
names(unclass(lt))
lt$mon
```

There exists a handy `strptime()` function that consumes character vector with dates and format string that describes what time parts are at what positions. As a result `POSIXlt` vector is returned.

```{r}
datestrings <- c("January 1, 1970 11:11", "January 2, 1970 15:15")
dates <- strptime(datestrings, "%B %d, %Y %H:%M")
dates
class(dates)
```

The operations with dates and times must be done with instances that are in the same format. Mixing `Date` with e.g. `POSIXlt` will cause error. Operations on dates do count with all the specifics of dates and times like leap years, different timezone, ...

```{r}
x <- as.POSIXlt("1970-01-01 12:35:44", tz="EET")
y <- as.POSIXlt("1970-02-09 11:18:56", tz="GMT")
y-x
class(y-x)
```

### Loop functions

A set of functions that allow for application of given function over a list of arguments. Using this, programs are shorter and necessity for explicit loops is minimized. Loop functions could also gain a performance benefit as the actual looping is done internally in C code.

`lapply()` function executes a function over a list passed as argument. If argument is not list, `lapply()` will try to coerce it to list. If that is not possible, `lapply()` will fail.

`sapply()` function is same as `lapply()` but result is simplified - if FILL RULES HERE

`apply()` function applies function over margins of an array

`tapply()` function applies function over subsets of a vector (sort of *table apply*)

`mapply()` function is multivariate version of `lapply()`

`split()` is function that is frequently used with `lapply()` and allows for splitting of data into several pieces based on some factor variable.

All *apply* functions take at least 3 arguments - a collection over which it operates, a function that is executed over elements of collection and optionally arguments that are passed to function.

```{r}
# Calculate means of collections
l <- list(a = 1:10, b = rnorm(10))
lapply(l, mean)

# Generate 2 collections of rand values by runif() with passing it min and max
lapply(c(3, 3), runif, min = 1, max = 10)

# Extract first rows of a list of matrices
ms <- list(m1 = matrix(rnorm(2*2), nrow = 2, ncol = 2),
           m2 = matrix(rnorm(2*2), nrow = 2, ncol = 2))
ms
# Create in-place anonymous function used by lapply
lapply(ms, function(m){ m[1,] })
```

Function `lapply()` always returns a list.

The `sapply()` function will do the same as `lapply()` and then will simplify the result base on few rules:

* if result is list where every element is of lenght 1, then vector is returned
* if result is list where every element is vector of the same length (> 1), then matrix is returned
* otherwise list is returned


```{r}
# Get list with  lapply
lapply(list(1:5, 11:15), mean)
# Get vector with sapply
sapply(list(1:5, 11:15), mean)
```

The `apply()` function is used to evaluate a function over an array while fixing some dimension, e.g. over columns or rows of a matrix. It is not faster than explicit loop, but is less verbose (often one-liner). The signature of `apply()` is following:

```{r}
str(apply)
```

The argument `MARGIN` is an integer vector of margins that shall be retained (the dimension that shall not be collapsed by the function).

```{r}
m <- matrix(1:10, nrow = 2, ncol = 5)
m
# To get sum of each row
apply(m, MARGIN = 1, sum)
# To get sum of each column
apply(m, MARGIN = 2, sum)
```

There are shortcut functions for commonly used margins and functions.

* `rowSums(x) = apply(x, 1, sum)` 
* `rowMeans(x) = apply(x, 1, mean)`
* `colSums(x) = apply(x, 2, sum)`
* `colMeans(x) = apply(x, 2, mean)`

The shortcut functions are optimized and will run faster, but that becomes observable when working with larger matrices.

```{r}
rowSums(m)
```

Functions from `apply()` family support also functions that require more arguments. For example, to calculate 25% and 75% percentiles.

```{r}
apply(m, 1, quantile, probs = c(0.25, 0.75))
```

It is possible to apply a function to n-dimensional value and fix multiple dimensions. E.g. having cube of dimension `2x2x4` which looks like `4` matrices of `2x2` stacked one after another and summing columns "to the back" is done by fixing dimensions `1` and `2` while dimension `3` is collapsed.

```{r}
mm <- array(1:16, c(2,2,4))
mm

apply(mm, c(1,2), sum)
```

Function `tapply()` is used to apply a function over a subsets of a vector. To caculate these subsets, `tapply()` accepts `INDEX` argument which is a factor vector (or the argument is coerced to factor vector) that identifies subsets within the original input vector `X`.

```{r}
str(tapply)
```

If I setup 3 groups of randomly generated values, I could calculate their means like this:

```{r}
x <- c(rnorm(10, 1), rnorm(10, 5), rnorm(10, 9))
factors <- gl(3, 10)
factors
tapply(x, INDEX = factors, mean)
```

Function `tapply()` by default simplifies its result same way as `sapply()` does. To switch off this behavior and get list instead, pass `simplify = FALSE`as argument.

The grouping mechanism provided in `tapply()` functions is very useful and is provided separatelly using `split()` function.

```{r}
str(split)
```

From the previous example, if I wanted just the separated list of values according to their factors, I could get them:

```{r}
split(x, factors)
```

Then I could use `lapply()` function on the result, but this is what `tapply()` will do also.

Function `split()` is particulary usefull when separating data in data frames. For example, having standard sample data set.

```{r}
library(datasets)
head(airquality)
str(airquality)
```

I could separate it based on months. This will yield list with elements named with month numbers (there are data for months 5 to 9)

```{r}
month_airq <- split(airquality, airquality$Month)
# Check the list element names
names(month_airq)
```

Having this separated data, calculating means for each given columns within month is straightforward.

```{r}
sapply(month_airq, function(x) { colMeans(x[, c("Ozone", "Wind")]) })
```

The `NA` values are there because Ozone column does have some, I need to get rid of them, and passing `na.rm = TRUE` to `colMeans()` will do the job.

```{r}
sapply(month_airq, function(x) { colMeans(x[, c("Ozone", "Wind")], na.rm = TRUE) })
```

Splitting is possible at more than just one level. For example, when I have data with 2 categorical variables and want to calculate some measure on all combinations of these two, I can generate relevant subsets by passing list of factor indices to `split()`

```{r}
f1 <- gl(2, 5)
f2 <- gl(5, 2)
x <- rnorm(10)
split(x, list(f1, f2), drop = TRUE)

df <- data.frame(a = 1:4, b = 2:5, c = rnorm(4))
df
split(df, list(df$a, df$b), drop = TRUE)
```

Function `mapply()` a multivariate variant of `lapply()` and `sapply()` functions.

```{r}
str(mapply)
```

The `...` argument is used to pass number of lists that `FUN` will operate on. If I have `mapply(f, a, b])` then gradually `f(a[1], b[1])`, `f(a[2], b[2])`, ... will be executed. If `a.length != b.length` the shorter vector will be iterated from the start once end is reached.

```{r}
mapply(rep, 1:4, 5:1)
```

### Vectorization of a function
To make from a function that does not accept vector as an argiment a vectorized function I can call `mapply()` function on a function I want to vectorize. This way I can call a function with a vector in place of argument that shall not be a vector.

Here, `samples()` function accepts 3 non-vector arguments
```{r}
samples <- function(n, mean, sd) { rnorm(n, mean, sd) }
samples(6, 2, 3)
```

I want now to generate 2 samples with 5 and 6 values. Calling `samples()` function with `n = 5:6` will not give desired output.

```{r}
samples(5:6, 2, 3)
```
I need to vectorize this function with `mapply()` and then pass a vector.
```{r}
mapply(samples, 5:6, 2, 3)
```

### Generating variables from given distribution

There is number of standard distributions built in R: Normal, Poisson, Binomial, Exponential, Gamma, ... Examples of functions that provide some functionslity while adhering to given distribution: 

* `rnorm()` - generate Normal variable with given mean and standard deviation
* `dnorm()` - evaluate Normal probability density (with given mean and sd) at a point (or vector of points)
* `pnorm()` - evaluate cummulative distribution for Normal distribution
* `rpois()` - generage random Poisson variates with given rate

Functions associated with distributions have specific prefix that tells what given function is good for:

* d - density - `Probability(X=x)`
* r - random
* p - cummulative distribution - `Probability(X<=x)`
* q - quantile function

```{r}
dnorm(x = 1:5)
```

### Reseting random number generator
By default, random numbers are generaged based on seed which when not specified is based on time. To set fixed seed and ensure reproducibility, call:

```{r}
set.seed(1)
rnorm(1)
# This will generate different number
rnorm(1)
# Reset the seed and get the same number as in the first call
set.seed(1)
rnorm(1)
```

### Generating random numbers from model

Suppose I have a linear model $y = \beta_0 + \beta_1 x + \epsilon$ where

* `x` is random variable with Normal distribution $x \sim N(0, 1^2)$
* $\epsilon$ is random noise (error) with Normal distribution $\epsilon \sim N(0, 2^2)$
* Constants in model $\beta_0 = 0.5$ and $\beta_1 = 2$

To calculate random samples from this model in R:

```{r}
set.seed(20)
x <- rnorm(1000)
e <- rnorm(1000, 0, 2)

y <- 0.5 + 2*x + e
summary(y)
str(y)
plot(x, y, main ="y = b0 + b1*x + e")
hist(y, prob=T, xlim = c(-10, 10), ylim=c(0,0.5), breaks=20, las=1)
lines(density(x), col = 2)
```

From the plot, there is obvious that there is linear dependency between `x` and `y`. 

The random variables in the model could be of whatever type. A modification of the previous example could be that `x` is binomial random variable.

```{r}
set.seed(20)
x <- rbinom(100, 1, 0.5)
e <- rnorm(100, 0, 2)

y <- 0.5 + 2*x + e
summary(y)
str(y)
plot(x, y, main ="y = b0 + b1*x + e")
```

### Generating from Generalized Linear Model
Suppose I want to simulate from Poisson model where $Y \sim Poisson(\mu)$, given that $log(\mu) = \beta_0 + \beta_1 x$ with $x \sim N(0, 1^2)$, $\beta_0 = 0.5$ and $\beta_1 = 0.3$.

```{r}
set.seed(20)
x <- rnorm(100, 0, 1)
log.mu <- 0.5 + 0.3*x
y <- rpois(100, exp(log.mu))
summary(y)
str(y)
plot(x, y)
```

### Random sampling
To randomly draw samples from specific vector of values, there is function `sample()`

```{r}
set.seed(20)
# Random sample of length 4 from number 1 to 10
sample(1:10, 4)

# Sample of 5 letters
sample(letters, 5)

# Without number, we get permutation
sample(1:10)

# With replacement
sample(1:10, replace = TRUE)
```


### Getting and Cleaning data

Processed data are ready for analysis.  For clean data, there should be 4 things available:

1. The raw data
1. Tidy data set
1. **Code book** describing each variable and its values in tidy data set.
1. Explicit and exact recipe used to go from 1 -> 2, 3



### Data Tables
The `data.tables` package provides an alternative to `data.frame`. It inherits from `data.frame` but as it is written in C, it is much faster and is much faster on subsetting, grouping, updating functions. As it inherits from `data.frame`, all functions that accept `data.frame` shall also accept `data.table`.

The way how `data.table` is created is same as for `data.frame`.

```{r}
library(data.table)

df <- data.frame(x = rnorm(6), y = 1:6, z = rep(c("a", "b"), each = 3))
df

dt <- data.table(x = rnorm(6), y = 1:6, z = rep(c("a", "b"), each = 3))
dt
```

To see all tables that are actually in memory, call `tables()` function

```{r}
tables()
```

The subsetting works as expected. For rows:

```{r}
dt[2,]
```

For columns:

```{r}
dt[dt$z == "a", ]
```

Subsetting more rows:

```{r}
dt[c(2, 3), ]
```

But subsetting more columns is different

```{r}
dt[, c(2, 3)]
```

Column subsetting works in a different way that for `data.frame`. For `data.table`, the argument that comes after comma is **expression** which is collection of statements enclosed in `{}`.

To work with columns in `data.table` I can directly set up a function that will be called on given table column.

E.g. get mean of values in column `x` and sum of values in column `y`
```{r}
dt[, list(mean(x), sum(y))]
```

or table of values in column `z`

```{r}
dt[, table(z)]
```

The `data.table` will know that I am working with `dt` objects so it is not necessary to write `dt[, table(dt$z)]`.

It is also possible to add new columns based on existing ones:


```{r}
dt[, sqy := y^2]
```

Note that assigning a data table to another variable, does not create new data table, but only assigns a reference. Thus, changing the original data table is projected to all references. To make a copy of a data table, use function `copy()`.

On a data table, using expressions, I can perform multiple oparations.

```{r}
dt[, log_xy := {t <- abs(x + y); log(t)}]
```

I can also apply an operation to subsets of the table. Let me create new column telling if `x` is negative. Then based on its value, calculate mean of those that are negative and those that are not.

```{r}
dt[, neg := x>0]
dt[, mu := mean(x), by = neg]
```

There are special variables in data table that aloow to calculate certain things fast. First variable is `.N` which provides number of occurences of each group in given column. If I gave e.g. column with DNA letters in it ("T", "C", "A", "G") and want to count number occurences of each letter, I do:

```{r}
dt <- data.table(dna = sample(c("T", "C", "A", "G"), size = 100, replace = TRUE), prob = rnorm(100))
dt[, .N, by = dna]
```

If I want to work with certain column based on its values, I could set a key column for it with `setKey()` function. Then I can subselect rows just with the values. E.g. to get just values for DNA letter "G" and then calculate mean in column `prob`, I do:


```{r}
setkey(dt, dna)
head(dt["G"], n = 5)
mean(dt["G"]$prob)
```

By setting keys on two tables, I can perform join operation based on values of the key columns.

```{r}
dt1 <- data.table(k = c("G", "C", "C"), a = c(1, 2, 3))
dt2 <- data.table(k = c("T", "C", "A"), b = c(-1, -2, -3))
setkey(dt1, k)
setkey(dt2, k)
merge(dt1, dt2)
```

The result is given that `dt1` and `dt2` have only one common value `"C"` in key columns. In table `dt1` there are 2 rows having it, with values 2 and 3 in column `a`. In table `dt2` there is only one row, with value -2 in column `b`. So, in the merged table, there will be 2 rows from `dt1` combined with 1 row from `dt2`.

Note that in this case, the relationship is `N:1`, which is fine. The `merge()` function will work on `1:1`, `1:N`, and `N:1` relationships. It will raise an error when `M:N` relationship is detected.

For very large tables, there is special function `fread()` that allows for very fast reading into memory.

### Accessing MySQL database

* Open connection with `dbc <- dbConnect(MySQL(), user="genome", host="genome-mysql.cse.ucsc.edu")`
* Do the query with `result <- gbGetQuery(dbc, "show databases")`
* Close the connection at the end with `dbDisconnect(db)`

In order to issue queries towards specific database, pass `db="hg19"` parameter to the `dbConnect()` function. This will select database "hg19" and perform all queries on it. The object returned by `dbConnect()` is the connection to that specific database.

When connected to specific database, function `dbListTables()` will provide list of all tables that are in that database. To get list of fields of specific table, use `dbListFields(dbc, "tablename")`.

For example, to get number of records in a table , do `dbGetQuery(dbc, "select count(*) from tablename")`

To get the whole table in R, there is function `dbReadTable(dbc, "tablename")`. This will load whole table into data frame.

To select a subset of a table, I must send a SQL SELECT query with WHERE clause. This is done via `query <- dbSendQuery(dbc, "SELECT column FROM tablename WHERE condition"`. This will create a query object that provides access to the rows. To get the actual rows into data frame, function `result <- fetch(query)` is used.

To dettach the query from the remote server, I have to run `dbClearResult(query)`. Until this function is called, the connection for the query is held to transfer additional data.

### Reading data from Web page

* Open an URL with `con <- url("http://.....")`
* Read contents of the page with `lines <- readLines(con)`
* Close connection to web with `close(con)`

Or, I could make use of XML library and read the contents of the page directly into XML document and then inspect it.

Another alternative is to use `httr` package

* Load the library `library(httr)`
* Get the document with `html <- GET("http://....")`
* Parse it with `parsed <- htmlParse(html)`
* Use the Xpath expressions from XML package.

To access password protected pages, use `GET("Http://...", authenticate("user", "pass"))`

If I want to access multiple pages on the same site, I can use *handles*

* Create handle with `google <- handle("http://google.com")`
* Load root page with `root <- GET(handle = google, path = "/")`
* Load search page with `search <- GET(handle = google, path = "/search")`

If there is authentication on the site, authenticating with handle will keep authentication for all the paths used with that handle.

### Reading data with API

To load data from Twitter, first I need to create an application which gives me set of access tokens used in R for actual access.

* Define authentication object with `myapp <- oauth_app("twitter", key = "consumerKey", secret = "consumerSecret")`
* Sign in with `sig <- sig_ouath1.0(myapp, token = "token", token_secret = "tokenSecret")`
* Get the data with `tl <- GET("https://api.twiter.com/1.1/statuses/home_timeline.json", sig)`

To read the data from the JSON object, do

* First take the content from the returned body with `js1 <- content(tl)`
* Convert the JSON object to data frame with `js2 <- jsonlite::fromJSON(toJSON(js1))`
* Work with the result as with data frame, eg. first line and four columns `js2[1, 1:4]`