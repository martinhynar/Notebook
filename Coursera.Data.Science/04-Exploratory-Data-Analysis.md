---
title: "Exploratory Data Analysis"
output: html_document
---

### Basic rules and principles when building analytic graphics

#### Show comparison

+ Evidence for hypothesis is **always** relative to another competing hypothesis
+ Ask **compared to what?**

#### Show causality

+ Show how "world works"; what leads to something happening.
+ Show how dependent variable changed based on some source. (e.g. values of polution decrease when air cleaner is used)

#### Show multivariate (more than 2 variables) data

+ Because world **is** multivariate
+ Introduce more variables when displaying relationship between 2 variables, e.g.
    + based on season of the year
    + based on hour of the day
    + based on location in the world
    + based on age
  
#### Integration of evidence

+ Explain evidence in multiple ways - words, numbers, images, diagrams
+ Graphic visualization of data shall use many modes of presentation
+ Do not let the used tool to drive the analysis

#### Describe and document the evidence with appropriate labels, scales, surces, ...
+ Data graphic shall tell the whole story that is credible

#### Content is king

+ If there is no interesting story to tell, then no amount of presentation will make it interesting

*Reference: Edward Tufte (2006) - Beautiful Evidence (edwardtufte.com)*

## Exploratory graphs

Use them to

+ understand data properties
+ find patterns
+ suggest modeling strategies
+ debug analyses
+ communicate results

```{r}
library(datasets)
str(airquality)
```

The basic tools to summarize data in one dimension are

##### 5-number summary

(min, 0.25-quantile, median, mean, 0.75-quantile, max) - using `summary()` function

```{r}
summary(airquality$Temp)
```

##### Boxplot

Boxplot displays almost the same information as summary (except mean), but in graphical form. When more graphic functions are used, they will be overlayed in the same plotting.
```{r}
# Draw boxplot first
boxplot(airquality$Temp, col = "gray")
# Then add line at mean
abline(h = mean(airquality$Temp))
```

##### Histogram

Plot distribution of values, so that I can see which values are most frequent. When used together with `rug()` function, individual data points are displayed too.

```{r}
hist(airquality$Temp, col = "gray", ylim = c(0, 40))
abline(v = median(airquality$Temp), lwd = 4)
rug(cars$dist)
```

To make the histogram more fine-grained, `break` parameter is handy.

```{r}
hist(airquality$Temp, col = "gray", ylim = c(0, 25), breaks = 20)
abline(h = 20)
rug(airquality$Temp)
```

##### Bar plot

For categorical data, there is similar graph called *barplot* that depicts frequencies in categories.

```{r}
barplot(table(airquality$Month))
```

##### Density plot

### Displaying more dimensions

In principle, single multidimensional graph is not that useful as several composed 1D and 2D graphs. To express more dimensions, variable colours, shapes or sizes could be used.

E.g. to display given variable based on value of some other variable, do:

```{r}
boxplot(Temp ~ Month, data = airquality)
```

To create matrix of plots, a `par()` functions is used.

```{r}
par(mfrow = c (5, 1), mar = c(4, 4, 2, 1))
hist(subset(airquality, Month == 5)$Temp, main = "Temp in May")
hist(subset(airquality, Month == 6)$Temp, main = "Temp in June")
hist(subset(airquality, Month == 7)$Temp, main = "Temp in July")
hist(subset(airquality, Month == 8)$Temp, main = "Temp in August")
hist(subset(airquality, Month == 9)$Temp, main = "Temp in September")
```

#### Scatterplot

This displays dependency between two variables.

```{r}
with(airquality, plot(Temp, Wind))
```

To display in the same scatterplot another dimension, I can color the dots based on another variables:

```{r}
with(airquality, plot(Temp, Wind, col = Month))
```

Same information could be achieved by placing several scatterplots:

```{r}
par(mfrow = c(3, 2), mar = c(5, 4, 2, 1))
with(subset(airquality, Month == 5), plot(Temp, Wind, main = "May"))
with(subset(airquality, Month == 6), plot(Temp, Wind, main = "June"))
with(subset(airquality, Month == 7), plot(Temp, Wind, main = "July"))
with(subset(airquality, Month == 8), plot(Temp, Wind, main = "August"))
with(subset(airquality, Month == 9), plot(Temp, Wind, main = "September"))
```

Exploratory graphs are used to do quick and dirty analyses, to summarize data. They allow to explore basic questions and hypotheses and perhaps rule them out. They suggest modeling strategies that will follow.

+ **Base plotting system** - plot is constructed piece-by-piece. Each construct in the plot is handled by separate piece of code. Plotting this way mirrors the thought process (I would like to add description here, little overlay there, ...)
+ **Lattice plotting system** - plot is constructed using single function call and all aspects must be specified at once. This allows for R to automatically calculate necessary spacings and font sizes. Once the plot is made, it cannot be changed.
+ **ggplot2 plotting system** - combines concepts from both previous systems, but uses independent implementation. For showcases of ggplot2 see *R Gallery*.

## Base plotting system

In order to create a plot using any of the plotting systems, one must first consider several things:

+ where will the plot go? Screen or file
+ How trhe plot will be used?
    + temporarily on the screen
    + presented in web browser
    + in the paper
    + in presentation
+ Is there large amount of data for the plot? Or just few points.
+ Is there a need to dynamically resize the plot?

Base plotting system is powerful and widely used for creating 2-D graphics. There are usually 2 phases.

1. Initializing a plot
1. Annotating (adding) to the plot

By calling a `plot(x, y)` or `hist(x)` function, graphic device is opened (if one is already open, it will be used) and new plot is drawn. There is pile of parameters to the `plot()` function and they are documented under `?par`.

An example of the plot using base system (with default settings for point shape, colors, ...):

```{r}
with(airquality, plot(Ozone, Wind))
```

The basic parameters to the `plot()` function are:

+ `pch` - plotting character. Default is open circle
+ `lty` - line type
+ `lwd` - line width
+ `col` - color (number, string or hex code) - use `colors()` to get color names
+ `xlab` - x-axis label
+ `ylab` - y-axis label

When plotting multiple plot into single picture, function `par()` is used to setup parameters of the graphic device. All parameters valid for `plot()` function are accepted by `par()` and are taken as global. They can be overriden by individual plots. 

+ `las` - the orientation of the axis labels
+ `bg` - background color
+ `mar` - margin size
+ `oma` - outer margin size (default 0 for all sides)
+ `mfrow` - number of plots per row, column (plots are filled row-wise)
+ `mfcol` - number of plots per row, column (plots are filled column-wise)

Calling function `par()` with just a name of the parameter gives its default value.

```{r}
par("lty", "col")
```

### Base plotting functions

+ `plot()` - scatterplot or other type of plot base on the class of objects being plotted
+ `lines()` - add lines to plot. Given a vector of x and y values, this function connects the dots.
+ `points()` - add points to the plot
+ `text()` - add text labels to the plot on given position
+ `title()` - add annotation to the plot, axis, margin
+ `mtext()` - add arbitrary text to the margins
+ `axis()` - add axis ticks/labels

To add regression line to the plot, first calculate it with `lm()` function and then draw it as a line.

```{r}
# Draw all points with type = "n".
# This will only calculate dimensions, but not draw the plot itself.
with(airquality, plot(Wind, Temp, xlab = "", type = "n"))
with(subset(airquality, Month == 5), points(Wind, Temp, xlab = "", pch = 5))
with(subset(airquality, Month != 5), points(Wind, Temp, xlab = "", pch = 1))
text(x = 1.2, y = 3.2, labels = "Some text")
title(xlab = "The winds ...")
mtext(text = "clever note in margin")
legend("topright", pch = c(1, 5), legend = c("Other months", "May"))
model <- lm(Temp ~ Wind, airquality)
abline(model)
```


## Graphic devices

TBD

## Lattice plotting system

This system is good for plotting highdimensional data and making many plots at once. Similar to base plotting system with `mfrow` set to matrix, lattice plotting system is optimized for such plotting.

To use lattice plotting functions, load `lattice` package. To control plotting into grid, load `grid` package. Functions from `grid` package are usually not called directly.

Few main functions from `lattice` package

+ `xyplot()` - main function for plotting scatterplots
+ `bwplot()` - box-and-whiskers` plots (boxplot)
+ `histogram()`
+ `stripplot()` - like boxplot, but with points
+ `dotplot()` - plot dots on *violin strings*
+ `splom()` - scatterplot matrix (like `pairs()` in base plotting system)
+ `levelplot()`, `contourplot()` - for plotting image data

#### Function `xyplot()`

Lattice functions usually take the parameters in the form

`xyplot(y ~ x | f * g, data)`

The first parameter is *formula*, telling what will be on y-axis and x-axis. After the vertical bar, there is conditioning expression, where `*` states that there is interaction between conditional variable `f` and `g`. The second parameter is the data that the plotting function is working on. If no data used, first data frame from parent frame is used.

```{r}
library(lattice)
xyplot(Ozone ~ Wind, data = airquality, col = "black")
```

To draw the same graph, but for each month separately, I need first to convert Month variable to factor. Then use the `xyplot()` with conditioning excpression.

```{r}
airquality <- transform(airquality, Month = factor(Month))
p <- xyplot(Ozone ~ Wind | Month, data = airquality, col = "black", layout = c(5, 1))
# At this point, no plot is printed yet. The next line sends it to the graphic device
print(p)
```

The `layout` parameter controls how many plots will be in row (column).

There is one very important aspect in which lattice package behaves differently than base plotting system. In base plotting system, plots are drawn directly to graphic device. Lattice functions return an object of class **trellis**. The print methods for lattice functions actually print the object to the graphic device. On the command line, trellis objects are auto-printed so that the graphic appears directly on the screen.

Lattice functions have a **panel function**  which controls what happens inside each panel of the plot. There is default panel functions, but custom one can be supplied. Panel functions receive x, y of the data points in their panel.

```{r}
set.seed(1)
x <- rnorm(100)
f <- rep(0:1, each = 50)
y <- x + f - f * x + rnorm(100, sd = 0.5)
f <- factor(f, labels = c("Group 1", "Group 2"))
xyplot(y ~ x | f)
# and using custom panel function
xyplot(y ~ x | f,
       # define custom panel function that ...
       panel = function(x, y, ...) {
         # ... first, calls default panel function 
         panel.xyplot(x, y, ...)
         # ... second,creates line at median
         panel.abline(h = median(y), lty = 2)
         # ... third, add regression line
         panel.lmline(x, y, lty = 3)
})
```

**Note:** Functions cannot be mixed between different plotting systems.

## GGPLOT2 plotting system

**ggplot2// is implementation of *Grammar of Graphics* by *Leland Wilkinson*, which represents an abstraction of graphic ideas and objects and provokes to think about graphics in terms of *verbs*, *nouns* and *adjectives*. This allows for *theory* of graphics in which new graphic objects can be built.

Basic componenets of `ggplot2`:

+ data frame - if not specified, `ggplot2` will pick from workspace
+ aesthetic mapping - describing how data are presented in terms of color, shape, size, ...
+ geoms - specific objects that could be put into plot (additional points, lines, shapes, ..)
+ facets - for conditional plots (in grid)
+ stats - statistical transformations (smoothing, binning, quantiles)
+ scales - what scale an aesthetic map uses (different color or shape for factor levels, e.g. Male = blue, Female = green)
+ coordinate system

Building plot in `ggplot2` system (not using the basic `qplot()` function) consists of putting layers:

1. plot the data
1. overlay a summary
1. metadata and annotation

```{r}
library(ggplot2)
str(mpg)
```

To draw basic plot in ggplot2 system:

```{r}
qplot(displ, hwy, data = mpg)
```

As mentioned before, one of the aspects in ggplot is **aesthetics**. To control it, modify parameters designated for this purpose. E.g. color aesthetics will be controlled by value of `drv` factor variable, ggplo2 will automatically generate legend.

```{r}
qplot(displ, hwy, data = mpg, color = drv)
```

The second aspect of ggplot2 system is controlling of **geom** features. E.g. add smooth regression line into the data. The gray zone around the line means 90% confidence interval.

```{r}
qplot(displ, hwy, data = mpg, geom = c("point", "smooth"), method = "lm")
```

When only one variable is passed to the `qplot()` function, it will plot histogram automatically.

```{r}
qplot(hwy, data = mpg, binwidth = 1)
```

Or, when `fill` is assigned with some variable, values of that variable will be used to calculate distribution within given column.

```{r}
qplot(hwy, data = mpg, fill = drv, binwidth = 1)
```

#### Facets

To support drawing of multiple plots in a grid, ggplot2 functions use `facets` parameter. To draw separate plots in grid for each value of some variable do:

```{r}
qplot(displ, hwy, data = mpg, fill = drv, facets = . ~ drv)
```

In the facets expression, the left-hand side is the variable that will be put as columns. The right-hand side then gives rows. The `.` sign stands for *use no variable*, the there will be one row. E.g. `fl ~ drv` will make matrix of combinations.

#### Example of creating a plot with layering

First, the basic plot created with `qplot()` function

```{r}
qplot(displ, hwy, data = mpg,
      facets = . ~ drv,
      geom = c("point", "smooth"),
      method = "lm")
```

The same plot, build with ggplot2's more low level functions, allows to create it in layers.

```{r}
# data frame is mpg
head(mpg[1, ])
# draw data first, and say what x and y axis will be using the aesthetics
gplot <- ggplot(mpg, aes(displ, hwy))
# take a look on the gplot's graphic data
summary(gplot)
```

At this point, `gplot` cannot be printed because it has no layers! Simply, ggplot2 does not know what I want to draw. I need to *add* geom objects that shall be printed. E.g. to add points, literaly 'ADD' geom aspect 'point' to the plot.

```{r}
gplot <- gplot + geom_point()
summary(gplot)
```

I can add more geom aspects with dedicated functions. E.g. smooth regression line and facets based on values of a variable.

```{r}
gplot <- gplot + geom_smooth(method = "lm") + facet_grid(. ~ drv)
summary(gplot)
```

At this point, there are 2 geom layers and I can print the plot to graphic device.

```{r}
print(gplot)
```

Another functions to modify ggplot2 plot are to add annotations

+ `xlab()`, `ylab()`, `labs()`, `ggtitle()`
+ All `geom_...()` functions have parameters to modify
+ For things that only make sense globally use `theme()` e.g. `theme(legend.position = "none")`
+ Two standard color themes - default `theme_gray()` and `theme_bw()`

In `geom_...()` functions, parameter controlling aesthetics could be assigned to constants or to variable. If variable is assigned, then given aesthetic will change based on the value of that variable.

See the difference:

```{r}
gplot <- ggplot(mpg, aes(displ, hwy))
gplot <- gplot + geom_point(color = "black")
print(gplot)
```


```{r}
gplot <- ggplot(mpg, aes(displ, hwy))
gplot <- gplot + geom_point(aes(color = drv))
print(gplot)
```

The aesthetic parameters of any `geom_...()` function could be changed using paraneters wrapped in `aes()` function.
